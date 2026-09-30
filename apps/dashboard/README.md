# Invoice Dashboard — rebuild specification

The previous `customer-invoice-dashboard` repository was based on the Next.js Learn/ACME course application. Its source is not being copied into the canonical platform.

This folder defines the replacement dashboard boundary for an original invoice product UI.

## Primary views

### Overview
- total invoices processed
- gross invoice value
- processing success/failure counts
- recent ingestion activity
- payment-method breakdown
- store/location breakdown

### Invoices
- paginated invoice list
- search by invoice number, store, POS or customer-card reference
- filter by payment method, delivery type, store and date range
- processing/status indicator
- invoice detail with flattened line items

### Processing health
- latest ingestion/processing events
- failed/rejected contract records
- lag/checkpoint visibility where available
- retry/reprocess action only through an explicit backend command

## Architectural rule

The dashboard must consume a stable API/read model. It must not connect directly to Spark jobs, Kafka internals or processing storage merely because those resources exist in the same repository.

## Rebuild criteria

A future implementation should be considered a replacement for the old course-derived dashboard only when it has:

1. original product/domain navigation and copy;
2. typed API contracts derived from platform read models;
3. invoice-specific loading/error/empty states;
4. tests for core invoice list/detail behaviour;
5. no ACME/Next.js Learn placeholder identity.
