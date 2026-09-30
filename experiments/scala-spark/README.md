# Scala / Spark invoice experiment

This folder preserves the invoice-specific part of the former `invoice-processing-spark` repository.

The original repository also contained generic Spark teaching exercises, empty environment files, generated Parquet output, CRC files and duplicated invoice fixtures. Those were intentionally not migrated.

`InvoiceFileStream.scala` demonstrates the historical pattern:

1. stream JSON invoice files;
2. explode nested `InvoiceLineItems`;
3. flatten line-item fields;
4. write normalised JSON with a checkpoint.

The experiment uses environment variables for input/output/checkpoint paths instead of hard-coded paths. It is retained for comparison and learning; the canonical production direction is the processor under `services/invoice-processor/` as that boundary is developed.
