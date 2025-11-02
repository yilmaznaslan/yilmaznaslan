# MongoDB Interview Questions

Comprehensive guide to MongoDB interview topics for backend developer positions.

## Core Concepts

### Document Model & Architecture

**Q: What is MongoDB and how does it differ from relational databases?**

- MongoDB is a NoSQL document database that stores data in flexible, JSON-like documents called BSON
- No fixed schema - documents can have different fields and structures
- Horizontal scaling through sharding
- Embedding vs referencing for relationships
- Uses collections (like tables) and documents (like rows)

**Q: Explain the document-based model in MongoDB and its advantages/disadvantages over relational databases.**

**Advantages:**

- Flexible schema - easy to evolve data structure
- Fast development - no complex joins or migrations
- Better performance for read-heavy workloads with proper indexing
- Horizontal scalability through sharding
- Native JSON support - maps well to application objects
- Good for unstructured or semi-structured data

**Disadvantages:**

- No ACID transactions across documents (before 4.0)
- Limited complex querying compared to SQL
- No JOIN operations - must use application-level joins
- Data duplication can occur
- Less mature tooling ecosystem
- Memory usage can be high for large documents

**Q: What is BSON and how does it differ from JSON?**

- BSON (Binary JSON) is the binary-encoded serialization format MongoDB uses
- More efficient than JSON - faster parsing, smaller size
- Supports additional data types: Date, ObjectId, Binary, Decimal128, etc.
- Type information is preserved

**Q: When would you choose MongoDB over a relational database?**

- Rapid prototyping and schema evolution
- Content management systems
- Real-time analytics and IoT data
- Mobile applications with flexible schemas
- Social networking features (users, posts, comments)
- Catalog data with varying attributes
- Logging and time-series data
- When horizontal scaling is a priority

## Data Modeling

**Q: How do you decide between embedding and referencing documents?**

**Embedding (one-to-one, one-to-few):**

- Use when related data is frequently accessed together
- Better read performance
- Atomic updates possible
- Documents stay under 16MB limit
- Examples: User address, comments on a post (few), tags

**Referencing (one-to-many, many-to-many):**

- Use when related data is large or accessed independently
- Avoids duplication
- Allows for growth without document size limits
- More flexible for relationships that change frequently
- Examples: User posts (many), products in orders

**Q: Explain the difference between normalized and denormalized data in MongoDB.**

- **Normalized**: Data split across collections using references (like relational DB)
- **Denormalized**: Related data embedded in same document
- MongoDB favors denormalization for read performance
- Trade-off: Faster reads vs. more complex updates and potential duplication

**Q: How would you model a one-to-many relationship in MongoDB?**

**Option 1: Embedding (small, bounded many)**

```javascript
{
  _id: "post123",
  title: "My Post",
  comments: [
    {user: "user1", text: "Great!"},
    {user: "user2", text: "Thanks"}
  ]
}
```

**Option 2: Referencing (large or unbounded many)**

```javascript
// Posts collection
{_id: "post123", title: "My Post"}

// Comments collection
{_id: "comment1", postId: "post123", user: "user1", text: "Great!"}
{_id: "comment2", postId: "post123", user: "user2", text: "Thanks"}
```

## Queries & Operations

**Q: Explain the difference between `find()`, `findOne()`, and `findOneAndUpdate()`.**

- `find()`: Returns a cursor with all matching documents
- `findOne()`: Returns a single document (first match) or null
- `findOneAndUpdate()`: Atomically finds and updates a document, returns the original or updated document

**Q: How do you perform complex queries in MongoDB?**

- Comparison operators: `$gt`, `$gte`, `$lt`, `$lte`, `$ne`, `$in`, `$nin`
- Logical operators: `$and`, `$or`, `$nor`, `$not`
- Element operators: `$exists`, `$type`
- Array operators: `$all`, `$elemMatch`, `$size`
- Regex for pattern matching
- Text search with `$text`
- Geospatial queries

**Q: What is an aggregation pipeline and when would you use it?**

