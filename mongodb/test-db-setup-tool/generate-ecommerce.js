const { MongoClient } = require('mongodb');

const MONGODB_URI = process.env.MONGODB_URI || 'mongodb://localhost:27017';
const DATABASE_NAME = process.env.DB_NAME || 'ecommerce';
const BATCH_SIZE = parseInt(process.env.BATCH_SIZE || '1000');

const TOTAL_USERS = parseInt(process.env.TOTAL_USERS || '1000000');
const TOTAL_PRODUCTS = parseInt(process.env.TOTAL_PRODUCTS || '10000');
const TOTAL_ORDERS = parseInt(process.env.TOTAL_ORDERS || '500000');
const TOTAL_CATEGORIES = parseInt(process.env.TOTAL_CATEGORIES || '50');
const TOTAL_REVIEWS = parseInt(process.env.TOTAL_REVIEWS || '200000');

const firstNames = [
  'James',
  'Mary',
  'John',
  'Patricia',
  'Robert',
  'Jennifer',
  'Michael',
  'Linda',
  'William',
  'Elizabeth',
  'David',
  'Barbara',
  'Richard',
  'Susan',
  'Joseph',
  'Jessica',
  'Thomas',
  'Sarah',
  'Charles',
  'Karen',
  'Christopher',
  'Nancy',
  'Daniel',
  'Lisa',
  'Matthew',
  'Betty',
  'Anthony',
  'Margaret',
  'Mark',
  'Sandra',
  'Donald',
  'Ashley',
  'Steven',
  'Kimberly',
  'Paul',
  'Emily',
  'Andrew',
  'Donna',
  'Joshua',
  'Michelle',
];

const lastNames = [
  'Smith',
  'Johnson',
  'Williams',
  'Brown',
  'Jones',
  'Garcia',
  'Miller',
  'Davis',
  'Rodriguez',
  'Martinez',
  'Hernandez',
  'Lopez',
  'Wilson',
  'Anderson',
  'Thomas',
  'Taylor',
  'Moore',
  'Jackson',
  'Martin',
  'Lee',
  'Thompson',
  'White',
  'Harris',
  'Sanchez',
  'Clark',
  'Ramirez',
  'Lewis',
  'Robinson',
  'Walker',
  'Young',
  'Allen',
  'King',
  'Wright',
  'Scott',
  'Torres',
  'Nguyen',
  'Hill',
  'Flores',
  'Green',
  'Adams',
];

const cities = [
  'New York',
  'Los Angeles',
  'Chicago',
  'Houston',
  'Phoenix',
  'Philadelphia',
  'San Antonio',
  'San Diego',
  'Dallas',
  'San Jose',
  'Austin',
  'Jacksonville',
  'Fort Worth',
  'Columbus',
  'Charlotte',
  'San Francisco',
  'Indianapolis',
  'Seattle',
  'Denver',
  'Washington',
  'Boston',
  'El Paso',
  'Nashville',
  'Detroit',
];

const states = [
  'CA',
  'TX',
  'FL',
  'NY',
  'PA',
  'IL',
  'OH',
  'GA',
  'NC',
  'MI',
  'NJ',
  'VA',
  'WA',
  'AZ',
  'MA',
  'TN',
  'IN',
  'MO',
  'MD',
  'WI',
];

const productCategories = [
  'Electronics',
  'Clothing',
  'Home & Garden',
  'Sports & Outdoors',
  'Books',
  'Toys & Games',
  'Health & Beauty',
  'Automotive',
  'Food & Beverages',
  'Pet Supplies',
  'Office Supplies',
  'Jewelry',
  'Musical Instruments',
  'Baby Products',
  'Furniture',
  'Computer & Accessories',
  'Cell Phones',
  'Appliances',
  'Tools',
  'Gaming',
];

