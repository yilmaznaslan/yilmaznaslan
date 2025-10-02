# Java Collections Fundamentals

## Overview

The Java Collections Framework is a unified architecture for representing and manipulating collections. It provides a set of interfaces, implementations, and algorithms to work with groups of objects.

## Core Collection Interfaces

### 1. Collection Interface

- Root interface for all collections
- Defines basic operations: add, remove, contains, size, etc.

### 2. List Interface

- Ordered collection that allows duplicate elements
- Elements can be accessed by index
- Common implementations: ArrayList, LinkedList, Vector

### 3. Set Interface

- Collection that contains no duplicate elements
- Common implementations: HashSet, TreeSet, LinkedHashSet

### 4. Map Interface

- Maps keys to values
- Cannot contain duplicate keys
- Common implementations: HashMap, TreeMap, LinkedHashMap

## ArrayList vs LinkedList: Detailed Comparison

### ArrayList

**Definition:** ArrayList is a resizable array implementation of the List interface.

**Internal Structure:**

```java
// Simplified ArrayList structure
public class ArrayList<E> {
    private Object[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
}
```

**Key Characteristics:**

- **Backed by Array:** Uses a dynamic array internally
- **Random Access:** Elements can be accessed directly by index
- **Memory Efficient:** Contiguous memory allocation
- **Resizable:** Automatically grows when capacity is exceeded

### LinkedList

**Definition:** LinkedList is a doubly-linked list implementation of the List interface.

**Internal Structure:**

```java
// Simplified LinkedList structure
public class LinkedList<E> {
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
    }
}
```

**Key Characteristics:**

- **Backed by Nodes:** Uses doubly-linked list structure
- **Sequential Access:** Must traverse nodes to reach elements
- **Memory Overhead:** Each element requires additional node objects
- **Dynamic Size:** No capacity constraints

## Performance Comparison

### Time Complexity Analysis

| Operation               | ArrayList      | LinkedList | Winner    |
| ----------------------- | -------------- | ---------- | --------- |
| **get(index)**          | O(1)           | O(n)       | ArrayList |
| **add(element)**        | O(1) amortized | O(1)       | Tie       |
| **add(index, element)** | O(n)           | O(n)       | Tie       |
| **remove(index)**       | O(n)           | O(n)       | Tie       |
| **remove(element)**     | O(n)           | O(n)       | Tie       |
| **contains(element)**   | O(n)           | O(n)       | Tie       |
| **indexOf(element)**    | O(n)           | O(n)       | Tie       |

### Space Complexity

| Aspect                | ArrayList                      | LinkedList                       |
| --------------------- | ------------------------------ | -------------------------------- |
| **Memory Usage**      | More efficient (contiguous)    | Less efficient (node overhead)   |
| **Cache Performance** | Better (locality of reference) | Worse (scattered memory)         |
| **Memory Overhead**   | Minimal                        | Significant (pointers + objects) |

## Detailed Performance Analysis

### 1. Access Operations

**ArrayList - Random Access:**

```java
// Direct array access - O(1)
public E get(int index) {
    return (E) elementData[index];
}
```

**LinkedList - Sequential Access:**

```java
// Must traverse to index - O(n)
public E get(int index) {
    Node<E> x = first;
    for (int i = 0; i < index; i++)
        x = x.next;
    return x.item;
}
```

### 2. Insertion Operations

**ArrayList - End Insertion:**

```java
// Simple array assignment - O(1) amortized
public boolean add(E e) {
    ensureCapacityInternal(size + 1);
    elementData[size++] = e;
    return true;
}
```

**ArrayList - Middle Insertion:**

```java
// Requires shifting elements - O(n)
public void add(int index, E element) {
    System.arraycopy(elementData, index, elementData, index + 1, size - index);
    elementData[index] = element;
    size++;
}
```

**LinkedList - Any Position:**

```java
// Direct node manipulation - O(1) for known position
void linkBefore(E e, Node<E> succ) {
    final Node<E> pred = succ.prev;
    final Node<E> newNode = new Node<>(pred, e, succ);
    succ.prev = newNode;
    if (pred == null)
        first = newNode;
    else
        pred.next = newNode;
    size++;
}
```

### 3. Memory Layout

**ArrayList Memory Layout:**

```
[Element1][Element2][Element3][Element4][Element5]
     ↑         ↑         ↑         ↑         ↑
   Index 0   Index 1   Index 2   Index 3   Index 4
```

