# Iot Use Case for processing the device data

## PartitionKey

1. Device specific events: Ensures all events for a specific device go to the same partition, maintaining per-device ordering.
   Partition Key pattern is `{country}-{customerId}-{deviceId}`
2. Customer specific events: groups the events at the customer level when device-level granularity isn't needed.
   Partition Key pattern is `{country}-{customerId}-{deviceId}`