const productBrands = [
  'TechCorp',
  'StyleBrand',
  'HomeEssentials',
  'SportMax',
  'BookWorld',
  'ToyLand',
  'BeautyPlus',
  'AutoPro',
  'FoodFresh',
  'PetCare',
  'OfficePro',
  'JewelryElite',
  'MusicPro',
  'BabySafe',
  'FurnitureCo',
  'CompTech',
  'PhoneMax',
  'AppliancePro',
  'ToolMaster',
  'GameZone',
];

const productAdjectives = [
  'Premium',
  'Professional',
  'Deluxe',
  'Standard',
  'Advanced',
  'Basic',
  'Ultra',
  'Pro',
  'Elite',
  'Classic',
  'Modern',
  'Vintage',
  'Smart',
  'Digital',
];

const productNouns = [
  'Widget',
  'Device',
  'Tool',
  'System',
  'Kit',
  'Set',
  'Pack',
  'Bundle',
  'Unit',
  'Model',
  'Edition',
  'Version',
  'Series',
  'Collection',
  'Item',
];

const orderStatuses = ['pending', 'processing', 'shipped', 'delivered', 'cancelled', 'refunded'];
const paymentMethods = ['credit_card', 'debit_card', 'paypal', 'apple_pay', 'google_pay', 'bank_transfer'];
const shippingMethods = ['standard', 'express', 'overnight', 'international'];
const subscriptionTypes = ['monthly', 'yearly', 'quarterly', 'none'];

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
  const domains = ['gmail.com', 'yahoo.com', 'outlook.com', 'hotmail.com', 'example.com'];
  return `${firstName.toLowerCase()}.${lastName.toLowerCase()}${id}@${randomElement(domains)}`;
}

function generatePhoneNumber() {
  return `+1${randomInt(200, 999)}${randomInt(200, 999)}${randomInt(1000, 9999)}`;
}

function generateAddress() {
  return {
    street: `${randomInt(1, 9999)} ${randomElement([
      'Main',
      'Oak',
      'Park',
      'Maple',
      'Elm',
      'Cedar',
      'Pine',
      'First',
      'Second',
      'Third',
    ])} St`,
    city: randomElement(cities),
    state: randomElement(states),
    zipCode: `${randomInt(10000, 99999)}`,
    country: 'USA',
  };
}

function generateSubscription() {
  const subscriptionType = randomElement(subscriptionTypes);
  if (subscriptionType === 'none') {
    return null;
  }

  const now = new Date();
  const renewableDate = new Date(now);

  if (subscriptionType === 'monthly') {
    renewableDate.setMonth(renewableDate.getMonth() + 1);
  } else if (subscriptionType === 'quarterly') {
    renewableDate.setMonth(renewableDate.getMonth() + 3);
  } else if (subscriptionType === 'yearly') {
    renewableDate.setFullYear(renewableDate.getFullYear() + 1);
  }

  return {
    type: subscriptionType,
    renewable_date: {
      day: renewableDate.getDate(),
      month: renewableDate.getMonth() + 1,
      year: renewableDate.getFullYear(),
    },
    purchases: [],
  };
}

function generateUser(id) {
  const firstName = randomElement(firstNames);
  const lastName = randomElement(lastNames);
  const createdAt = new Date(Date.now() - randomInt(0, 365 * 3 * 24 * 60 * 60 * 1000));
  const lastLogin = new Date(Date.now() - randomInt(0, 90 * 24 * 60 * 60 * 1000));
  const subscription = generateSubscription();

  return {
    _id: id,
    firstName,
    lastName,
    email: generateEmail(firstName, lastName, id),
    phone: generatePhoneNumber(),
    dateOfBirth: new Date(Date.now() - randomInt(18 * 365, 80 * 365) * 24 * 60 * 60 * 1000),
    address: generateAddress(),
    isActive: Math.random() > 0.15,
    isVerified: Math.random() > 0.2,
    createdAt,
    lastLogin,
    totalOrders: 0,
    totalSpent: 0,
    loyaltyPoints: randomInt(0, 5000),
    subscription_type: subscription,
  };
}

