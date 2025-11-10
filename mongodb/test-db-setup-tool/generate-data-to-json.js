// Script: generate-data-to-json.js
// Usage: node generate-data-to-json.js --total=1000 --batch=100 --output=out.json --ndjson=true
const fs = require("fs");
const path = require("path");
const { once } = require("events");

const args = process.argv.slice(2);
const getArg = (name) => {
  const a = args.find((s) => s.startsWith(`--${name}=`));
  return a ? a.split("=")[1] : undefined;
};

const TOTAL_DOCUMENTS = parseInt(
  getArg("total") || process.env.TOTAL_DOCS || "10000000",
  10
);
const BATCH_SIZE = parseInt(
  getArg("batch") || process.env.BATCH_SIZE || "1000",
  10
);
const OUTPUT_FILE =
  getArg("output") || process.env.OUTPUT_FILE || "dataset.json";
const NDJSON =
  (getArg("ndjson") || process.env.NDJSON || "false")
    .toString()
    .toLowerCase() === "true";

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
    Date.now() - randomInt(0, 365 * 5 * 24 * 60 * 60 * 1000)
  );
  const lastLogin = new Date(
    Date.now() - randomInt(0, 365 * 1 * 24 * 60 * 60 * 1000)
  );

  // addresses
  const addresses = Array.from({ length: randomInt(1, 3) }, (_, i) => ({
    id: `${id}-addr-${i + 1}`,
    label: randomElement(["home", "work", "other"]),
    street: `${randomInt(100, 9999)} ${randomElement([
      "Oak",
      "Maple",
      "Pine",
      "Cedar",
      "Elm",
      "Willow",
    ])} ${randomElement(["St", "Ave", "Blvd", "Road", "Ln"])}`,
    city: randomElement(cities),
    postalCode: `${randomInt(10000, 99999)}`,
    country: randomElement(["US", "GB", "DE", "FR", "ES", "IT", "CA", "AU"]),
    location: {
      lat: parseFloat(randomFloat(-90, 90).toFixed(6)),
      lon: parseFloat(randomFloat(-180, 180).toFixed(6)),
    },
    updatedAt: new Date(
      Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)
    ),
  }));

  // devices
  const devices = Array.from({ length: randomInt(0, 4) }, (_, i) => ({
    id: `${id}-dev-${i + 1}`,
    type: randomElement(["mobile", "desktop", "tablet"]),
    os: randomElement(["iOS", "Android", "Windows", "macOS", "Linux"]),
    lastSeen: new Date(Date.now() - randomInt(0, 90 * 24 * 60 * 60 * 1000)),
    metadata: {
      model: randomElement(["X1", "Pro", "Mini", "Max", "S"]),
      appVersion: `${randomInt(1, 3)}.${randomInt(0, 9)}.${randomInt(0, 99)}`,
    },
  }));

  // sessions / activity
  const sessions = Array.from({ length: randomInt(0, 6) }, (_, i) => ({
    sessionId: `${id}-sess-${i + 1}-${randomInt(1000, 9999)}`,
    startedAt: new Date(Date.now() - randomInt(0, 90 * 24 * 60 * 60 * 1000)),
    durationSec: randomInt(10, 60 * 60),
    deviceId: devices.length
      ? devices[randomInt(0, devices.length - 1)].id
      : null,
    actions: Array.from({ length: randomInt(1, 10) }, () => ({
      type: randomElement([
        "view",
        "click",
        "purchase",
        "search",
        "login",
        "logout",
      ]),
      target: randomElement([
        "home",
        "product",
        "checkout",
        "profile",
        "search",
      ]),
      ts: new Date(Date.now() - randomInt(0, 90 * 24 * 60 * 60 * 1000)),
    })),
  }));

  // orders
  const orders = Array.from({ length: randomInt(0, 6) }, (_, oi) => {
    const items = Array.from({ length: randomInt(1, 6) }, (_, li) => ({
      sku: `SKU-${randomInt(1000, 9999)}`,
      title: `${randomElement([
        "Widget",
        "Gadget",
        "Thing",
        "Item",
      ])} ${randomInt(1, 100)}`,
      qty: randomInt(1, 5),
      price: randomInt(100, 20000) / 100,
      tags: Array.from(
        { length: randomInt(0, 4) },
        () => `tag${randomInt(1, 50)}`
      ),
    }));

    const total = items.reduce((s, it) => s + it.qty * it.price, 0);
    return {
      orderId: `${id}-ord-${oi + 1}-${randomInt(1000, 9999)}`,
      createdAt: new Date(
        Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)
      ),
      status: randomElement([
        "created",
        "paid",
        "shipped",
        "delivered",
        "cancelled",
      ]),
      items,
      shippingAddress: addresses[randomInt(0, addresses.length - 1)],
      payment: {
        method: randomElement(["card", "paypal", "bank_transfer"]),
        paid: Math.random() > 0.2,
        amount: parseFloat(total.toFixed(2)),
        currency: "USD",
      },
      metadata: {
        promo: Math.random() > 0.8 ? `PROMO-${randomInt(10, 99)}` : null,
      },
    };
  });

  // payment methods
  const paymentMethods = Array.from({ length: randomInt(0, 3) }, (_, i) => ({
    id: `${id}-pm-${i + 1}`,
    type: randomElement(["card", "paypal", "bank"]),
    last4: `${randomInt(1000, 9999)}`,
    expiry: `${randomInt(1, 12).toString().padStart(2, "0")}/${randomInt(
      22,
      30
    )}`,
    preferred: i === 0,
  }));

  // friends
  const friends = Array.from({ length: randomInt(0, 8) }, (_, i) => ({
    id: `${id}-f-${i + 1}`,
    name: `${randomElement(firstNames)} ${randomElement(lastNames)}`,
    connectedAt: new Date(
      Date.now() - randomInt(0, 365 * 3 * 24 * 60 * 60 * 1000)
    ),
    closeness: randomFloat(0, 1),
  }));

  // large bio (repeat short phrase to simulate larger text)
  const bio = Array.from(
    { length: randomInt(1, 6) },
    () =>
      "Enthusiastic user of our platform. Loves testing features and providing feedback."
  ).join(" ");

  // experiments / flags
  const experiments = {
    newCheckout: Math.random() > 0.95,
    recommendationsV2: Math.random() > 0.8,
    betaTester: Math.random() > 0.98,
  };

  return {
    _id: id,
    uuid: `user-${id}`,
    name: `${firstName} ${lastName}`,
    profile: {
      firstName,
      lastName,
      fullName: `${firstName} ${lastName}`,
      email: generateEmail(firstName, lastName, id),
      phone: `${randomInt(100, 999)}-${randomInt(100, 999)}-${randomInt(
        1000,
        9999
      )}`,
      age: randomInt(18, 85),
      gender: randomElement([
        "male",
        "female",
        "non-binary",
        "prefer_not_to_say",
      ]),
      createdAt,
      lastLogin,
    },
    contact: {
      primaryCity: randomElement(cities),
      addresses,
      social: {
        twitter: `@${firstName.toLowerCase()}${randomInt(1, 999)}`,
        linkedIn: `${firstName}-${lastName}-${randomInt(100, 999)}`,
      },
    },
    preferences: {
      newsletter: Math.random() > 0.5,
      notifications: {
        email: Math.random() > 0.4,
        sms: Math.random() > 0.9,
        push: Math.random() > 0.6,
      },
      locale: randomElement(["en-US", "en-GB", "de-DE", "fr-FR", "es-ES"]),
      timezone: randomElement([
        "UTC",
        "Europe/Berlin",
        "America/New_York",
        "Asia/Tokyo",
      ]),
    },
    devices,
    sessions,
    orders,
    paymentMethods,
    friends,
    roles: Array.from({ length: randomInt(1, 3) }, () =>
      randomElement(["user", "admin", "moderator", "support"])
    ),
    permissions: {
      canPurchase: Math.random() > 0.1,
      canReview: Math.random() > 0.2,
      canEditProfile: true,
    },
    analytics: {
      lifetimeValue: parseFloat(
        orders.reduce((s, o) => s + (o.payment?.amount || 0), 0).toFixed(2)
      ),
      avgSessionDurationSec:
        sessions.length === 0
          ? 0
          : Math.round(
              sessions.reduce((s, se) => s + se.durationSec, 0) /
                sessions.length
            ),
      lastSeen: lastLogin,
      purchaseCount: orders.length,
    },
    bio,
    tags: Array.from(
      { length: randomInt(1, 6) },
      () => `tag${randomInt(1, 100)}`
    ),
    attachments: Array.from({ length: randomInt(0, 4) }, (_, i) => ({
      url: `https://cdn.example.com/${id}/att-${i + 1}.bin`,
      size: randomInt(100, 5_000_000),
      mime: randomElement([
        "image/png",
        "application/pdf",
        "image/jpeg",
        "text/plain",
      ]),
      uploadedAt: new Date(
        Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)
      ),
    })),
    experiments,
    metadata: {
      version: randomInt(1, 10),
      source: randomElement(["web", "mobile", "api", "batch"]),
      region: randomElement(["US", "EU", "APAC", "LATAM"]),
      createdAt,
    },
  };
}

