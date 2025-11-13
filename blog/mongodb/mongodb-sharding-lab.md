# MongoDb lab for sharding

- 1. Start the mongodb docker instance

```bash
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  mongo:7
```

- 2. First create a syntetic ecommerce dataset using the `node /data-generator/genereate-ecommerce.js`

You need to create:

- 1 Config Server Replicate Set(configReplSet)
- 2 Shard Replicate Sets
- 1 Mongos router

Each will be a seperate docker container

Create Docker Network so containers can talk to each other.

```bash
docker network create mongo-shard-net
```
