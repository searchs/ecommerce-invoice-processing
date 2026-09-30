# Invoice Platform Architecture

## Bounded contexts

### Ingestion
Accept invoice files/events and publish a stable integration event. Sources may be files, sockets, APIs or Kafka producers, but downstream processing should not depend on the source-specific transport.

### Processing
Validate the contract, flatten nested line items, derive processing metadata and emit normalised invoice/line-item records. Spark/PySpark remains the batch/stream processing technology used by the existing implementation.

### Read model
Expose processed invoice information in a query-friendly form for reporting and dashboard use. The dashboard should consume an API/read model rather than importing processing concerns directly.

## Logical flow

```text
+----------------+
| Invoice source |
+-------+--------+
        |
        v
+-------------------+
| Ingestion adapter |
+---------+---------+
          |
          v
+-------------------+
| Kafka / event bus |
+---------+---------+
          |
          v
+-----------------------+
| Invoice processor     |
| - contract validation |
| - flatten line items  |
| - transformations     |
+----------+------------+
           |
       +---+---+
       |       |
       v       v
 processed   analytical/
 events      read storage
                |
                v
          +-------------+
          | Dashboard   |
          | / API       |
          +-------------+
```

## Contract strategy

`contracts/invoice.schema.json` is the canonical schema for the historical invoice fixture shape. Future contract changes should be additive where possible and versioned when breaking.

## Migration strategy

The repository currently contains older learning-oriented folders. They are not being renamed in bulk because that would create a large low-value diff. New work should follow the target boundaries; existing code can be moved behind those boundaries when it is touched and tested.

## Non-goals

- preserving every historical Spark exercise
- retaining generated Parquet/CRC output
- reproducing the Next.js Learn ACME dashboard
- treating sample invoice datasets as production data