**LinkedList Memory Layout:**

```
Node1 → Node2 → Node3 → Node4 → Node5
  ↓       ↓       ↓       ↓       ↓
Element1 Element2 Element3 Element4 Element5
```

## When to Use Each

### Use ArrayList When:

- **Frequent random access** by index
- **Read-heavy operations**
- **Memory efficiency** is important
- **Cache performance** matters
- **Small to medium collections**

**Example Use Cases:**

```java
// Good for ArrayList
List<String> names = new ArrayList<>();
names.add("Alice");
names.add("Bob");
names.add("Charlie");

// Frequent random access
String first = names.get(0);  // O(1)
String last = names.get(names.size() - 1);  // O(1)

// Iteration
for (int i = 0; i < names.size(); i++) {
    System.out.println(names.get(i));  // Efficient
}
```

### Use LinkedList When:

- **Frequent insertions/deletions** at beginning or middle
- **Sequential access** patterns
- **Unknown collection size** at creation
- **Stack or Queue** operations

**Example Use Cases:**

```java
// Good for LinkedList
LinkedList<String> queue = new LinkedList<>();
queue.addFirst("Task1");  // O(1)
queue.addFirst("Task2");  // O(1)
queue.addFirst("Task3");  // O(1)

// Queue operations
String next = queue.removeLast();  // O(1)

// Stack operations
String top = queue.removeFirst();  // O(1)
```

## Practical Examples

### Example 1: Performance Test

```java
public class CollectionPerformanceTest {
    public static void main(String[] args) {
        int size = 100000;

        // ArrayList performance
        List<Integer> arrayList = new ArrayList<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arrayList.add(i);
        }

        long arrayListTime = System.currentTimeMillis() - start;
        System.out.println("ArrayList add time: " + arrayListTime + "ms");

        // LinkedList performance
        List<Integer> linkedList = new LinkedList<>();
        start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            linkedList.add(i);
        }

        long linkedListTime = System.currentTimeMillis() - start;
        System.out.println("LinkedList add time: " + linkedListTime + "ms");
    }
}
```

### Example 2: Random Access Comparison

```java
public class RandomAccessComparison {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        // Populate both lists
        for (int i = 0; i < 10000; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        // Random access test
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            arrayList.get(5000);  // O(1)
        }
        long arrayListTime = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            linkedList.get(5000);  // O(n)
        }
        long linkedListTime = System.currentTimeMillis() - start;

        System.out.println("ArrayList random access: " + arrayListTime + "ms");
        System.out.println("LinkedList random access: " + linkedListTime + "ms");
    }
}
```

## Best Practices

### 1. Choose the Right Implementation

```java
// For frequent random access
List<String> readHeavyList = new ArrayList<>();

// For frequent insertions/deletions
List<String> writeHeavyList = new LinkedList<>();

// For stack operations
Deque<String> stack = new LinkedList<>();

// For queue operations
Queue<String> queue = new LinkedList<>();
```

### 2. Initialize with Capacity

```java
// ArrayList with known capacity
List<String> list = new ArrayList<>(1000);

// Avoid frequent resizing
List<String> efficientList = new ArrayList<>(expectedSize);
```

### 3. Use Enhanced For Loop

```java
// Efficient iteration
for (String item : list) {
    System.out.println(item);
}

// Avoid this for LinkedList
for (int i = 0; i < list.size(); i++) {
    System.out.println(list.get(i));  // O(n) for LinkedList
}
```

## Summary

| Aspect                | ArrayList                 | LinkedList                             |
| --------------------- | ------------------------- | -------------------------------------- |
| **Best For**          | Random access, read-heavy | Frequent insertions/deletions          |
| **Access Time**       | O(1)                      | O(n)                                   |
| **Memory**            | Efficient                 | Overhead                               |
| **Cache Performance** | Excellent                 | Poor                                   |
| **Use Cases**         | General purpose, indexing | Stacks, queues, frequent modifications |

**Key Takeaway:** Choose ArrayList for most use cases due to its superior random access performance and memory efficiency. Use LinkedList only when you need frequent insertions/deletions at the beginning or middle of the list, or when implementing stack/queue operations.

## HashMap vs TreeMap: Detailed Comparison

### HashMap

**Definition:** HashMap is a hash table-based implementation of the Map interface that provides constant-time performance for basic operations.

