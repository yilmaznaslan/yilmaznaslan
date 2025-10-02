# What is Database sharding

Database sharding is a type of database partitioning that separates very large databases into smaller, faster, more
easily managed parts called data shards.

Each shard is a separate database in its own right that holds a portion of the total data. For example, one shard may
hold all the data for customers whose last names start with A-M while another shard may hold all the data for customers
whose last names start with N-Z.

The concept of database sharding is usually applied in distributed databases, where data is spread across multiple
servers or locations. Here's how sharding works:

1) **Data Splitting**: Sharding involves breaking up a big database into smaller chunks, with each chunk called a shard.
   Each
   shard has the same schema but holds its unique set of data.

2) **Shard Key**: Each shard is defined by a shard key. The shard key determines how the data is distributed across the
   different shards. The shard key could be a customer ID, a geographic location, or some other data field. A
   well-chosen
   shard key evenly distributes data and load, optimizing query performance. The sharding key must be chosen carefully.
   A poor choice of sharding key may result in uneven data distribution,
   leading
   to hotspots.

3) **Distribution**: Shards can be distributed across multiple servers. This allows a database to grow beyond the 
   hardware
   limits of one machine.

### Benefits of sharding include:

- Improved Performance: Since each shard is smaller, it is faster to search and manage.

- Scalability: Sharding allows for horizontal scaling, often called scale-out. As data grows, more shards or servers can
  be added.

- Reliability and Redundancy: If one shard goes down, the other shards can still function, and only a portion of the
  data
  is impacted.

### Disadvantages of sharding;

However, sharding comes with its challenges:

- **Cross-shard operations** can be complex. For example, performing a query that aggregates order data across all
  continents
  will require the application to query all shards and then consolidate the results.

- **Resharding** can also be challenging. In the above example, if the user base in Asia grows much faster than in other
  continents, the Asia shard might start to experience performance issues. You might have to split the Asia shard into
  multiple shards, which requires careful planning and execution.

- **Maintaining consistency** across all shards can be difficult.


Despite these challenges, sharding is an effective strategy for scaling databases and is used by many large,
high-traffic applications.