function generateCategory(id) {
  const name = randomElement(productCategories);
  return {
    _id: id,
    name,
    slug: name.toLowerCase().replace(/\s+/g, '-'),
    description: `Browse our collection of ${name.toLowerCase()} products`,
    parentCategory: Math.random() > 0.7 ? randomInt(1, Math.min(id - 1, TOTAL_CATEGORIES)) : null,
    isActive: true,
    createdAt: new Date(Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)),
  };
}

function generateProduct(id, categoryIds) {
  const adjective = randomElement(productAdjectives);
  const noun = randomElement(productNouns);
  const brand = randomElement(productBrands);
  const categoryId = randomElement(categoryIds);
  const basePrice = randomFloat(9.99, 999.99);
  const discount = Math.random() > 0.7 ? randomFloat(0.1, 0.5) : 0;
  const price = parseFloat((basePrice * (1 - discount)).toFixed(2));

  return {
    _id: id,
    name: `${brand} ${adjective} ${noun} ${randomInt(100, 9999)}`,
    description: `High-quality ${adjective.toLowerCase()} ${noun.toLowerCase()} from ${brand}. Perfect for your needs.`,
    sku: `SKU-${String(id).padStart(8, '0')}`,
    categoryId,
    brand,
    price,
    originalPrice: discount > 0 ? basePrice : price,
    discount: discount > 0 ? parseFloat((discount * 100).toFixed(0)) : 0,
    stock: randomInt(0, 1000),
    isActive: Math.random() > 0.1,
    isFeatured: Math.random() > 0.9,
    rating: randomFloat(3.0, 5.0),
    reviewCount: randomInt(0, 500),
    weight: randomFloat(0.1, 50.0),
    dimensions: {
      length: randomFloat(5, 100),
      width: randomFloat(5, 100),
      height: randomFloat(5, 100),
    },
    tags: Array.from({ length: randomInt(2, 5) }, () => `tag${randomInt(1, 50)}`),
    createdAt: new Date(Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000)),
    updatedAt: new Date(),
  };
}

function generateOrder(id, userIds, productMap) {
  const userId = randomElement(userIds);
  const itemCount = randomInt(1, 8);
  const items = [];
  let subtotal = 0;

  const productIds = Array.from(productMap.keys());
  const selectedProducts = [];
  for (let i = 0; i < itemCount; i++) {
    const productId = randomElement(productIds);
    if (!selectedProducts.includes(productId)) {
      selectedProducts.push(productId);
    }
  }

  for (const productId of selectedProducts) {
    const product = productMap.get(productId);
    const quantity = randomInt(1, 5);
    const unitPrice = product.price;
    const itemTotal = parseFloat((unitPrice * quantity).toFixed(2));
    subtotal += itemTotal;

    items.push({
      productId,
      productName: product.name,
      brand: product.brand,
      price: unitPrice,
      quantity,
      total: itemTotal,
    });
  }

  const tax = parseFloat((subtotal * 0.08).toFixed(2));
  const shipping = randomFloat(5.99, 29.99);
  const total = parseFloat((subtotal + tax + shipping).toFixed(2));

  const orderDate = new Date(Date.now() - randomInt(0, 365 * 2 * 24 * 60 * 60 * 1000));
  const status = randomElement(orderStatuses);
  const shippedDate =
    status === 'shipped' || status === 'delivered'
      ? new Date(orderDate.getTime() + randomInt(1, 7) * 24 * 60 * 60 * 1000)
      : null;
  const deliveredDate =
    status === 'delivered' ? new Date(shippedDate.getTime() + randomInt(1, 5) * 24 * 60 * 60 * 1000) : null;

  return {
    _id: id,
    userId,
    orderNumber: `ORD-${String(id).padStart(10, '0')}`,
    items,
    subtotal,
    tax,
    shipping,
    total,
    status,
    paymentMethod: randomElement(paymentMethods),
    shippingMethod: randomElement(shippingMethods),
    shippingAddress: generateAddress(),
    billingAddress: generateAddress(),
    orderDate,
    shippedDate,
    deliveredDate,
    createdAt: orderDate,
    updatedAt: new Date(),
  };
}