- Pipeline of stages that process documents sequentially
- Stages: `$match`, `$group`, `$project`, `$sort`, `$limit`, `$lookup`, etc.
- Use for complex data transformations, analytics, reporting
- More powerful than simple queries for data processing

**Example:**

```javascript
db.orders.aggregate([
  { $match: { status: 'completed' } },
  { $group: { _id: '$customerId', total: { $sum: '$amount' } } },
  { $sort: { total: -1 } },
  { $limit: 10 },
]);
```

**Q: Explain `$lookup` in aggregation pipelines.**

- Performs left outer join between two collections
- Similar to SQL LEFT JOIN
- Can join with other collection to get related data
- Available from MongoDB 3.2+

**Q: How do you handle transactions in MongoDB?**

- Multi-document transactions available from MongoDB 4.0+
- ACID guarantees within a replica set or sharded cluster
- Use `startSession()` and `withTransaction()`
- Important for financial operations, inventory management
- Performance impact - should be used judiciously

## Indexing

**Q: What are indexes in MongoDB and why are they important?**

- Indexes improve query performance by creating sorted data structures
- Without indexes, MongoDB performs collection scans (slow)
- Default index on `_id` field
- Trade-off: Faster queries vs. storage space and slower writes

**Q: Explain different types of indexes in MongoDB.**

1. **Single Field Index**: On one field

   ```javascript
   db.users.createIndex({ email: 1 });
   ```

2. **Compound Index**: On multiple fields

   ```javascript
   db.users.createIndex({ name: 1, age: -1 });
   ```

3. **Multikey Index**: For array fields

   ```javascript
   db.posts.createIndex({ tags: 1 });
   ```

4. **Text Index**: For full-text search

   ```javascript
   db.articles.createIndex({ title: 'text', content: 'text' });
   ```

5. **Geospatial Index**: For location data

   ```javascript
   db.places.createIndex({ location: '2dsphere' });
   ```

6. **TTL Index**: Automatically delete documents after expiration

   ```javascript
   db.sessions.createIndex({ createdAt: 1 }, { expireAfterSeconds: 3600 });
   ```

7. **Partial Index**: Only indexes documents matching filter
   ```javascript
   db.users.createIndex({ email: 1 }, { partialFilterExpression: { active: true } });
   ```

**Q: How do compound indexes work and what is index prefix?**

- Compound index on multiple fields in specific order
- Can be used for queries on: first field, first two fields, etc. (prefix)
- Order matters: `{a: 1, b: 1, c: 1}` can be used for queries on `a`, `a+b`, `a+b+c`, but not `b` or `c` alone
- Follow ESR rule: Equality, Sort, Range fields

**Q: What is the ESR (Equality, Sort, Range) rule for index design?**

- Order fields in compound index: Equality → Sort → Range
- Improves index effectiveness
- Example: Query with `status: "active"` (equality), `sort by createdAt` (sort), `age > 25` (range)
- Index: `{status: 1, createdAt: 1, age: 1}`

**Q: How do you identify slow queries in MongoDB?**

- Enable profiling: `db.setProfilingLevel(2)`
- Check profiler collection: `db.system.profile.find()`
- Use `db.currentOp()` for active operations
- MongoDB Atlas Performance Advisor
- Explain plans: `db.collection.find().explain("executionStats")`

## Performance Optimization

**Q: What are common MongoDB performance optimization techniques?**

1. **Indexing**: Create proper indexes for frequently queried fields
2. **Query Optimization**: Use projections, limit results, avoid `$where`
3. **Read Preferences**: Read from secondaries for analytics workloads
4. **Connection Pooling**: Reuse connections, limit pool size
5. **Sharding**: Distribute data across multiple servers
6. **Denormalization**: Embed frequently accessed data
7. **Document Size**: Keep documents under 16MB, optimize schema
8. **Write Concerns**: Balance durability vs. performance
9. **Covered Queries**: Use indexes that include all requested fields
10. **Batch Operations**: Use `bulkWrite()` for multiple operations

