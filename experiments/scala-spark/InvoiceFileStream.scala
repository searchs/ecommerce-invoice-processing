package invoice.platform.experimental

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.expr
import org.apache.spark.sql.streaming.Trigger

/**
 * Historical Scala/Spark implementation of the invoice-file streaming pattern.
 *
 * Preserved from the former invoice-processing-spark repository because it is
 * invoice-specific. Generic Spark teaching exercises from that repository were
 * intentionally not migrated here.
 */
object InvoiceFileStream {
  def main(args: Array[String]): Unit = {
    val inputPath = sys.env.getOrElse("INVOICE_INPUT_PATH", "input")
    val outputPath = sys.env.getOrElse("INVOICE_OUTPUT_PATH", "output")
    val checkpointPath = sys.env.getOrElse("INVOICE_CHECKPOINT_PATH", "invoice-checkpoint")

    val spark = SparkSession.builder()
      .master("local[3]")
      .appName("InvoiceFileStream")
      .config("spark.streaming.stopGracefullyOnShutdown", "true")
      .config("spark.sql.shuffle.partitions", "3")
      .getOrCreate()

    val raw = spark.readStream
      .format("json")
      .option("path", inputPath)
      .option("maxFilesPerTrigger", 1)
      .load()

    val exploded = raw.selectExpr(
      "InvoiceNumber",
      "CreatedTime",
      "StoreID",
      "PosID",
      "CustomerType",
      "PaymentMethod",
      "DeliveryType",
      "DeliveryAddress.City as DeliveryCity",
      "DeliveryAddress.State as DeliveryState",
      "DeliveryAddress.PinCode as DeliveryPinCode",
      "explode(InvoiceLineItems) as LineItem"
    )

    val flattened = exploded
      .withColumn("ItemCode", expr("LineItem.ItemCode"))
      .withColumn("ItemDescription", expr("LineItem.ItemDescription"))
      .withColumn("ItemPrice", expr("LineItem.ItemPrice"))
      .withColumn("ItemQty", expr("LineItem.ItemQty"))
      .withColumn("TotalValue", expr("LineItem.TotalValue"))
      .drop("LineItem")

    val query = flattened.writeStream
      .format("json")
      .option("path", outputPath)
      .option("checkpointLocation", checkpointPath)
      .outputMode("append")
      .queryName("invoice-flattening")
      .trigger(Trigger.ProcessingTime("1 minute"))
      .start()

    query.awaitTermination()
  }
}
