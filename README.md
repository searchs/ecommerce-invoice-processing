# Invoice Platform

A consolidated engineering project for invoice ingestion, event streaming, Spark-based processing, schema contracts and dashboard-facing read models.

## Purpose

This repository evolves the original PySpark/Kafka invoice-processing work into a coherent platform case study. It keeps the strongest authored processing code while separating product concerns from older learning experiments.

## Target architecture

```text
Invoice sources
      |
      v
Kafka / event ingestion
      |
      v
Invoice processor (PySpark / Spark)
      |
      +--> normalised events / processed records
      |
      +--> analytical storage / read models
                      |
                      v
                 Dashboard/API
```

## Repository direction

```text
apps/dashboard/                  Product dashboard boundary and rebuild specification
contracts/                       Versioned invoice/event schemas
services/invoice-processor/      Canonical processing service (incremental migration)
infrastructure/                  Kafka, Kafka Connect, Docker and observability assets
experiments/scala-spark/         Preserved Scala/Spark invoice experiments
docs/                            Architecture, ADRs and migration notes
```

The existing `KafkaStream`, `StreamFiles` and related directories are retained for now and will be refactored incrementally into the target structure rather than moved blindly.

## Consolidation provenance

This repository is the canonical successor for the invoice-processing work previously spread across:

- `ecommerce-invoice-processing` (canonical base)
- `invoice-processing-spark` (selected Scala/Spark invoice experiments)
- `customer-invoice-dashboard` (requirements only; the old Next.js Learn/ACME implementation is not being transplanted)

See `docs/migration.md` for the curation decisions.

## Principles

- keep invoice contracts explicit and versioned
- treat Kafka messages as integration contracts, not ad-hoc JSON
- separate ingestion, transformation and dashboard/read-model concerns
- do not commit generated Spark output, CRC files or duplicate datasets
- rebuild the dashboard around this domain instead of preserving course scaffolding