**Q: Explain query execution plans and how to use them.**

- Use `explain()` to see how MongoDB executes a query
- Shows: execution stats, index usage, documents examined
- Helps identify missing indexes or inefficient queries
- Types: `queryPlanner`, `executionStats`, `allPlansExecution`

**Q: What is a covered query?**

- Query that can be satisfied entirely using an index
- No documents need to be examined
- Very fast - only index is accessed
- Requires all projected fields to be in the index

## Replication

**Q: What is replication in MongoDB and why is it used?**

- Replication maintains copies of data across multiple servers (replica set)
- Provides high availability - if primary fails, secondary takes over
- Data redundancy and disaster recovery
- Read scalability - can read from secondaries

**Q: Explain replica set architecture.**

- **Primary**: Handles all write operations
- **Secondaries**: Maintain copies of primary's data, can handle reads
- **Arbiter**: Votes in elections but doesn't store data
- Minimum: 1 primary + 2 secondaries (or 1 primary + 1 secondary + 1 arbiter)
- Automatic failover through elections

**Q: What is eventual consistency in MongoDB replica sets?**

- Secondaries may have slight lag behind primary
- Reads from secondaries may return slightly stale data
- Data will eventually become consistent across all replicas
- Trade-off for read scalability and availability

**Q: How do you achieve strong consistency in MongoDB?**

- Read from primary (`readPreference: "primary"`)
- Use write concern `{w: "majority"}` - waits for majority of nodes to acknowledge
- Read concern `"majority"` - only read data confirmed by majority
- Slower but guarantees strong consistency

**Q: Explain read preferences and write concerns.**

**Read Preferences:**

- `primary`: Read only from primary (default for writes)
- `primaryPreferred`: Prefer primary, fallback to secondary
- `secondary`: Read only from secondaries
- `secondaryPreferred`: Prefer secondary, fallback to primary
- `nearest`: Read from lowest latency member

**Write Concerns:**

- `{w: 1}`: Acknowledge write after primary confirms (default)
- `{w: "majority"}`: Wait for majority of nodes
- `{w: 2}`: Wait for 2 nodes
- `{j: true}`: Wait for journal commit (durability)

## Sharding

**Q: What is sharding in MongoDB and when would you use it?**

- Sharding distributes data across multiple machines (shards)
- Used when data exceeds single server capacity
- Horizontal scaling for large datasets and high throughput
- Each shard is a replica set

**Q: Explain sharding architecture components.**

1. **Shards**: Store data (replica sets)
2. **Config Servers**: Store cluster metadata and chunk mappings
3. **Mongos (Router)**: Routes queries to appropriate shards
4. **Shard Key**: Field(s) used to distribute data across shards

**Q: How do you choose a shard key?**

- Should have high cardinality (many unique values)
- Good write distribution - avoid hotspots
- Supports common query patterns
- Avoid monotonically increasing keys (can cause hot shards)
- Compound shard keys can help balance load

**Q: What are the consequences of a bad shard key?**

- **Hot Shards**: Uneven distribution, some shards overloaded
- **Jumbo Chunks**: Chunks that can't be split (all documents have same shard key value)
- **Poor Query Performance**: Queries may hit all shards instead of targeted ones
- **Manual Rebalancing**: May require manual intervention

**Q: Explain chunk splitting and balancing in sharding.**

- Data is divided into chunks (default 64MB)
- Chunks are distributed across shards
- When chunk grows too large, MongoDB splits it
- Balancer moves chunks between shards to maintain balance
- Configurable chunk size affects balance vs. migration overhead

## Transactions & ACID

**Q: Does MongoDB support ACID transactions?**

- Yes, multi-document transactions from MongoDB 4.0+
- ACID guarantees within a replica set
- ACID transactions in sharded clusters from MongoDB 4.2+
- Previously only single-document atomicity

**Q: Explain write atomicity in MongoDB.**

- Single-document writes are always atomic
- Multi-document transactions require explicit session
- Operations either fully succeed or fully fail (no partial writes)
- Isolation levels: snapshot isolation for transactions

