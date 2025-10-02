# Interview Questions - Architecture & System Design

- **Question**: Great! Could you share some specific examples where you chose one architecture over the other? What
  were the factors that guided your decision?
    - Answer: In the e-commerce company, we had a small, tightly-knit team and the application was relatively simple.
      The monolithic approach allowed us to move quickly without getting caught up in the complexities of distributed
      systems. In contrast, at the fintech startup, the system needed to handle a wide range of services with differing
      loads and requirements. A microservices architecture allowed us to scale and deploy services independently, and
      enabled us to use different technologies that were best suited for each service.


- **Question 2)**: imagine you're tasked with designing a new payment service at Klarna. The service would have to
  handle a large volume of transactions, needs to interact with other services like user management and risk assessment,
  and should be flexible to comply with regulations of different countries. What kind of architectural approach would
  you take and why?


- **Question 3)** How would you monitor the health and performance of your service in a production environment? What
  metrics would you focus on?
    - For monitoring, I'd use a combination of logging, metrics, and distributed tracing.
    - For metrics, we'd look at things like request rates, error rates, and response times.
    - Additionally, we'd monitor system metrics such as CPU usage, memory usage, and network IO. Tools like Logstash for
      logging, Prometheus for metrics, and Jaeger for distributed tracing could be used.
    - I would also set up alerts to notify us of any anomalies in these metrics.


- **Question 4)** Suppose you're designing the database for a new application at Klarna. What considerations would
  you keep in mind to ensure the database is scalable?


- **Question 5)** Could you explain how you might use indexing to improve the performance of a database?
    - Indexing can greatly improve query performance by reducing the amount of data that needs to be examined for a
      query. An index is a data structure that allows the database to find the data associated with a query condition
      more quickly. Without an index, the database has to scan through all the data to find matching rows, which can be
      slow for large tables.
    - However, indexes also have a cost. They take up storage space and slow down write operations because the index
      also needs to be updated whenever data is inserted, updated, or deleted. Therefore, it's important to find a
      balance and only create indexes for columns that are frequently used in query conditions.


- **Question 6)** What is the importance of ACID properties in a database?
    - ACID stands for **Atomicity, Consistency, Isolation, and Durability**. These properties are important for ensuring
      reliable processing of transactions.
    - **Atomicity** ensures that a series of database operations within a transaction are treated as a single unit of
      work. Either all the operations are performed, or none of them are. This prevents the database from ending up in
      an inconsistent state due to partially completed transactions.
    - **Consistency** ensures that a transaction brings the database from one valid state to another. This means that
      all data written to the database must follow all defined rules, including constraints, cascades, and triggers.
    - **Isolation** ensures that concurrent transactions do not interfere with each other. The effects of a transaction
      should not be visible to others until the transaction is committed.
    - **Durability** guarantees that once a transaction is committed, it will remain in the system permanently. Even in
      the event of a system failure, the committed transaction data will not be lost.

- **Question 7)** Which databases enforces ACID and which databases not
    - Most traditional relational databases (RDBMS) adhere to ACID properties to ensure reliable transaction processing.
      These include:
        - MySQL: MySQL provides full ACID compliance when using the InnoDB storage engine (which is the default engine
          in recent
          versions of MySQL).
        - PostgreSQL: PostgreSQL is fully ACID-compliant and is often chosen for applications where data integrity is
          paramount.
        - Oracle Database: Oracle also fully supports ACID properties.
        - SQL Server: Microsoft's SQL Server is fully ACID-compliant.

    - On the other hand, many NoSQL databases loosen ACID requirements to achieve other benefits such as improved
      performance, horizontal scalability, and flexibility in handling unstructured data. However, there's quite a bit
      of variation among
      NoSQL databases:
        - MongoDB: While MongoDB was originally non-ACID compliant, it has added support for multi-document ACID
          transactions
          since version 4.0.

        - Cassandra: Cassandra prioritizes availability and partition tolerance over consistency (following the CAP
          theorem) and
          doesn't fully support ACID properties, though it does offer tunable consistency.

        - DynamoDB: Amazon's DynamoDB does not support ACID across multiple tables. However, it provides ACID guarantees
          for
          transactions that operate on items within a single table.

    - Please note that the decision to use a specific type of database depends on the requirements of the specific
      use case
      you are dealing with. Some applications may require the strict consistency model provided by ACID-compliant
      databases,
      while others may prefer the scalability and flexibility offered by NoSQL databases.

<details>
<summary>Deployment & Scaling</summary>

