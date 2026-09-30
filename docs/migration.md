# Invoice repository consolidation

## Canonical repository

`ecommerce-invoice-processing` is the canonical base and will eventually be renamed to `invoice-platform` after repository-name coupling in CI/deployment links has been checked.

## Source: invoice-processing-spark

### Preserved
- invoice-specific Spark file-streaming/line-item flattening pattern;
- the historical Scala implementation as an experiment;
- the conceptual separation of file ingestion, transformation and output.

### Not migrated
- duplicated invoice JSON fixtures already present in the canonical repository;
- generated Parquet files, `_SUCCESS` and `.crc` artefacts;
- generic word-count, store/join and unrelated teaching exercises;
- empty environment configuration files;
- obsolete build identity (`WordCounta Streama`).

Generic Spark lessons belong in `data-engineerings`, not inside the invoice product boundary.

## Source: customer-invoice-dashboard

The old repository is structurally based on the Next.js Learn/ACME dashboard course. It contributed useful learning around pagination, data fetching, forms and dashboard composition, but the implementation is not being copied or represented as original product UI.

Instead, `apps/dashboard/README.md` defines an original invoice-domain dashboard to be implemented against stable platform read models.

## Existing canonical code

The original PySpark/Kafka invoice processing under `KafkaStream`, `StreamFiles` and related folders remains in place for now. It should be refactored incrementally into the target boundaries as tests are added; this migration deliberately avoids a high-risk bulk move.

## Deletion readiness

After this consolidation is merged:

- `invoice-processing-spark` has no unique invoice-specific value that is not represented here or intentionally assigned to the generic data-engineering knowledge base;
- `customer-invoice-dashboard` may be removed because its course-derived source is intentionally not part of the product estate and its useful product requirements are represented by the rebuild specification.