**Q: When should you use transactions vs. embedded documents?**

- **Transactions**: Cross-document updates that must be atomic (e.g., transfer money between accounts)
- **Embedded Documents**: Related data that's frequently accessed together (e.g., user profile)
- Transactions have performance overhead, use sparingly
- Prefer embedding for better performance when possible

## Security

**Q: How do you secure MongoDB?**

1. **Authentication**: Enable authentication, use strong passwords
2. **Authorization**: Role-based access control (RBAC)
3. **Network Security**: Bind to specific IPs, use VPN/firewalls
4. **Encryption**: Encrypt data at rest and in transit (TLS/SSL)
5. **Auditing**: Enable auditing for compliance
6. **Regular Updates**: Keep MongoDB updated

**Q: Explain MongoDB authentication mechanisms.**

- SCRAM-SHA-1, SCRAM-SHA-256 (default)
- LDAP authentication
- x.509 certificate authentication
- Kerberos authentication
- MongoDB Atlas uses AWS IAM for authentication

**Q: What are MongoDB roles and privileges?**

- Built-in roles: `read`, `readWrite`, `dbAdmin`, `userAdmin`, etc.
- Custom roles: Define specific privileges
- Privileges: actions like `find`, `insert`, `update`, `remove`
- Resource: database and collection scope

## Monitoring & Operations

**Q: How do you monitor MongoDB performance?**

- MongoDB Atlas monitoring dashboard
- `mongostat` and `mongotop` command-line tools
- Profiler for slow queries
- `db.serverStatus()` for server metrics
- Third-party tools: Datadog, New Relic, Prometheus
- Key metrics: OPS, connections, memory, CPU, replication lag

**Q: What are important MongoDB metrics to monitor?**

1. **Operations**: Read/write operations per second
2. **Connections**: Active connections, connection pool usage
3. **Memory**: Working set, resident memory
4. **CPU**: Usage percentage
5. **Replication Lag**: Delay between primary and secondaries
6. **Disk I/O**: Read/write throughput
7. **Index Hit Ratio**: Percentage of queries using indexes
8. **Cache Hit Ratio**: WiredTiger cache efficiency

**Q: How do you backup and restore MongoDB?**

- **mongodump**: Creates binary export of database
- **mongorestore**: Restores from mongodump backup
- **mongoexport**: Exports data in JSON/CSV format
- **mongoimport**: Imports from JSON/CSV format
- Point-in-time recovery using oplog
- MongoDB Atlas automated backups

**Q: Explain the oplog in MongoDB.**

- Operations log - a capped collection that records all write operations
- Used for replication - secondaries read from primary's oplog
- Used for point-in-time recovery
- Limited size - oldest entries removed when full
- Only on replica set primary

## Comparison Questions

**Q: Compare MongoDB with PostgreSQL/MySQL.**

| Aspect         | MongoDB                                 | PostgreSQL               |
| -------------- | --------------------------------------- | ------------------------ |
| Data Model     | Document (NoSQL)                        | Relational (SQL)         |
| Schema         | Flexible                                | Fixed                    |
| Joins          | Application-level                       | Native SQL joins         |
| Transactions   | Multi-doc (4.0+)                        | Full ACID support        |
| Scaling        | Horizontal (sharding)                   | Vertical + read replicas |
| Query Language | JavaScript-like                         | SQL                      |
| ACID           | Per-document (always), Multi-doc (4.0+) | Full ACID                |

**Q: When would you choose MongoDB vs. other NoSQL databases?**

- **MongoDB**: General-purpose document store, good balance of features
- **Cassandra**: Write-heavy, high availability, eventual consistency
- **Redis**: In-memory caching, real-time applications
- **Elasticsearch**: Full-text search, log analytics
- **DynamoDB**: AWS-native, serverless, predictable performance
- **CouchDB**: Offline-first, sync capabilities

## Practical Questions

**Q: Design a schema for a social media application using MongoDB.**