function generateReview(id, userIds, productIds) {
  const userId = randomElement(userIds);
  const productId = randomElement(productIds);
  const rating = randomInt(1, 5);
  const isVerified = Math.random() > 0.3;

  const reviewTexts = {
    5: ['Excellent product! Highly recommend.', 'Amazing quality, exceeded expectations.', 'Perfect! Will buy again.'],
    4: ['Good product, works as expected.', 'Nice quality, satisfied with purchase.', 'Pretty good overall.'],
    3: ['Average product, nothing special.', "It's okay, could be better.", 'Meets basic expectations.'],
    2: ['Not great, has some issues.', 'Disappointed with quality.', 'Could be improved.'],
    1: ['Poor quality, would not recommend.', 'Very disappointed.', 'Not worth the money.'],
  };

  return {
    _id: id,
    userId,
    productId,
    rating,
    title: rating >= 4 ? 'Great product' : rating >= 3 ? 'Okay product' : 'Not satisfied',
    comment: randomElement(reviewTexts[rating]),
    isVerified,
    helpfulCount: randomInt(0, 50),
    createdAt: new Date(Date.now() - randomInt(0, 180 * 24 * 60 * 60 * 1000)),
  };
}

async function generateBatch(client, collectionName, documents) {
  const db = client.db(DATABASE_NAME);
  const collection = db.collection(collectionName);
  await collection.insertMany(documents, { ordered: false });
  return documents.length;
}

async function generateUsers(client) {
  console.log(`\n👥 Generating ${TOTAL_USERS.toLocaleString()} users...`);
  const db = client.db(DATABASE_NAME);
  const collection = db.collection('users');

  const existingCount = await collection.countDocuments();
  if (existingCount > 0) {
    console.log(`   ⚠️  Collection already has ${existingCount.toLocaleString()} users`);
  }

  const startTime = Date.now();
  let inserted = 0;
  const batches = Math.ceil(TOTAL_USERS / BATCH_SIZE);
  const userIds = [];

  for (let batch = 0; batch < batches; batch++) {
    const documents = [];
    const batchStartId = existingCount + batch * BATCH_SIZE + 1;
    const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_USERS - inserted);

    for (let i = 0; i < currentBatchSize; i++) {
      const id = batchStartId + i;
      const user = generateUser(id);
      documents.push(user);
      userIds.push(id);
    }

    await generateBatch(client, 'users', documents);
    inserted += currentBatchSize;

    const progress = ((inserted / TOTAL_USERS) * 100).toFixed(1);
    const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
    const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

    process.stdout.write(
      `\r   📝 Progress: ${inserted.toLocaleString()}/${TOTAL_USERS.toLocaleString()} ` +
        `(${progress}%) | ${rate} docs/sec | ${elapsed}s`
    );
  }

  console.log(`\n   ✅ Generated ${inserted.toLocaleString()} users`);
  return userIds;
}

async function generateCategories(client) {
  console.log(`\n📁 Generating ${TOTAL_CATEGORIES.toLocaleString()} categories...`);
  const db = client.db(DATABASE_NAME);
  const collection = db.collection('categories');

  const existingCount = await collection.countDocuments();
  if (existingCount > 0) {
    console.log(`   ⚠️  Collection already has ${existingCount.toLocaleString()} categories`);
  }

  const documents = [];
  const categoryIds = [];
  const startId = existingCount + 1;

  for (let i = 0; i < TOTAL_CATEGORIES; i++) {
    const id = startId + i;
    documents.push(generateCategory(id));
    categoryIds.push(id);
  }

  await generateBatch(client, 'categories', documents);
  console.log(`   ✅ Generated ${TOTAL_CATEGORIES.toLocaleString()} categories`);
  return categoryIds;
}

