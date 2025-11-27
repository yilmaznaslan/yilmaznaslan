# MongoDB lab for sharding

1. Start the MongoDB Docker instance

```bash
docker run -d \
  --name mongodb \
  -p 27018:27017 \
  mongo:7
```

2. Load synthetic ecommerce data (optional)

```bash
node blog/mongodb/data-generator/generate-ecommerce.js
```

Before sharding a collection, the database must be shard-enabled first
`