- Users collection: profile, authentication info
- Posts collection: content, author reference, timestamps, embedded comments (if few)
- Comments collection: separate if many per post
- Follows collection: user relationships
- Indexes: user email (unique), post author, timestamp sorting
- Embedding: User profile info in posts for denormalized reads
- References: User IDs for relationships

**Q: How would you optimize a slow query in MongoDB?**

1. Use `explain()` to analyze query plan
2. Check if index is being used
3. Create appropriate index if missing
4. Optimize query - use projections, add filters
5. Consider denormalization if frequently accessed together
6. Review document structure
7. Check for collection scans
8. Monitor query performance after changes

**Q: Explain how you would handle a MongoDB migration.**

- Use migration scripts (Mongoose migrations, custom scripts)
- Version control schema changes
- Blue-green deployment for zero downtime
- Data transformation scripts for schema updates
- Testing on staging first
- Rollback plan
- Monitor during and after migration

**Q: How do you handle schema changes in MongoDB?**

- MongoDB doesn't enforce schema, but application does
- Backward compatibility: Add new fields as optional
- Migration scripts for data transformation
- Gradually migrate documents
- Update application code to handle both old and new formats
- Remove old fields after migration complete

## Advanced Topics

**Q: What is change streams in MongoDB?**

- Real-time notifications of data changes
- Available from MongoDB 3.6+
- Can watch specific collections, databases, or entire deployment
- Uses aggregation pipeline for filtering
- Useful for: cache invalidation, triggers, real-time analytics

**Q: Explain MongoDB Atlas and its benefits.**

- Managed MongoDB service by MongoDB Inc.
- Automatic backups, monitoring, scaling
- Multi-cloud support (AWS, Azure, GCP)
- Global clusters, data residency
- Security features built-in
- No database administration overhead

**Q: What is MongoDB Compass?**

- GUI tool for MongoDB
- Query interface, schema analysis
- Explain plans visualization
- Index management
- Data import/export
- Performance monitoring

## Best Practices

**Q: What are MongoDB best practices?**

1. **Schema Design**: Embed frequently accessed data, reference for large/independent data
2. **Indexing**: Index frequently queried fields, follow ESR rule
3. **Queries**: Use projections, limit results, avoid full collection scans
4. **Write Concerns**: Balance durability with performance based on use case
5. **Connection Pooling**: Reuse connections, don't create new for each request
6. **Document Size**: Keep under 16MB, avoid large arrays
7. **Naming**: Use descriptive collection and field names
8. **Monitoring**: Set up alerts for key metrics
9. **Backups**: Regular backups, test restore procedures
10. **Security**: Enable authentication, use encrypted connections

**Q: What common mistakes should be avoided in MongoDB?**

- Creating too many indexes (slows writes)
- Not creating indexes for frequently queried fields
- Storing large arrays that grow unbounded
- Not using connection pooling
- Reading from secondary when consistency is required
- Using `$where` with JavaScript (slow)
- Ignoring write concerns for critical data
- Not monitoring performance
- Poor shard key selection
- Not planning for growth

## Coding Questions

**Q: Write a query to find all users with age > 25 and salary < 100000.**

```javascript
db.users.find({
  age: { $gt: 25 },
  salary: { $lt: 100000 },
});
```

**Q: Write an aggregation pipeline to get average salary by department.**

```javascript
db.users.aggregate([
  {
    $group: {
      _id: '$department',
      avgSalary: { $avg: '$salary' },
      count: { $sum: 1 },
    },
  },
  { $sort: { avgSalary: -1 } },
]);
```

**Q: Write a query to update user salary and return the updated document.**

```javascript
db.users.findOneAndUpdate({ email: 'user@example.com' }, { $inc: { salary: 5000 } }, { returnDocument: 'after' });
```

**Q: How would you find users who have not logged in for 30 days?**

```javascript
const thirtyDaysAgo = new Date();
thirtyDaysAgo.setDate(thirtyDaysAgo.getDate() - 30);

db.users.find({
  lastLogin: { $lt: thirtyDaysAgo },
});
```