**Internal Structure:**

```java
// Simplified HashMap structure
public class HashMap<K,V> {
    Node<K,V>[] table;
    int size;
    float loadFactor = 0.75f;
    int threshold;

    static class Node<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
    }
}
```

**Key Characteristics:**

- **Hash Table Based:** Uses array of buckets with linked lists/chains
- **No Ordering:** Elements are not ordered
- **Null Keys/Values:** Allows one null key and multiple null values
- **Fast Access:** O(1) average time complexity for get/put operations
- **Not Thread-Safe:** Requires external synchronization for concurrent access

### TreeMap

**Definition:** TreeMap is a Red-Black tree-based implementation of the NavigableMap interface that maintains elements in sorted order.

**Internal Structure:**

```java
// Simplified TreeMap structure
public class TreeMap<K,V> {
    private Entry<K,V> root;
    private int size = 0;

    static final class Entry<K,V> {
        K key;
        V value;
        Entry<K,V> left;
        Entry<K,V> right;
        Entry<K,V> parent;
        boolean color = BLACK;
    }
}
```

**Key Characteristics:**

- **Red-Black Tree Based:** Self-balancing binary search tree
- **Sorted Order:** Elements are automatically sorted by key
- **No Null Keys:** Does not allow null keys (but allows null values)
- **Logarithmic Access:** O(log n) time complexity for all operations
- **NavigableMap:** Provides navigation methods (first, last, higher, lower)

## Performance Comparison

### Time Complexity Analysis

| Operation                | HashMap      | TreeMap  | Winner  |
| ------------------------ | ------------ | -------- | ------- |
| **get(key)**             | O(1) average | O(log n) | HashMap |
| **put(key, value)**      | O(1) average | O(log n) | HashMap |
| **remove(key)**          | O(1) average | O(log n) | HashMap |
| **containsKey(key)**     | O(1) average | O(log n) | HashMap |
| **containsValue(value)** | O(n)         | O(n)     | Tie     |
| **size()**               | O(1)         | O(1)     | Tie     |
| **clear()**              | O(n)         | O(n)     | Tie     |

### Space Complexity

| Aspect           | HashMap                        | TreeMap                      |
| ---------------- | ------------------------------ | ---------------------------- |
| **Memory Usage** | More efficient (array + nodes) | Less efficient (tree nodes)  |
| **Overhead**     | Minimal (hash table)           | Significant (tree structure) |
| **Load Factor**  | 0.75 (75% full before resize)  | N/A (dynamic tree)           |

## Detailed Performance Analysis

### 1. Hash Function and Collision Handling

**HashMap - Hash Distribution:**

```java
// Hash function for key distribution
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// Collision resolution with chaining
if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k))))
    return p;
```

**TreeMap - Tree Traversal:**

```java
// Binary search tree traversal
private Entry<K,V> getEntry(Object key) {
    Entry<K,V> p = root;
    while (p != null) {
        int cmp = compare(key, p.key);
        if (cmp < 0)
            p = p.left;
        else if (cmp > 0)
            p = p.right;
        else
            return p;
    }
    return null;
}
```

### 2. Insertion Operations

**HashMap - Hash Table Insertion:**

```java
// Hash table insertion - O(1) average
public V put(K key, V value) {
    int hash = hash(key);
    int index = (table.length - 1) & hash;

    // Handle collision with chaining
    for (Node<K,V> e = table[index]; e != null; e = e.next) {
        if (e.hash == hash && Objects.equals(e.key, key)) {
            V oldValue = e.value;
            e.value = value;
            return oldValue;
        }
    }

    // Add new node
    addEntry(hash, key, value, index);
    return null;
}
```

**TreeMap - Tree Insertion:**

```java
// Tree insertion with rebalancing - O(log n)
public V put(K key, V value) {
    Entry<K,V> t = root;
    if (t == null) {
        root = new Entry<>(key, value, null);
        size = 1;
        return null;
    }

    // Find insertion point
    Entry<K,V> parent;
    int cmp;
    do {
        parent = t;
        cmp = compare(key, t.key);
        if (cmp < 0)
            t = t.left;
        else if (cmp > 0)
            t = t.right;
        else
            return t.setValue(value);
    } while (t != null);

    // Insert and rebalance
    Entry<K,V> e = new Entry<>(key, value, parent);
    if (cmp < 0)
        parent.left = e;
    else
        parent.right = e;

    fixAfterInsertion(e);
    size++;
    return null;
}
```

