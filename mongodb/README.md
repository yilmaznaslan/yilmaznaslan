# Mongodb Cheetsheet

## General commands

`show dbs`
`show collections`
`use <dbname>`
`db.dropDatabase()`

## CRUD Operations

### Insert

- `db.insertOne({name:"yilmaz"})`
- `db.users.insertMany([{name:"asd"},{name:"anan"}])`

### Practice Queries with sample-users.csv

The enhanced CSV includes fields for practicing various MongoDB operations:

**Fields available:**

- `name`, `email`, `city`, `occupation`, `department` - Strings for text search and filtering
- `age`, `salary`, `rating`, `score` - Numbers for comparison, aggregation, sorting
- `isActive`, `isVerified` - Booleans for boolean queries
- `createdAt`, `lastLogin` - Dates (imported as strings; convert with `new Date()` after import)
- 13 records with varied values for grouping and aggregation

**Query Types You Can Practice:**

1. **Basic Queries:**

   - `db.users.find({age: {$gt: 30}})`
   - `db.users.find({isActive: true})`
   - `db.users.find({salary: {$lte: 90000}})`
   - `db.users.find({address.street : ""})`

2. **Text Search:**

   - `db.users.find({name: /John/})`
   - `db.users.find({city: "New York"})`

3. **Comparison Operators:**

   - `$gt`, `$gte`, `$lt`, `$lte`, `$ne`, `$in`
   - `db.users.find({rating: {$gte: 4.5}})`
   - `db.users.find({department: {$in: ["Design","Sales"]}}).skip(1).limit(10)`
   - `db.users.find({city: {$ne: "Los Angeles"}})`
   - `db.users.find({salary: {$gt:10000, $lt: 100000}})`
   - `db.users.find({salary: {$gt:10000, $lt: 100000}, age: {$gte: 20}})`

4. **Logical Operators:**

   - `$and`, `$or`, `$nor`, `$not`
   - `db.users.find({$and: [{age: {$gt: 25}}, {salary: {$lt: 100000}}]})`
   - `db.users.find({age: {$not: {$gte: 10}}})`

5. **Aggregation:**

   - `$group`, `$sum`, `$avg`, `$min`, `$max`, `$count`
   - `db.users.aggregate([{$group: {_id: "$department", avgSalary: {$avg: "$salary"}}}])`

6. **Sorting:**

   - `db.users.find().sort({salary: -1})`
   - `db.users.find().sort({name: 1, age: -1})`

7. **Projection:**

   - `db.users.find({}, {name: 1, email: 1, salary: 1})`
   - `db.users.find({},{name: 0})`

8. **Limit and Skip:**

   - `db.users.find().limit(5).skip(2)`

9. **Update Operations:**

   - `db.users.updateOne({name: "John Doe"}, {$set: {salary: 100000}})`
   - `db.users.updateMany({department: "Engineering"}, {$inc: {salary: 5000}})`

10. **Date Queries (after converting date strings):**

    - Convert dates: `db.users.updateMany({}, [{$set: {createdAt: {$dateFromString: {dateString: "$createdAt"}}}}])`
    - Then: `db.users.find({createdAt: {$gte: ISODate("2023-01-01")}})`

11. **Distinct Values:**

    - `db.users.distinct("department")`
    - `db.users.distinct("city")`

12. **Count:**

    - `db.users.countDocuments({isActive: true})`
    - `db.users.countDocuments({salary: {$gt: 100000}})`

13. **Complext Queries**
    - `db.users.find({$expr: {$gte: ["$age","$score"]}})`

## Importing CSV Files

```bash
brew install mongodb-database-tools
```

**Verify installation:**

```bash
mongoimport --version
```

MongoDB provides `mongoimport` tool to import CSV files directly into collections:

```bash
mongoimport --db <database_name> --collection <collection_name> --type csv --headerline --file <path/to/file.csv>
```

Options:

- `--db`: Database name
- `--collection`: Collection name
- `--type csv`: Specify CSV format
- `--headerline`: Use first line as field names
- `--file`: Path to CSV file
- `--drop`: Drop collection before importing (optional)
- `--ignoreBlanks`: Ignore blank fields (optional)

Example:

```bash
mongoimport --db mydb --collection users --type csv --headerline --file ./users.csv
```

If CSV doesn't have headers, specify fields:

```bash
mongoimport --db mydb --collection users --type csv --fields name,email,age --file ./users.csv
```
