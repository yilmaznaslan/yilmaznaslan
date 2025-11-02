# Mongodb Commands

`show dbs`
`show collections`

`use <dbname>`

`db.dropDatabase()`

`db.mycollection.insertOne({})`
`db.mycollection.insertMany([{field1: "value1"}, {field2: "value2"}])`

## Importing CSV Files

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