### 3. Memory Layout Comparison

**HashMap Memory Layout:**

```
Bucket 0: [Key1→Value1] → [Key2→Value2] → null
Bucket 1: [Key3→Value3] → null
Bucket 2: null
Bucket 3: [Key4→Value4] → null
```

**TreeMap Memory Layout:**

```
        [Key2→Value2]
       /              \
[Key1→Value1]    [Key3→Value3]
                        \
                    [Key4→Value4]
```

## When to Use Each

### Use HashMap When:

- **Fast access** is critical
- **No ordering** requirements
- **General-purpose** mapping
- **High performance** is needed
- **Null keys/values** are required

**Example Use Cases:**

```java
// Good for HashMap
Map<String, Integer> wordCount = new HashMap<>();
wordCount.put("hello", 1);
wordCount.put("world", 1);
wordCount.put("java", 2);

// Fast lookups
int count = wordCount.get("java");  // O(1)

// Caching
Map<String, Object> cache = new HashMap<>();
cache.put("user:123", userObject);
Object cached = cache.get("user:123");  // O(1)
```

### Use TreeMap When:

- **Sorted iteration** is required
- **Range queries** are needed
- **Navigation operations** are important
- **Consistent ordering** is required
- **SubMap operations** are needed

**Example Use Cases:**

```java
// Good for TreeMap
TreeMap<Integer, String> scoreRanking = new TreeMap<>();
scoreRanking.put(95, "Alice");
scoreRanking.put(87, "Bob");
scoreRanking.put(92, "Charlie");

// Sorted iteration
for (Map.Entry<Integer, String> entry : scoreRanking.entrySet()) {
    System.out.println(entry.getValue() + ": " + entry.getKey());
}

// Range queries
SortedMap<Integer, String> topScores = scoreRanking.tailMap(90);
NavigableMap<Integer, String> highScores = scoreRanking.headMap(95, true);
```

## Advanced Features Comparison

### HashMap Features

```java
// Load factor control
Map<String, Integer> map = new HashMap<>(16, 0.5f);

// Custom hash function
class CustomKey {
    @Override
    public int hashCode() {
        return Objects.hash(field1, field2);
    }

    @Override
    public boolean equals(Object obj) {
        // Custom equality logic
    }
}
```

### TreeMap Features

```java
// Custom comparator
TreeMap<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

// Navigation methods
String first = map.firstKey();
String last = map.lastKey();
String higher = map.higherKey("middle");
String lower = map.lowerKey("middle");

// SubMap operations
SortedMap<String, Integer> subMap = map.subMap("a", "m");
NavigableMap<String, Integer> headMap = map.headMap("middle", true);
NavigableMap<String, Integer> tailMap = map.tailMap("middle", false);
```

## Practical Examples

### Example 1: Performance Comparison

```java
public class MapPerformanceTest {
    public static void main(String[] args) {
        int size = 100000;

        // HashMap performance
        Map<Integer, String> hashMap = new HashMap<>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            hashMap.put(i, "Value" + i);
        }

        long hashMapTime = System.currentTimeMillis() - start;
        System.out.println("HashMap put time: " + hashMapTime + "ms");

        // TreeMap performance
        Map<Integer, String> treeMap = new TreeMap<>();
        start = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            treeMap.put(i, "Value" + i);
        }

        long treeMapTime = System.currentTimeMillis() - start;
        System.out.println("TreeMap put time: " + treeMapTime + "ms");
    }
}
```

### Example 2: Sorted vs Unsorted Access

```java
public class MapAccessComparison {
    public static void main(String[] args) {
        Map<String, Integer> hashMap = new HashMap<>();
        Map<String, Integer> treeMap = new TreeMap<>();

        // Populate both maps
        String[] keys = {"zebra", "apple", "banana", "cherry"};
        for (String key : keys) {
            hashMap.put(key, key.length());
            treeMap.put(key, key.length());
        }

        // HashMap iteration (unsorted)
        System.out.println("HashMap iteration:");
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // TreeMap iteration (sorted)
        System.out.println("\nTreeMap iteration:");
        for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
```

## Best Practices

### 1. Choose the Right Implementation

```java
// For general-purpose mapping with fast access
Map<String, Object> generalMap = new HashMap<>();

// For sorted data with navigation needs
Map<String, Object> sortedMap = new TreeMap<>();

// For thread-safe operations
Map<String, Object> concurrentMap = new ConcurrentHashMap<>();
```

