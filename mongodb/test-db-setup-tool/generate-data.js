const { MongoClient } = require("mongodb");

const MONGODB_URI = process.env.MONGODB_URI || "mongodb://localhost:27017";
const DATABASE_NAME = process.env.DB_NAME || "appdb";
const COLLECTION_NAME = process.env.COLLECTION_NAME || "users";
const TOTAL_DOCUMENTS = parseInt(process.env.TOTAL_DOCS || "1000000");
const BATCH_SIZE = parseInt(process.env.BATCH_SIZE || "1000");

const firstNames = [
  "James",
  "Mary",
  "John",
  "Patricia",
  "Robert",
  "Jennifer",
  "Michael",
  "Linda",
  "William",
  "Elizabeth",
  "David",
  "Barbara",
  "Richard",
  "Susan",
  "Joseph",
  "Jessica",
  "Thomas",
  "Sarah",
  "Charles",
  "Karen",
  "Christopher",
  "Nancy",
  "Daniel",
  "Lisa",
  "Matthew",
  "Betty",
  "Anthony",
  "Margaret",
  "Mark",
  "Sandra",
  "Donald",
  "Ashley",
  "Steven",
  "Kimberly",
  "Paul",
  "Emily",
  "Andrew",
  "Donna",
  "Joshua",
  "Michelle",
];

const lastNames = [
  "Smith",
  "Johnson",
  "Williams",
  "Brown",
  "Jones",
  "Garcia",
  "Miller",
  "Davis",
  "Rodriguez",
  "Martinez",
  "Hernandez",
  "Lopez",
  "Wilson",
  "Anderson",
  "Thomas",
  "Taylor",
  "Moore",
  "Jackson",
  "Martin",
  "Lee",
  "Thompson",
  "White",
  "Harris",
  "Sanchez",
  "Clark",
  "Ramirez",
  "Lewis",
  "Robinson",
  "Walker",
  "Young",
  "Allen",
  "King",
  "Wright",
  "Scott",
  "Torres",
  "Nguyen",
  "Hill",
  "Flores",
  "Green",
  "Adams",
];

const cities = [
  "New York",
  "Los Angeles",
  "Chicago",
  "Houston",
  "Phoenix",
  "Philadelphia",
  "San Antonio",
  "San Diego",
  "Dallas",
  "San Jose",
  "Austin",
  "Jacksonville",
  "Fort Worth",
  "Columbus",
  "Charlotte",
  "San Francisco",
  "Indianapolis",
  "Seattle",
  "Denver",
  "Washington",
  "Boston",
  "El Paso",
  "Nashville",
  "Detroit",
  "Oklahoma City",
  "Portland",
  "Las Vegas",
  "Memphis",
  "Louisville",
  "Baltimore",
  "Milwaukee",
  "Albuquerque",
  "Tucson",
  "Fresno",
  "Sacramento",
];

const departments = [
  "Engineering",
  "Sales",
  "Marketing",
  "Design",
  "Product",
  "Operations",
  "Analytics",
  "HR",
  "Finance",
  "Legal",
  "Customer Support",
  "Research",
  "Quality Assurance",
];

const occupations = [
  "Software Engineer",
  "Designer",
  "Manager",
  "Analyst",
  "Product Manager",
  "Sales Director",
  "Marketing Manager",
  "Developer",
  "Data Scientist",
  "DevOps Engineer",
  "QA Engineer",
  "Business Analyst",
  "Project Manager",
  "Consultant",
  "Architect",
  "Scrum Master",
];

function randomElement(array) {
  return array[Math.floor(Math.random() * array.length)];
}

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function randomFloat(min, max) {
  return parseFloat((Math.random() * (max - min) + min).toFixed(2));
}

function generateEmail(firstName, lastName, id) {
  const domains = [
    "example.com",
    "test.com",
    "demo.org",
    "sample.net",
    "practice.io",
  ];
  return `${firstName.toLowerCase()}.${lastName.toLowerCase()}${id}@${randomElement(
    domains
  )}`;
}