async function writeBatchToStream(stream, docs, options = {}) {
  const { ndjson = false, isLastBatch = false, isFirstDoc = false } = options;
  for (let i = 0; i < docs.length; i++) {
    const doc = docs[i];
    let chunk;
    if (ndjson) {
      chunk = JSON.stringify(doc) + "\n";
    } else {
      // pretty JSON objects inside an array: indent each object (human readable)
      const json = JSON.stringify(doc, null, 2);
      if (isFirstDoc && i === 0) {
        // first document in the entire file — no leading comma
        chunk = json;
      } else {
        // subsequent documents need a comma separator and a newline
        chunk = ",\n" + json;
      }
      // if it's the last doc of the last batch, append a newline for readability
      if (isLastBatch && i === docs.length - 1) chunk += "\n";
    }

    if (!stream.write(chunk, "utf8")) {
      await once(stream, "drain");
    }
  }
}

async function main() {
  console.log("🚀 Generating dataset to file");
  console.log(`   Total documents: ${TOTAL_DOCUMENTS}`);
  console.log(`   Batch size: ${BATCH_SIZE}`);
  console.log(`   Output file: ${OUTPUT_FILE}`);
  console.log(`   NDJSON mode: ${NDJSON}`);

  // ensure output directory exists
  const dir = path.dirname(OUTPUT_FILE);
  if (dir && dir !== ".") {
    fs.mkdirSync(dir, { recursive: true });
  }

  const stream = fs.createWriteStream(OUTPUT_FILE, { flags: "w" });

  const batches = Math.ceil(TOTAL_DOCUMENTS / BATCH_SIZE);
  let inserted = 0;
  const startTime = Date.now();
  let firstDocWritten = false;

  try {
    if (!NDJSON) {
      // start array (pretty entries will follow)
      if (!stream.write("[\n", "utf8")) {
        await once(stream, "drain");
      }
    }

    for (let batch = 0; batch < batches; batch++) {
      const batchStartId = batch * BATCH_SIZE + 1;
      const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_DOCUMENTS - inserted);
      const docs = [];
      for (let i = 0; i < currentBatchSize; i++) {
        docs.push(generateDocument(batchStartId + i));
      }

      const isLastBatch = batch === batches - 1;
      await writeBatchToStream(stream, docs, {
        ndjson: NDJSON,
        isLastBatch,
        isFirstDoc: !firstDocWritten,
      });

      firstDocWritten = true;
      inserted += currentBatchSize;

      const progress = ((inserted / TOTAL_DOCUMENTS) * 100).toFixed(1);
      const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
      const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

      process.stdout.write(
        `\r📝 Progress: ${inserted}/${TOTAL_DOCUMENTS} (${progress}%) | ${rate} docs/sec | ${elapsed}s`
      );
    }

    if (!NDJSON) {
      // close array with trailing newline
      if (!stream.write("\n]\n", "utf8")) {
        await once(stream, "drain");
      }
    }

    // finish stream
    await new Promise((resolve) => stream.end(resolve));

    const totalTime = ((Date.now() - startTime) / 1000).toFixed(2);
    console.log("\n✅ Dump complete");
    console.log(`   File: ${OUTPUT_FILE}`);
    console.log(`   Documents: ${inserted}`);
    console.log(`   Time: ${totalTime}s`);
    console.log(`   Avg rate: ${(inserted / totalTime).toFixed(0)} docs/sec`);
  } catch (err) {
    console.error("❌ Error writing file:", err);
    process.exit(1);
  }
}

if (require.main === module) {
  main().catch((err) => {
    console.error(err);
    process.exit(1);
  });
}

module.exports = { generateDocument };