### 2. Optimize HashMap Performance

```java
// Set appropriate initial capacity
Map<String, Integer> map = new HashMap<>(expectedSize);

// Use good hash functions
class GoodKey {
    @Override
    public int hashCode() {
        return Objects.hash(field1, field2, field3);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GoodKey other = (GoodKey) obj;
        return Objects.equals(field1, other.field1) &&
               Objects.equals(field2, other.field2) &&
               Objects.equals(field3, other.field3);
    }
}
```

### 3. Leverage TreeMap Features

```java
// Use navigation methods
TreeMap<Integer, String> map = new TreeMap<>();
String first = map.firstKey();
String last = map.lastKey();

// Use subMap for range operations
SortedMap<Integer, String> range = map.subMap(10, 20);

// Use custom comparators
TreeMap<String, Integer> caseInsensitive = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
```

## Summary

| Aspect          | HashMap                      | TreeMap                           |
| --------------- | ---------------------------- | --------------------------------- |
| **Best For**    | Fast access, general purpose | Sorted data, navigation           |
| **Access Time** | O(1) average                 | O(log n)                          |
| **Memory**      | Efficient                    | Overhead                          |
| **Ordering**    | None                         | Sorted                            |
| **Null Keys**   | Allowed                      | Not allowed                       |
| **Navigation**  | Limited                      | Rich (first, last, higher, lower) |
| **Use Cases**   | Caching, general mapping     | Rankings, ranges, sorted data     |

**Key Takeaway:** Use HashMap for general-purpose mapping where fast access is critical. Use TreeMap when you need sorted data, range queries, or navigation operations. The performance difference is significant for large datasets, so choose based on your specific requirements.

## HashMap Buckets and hashCode: Deep Dive

### Understanding HashMap Buckets

**Definition:** A bucket in HashMap is a slot in the internal array where key-value pairs are stored. Multiple key-value pairs can be stored in the same bucket when they have the same hash value (collision).

**Internal Structure:**

```java
// HashMap internal structure
public class HashMap<K,V> {
    Node<K,V>[] table;        // Array of buckets
    int size;                 // Number of key-value pairs
    float loadFactor = 0.75f; // When to resize
    int threshold;            // Size threshold for resizing

    // Each bucket can contain a linked list of nodes
    static class Node<K,V> {
        final int hash;       // Hash code of the key
        final K key;          // The key
        V value;              // The value
        Node<K,V> next;       // Next node in the chain
    }
}
```

**Bucket Array Visualization:**

```
Index 0: [Key1→Value1] → [Key2→Value2] → null
Index 1: [Key3→Value3] → null
Index 2: null
Index 3: [Key4→Value4] → null
Index 4: [Key5→Value5] → [Key6→Value6] → [Key7→Value7] → null
```

### How Buckets Work

**1. Bucket Selection Process:**

```java
// Step 1: Calculate hash code
int hash = key.hashCode();

// Step 2: Apply additional hash function (XOR with shifted bits)
hash = hash ^ (hash >>> 16);

// Step 3: Calculate bucket index using bitwise AND
int bucketIndex = (table.length - 1) & hash;

// Step 4: Access the bucket
Node<K,V> bucket = table[bucketIndex];
```

**2. Collision Handling:**

```java
// When two keys hash to the same bucket (collision)
// They are stored as a linked list in that bucket

// Example: Keys "apple" and "orange" might hash to bucket 2
// Bucket 2: [apple→red] → [orange→orange] → null
```

### Understanding hashCode()

**Definition:** hashCode() is a method defined in the Object class that returns an integer hash code for the object. It's used by hash-based collections like HashMap to determine which bucket to store the object in.

**Key Properties of hashCode():**

- **Consistency:** Same object must always return the same hash code
- **Equality:** If two objects are equal (equals() returns true), they must have the same hash code
- **Distribution:** Different objects should ideally have different hash codes

**Default Implementation:**

```java
// Object class default hashCode() implementation
public native int hashCode();

// This typically returns the memory address of the object
// But this is JVM implementation specific
```

### How hashCode() is Calculated

**1. For Primitive Types:**

