# Database scalability considering both RDBMS and NoSQL

Database scalability is the ability of a database system to handle an increased amount of work or to be readily
enlarged. It refers to the capability to increase the capacity of the existing hardware or software by adding resources.

Scalability in databases can be categorized mainly into two types:

1. **Vertical scalability (scale-up)**: This refers to adding more resources such as CPUs, memory to an existing
   machine. For example, you can add more power to your server to accommodate more transactions. The limitation of
   vertical
   scaling is that there's an upper limit to the amount of resources you can add to a single system.

2. **Horizontal scalability (scale-out)**: This refers to adding more machines into your pool of resources which work in
   unison. This strategy helps to distribute the database over many servers, which can then handle high traffic loads.

### 1) Scaling Relational database management system (RDBMS)

Scalability is often a challenge with relational database systems because they are designed to run on a single server.
As a result, they often require vertical scaling when workload increases, which can be costly and has limitations.
Moreover, ACID transactions (Atomicity, Consistency, Isolation, Durability) are hard to maintain over distributed
systems.

Here are some methods commonly used for horizontal scaling in RDBMSs:

- **Sharding**: Sharding is a method where data is partitioned across multiple databases. Each partition is called a
  shard and
  can be placed on a different server, spreading the load. However, sharding can be complex to implement and manage.
  Queries that span multiple shards can be particularly challenging.

- **Replication**: In replication, the same data is copied and kept on multiple databases. This is a good way to
  distribute
  read load across multiple servers. There are multiple ways to do replication
    - **Master-Slave Replication**: The master serves reads and writes, replicating writes to one or more slaves, which
      serve only reads. Slaves can also
      replicate to additional slaves in a tree-like fashion. If the master goes offline, the system can continue to
      operate in read-only mode until a slave is promoted to a master or a new master is provisioned.
    - **Master-Master Replication**: Both masters serve reads and writes and coordinate with each other on writes. If
      either master goes down, the system can continue to operate with both reads and writes.

- **Caching**: Caching is another way to scale RDBMSs. Frequently accessed data can be stored in a cache for quick
  retrieval,
  reducing the load on the database server.

Despite these strategies, horizontal scaling can be challenging with RDBMS due to its ACID properties (Atomicity,
Consistency, Isolation, Durability), and it often requires application-level changes.

### 2) Scalability in NoSQL:

NoSQL databases are built to provide excellent horizontal scalability. They are designed from the ground up to run
across many servers, making them a good fit for big data and real-time applications. They sacrifice ACID properties for
performance and horizontal scalability.

Document databases (like MongoDB) and wide-column stores (like Cassandra) distribute data across many servers for high
traffic loads.

Key-value stores (like Redis) hold the data in memory, which provides fast access but is usually limited by the amount
of memory.

Graph databases (like Neo4j) are optimized to represent complex relationships with many foreign keys or many-to-many
relationships.

It's important to note that NoSQL databases have a variety of data models that can be more flexible than SQL's tabular
relation model. This makes NoSQL databases excellent for a wide variety of applications.

In conclusion, the choice between SQL or NoSQL databases (and how you scale them) really depends on the specific
requirements of your project. Both types of databases can be an excellent choice in the right circumstances.


