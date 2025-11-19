# Kafka

## Key Kafka Properties

- Decoupled
- Scalability:
- Fault Tolerance: Running multiple Brokers(nodes) across region

## Key Concepts

### Broker

Brokers hols the **queue**

### Topic

In kafka a topic is a collection of event where it is replicated and partioned across multiple nodes

Note:

> - Topics are logical grouping of messages whereas partitions physical grouping on disc.
> - Topics for organizing data, partitions are for scaling data.

### Partition

- Each topic is plit into partitions, which are ordered, immutable sequence of messages
- Partitions allow parallesim (multiple consumers readin simultaenously)
- Each message in a partition has a unique offset(its poisiton in the partition)

- Partitions are fixed and configured per topic (e.g., 3, 6, 12, 20, 100)

- Key in the messages determines which partigion a message goes to

### Consumer Groups

In Kafka each event is guarenteed to be processed by only one consumer in the group.

### Message / Record

- Headers(http headers, key value pairs)
- Key
- Value
- Timestamp

## Flow

3. Message is appended to partition via append only log file

4. Consumer doesn#t read messaged by 'name' or 'ID'. Consumers consume message by specifiyng the offset of the last message they have read.
   `Give me the next message after offset X in partition Y.`. In Spring Kafka, this offset tracking is automatic via

   ```
   @KafkaListener(...)
   public void handle(String message){
    ...
   }
   ```

   Consumer prediocly `commits` the offset
   Kafka mainteins the latest offset bein red

5. Leader Replicca is responsofle for all th read and write requests to the partition. The assignment of the leader replica is managed centrally by some cluster controller which assures the each partitions leader replicate is effectively distrubted across the cluster to balance the load.

Partition is automatically deteremend by Kafka using the key's hash, so producer does not specify the partitio number directly
The `Follower Replica` s may

6. Consumer REtries
   Kafka does not support the consumer retries ot of the box, but AWS SQS supports. If you need **consumer retries** you may want to consider sqs's.

## Performance Optimizations

1. Batch messages in producer
2. Compress messages in producer

3) PartitionKey or partitioning Stratgey```

## Retention Policy

Two settings

1. Retention.ms(default 7 days)

2) retention.bytes(Default 1GB)

## Difference Between Kafka and other Message Brokers(ActiveMQ or Rabbit MQ)

- Kafka messages are saved, in traditiona lmessages queue messages are deleted as soon as they are consumed. In Kafka messages are still there even though they are consumed