```java
// Integer hashCode
Integer num = 42;
int hash = num.hashCode(); // Returns 42

// String hashCode
String str = "hello";
int hash = str.hashCode(); // Returns calculated hash based on characters

// String hashCode calculation (simplified)
public int hashCode() {
    int h = hash;
    if (h == 0 && value.length > 0) {
        char val[] = value;
        for (int i = 0; i < value.length; i++) {
            h = 31 * h + val[i];
        }
        hash = h;
    }
    return h;
}
```

**2. For Custom Objects:**

```java
// Custom class with proper hashCode implementation
public class Person {
    private String name;
    private int age;
    private String email;

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person person = (Person) obj;
        return age == person.age &&
               Objects.equals(name, person.name) &&
               Objects.equals(email, person.email);
    }
}
```

**3. Objects.hash() Implementation:**

```java
// Objects.hash() method implementation
public static int hash(Object... values) {
    return Arrays.hashCode(values);
}

// Arrays.hashCode() implementation
public static int hashCode(Object a[]) {
    if (a == null)
        return 0;

    int result = 1;
    for (Object element : a)
        result = 31 * result + (element == null ? 0 : element.hashCode());

    return result;
}
```

### HashMap Hash Function

**Two-Step Hash Process:**

```java
// Step 1: Get object's hashCode()
int hash = key.hashCode();

// Step 2: Apply HashMap's hash function
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

// Step 3: Calculate bucket index
int bucketIndex = (table.length - 1) & hash;
```

**Why XOR with Shifted Bits?**

```java
// Without XOR: hash = 0x12345678
// table.length = 16, so (table.length - 1) = 15 = 0x0000000F
// bucketIndex = 0x12345678 & 0x0000000F = 0x00000008
// Only uses lower 4 bits!

// With XOR: hash = 0x12345678 ^ (0x12345678 >>> 16)
// hash = 0x12345678 ^ 0x00001234 = 0x1234444C
// bucketIndex = 0x1234444C & 0x0000000F = 0x0000000C
// Uses more bits for better distribution
```

### Collision Resolution Strategies

**1. Chaining (Java HashMap uses this):**

```java
// Multiple nodes in the same bucket
Bucket 0: [Key1→Value1] → [Key2→Value2] → [Key3→Value3] → null

// When searching:
Node<K,V> current = table[bucketIndex];
while (current != null) {
    if (current.hash == hash && Objects.equals(current.key, key)) {
        return current.value; // Found!
    }
    current = current.next;
}
```

**2. Open Addressing (not used in Java HashMap):**

```java
// Alternative approach: find next available slot
// Linear probing: check next slot
// Quadratic probing: check slot + 1, +4, +9, +16...
// Double hashing: use second hash function
```

### Load Factor and Resizing

**Load Factor Concept:**

```java
// Load factor = number of elements / number of buckets
// Default load factor = 0.75 (75%)

// When load factor exceeds threshold, HashMap resizes
if (size > threshold) {
    resize(); // Double the table size
}

// Resizing process:
// 1. Create new table with double capacity
// 2. Rehash all existing elements
// 3. Redistribute elements across new buckets
```

**Resizing Example:**

```java
// Before resize: table.length = 16, size = 12, loadFactor = 0.75
// After resize: table.length = 32, size = 12, loadFactor = 0.375

// Elements are rehashed and redistributed
// Old bucket 0: [Key1→Value1] → [Key2→Value2]
// New bucket 0: [Key1→Value1]
// New bucket 16: [Key2→Value2] (if hash changed)
```

### Practical Examples

**Example 1: hashCode() Demonstration**

```java
public class HashCodeDemo {
    public static void main(String[] args) {
        // String hashCode
        String str1 = "hello";
        String str2 = "hello";
        String str3 = "world";

        System.out.println("str1.hashCode(): " + str1.hashCode());
        System.out.println("str2.hashCode(): " + str2.hashCode());
        System.out.println("str3.hashCode(): " + str3.hashCode());
        System.out.println("str1.equals(str2): " + str1.equals(str2));

        // Integer hashCode
        Integer num1 = 42;
        Integer num2 = 42;
        System.out.println("num1.hashCode(): " + num1.hashCode());
        System.out.println("num2.hashCode(): " + num2.hashCode());

        // Custom object hashCode
        Person person1 = new Person("John", 25, "john@email.com");
        Person person2 = new Person("John", 25, "john@email.com");
        System.out.println("person1.hashCode(): " + person1.hashCode());
        System.out.println("person2.hashCode(): " + person2.hashCode());
        System.out.println("person1.equals(person2): " + person1.equals(person2));
    }
}
```