- **What are the pro's and con's of auto scaling ?**

    - Auto-scaling, the process of automatically adjusting the amount of computational resources based on the current
      demand, offers several benefits and also has a few downsides. Let's look at both:

    - Pros of Auto-Scaling:
        - Cost-Effective: With auto-scaling, you only use and pay for the resources you need. When demand is low, fewer
          resources
          are used, which can result in cost savings.
        - Performance: Auto-scaling can help maintain the performance of your application during peak usage times by
          automatically
          adding more resources.
        - Availability: If a server fails, auto-scaling can replace it automatically, increasing the availability of
          your
          application.
        - Resource Optimization: Auto-scaling allows your system to better handle resource-intensive tasks by allocating
          more
          resources when needed and scaling back when they're not.

    - Cons of Auto-Scale:
        - Complexity: Implementing an auto-scaling strategy can be complex. You'll need to define when to scale up and
          scale down,
          which can be challenging.
        - Cost Control: While auto-scaling can save costs, if not monitored closely, it can also lead to unexpected
          costs. For
          example, if a faulty service continually consumes resources, auto-scaling can keep adding more resources,
          leading to a
          high bill.
        - Cold Start: Depending on the system, new instances might take some time to start up and be ready to serve
          requests (
          known as a cold start). This could potentially impact the performance of your application.
        - State Management: If your application maintains state on the instances, auto-scaling could be a problem as new
          instances
          won't share the same state as existing ones. For auto-scaling to work effectively, the application should be
          stateless.

</details>

<details>
<summary>Can you explain indexing in database</summary>

<details>
<summary>asd</summary>

Hlle
</details>
- **Can you explain indexing in database**?

    Certainly! Indexing is a data structure technique to efficiently retrieve records from the database files based on some
    attributes on which the indexing has been done. Indexing in databases is similar to what we see in books. In a book, an
    index allows you to go directly to the pages containing a certain topic. In the same way, a database index allows the
    database program to find data rows much more quickly and with fewer disk I/O operations, without scanning through the
    entire table.
    
    There are different types of indexing techniques, but the most commonly used are:
    
    Single-column Indexes: An index that is created on a single column of a table.
    
    Unique Indexes: This is the same as single-column indexes but, as the name suggests, all the column values are unique.
    
    Composite (concatenated) Indexes: An index that is created on multiple columns of a table.
    
    Covering Indexes: An index that includes all of the columns referenced in a query. Instead of having to go back to the
    table to get the data, the database can get all the needed data directly from the index itself, leading to a significant
    performance improvement.
    
    Let's consider a simple example:
    
    Suppose you have a "users" table in a database with a million rows. If you want to find a user whose user_id is 123456,
    without an index the database would have to go through the whole table row by row, which could take a lot of time.
    
    But if there's an index on the user_id column, the database can use this index to locate the data more quickly, similar
    to how you'd look up a word in a book's index. The database can essentially skip directly to the location where user_id
    is 123456 without scanning the entire table.
    
    The advantage of using indexes comes with a trade-off, though: while they speed up read queries, they slow down write
    operations (INSERT, UPDATE, DELETE), because each time we modify the data, the index also needs to be updated. Indexes
    also require storage space. So it's important to strike the right balance and use indexes judiciously.

<details>
<summary>asd</summary>

Hlle
</details>

</details>

## Notes

Focusing on the customer is a key element in successful system design, and it's crucial to ask the right questions
to understand the customer's needs, behavior, and pain points. Here are some questions you might consider asking:

- **Who are the primary users of this system?** Understanding who you're designing for is the first step in creating a
  user-centered design. The users could be external customers, internal employees, or another system. Each type of user
  will have different needs and expectations.

What are the key tasks the users need to perform? Understanding the users' goals will help you design a system that
supports them in achieving these goals effectively and efficiently.

What are the pain points in the current system or process? If the new system is replacing or improving an existing one,
it's helpful to understand the current pain points to make sure they are addressed in the new design.

How do the users expect to interact with the system? The interaction could be via a web interface, a mobile app, an API,
etc. Understanding this will help you design the right interfaces and interactions.

- **What are the performance expectations from a user's perspective?** Performance is a key aspect of the user
  experience.
  For example, how fast do users expect the system to respond? Are there peak times when the system needs to handle a
  higher load?

- **What happens if something goes wrong?** Understanding the consequences of system failures from a user's perspective
  can help in designing robust error handling and recovery mechanisms. For instance, how should the system handle
  failures? How should it communicate errors to users?

Are there any specific security or privacy concerns from a user's perspective? Security and privacy are crucial aspects
of user trust. Understanding any specific concerns in these areas will help you design a system that respects and
safeguards user data.

Remember, the aim of these questions is to keep the customer at the heart of your design process, making sure their
needs, expectations, and concerns are addressed in your solution.