async function generateProducts(client, categoryIds) {
  console.log(`\n🛍️  Generating ${TOTAL_PRODUCTS.toLocaleString()} products...`);
  const db = client.db(DATABASE_NAME);
  const collection = db.collection('products');

  const existingCount = await collection.countDocuments();
  if (existingCount > 0) {
    console.log(`   ⚠️  Collection already has ${existingCount.toLocaleString()} products`);
  }

  const startTime = Date.now();
  let inserted = 0;
  const batches = Math.ceil(TOTAL_PRODUCTS / BATCH_SIZE);
  const productMap = new Map();

  for (let batch = 0; batch < batches; batch++) {
    const documents = [];
    const batchStartId = existingCount + batch * BATCH_SIZE + 1;
    const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_PRODUCTS - inserted);

    for (let i = 0; i < currentBatchSize; i++) {
      const id = batchStartId + i;
      const product = generateProduct(id, categoryIds);
      documents.push(product);
      productMap.set(id, {
        id,
        name: product.name,
        brand: product.brand,
        price: product.price,
      });
    }

    await generateBatch(client, 'products', documents);
    inserted += currentBatchSize;

    const progress = ((inserted / TOTAL_PRODUCTS) * 100).toFixed(1);
    const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
    const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

    process.stdout.write(
      `\r   📝 Progress: ${inserted.toLocaleString()}/${TOTAL_PRODUCTS.toLocaleString()} ` +
        `(${progress}%) | ${rate} docs/sec | ${elapsed}s`
    );
  }

  console.log(`\n   ✅ Generated ${inserted.toLocaleString()} products`);
  return productMap;
}

async function generateOrders(client, userIds, productMap) {
  console.log(`\n📦 Generating ${TOTAL_ORDERS.toLocaleString()} orders...`);
  const db = client.db(DATABASE_NAME);
  const collection = db.collection('orders');

  const existingCount = await collection.countDocuments();
  if (existingCount > 0) {
    console.log(`   ⚠️  Collection already has ${existingCount.toLocaleString()} orders`);
  }

  const startTime = Date.now();
  let inserted = 0;
  const batches = Math.ceil(TOTAL_ORDERS / BATCH_SIZE);
  const userOrdersMap = new Map();

  for (let batch = 0; batch < batches; batch++) {
    const documents = [];
    const batchStartId = existingCount + batch * BATCH_SIZE + 1;
    const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_ORDERS - inserted);

    for (let i = 0; i < currentBatchSize; i++) {
      const id = batchStartId + i;
      const order = generateOrder(id, userIds, productMap);
      documents.push(order);

      if (!userOrdersMap.has(order.userId)) {
        userOrdersMap.set(order.userId, []);
      }
      userOrdersMap.get(order.userId).push(order);
    }

    await generateBatch(client, 'orders', documents);
    inserted += currentBatchSize;

    const progress = ((inserted / TOTAL_ORDERS) * 100).toFixed(1);
    const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
    const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

    process.stdout.write(
      `\r   📝 Progress: ${inserted.toLocaleString()}/${TOTAL_ORDERS.toLocaleString()} ` +
        `(${progress}%) | ${rate} docs/sec | ${elapsed}s`
    );
  }

  console.log(`\n   ✅ Generated ${inserted.toLocaleString()} orders`);
  return userOrdersMap;
}