**Example 2: Bucket Distribution Analysis**

```java
public class BucketAnalysis {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Add elements and analyze bucket distribution
        String[] keys = {"apple", "banana", "cherry", "date", "elderberry"};

        for (String key : keys) {
            map.put(key, key.length());

            // Calculate bucket index
            int hash = key.hashCode();
            int bucketIndex = (map.size() - 1) & hash;

            System.out.println("Key: " + key +
                             ", hashCode: " + hash +
                             ", bucket: " + bucketIndex);
        }

        // Show final bucket distribution
        System.out.println("\nFinal map size: " + map.size());
        System.out.println("Load factor: " + (map.size() / 16.0));
    }
}
```

**Example 3: Collision Demonstration**

```java
public class CollisionDemo {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();

        // Add elements that might collide
        map.put("Aa", 1);
        map.put("BB", 2);
        map.put("C#", 3);

        // Check if they have the same hash code
        System.out.println("Aa.hashCode(): " + "Aa".hashCode());
        System.out.println("BB.hashCode(): " + "BB".hashCode());
        System.out.println("C#.hashCode(): " + "C#".hashCode());

        // These strings have the same hash code due to:
        // 'A' = 65, 'a' = 97, so "Aa" = 65*31 + 97 = 2112
        // 'B' = 66, 'B' = 66, so "BB" = 66*31 + 66 = 2112
    }
}
```

### Best Practices for hashCode()

**1. Always Override hashCode() with equals():**

```java
// Bad: Only overriding equals()
public class BadExample {
    private String name;

    @Override
    public boolean equals(Object obj) {
        // Implementation
    }
    // Missing hashCode() override!
}

// Good: Override both
public class GoodExample {
    private String name;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GoodExample other = (GoodExample) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
```

**2. Use Objects.hash() for Multiple Fields:**

```java
public class Person {
    private String firstName;
    private String lastName;
    private int age;

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }
}
```

**3. Avoid Mutable Fields in hashCode():**

```java
// Bad: Using mutable fields
public class BadPerson {
    private String name;
    private int age;

    @Override
    public int hashCode() {
        return Objects.hash(name, age); // age can change!
    }
}

// Good: Use immutable fields or be careful
public class GoodPerson {
    private final String name;
    private final int age;

    @Override
    public int hashCode() {
        return Objects.hash(name, age); // Safe because final
    }
}
```

### Common hashCode() Mistakes

**1. Inconsistent with equals():**

```java
// Wrong: Different hash codes for equal objects
public class WrongExample {
    private String value;

    @Override
    public boolean equals(Object obj) {
        return value.equals(((WrongExample) obj).value);
    }

    @Override
    public int hashCode() {
        return value.length(); // Wrong! Different strings can have same length
    }
}
```

**2. Using Random Numbers:**

```java
// Wrong: hashCode() should be consistent
@Override
public int hashCode() {
    return (int) (Math.random() * 1000); // Wrong! Different each time
}
```

**3. Not Handling Null Fields:**

```java
// Wrong: Can cause NullPointerException
@Override
public int hashCode() {
    return name.hashCode() + age; // name might be null
}

// Correct: Handle null values
@Override
public int hashCode() {
    return Objects.hash(name, age); // Objects.hash handles nulls
}
```

## Summary

| Concept           | Description                                   | Key Points                                     |
| ----------------- | --------------------------------------------- | ---------------------------------------------- |
| **Buckets**       | Array slots where key-value pairs are stored  | Multiple pairs can share a bucket (collision)  |
| **hashCode()**    | Method returning integer hash code for object | Must be consistent and work with equals()      |
| **Hash Function** | Converts hashCode to bucket index             | Uses bitwise operations for efficiency         |
| **Collision**     | Multiple keys hash to same bucket             | Resolved using chaining (linked lists)         |
| **Load Factor**   | Ratio of elements to buckets                  | Triggers resizing when exceeded (default 0.75) |

**Key Takeaways:**

1. **Buckets** are array slots that can contain multiple key-value pairs
2. **hashCode()** determines which bucket an object goes into
3. **Collisions** are handled using linked lists in buckets
4. **Load factor** triggers automatic resizing for performance
5. **Always override hashCode() with equals()** for custom objects
6. **Use Objects.hash()** for consistent, null-safe hash codes