function generateDocument(id) {
  const firstName = randomElement(firstNames);
  const lastName = randomElement(lastNames);
  const createdAt = new Date(
    Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)
  );
  const lastLogin = new Date(
    Date.now() - randomInt(0, 90 * 24 * 60 * 60 * 1000)
  );

  return {
    _id: id,
    name: `${firstName} ${lastName}`,
    firstName,
    lastName,
    email: generateEmail(firstName, lastName, id),
    age: randomInt(22, 65),
    city: randomElement(cities),
    department: randomElement(departments),
    occupation: randomElement(occupations),
    salary: randomInt(50000, 200000),
    rating: randomFloat(3.0, 5.0),
    score: randomInt(60, 100),
    isActive: Math.random() > 0.2,
    isVerified: Math.random() > 0.15,
    createdAt,
    lastLogin,
    tags: Array.from(
      { length: randomInt(2, 5) },
      () => `tag${randomInt(1, 50)}`
    ),
    metadata: {
      version: randomInt(1, 5),
      source: randomElement(["web", "mobile", "api", "batch"]),
      region: randomElement(["US", "EU", "APAC", "LATAM"]),
    },
  };
}

async function generateBatch(client, startId, batchSize) {
  const db = client.db(DATABASE_NAME);
  const collection = db.collection(COLLECTION_NAME);

  const documents = [];
  for (let i = 0; i < batchSize; i++) {
    documents.push(generateDocument(startId + i));
  }

  await collection.insertMany(documents, { ordered: false });
  return documents.length;
}

async function main() {
  console.log("🚀 Starting data generation...");
  console.log(`📊 Configuration:`);
  console.log(`   Database: ${DATABASE_NAME}`);
  console.log(`   Collection: ${COLLECTION_NAME}`);
  console.log(`   Total Documents: ${TOTAL_DOCUMENTS.toLocaleString()}`);
  console.log(`   Batch Size: ${BATCH_SIZE.toLocaleString()}`);
  console.log(`   MongoDB URI: ${MONGODB_URI}`);
  console.log("");

  const client = new MongoClient(MONGODB_URI);

  try {
    await client.connect();
    console.log("✅ Connected to MongoDB");

    const db = client.db(DATABASE_NAME);
    const collection = db.collection(COLLECTION_NAME);

    const existingCount = await collection.countDocuments();
    if (existingCount > 0) {
      console.log(
        `⚠️  Collection already has ${existingCount.toLocaleString()} documents`
      );
      console.log(
        "   Delete existing collection? (use --drop flag or manually delete)"
      );
    }

    const startTime = Date.now();
    let inserted = 0;
    const batches = Math.ceil(TOTAL_DOCUMENTS / BATCH_SIZE);

    for (let batch = 0; batch < batches; batch++) {
      const batchStartId = existingCount + batch * BATCH_SIZE + 1;
      const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_DOCUMENTS - inserted);

      await generateBatch(client, batchStartId, currentBatchSize);
      inserted += currentBatchSize;

      const progress = ((inserted / TOTAL_DOCUMENTS) * 100).toFixed(1);
      const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
      const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

      process.stdout.write(
        `\r📝 Progress: ${inserted.toLocaleString()}/${TOTAL_DOCUMENTS.toLocaleString()} ` +
          `(${progress}%) | ${rate} docs/sec | ${elapsed}s`
      );
    }

    console.log("\n");
    const finalCount = await collection.countDocuments();
    const totalTime = ((Date.now() - startTime) / 1000).toFixed(2);

    console.log("✅ Data generation complete!");
    console.log(`   Total documents: ${finalCount.toLocaleString()}`);
    console.log(`   Time taken: ${totalTime}s`);
    console.log(
      `   Average rate: ${(finalCount / totalTime).toFixed(0)} docs/sec`
    );

    const stats = await db.stats();
    console.log(`\n📈 Database Statistics:`);
    console.log(
      `   Data size: ${(stats.dataSize / 1024 / 1024).toFixed(2)} MB`
    );
    console.log(
      `   Storage size: ${(stats.storageSize / 1024 / 1024).toFixed(2)} MB`
    );
    console.log(`   Indexes: ${stats.indexes}`);
    console.log(`   Collections: ${stats.collections}`);
  } catch (error) {
    console.error("❌ Error:", error);
    process.exit(1);
  } finally {
    await client.close();
    console.log("\n🔌 Connection closed");
  }
}

if (require.main === module) {
  main().catch(console.error);
}

module.exports = { generateDocument, generateBatch };
