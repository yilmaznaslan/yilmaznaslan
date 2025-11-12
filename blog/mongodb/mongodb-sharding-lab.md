# MongoDb lab for sharding

- First create a syntetic ecommerce dataset using the `node ../mongodb/data-generator/genereate-ecommerce.js`

You need to create:

- 1 Config Server Replicate Set(configReplSet)
- 2 Shard Replicate Sets
- 1 Mongos router

Each will be a seperate docker container


Create Docker Network so containers can talk to each other.
```bash
docker network create mongo-shard-net
```