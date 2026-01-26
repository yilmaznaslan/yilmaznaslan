

# Java Threads, Performance & Async Programming - Fundamentals Guide

## Table of Contents
1. [What Are Threads?](#what-are-threads)
2. [Blocking vs Non-Blocking Operations](#blocking-vs-non-blocking-operations)
3. [Thread Pools](#thread-pools)
4. [CompletableFuture Basics](#completablefuture-basics)
5. [Thread-Local Storage (MDC Context)](#thread-local-storage-mdc-context)
6. [When to Use Async](#when-to-use-async)
7. [Performance Implications](#performance-implications)
8. [Common Pitfalls](#common-pitfalls)

---

## What Are Threads?

### The Basics

A **thread** is the smallest unit of execution in a Java program. Think of it as a worker that can do tasks.

**Important:** All threads are fundamentally the same - they're all just threads. The terms "servlet thread" and "background thread" refer to **where the thread comes from** and **what it's doing**, not different types of threads.

### Types of Threads (By Source)

**1. Servlet Threads (Request Threads)**
- Come from the **servlet container's thread pool** (e.g., Jetty, Tomcat, Grizzly)
- Handle incoming HTTP requests
- Managed by the web server (Dropwizard/Jersey)
- Limited in number (typically 200-500 threads)
- **Purpose:** Process HTTP requests and responses

**2. Background Threads (Worker Threads)**
- Come from **application thread pools** (ForkJoinPool, ExecutorService, etc.)
- Execute async tasks, scheduled jobs, background processing
- Managed by your application code
- Can have different pools for different purposes
- **Purpose:** Do work without blocking servlet threads

**3. System Threads (JVM Threads)**
- JVM internal threads (GC threads, finalizer threads, signal dispatcher, etc.)
- Created automatically by the JVM
- Not something you typically interact with
- Examples: `GC task thread#0`, `Finalizer`, `Signal Dispatcher`

**Important Distinction:**
- **Java Threads** = All threads created by your application (servlet threads, background threads)
- **System Threads** = JVM internal threads (not created by your code)
- **They're all OS threads** - the OS sees them all the same way!

### Key Insight: They're All Just Threads!

```java
// Servlet thread (from Jetty/Grizzly thread pool)
public CompletionStage<Result> getAccessories(...) {
    // This code runs on a SERVLET THREAD
    // It's just a regular thread, but it came from the web server's pool
    
    return CompletableFuture.supplyAsync(() -> {
        // This code runs on a BACKGROUND THREAD
        // It's also just a regular thread, but it came from ForkJoinPool
        // It's a DIFFERENT thread than the one above!
    });
}
```

**The Difference:**
- **Servlet thread:** Handles the HTTP request/response cycle
- **Background thread:** Does the actual work (SOAP call, etc.)
- **Both are regular Java threads** - just from different pools!

**Single-threaded (synchronous):**
```
Request 1 → [Thread 1: Do Task A] → Wait 500ms → [Thread 1: Do Task B] → Done
Request 2 → [Thread 1: Wait...] → Wait... → [Thread 1: Do Task A] → Done
```
- Only one task at a time
- Request 2 waits for Request 1 to finish
- **Slow!**

**Multi-threaded (asynchronous):**
```
Request 1 → [Thread 1: Do Task A] → Wait 500ms → [Thread 1: Do Task B] → Done
Request 2 → [Thread 2: Do Task A] → Wait 500ms → [Thread 2: Do Task B] → Done
```
- Multiple tasks run simultaneously
- Request 2 doesn't wait for Request 1
- **Fast!**

### Servlet Threads vs Background Threads - The Real Difference

**All threads are the same type** - they're all Java `Thread` objects. The difference is:

1. **Where they come from (which thread pool)**
2. **What they're responsible for**
3. **How many you have**

**Servlet Thread (Request Thread):**
```java
// This code runs on a SERVLET THREAD
// Thread name might be: "dw-123" (Dropwizard) or "qtp123456789-123" (Jetty)
public CompletionStage<Result> getAccessories(...) {
    // Thread ID: 45
    // Thread Pool: Jetty's HTTP thread pool (200 threads)
    // Responsibility: Handle HTTP request/response
    // Cost: EXPENSIVE - limited resource!
}
```

**Background Thread (Worker Thread):**
```java
CompletableFuture.supplyAsync(() -> {
    // This code runs on a BACKGROUND THREAD
    // Thread name might be: "ForkJoinPool.commonPool-worker-1"
    // Thread ID: 67 (DIFFERENT from servlet thread!)
    // Thread Pool: ForkJoinPool.commonPool() (typically CPU cores - 1)
    // Responsibility: Do the actual work (SOAP call, etc.)
    // Cost: CHEAPER - can have more of these
});
```

**Visual Example:**
```
HTTP Request arrives
    ↓
Servlet Thread #45 (from Jetty pool) receives request
    ↓
Calls: CompletableFuture.supplyAsync(...)
    ↓
Servlet Thread #45: Returns immediately, goes back to pool
    ↓
Background Thread #67 (from ForkJoinPool) executes lambda
    ↓
Background Thread #67: Makes SOAP call (blocked for 500ms)
    ↓
Background Thread #67: Completes, Jersey sends response
```

**Why This Matters:**
- **Servlet threads are limited** (200-500) - if all are blocked, new requests wait
- **Background threads can be more numerous** - better for I/O operations
- **Servlet thread freed immediately** - can handle other requests
- **Better throughput** - more requests processed per second

### Real-World Analogy

**Single-threaded = One cashier:**
- Customer 1: Order → Wait → Pay → Leave (2 minutes)
- Customer 2: Wait... Wait... Wait... (2 minutes waiting) → Order → Pay → Leave
- **Total: 4 minutes**

**Multi-threaded = Multiple cashiers:**
- Customer 1: Cashier 1 → Order → Pay → Leave (2 minutes)
- Customer 2: Cashier 2 → Order → Pay → Leave (2 minutes)
- **Total: 2 minutes** (both served simultaneously)

---

## Blocking vs Non-Blocking Operations

### Blocking Operations

A **blocking operation** makes a thread wait (do nothing) until the operation completes.

**Examples:**
- **Network calls** (SOAP, REST, HTTP) - waiting for response
- **Database queries** - waiting for query results
- **File I/O** - waiting for disk read/write
- **Sleep** - intentionally waiting

**Your SOAP call is blocking:**
```java
// This BLOCKS the thread for 500ms (or more)
domesticMipService.getAccessoriesForSingleAppliance(...);
// Thread sits idle, waiting for SOAP response
```

**Problem with blocking:**
```java
// Synchronous (blocking) - BAD for performance
public CompletionStage<Result> getAccessories(...) {
    // Thread is BLOCKED here, can't handle other requests
    var result = domesticMipService.getAccessoriesForSingleAppliance(...); // 500ms wait
    return CompletableFuture.completedFuture(result);
}
```

**Timeline:**
```
Time: 0ms    100ms   200ms   300ms   400ms   500ms
      |-------|-------|-------|-------|-------|
Thread: [BLOCKED - waiting for SOAP response]
        ↑ Can't do anything else!
```

### Non-Blocking Operations

A **non-blocking operation** returns immediately and does work in the background.

**Your async code:**
```java
// This returns IMMEDIATELY, work happens in background
return CompletableFuture.supplyAsync(() -> {
    // This runs in a DIFFERENT thread
    return domesticMipService.getAccessoriesForSingleAppliance(...);
});
```

**Timeline:**
```
Time: 0ms    100ms   200ms   300ms   400ms   500ms
      |-------|-------|-------|-------|-------|
Servlet Thread: [Returns immediately] → [Free to handle other requests]
Background Thread: [BLOCKED - waiting for SOAP response]
```

**Benefits:**
- Servlet thread freed immediately
- Can handle other requests while SOAP call runs
- Better throughput

### How to See Threads in Action

**Add logging to see thread names:**
```java
public CompletionStage<Result> getAccessories(...) {
    LOGGER.info("Servlet thread: {}", Thread.currentThread().getName());
    // Output: "Servlet thread: dw-123" or "qtp123456789-123"
    
    return CompletableFuture.supplyAsync(() -> {
        LOGGER.info("Background thread: {}", Thread.currentThread().getName());
        // Output: "Background thread: ForkJoinPool.commonPool-worker-1"
        return domesticMipService.getAccessoriesForSingleAppliance(...);
    });
}
```

**Thread names tell you:**
- `dw-*` = Dropwizard servlet thread
- `qtp*` = Jetty servlet thread (QueuedThreadPool)
- `ForkJoinPool.commonPool-worker-*` = Background worker thread
- `pool-*-thread-*` = Custom ExecutorService thread

**Key Point:** The thread name shows you which pool it came from, but they're all regular Java threads!

---

## Threads and Hardware Resources

### Does a Thread Correspond to a Hardware Resource?

**Short answer:** No, not directly. Threads are **software abstractions**, not hardware resources.

**The Relationship:**
- **CPU Cores** = Physical hardware (e.g., 6 CPU cores)
- **Threads** = Software workers that can run on those cores
- **OS Scheduler** = Decides which thread runs on which core

**Key Insight:**
- You can have **many more threads than CPU cores**
- The OS **time-slices** threads - each thread gets a small time slice on a core
- When a thread is **blocked** (waiting for I/O), the OS switches to another thread
- This is why you can have 200+ threads on a 6-core CPU!

**Example from appliance-twin:**
```
Hardware: 6 CPU cores
Threads in use:
  - Servlet threads (Spring Boot): ~200 (default)
  - WebSocket processor pool: 50 threads
  - Scheduled executor: 4 threads
  - WebSocket epoll pool: 24 threads
  - ForkJoinPool: 4 threads (parallelism=4)
  - Database connection pool: 100 threads
  - System threads: ~10-20 (GC, etc.)
  
Total: ~400 threads on 6 CPU cores!
```

**How it works:**
- Most threads are **blocked** (waiting for I/O, network, database)
- Only a few threads are **actually running** at any moment
- OS scheduler switches between threads very quickly (milliseconds)
- This gives the illusion of parallel execution

**When threads DO correspond to hardware:**
- **CPU-bound tasks** (calculations, processing) - one thread per core is optimal
- **Too many CPU-bound threads** = context switching overhead = slower!

**For I/O-bound tasks (like your SOAP calls):**
- Many threads are fine because they're mostly waiting
- Blocked threads don't consume CPU
- More threads = better throughput (up to a point)

---

## Thread Pools

### What is a Thread Pool?

A **thread pool** is a collection of reusable threads. Instead of creating/destroying threads constantly, you reuse them.

**Without thread pool (BAD):**
```java
// Creates new thread every time - EXPENSIVE!
new Thread(() -> doWork()).start();
new Thread(() -> doWork()).start();
new Thread(() -> doWork()).start();
// Each thread creation costs ~1ms
```

**With thread pool (GOOD):**
```java
// Reuses existing threads - EFFICIENT!
executorService.execute(() -> doWork());
executorService.execute(() -> doWork());
executorService.execute(() -> doWork());
// Threads already exist, just reuse them
```

### Real-World Example: Appliance-Twin Thread Pools

**Production Configuration (eu-prod-dom-1.yaml):**
```yaml
resources:
  limits:
    cpu: 6          # 6 CPU cores
    memory: 6Gi     # 6 GB RAM
    
miele:
  appliance-twin:
    thread-pool:
      size: 50      # WebSocket processor pool: 50 threads
  websocket:
    epollThreadPoolSize: 24  # Netty WebSocket pool: 24 threads (4 * CPU cores)
    
spring:
  datasource:
    hikari:
      maximum-pool-size: 100  # Database connection pool: 100 threads
```

**Total Threads Breakdown:**
- **Servlet threads (Spring Boot default):** ~200 threads
- **WebSocket processor pool:** 50 threads
- **Scheduled executor:** 4 threads (default)
- **WebSocket epoll pool:** 24 threads
- **ForkJoinPool.commonPool:** 4 threads (parallelism=4)
- **Database connection pool:** 100 threads
- **System threads (JVM):** ~10-20 threads

**Total: ~390 threads on 6 CPU cores!**

**Why this works:**
- Most threads are **blocked** (waiting for network, database, I/O)
- Only a few threads are **actively using CPU** at any moment
- OS scheduler efficiently switches between threads
- This allows handling many concurrent requests

**Thread Pool Sizing Rules:**
- **CPU-bound tasks:** Threads ≈ CPU cores (or cores × 2)
- **I/O-bound tasks:** Threads can be much higher (50-200+)
- **Database connections:** Usually 10-100 (depends on database capacity)
- **Servlet threads:** Usually 200-500 (depends on expected load)

### ForkJoinPool.commonPool()

`CompletableFuture.supplyAsync()` uses `ForkJoinPool.commonPool()` by default:

```java
// This uses ForkJoinPool.commonPool()
CompletableFuture.supplyAsync(() -> doWork());

// Equivalent to:
CompletableFuture.supplyAsync(() -> doWork(), ForkJoinPool.commonPool());
```

**Characteristics:**
- Size: `Runtime.getRuntime().availableProcessors() - 1` (usually 7 threads on 8-core CPU)
- Optimized for: CPU-bound tasks (computation)
- **Not ideal for**: I/O-bound tasks (network calls, database)

**For I/O-bound tasks, use a dedicated executor:**
```java
ExecutorService ioExecutor = Executors.newFixedThreadPool(32);

CompletableFuture.supplyAsync(() -> doWork(), ioExecutor);
```

---

## CompletableFuture Basics

### What is CompletableFuture?

`CompletableFuture` represents a **future result** - a value that will be available later.

**Synchronous (blocking):**
```java
String result = doSlowWork(); // Blocks until done
System.out.println(result);   // Can use result immediately
```

**Asynchronous (non-blocking):**
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> doSlowWork());
// Returns immediately! Work happens in background
// Can't use result yet - it's not ready

future.thenAccept(result -> {
    // This runs when result is ready
    System.out.println(result);
});
```

### Key Methods

**1. `supplyAsync()` - Start async work:**
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return "Hello"; // This runs in background thread
});
```

**2. `thenApply()` - Transform result:**
```java
CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");
CompletableFuture<String> upper = future.thenApply(s -> s.toUpperCase());
// "Hello" → "HELLO"
```

**3. `thenAccept()` - Do something with result:**
```java
future.thenAccept(result -> System.out.println(result));
```

**4. `exceptionally()` - Handle errors:**
```java
future.exceptionally(ex -> {
    System.out.println("Error: " + ex.getMessage());
    return "Default value";
});
```

### Your Code Explained

```java
return CompletableFuture.supplyAsync(() -> {
    // This lambda runs in a BACKGROUND thread (from ForkJoinPool)
    var result = domesticMipService.getAccessoriesForSingleAppliance(...);
    return new AccessoriesForSingleApplianceMieleTO(...);
});
```

**What happens:**
1. `supplyAsync()` is called → returns `CompletableFuture` immediately
2. Servlet thread is freed → can handle other requests
3. Background thread executes the lambda
4. When SOAP call completes, `CompletableFuture` is completed with result
5. Jersey framework handles the result and sends HTTP response

---

## Thread-Local Storage (MDC Context)

### What is Thread-Local Storage?

**Thread-local storage** is data that is **unique to each thread**. Each thread has its own copy.

**Regular variable (shared):**
```java
String userId = "user123"; // ALL threads see same value
```

**Thread-local variable (per-thread):**
```java
ThreadLocal<String> userId = new ThreadLocal<>();
userId.set("user123"); // Only THIS thread sees "user123"
// Other threads see null (or their own value)
```

### MDC (Mapped Diagnostic Context)

**MDC** is SLF4J's thread-local storage for logging context:

```java
MDC.put("userId", "user123");
MDC.put("requestId", "req-456");
logger.info("Processing request"); // Logs include userId and requestId
```

**Problem with async:**
```java
// Servlet thread
MDC.put("userId", "user123");
MDC.put("requestId", "req-456");

CompletableFuture.supplyAsync(() -> {
    // Background thread - DIFFERENT thread!
    logger.info("Processing"); // MDC is EMPTY! No userId, no requestId
    // Thread-local storage is NOT shared between threads!
});
```

**Solution: Copy MDC context:**
```java
// Servlet thread
MDC.put("userId", "user123");
Map<String, String> contextCopy = MDC.getCopyOfContextMap(); // Copy it!

CompletableFuture.supplyAsync(() -> {
    MDC.setContextMap(contextCopy); // Restore in background thread
    logger.info("Processing"); // Now has userId and requestId!
    MDC.clear(); // Clean up
});
```

### Your Code Issue

**Current code (missing MDC context):**
```java
try (final var ignored = LoggingContextCookie.create().mieleDeviceId(fabNumber)...) {
    // MDC context is set here (in servlet thread)
    return CompletableFuture.supplyAsync(() -> {
        // Background thread - MDC context is LOST!
        // Logs won't have mieleDeviceId, mieleOrganisation
    });
}
```

**Fixed code (with MDC context):**
```java
try (final var ignored = LoggingContextCookie.create().mieleDeviceId(fabNumber)...) {
    final var copyOfContextMap = MDC.getCopyOfContextMap(); // Copy context
    
    return CompletableFuture.supplyAsync(() -> {
        try {
            MDC.setContextMap(copyOfContextMap); // Restore context
            // Now logs will have mieleDeviceId, mieleOrganisation
            var result = domesticMipService.getAccessoriesForSingleAppliance(...);
            return new AccessoriesForSingleApplianceMieleTO(...);
        } finally {
            MDC.clear(); // Always cleanup
        }
    });
}
```

---

## When to Use Async (And When NOT To!)

### ✅ Use Async For:

1. **I/O-bound operations** (network, database, file):
   ```java
   // SOAP call - takes 500ms
   CompletableFuture.supplyAsync(() -> soapService.call());
   ```

2. **Long-running operations:**
   ```java
   // Image processing - takes 2 seconds
   CompletableFuture.supplyAsync(() -> processImage());
   ```

3. **Independent operations** (can run in parallel):
   ```java
   CompletableFuture<String> user = getUserAsync();
   CompletableFuture<String> config = getConfigAsync();
   CompletableFuture.allOf(user, config).thenRun(() -> {
       // Both done, use results
   });
   ```

### ❌ Don't Use Async For:

1. **CPU-bound, fast operations:**
   ```java
   // Simple calculation - 1ms
   int sum = a + b; // Don't async this!
   ```

2. **Operations that must be sequential:**
   ```java
   // Step 2 depends on Step 1 result
   var step1 = doStep1();
   var step2 = doStep2(step1); // Must wait for step1
   ```

3. **Operations that need immediate result:**
   ```java
   // Need result NOW, can't wait
   if (isValid(input)) {
       return "OK";
   }
   ```

## Why Async Isn't Used Everywhere

### The Hidden Costs of Async

**Async has overhead!** It's not free:

1. **Thread switching overhead:**
   - Context switching between threads costs ~1-10 microseconds
   - For fast operations (<1ms), this overhead can be **larger than the operation itself!**

2. **Memory overhead:**
   - Each thread uses ~1MB of stack space
   - More threads = more memory
   - CompletableFuture objects use memory

3. **Complexity:**
   - Harder to debug (stack traces across threads)
   - Harder to test
   - MDC context must be copied
   - Exception handling is more complex

4. **No benefit for fast operations:**
   - If operation takes 1ms, async adds overhead without benefit
   - Servlet thread would be blocked for 1ms anyway (negligible)

### Real Example: Your Codebase

**Most resources use `completedFuture()` (synchronous):**
```java
// DocumentsMipResourceV2 - Fast operation, no async needed
return CompletableFuture.completedStage(
    hygieneDocumentMapStructMapper.mapLegacyTOtoOpenApiTO(
        domesticMieleBackend.getHygieneAssistantDocumentByDocumentId(...)
    )
);
// This is just wrapping a fast operation in CompletionStage for API compatibility
```

**Only `AccessoriesMipResourceV2` uses `supplyAsync()`:**
```java
// Slow SOAP call - async makes sense here
return CompletableFuture.supplyAsync(() -> {
    return domesticMipService.getAccessoriesForSingleAppliance(...); // 500ms
});
```

### The Math: When Async Helps vs Hurts

**Fast operation (1ms):**
```
Synchronous:
  Servlet thread: [1ms work] → Done
  Total: 1ms

Asynchronous:
  Servlet thread: [0.1ms setup] → Free
  Background thread: [1ms work] → Done
  Total: 1.1ms + overhead
  
Result: Async is SLOWER! ❌
```

**Slow operation (500ms):**
```
Synchronous:
  Servlet thread: [500ms blocked] → Done
  Total: 500ms
  Can't handle other requests during this time!

Asynchronous:
  Servlet thread: [0.1ms setup] → Free (can handle 4999 other requests!)
  Background thread: [500ms work] → Done
  Total: 500.1ms
  
Result: Async is MUCH BETTER! ✅
```

### Rule of Thumb

**Use async when:**
- Operation takes >10ms
- Operation is I/O-bound (network, database, file)
- You have many concurrent requests
- Operation can block servlet threads

**Don't use async when:**
- Operation takes <1ms
- Operation is CPU-bound and fast
- You need the result immediately
- The overhead would be larger than the benefit

### Why Most Code Uses `completedFuture()`

Looking at your codebase, most resources use:
```java
return CompletableFuture.completedFuture(result);
```

**Why?**
- The operation is **already fast** (in-memory transformation, cache lookup)
- No I/O involved
- Async would add overhead without benefit
- They return `CompletionStage` for **API compatibility** (OpenAPI spec requires it)
- But the work is done synchronously

**Only use `supplyAsync()` when:**
- The operation is **actually slow** (like your SOAP call)
- The operation **blocks** (network, database)
- You want to **free the servlet thread** immediately

---

## Performance Implications

### Understanding Throughput Calculations

**Throughput** = How many requests can be processed per second

**Formula:**
```
Throughput = (Number of Threads) / (Time per Request)
```

**Example Calculation:**
```
Servlet thread pool: 50 threads
Each request blocks for: 500ms (0.5 seconds)

Step 1: How many requests can ONE thread handle per second?
  - 1 request takes 0.5 seconds
  - In 1 second, 1 thread can handle: 1 / 0.5 = 2 requests/second

Step 2: How many requests can ALL threads handle per second?
  - 50 threads × 2 requests/second = 100 requests/second

Max throughput = 100 requests/second
```

**Visual Timeline (50 threads, 500ms per request):**
```
Time:  0ms    500ms   1000ms   1500ms   2000ms
       |-------|-------|-------|-------|
Thread 1: [Req1] → [Req3] → [Req5] → [Req7] → ...
Thread 2: [Req2] → [Req4] → [Req6] → [Req8] → ...
...
Thread 50: [Req50] → [Req100] → [Req150] → ...

At 1000ms: All 50 threads have completed 2 requests each = 100 requests total
```

**What happens with 101 requests?**
```
Requests 1-100: Handled immediately by 50 threads
Request 101: Must WAIT in queue until a thread becomes available
  - Thread 1 finishes at 500ms → Request 101 starts at 500ms
  - Request 101 completes at 1000ms
  - Total wait time for request 101: 500ms
```

**Key Insight:**
- Throughput is limited by: `Threads / Time_per_Request`
- If requests arrive faster than throughput, they queue up
- Queue size grows until requests arrive slower than throughput

### Synchronous (Blocking) - Your Original Code

```java
public CompletionStage<Result> getAccessories(...) {
    var result = domesticMipService.getAccessoriesForSingleAppliance(...); // 500ms
    return CompletableFuture.completedFuture(result);
}
```

**Performance Calculation:**
- Servlet thread pool: 50 threads
- Each request blocks for: 500ms (0.5 seconds)
- **Max throughput: 50 threads / 0.5s = 100 requests/second**
- If 101 requests arrive in 1 second, 1 must wait in queue

### Asynchronous (Non-Blocking) - Your Current Code

```java
public CompletionStage<Result> getAccessories(...) {
    return CompletableFuture.supplyAsync(() -> {
        return domesticMipService.getAccessoriesForSingleAppliance(...); // 500ms
    });
}
```

**Performance Calculation:**

**Servlet Thread Throughput:**
- Servlet thread pool: 50 threads
- Each request takes: <1ms (just returns CompletableFuture)
- Servlet thread throughput: 50 threads / 0.001s = **50,000 requests/second** (theoretical max)

**Background Thread Throughput:**
- Background thread pool: 7 threads (ForkJoinPool.commonPool)
- Each SOAP call takes: 500ms
- Background thread throughput: 7 threads / 0.5s = **14 requests/second**

**Actual Max Throughput:**
- Limited by the **slowest part**: Background threads
- **Max throughput: 14 requests/second** (bottleneck is background threads)
- But servlet threads can handle **many more requests** without blocking!

**Real Scenario: 1000 requests arrive in 1 second**

**Synchronous:**
```
Requests 1-50: Start immediately (all threads busy)
Requests 51-1000: Wait in queue
After 500ms: Requests 1-50 complete, requests 51-100 start
After 1000ms: Requests 51-100 complete, requests 101-150 start
...
Total time to process 1000 requests: 1000 / 100 = 10 seconds
```

**Asynchronous:**
```
All 1000 requests: Servlet threads return immediately (<1ms each)
Background threads: Process 7 requests at a time
After 500ms: 7 requests complete, next 7 start
After 1000ms: 14 requests complete, next 7 start
...
Total time to process 1000 requests: 1000 / 14 = ~71 seconds
```

**Wait, that's slower!** 🤔

**But here's the key difference:**
- **Synchronous:** Servlet threads are blocked, can't handle NEW requests
- **Asynchronous:** Servlet threads are FREE, can handle NEW requests immediately

**Real-world benefit:**
- If requests arrive at 10/second (sustainable rate):
  - Synchronous: Can handle 100/second (plenty of headroom)
  - Asynchronous: Can handle 14/second (but servlet threads stay free)
- If requests arrive in bursts:
  - Synchronous: Servlet threads blocked, new requests must wait
  - Asynchronous: Servlet threads accept all requests immediately, background threads process them

**The real win:** Servlet threads stay available for other endpoints, health checks, etc.

---

## Common Pitfalls

### 1. Forgetting MDC Context

**BAD:**
```java
MDC.put("userId", "123");
CompletableFuture.supplyAsync(() -> {
    logger.info("Processing"); // No userId in logs!
});
```

**GOOD:**
```java
MDC.put("userId", "123");
var context = MDC.getCopyOfContextMap();
CompletableFuture.supplyAsync(() -> {
    MDC.setContextMap(context);
    logger.info("Processing"); // Has userId!
    MDC.clear();
});
```

### 2. Blocking in Async Code

**BAD:**
```java
CompletableFuture.supplyAsync(() -> {
    var result = blockingCall(); // Still blocks background thread!
    return result;
});
```

**Note:** This is OK if you have enough background threads. But if all background threads are blocked, you have the same problem.

### 3. Not Handling Exceptions

**BAD:**
```java
CompletableFuture.supplyAsync(() -> {
    return riskyOperation(); // Exception might be lost!
});
```

**GOOD:**
```java
CompletableFuture.supplyAsync(() -> {
    return riskyOperation();
}).exceptionally(ex -> {
    logger.error("Error", ex);
    return defaultValue;
});
```

### 4. Using Wrong Thread Pool

**BAD (for I/O):**
```java
// ForkJoinPool is for CPU-bound tasks
CompletableFuture.supplyAsync(() -> soapCall()); // Not ideal
```

**GOOD (for I/O):**
```java
ExecutorService ioExecutor = Executors.newFixedThreadPool(32);
CompletableFuture.supplyAsync(() -> soapCall(), ioExecutor);
```

---

## Summary: Key Takeaways

1. **Threads** = Workers that can do tasks simultaneously
2. **Blocking** = Thread waits (idle) during operation
3. **Async** = Work happens in background, thread freed immediately
4. **Thread pools** = Reusable threads (efficient)
5. **CompletableFuture** = Represents future result
6. **MDC/Thread-local** = Per-thread data (must copy for async)
7. **Use async for I/O**, not for fast CPU operations
8. **Always cleanup MDC** in finally block

---

## Your Code - Final Recommendation

```java
@Override
public CompletionStage<AccessoriesForSingleApplianceMieleTO> getAccessories(...) {
    LOGGER.debug("GET /internal/v2/accessories/{fabNumber} was called...");
    
    try (final var ignored = LoggingContextCookie.create()
            .mieleDeviceId(fabNumber)
            .mieleOrganisation(vgIso)) {
        
        // Copy MDC context for async thread
        final var copyOfContextMap = MDC.getCopyOfContextMap();
        
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Restore MDC context in background thread
                if (copyOfContextMap != null) {
                    MDC.setContextMap(copyOfContextMap);
                }
                
                // SOAP call (blocking, but in background thread)
                final var result = domesticMipService.getAccessoriesForSingleAppliance(
                        new Vg(vgIso), acceptLanguage, fabNumber, matNo, accessoryType);
                
                return new AccessoriesForSingleApplianceMieleTO(
                        result.getDeviceId(),
                        result.getMaterialNo(),
                        result.getAccessories().toJavaStream()
                                .map(accessory -> new AccessoryMieleTO(
                                        accessory.getAccessoryType(),
                                        accessory.getMaterialNo()))
                                .toList()
                );
            } finally {
                // Always cleanup MDC (prevents leaks)
                MDC.clear();
            }
        });
    }
}
```

**Why this is good:**
- ✅ Servlet thread freed immediately
- ✅ MDC context preserved for logging
- ✅ Exceptions propagate to mappers
- ✅ MDC always cleaned up
- ✅ Better performance under load

