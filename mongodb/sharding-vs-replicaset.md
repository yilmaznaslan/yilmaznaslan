# Replica Set vs Sharding

| Feature                    | Replica Set                             | Sharding                                       |
| -------------------------- | --------------------------------------- | ---------------------------------------------- |
| Purpose                    | High availability, failover, redundancy | Horizontal scaling, split data across machines |
| Data                       | Every node has ALL the data             | Each shard has ONLY PART of the data           |
| Writes                     | Only the primary can write              | Writes go to whichever shard owns the data     |
| Reads                      | Primary or secondaries                  | Across multiple shards                         |
| Storage                    | Duplicated on all nodes                 | Split across nodes                             |
| Improves write throughput? | ❌ No                                   | ✅ Yes                                         |
| Improves data capacity?    | ❌ No                                   | ✅ Yes                                         |

## Key point

A **replica set** = same data, multiple copies
A **sharded cluster** = data split into many replica sets
In fact

> A **Sharded cluster** is made up of multiple replica sets. Each shard is a replica set !

Example:

```bash
Shard 1 → Replica Set (Primary + Secondaries)
Shard 2 → Replica Set (Primary + Secondaries)
Shard 3 → Replica Set (Primary + Secondaries)
```