async function updateUsersWithPurchases(client, userOrdersMap) {
  console.log(`\n🔄 Updating users with purchase history...`);
  const db = client.db(DATABASE_NAME);
  const usersCollection = db.collection('users');

  const startTime = Date.now();
  let updated = 0;
  const totalUsers = userOrdersMap.size;
  const batchSize = 500;
  const userIds = Array.from(userOrdersMap.keys());
  const userMap = new Map();

  for (let i = 0; i < userIds.length; i += batchSize) {
    const batchUserIds = userIds.slice(i, Math.min(i + batchSize, userIds.length));
    const users = await usersCollection.find({ _id: { $in: batchUserIds } }).toArray();
    for (const user of users) {
      userMap.set(user._id, user);
    }
  }

  const bulkOps = [];

  for (const [userId, orders] of userOrdersMap.entries()) {
    const purchases = [];
    let totalSpent = 0;
    const totalOrders = orders.length;

    for (const order of orders) {
      if (order.status !== 'cancelled' && order.status !== 'refunded') {
        totalSpent += order.total;
        for (const item of order.items) {
          purchases.push({
            productName: item.productName,
            brand: item.brand,
            price: item.price,
            quantity: item.quantity,
            orderDate: order.orderDate,
            orderNumber: order.orderNumber,
          });
        }
      }
    }

    const user = userMap.get(userId);
    const updateData = {
      totalOrders,
      totalSpent: parseFloat(totalSpent.toFixed(2)),
    };

    if (purchases.length > 0 && user && user.subscription_type) {
      updateData['subscription_type.purchases'] = purchases;
    }

    bulkOps.push({
      updateOne: {
        filter: { _id: userId },
        update: { $set: updateData },
      },
    });

    if (bulkOps.length >= batchSize) {
      await usersCollection.bulkWrite(bulkOps);
      updated += bulkOps.length;
      bulkOps.length = 0;

      const progress = ((updated / totalUsers) * 100).toFixed(1);
      const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
      process.stdout.write(
        `\r   📝 Progress: ${updated.toLocaleString()}/${totalUsers.toLocaleString()} ` + `(${progress}%) | ${elapsed}s`
      );
    }
  }

  if (bulkOps.length > 0) {
    await usersCollection.bulkWrite(bulkOps);
    updated += bulkOps.length;
  }

  console.log(`\n   ✅ Updated ${updated.toLocaleString()} users with purchase history`);
}

async function generateReviews(client, userIds, productIds) {
  console.log(`\n⭐ Generating ${TOTAL_REVIEWS.toLocaleString()} reviews...`);
  const db = client.db(DATABASE_NAME);
  const collection = db.collection('reviews');

  const existingCount = await collection.countDocuments();
  if (existingCount > 0) {
    console.log(`   ⚠️  Collection already has ${existingCount.toLocaleString()} reviews`);
  }

  const startTime = Date.now();
  let inserted = 0;
  const batches = Math.ceil(TOTAL_REVIEWS / BATCH_SIZE);

  for (let batch = 0; batch < batches; batch++) {
    const documents = [];
    const batchStartId = existingCount + batch * BATCH_SIZE + 1;
    const currentBatchSize = Math.min(BATCH_SIZE, TOTAL_REVIEWS - inserted);

    for (let i = 0; i < currentBatchSize; i++) {
      const id = batchStartId + i;
      documents.push(generateReview(id, userIds, productIds));
    }

    await generateBatch(client, 'reviews', documents);
    inserted += currentBatchSize;

    const progress = ((inserted / TOTAL_REVIEWS) * 100).toFixed(1);
    const elapsed = ((Date.now() - startTime) / 1000).toFixed(1);
    const rate = ((inserted / (Date.now() - startTime)) * 1000).toFixed(0);

    process.stdout.write(
      `\r   📝 Progress: ${inserted.toLocaleString()}/${TOTAL_REVIEWS.toLocaleString()} ` +
        `(${progress}%) | ${rate} docs/sec | ${elapsed}s`
    );
  }

  console.log(`\n   ✅ Generated ${inserted.toLocaleString()} reviews`);
}

async function createIndexes(client) {
  console.log(`\n🔍 Creating indexes...`);
  const db = client.db(DATABASE_NAME);

  try {
    await db.collection('users').createIndex({ email: 1 }, { unique: true });
    await db.collection('users').createIndex({ createdAt: -1 });
    await db.collection('users').createIndex({ isActive: 1 });
    await db.collection('users').createIndex({ 'subscription_type.type': 1 });
    await db.collection('users').createIndex({ totalSpent: -1 });

    await db.collection('products').createIndex({ categoryId: 1 });
    await db.collection('products').createIndex({ sku: 1 }, { unique: true });
    await db.collection('products').createIndex({ price: 1 });
    await db.collection('products').createIndex({ isActive: 1 });
    await db.collection('products').createIndex({ rating: -1 });

    await db.collection('categories').createIndex({ slug: 1 }, { unique: true });
    await db.collection('categories').createIndex({ isActive: 1 });

    await db.collection('orders').createIndex({ userId: 1 });
    await db.collection('orders').createIndex({ orderNumber: 1 }, { unique: true });
    await db.collection('orders').createIndex({ status: 1 });
    await db.collection('orders').createIndex({ orderDate: -1 });
    await db.collection('orders').createIndex({ 'items.productId': 1 });

    await db.collection('reviews').createIndex({ userId: 1 });
    await db.collection('reviews').createIndex({ productId: 1 });
    await db.collection('reviews').createIndex({ rating: -1 });
    await db.collection('reviews').createIndex({ createdAt: -1 });
    await db.collection('reviews').createIndex({ userId: 1, productId: 1 });

    console.log(`   ✅ Indexes created successfully`);
  } catch (error) {
    console.log(`   ⚠️  Some indexes may already exist: ${error.message}`);
  }
}

async function main() {
  console.log('🚀 Starting ecommerce database generation...');
  console.log(`📊 Configuration:`);
  console.log(`   Database: ${DATABASE_NAME}`);
  console.log(`   MongoDB URI: ${MONGODB_URI}`);
  console.log(`   Batch Size: ${BATCH_SIZE.toLocaleString()}`);
  console.log(`\n📈 Data Scale:`);
  console.log(`   Users: ${TOTAL_USERS.toLocaleString()}`);
  console.log(`   Products: ${TOTAL_PRODUCTS.toLocaleString()}`);
  console.log(`   Categories: ${TOTAL_CATEGORIES.toLocaleString()}`);
  console.log(`   Orders: ${TOTAL_ORDERS.toLocaleString()}`);
  console.log(`   Reviews: ${TOTAL_REVIEWS.toLocaleString()}`);
  console.log('');

  const client = new MongoClient(MONGODB_URI);
  const overallStartTime = Date.now();

  try {
    await client.connect();
    console.log('✅ Connected to MongoDB\n');

    const categoryIds = await generateCategories(client);
    const userIds = await generateUsers(client);
    const productMap = await generateProducts(client, categoryIds);
    const productIds = Array.from(productMap.keys());
    const userOrdersMap = await generateOrders(client, userIds, productMap);
    await updateUsersWithPurchases(client, userOrdersMap);
    await generateReviews(client, userIds, productIds);
    await createIndexes(client);

    const totalTime = ((Date.now() - overallStartTime) / 1000).toFixed(2);

    console.log(`\n✅ Ecommerce database generation complete!`);
    console.log(`   Total time: ${totalTime}s`);

    const db = client.db(DATABASE_NAME);
    const stats = await db.stats();
    console.log(`\n📈 Database Statistics:`);
    console.log(`   Data size: ${(stats.dataSize / 1024 / 1024).toFixed(2)} MB`);
    console.log(`   Storage size: ${(stats.storageSize / 1024 / 1024).toFixed(2)} MB`);
    console.log(`   Indexes: ${stats.indexes}`);
    console.log(`   Collections: ${stats.collections}`);

    const collections = await db.listCollections().toArray();
    console.log(`\n📚 Collections:`);
    for (const collection of collections) {
      const count = await db.collection(collection.name).countDocuments();
      console.log(`   ${collection.name}: ${count.toLocaleString()} documents`);
    }
  } catch (error) {
    console.error('❌ Error:', error);
    process.exit(1);
  } finally {
    await client.close();
    console.log('\n🔌 Connection closed');
  }
}

if (require.main === module) {
  main().catch(console.error);
}

module.exports = {
  generateUser,
  generateProduct,
  generateOrder,
  generateReview,
  generateCategory,
};
