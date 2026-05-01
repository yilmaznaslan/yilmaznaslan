## Virtual Threads in Java

Virtual threads are **lightweight threads managed by the Java runtime**, introduced as a preview in Java 19 and standardized in Java 21. They are designed to make **concurrent, blocking-style code scale to thousands or even millions of tasks** without the high cost of traditional platform threads.

### Key ideas

- **Platform thread**: A traditional Java thread, usually mapped 1:1 to an OS thread. Creating many of these is expensive in memory and scheduling overhead.
- **Virtual thread**: A thread whose **scheduling is managed by the JVM**, not directly by the OS. Many virtual threads can be **multiplexed** onto a small pool of platform threads.
- **Structured concurrency**: A style of organizing concurrent tasks in a tree-like scope so that you can **join, cancel, and handle errors** in a predictable way.

With virtual threads:
- You can **keep your familiar blocking APIs** (`Socket`, `InputStream`, JDBC, HTTP clients, etc.).
- The JVM can **park** a virtual thread when it blocks (e.g., waiting for I/O) and **run another virtual thread** on the same carrier (platform) thread.
- This makes it practical to have **one thread per request / per task** again, without the typical thread-per-request limitations.

---

### I/O-bound vs CPU-bound: why it matters for virtual threads

**I/O-bound work** is work where the task spends most of its time **waiting** for something outside the CPU:

- **Input/Output**: reading from or writing to disk, network, database, or another service.
- The thread is often **blocked** (e.g. `socket.read()`, `resultSet.next()`, HTTP client call). During that wait, the CPU is mostly idle; the bottleneck is **I/O latency or throughput**, not CPU cycles.

**Examples of I/O-bound work:**

- HTTP request/response (waiting on the network).
- Database queries (waiting on DB and network).
- Reading/writing files.
- Calling external APIs or message queues.
- Any blocking call where “something else” (disk, network, another process) must respond.

**The opposite of I/O-bound is CPU-bound work.** A task is **CPU-bound** when:

- Most of the time is spent **doing computations on the CPU** (math, parsing, compression, encryption, image/video processing, simulations).
- The bottleneck is **CPU capacity** (cores and their speed), not waiting on I/O.
- Adding more threads does not help much beyond the number of cores; often it just adds context-switching and contention.

**Why virtual threads are ideal for I/O-bound workloads:**

1. **They park when blocked.** When a virtual thread blocks on I/O (e.g. `read()`, `get()` on a Future, `Thread.sleep()`), the JVM **parks** that virtual thread and does **not** hold onto a platform (OS) thread. Another virtual thread can run on the same carrier thread. So you can have 10,000 concurrent “waiting” tasks with only a small pool of real threads.
2. **Scaling matches the workload.** I/O-bound tasks spend a lot of time waiting; virtual threads scale with “number of concurrent waits,” not “number of CPU cores.” That’s exactly where virtual threads shine.
3. **You keep simple blocking code.** You don’t need to rewrite everything to async/ reactive style; the JVM uses the blocking points to multiplex.

**Why virtual threads are not a substitute for parallelism in CPU-bound work:**

- A CPU-bound task needs to **run on a core**. If you have 8 cores and 10,000 CPU-bound virtual threads, you still only do 8 things at a time at the CPU level; the rest are just waiting. Virtual threads don’t add more CPU capacity.
- For CPU-bound work you still need to **limit concurrency** (e.g. to the number of cores or a small multiple) and possibly use a bounded pool or structured concurrency, so you don’t oversubscribe the CPU.

**Rule of thumb:**

| Workload type | Bottleneck        | Virtual threads help? | Typical approach                          |
|---------------|-------------------|------------------------|-------------------------------------------|
| **I/O-bound** | Network, disk, DB | Yes, very much         | Many virtual threads, blocking APIs       |
| **CPU-bound** | CPU cores         | Limited                | Bounded parallelism (e.g. pool size ≈ cores) |

---

### When to use virtual threads

- **I/O-bound** workloads:
  - HTTP servers
  - Database access
  - Message processing
  - File/network I/O
- Places where you currently use:
  - Large thread pools
  - Complex asynchronous callback chains or `CompletableFuture` pipelines

They are **not a silver bullet** for CPU-bound code. If your task is CPU-heavy, you still need to consider:
- Limiting concurrency (e.g., via structured concurrency or semaphores)
- Not oversubscribing CPU cores with too many active CPU-bound tasks

---

## Basic virtual thread APIs

### 1. Creating a single virtual thread

```java
Thread.startVirtualThread(() -> {
    System.out.println("Running in a virtual thread: " + Thread.currentThread());
});
```

- This is the simplest way to **fire-and-forget** a virtual thread that runs a `Runnable`.

### 2. Creating many virtual threads

```java
for (int i = 0; i < 10_000; i++) {
    int taskId = i;
    Thread.startVirtualThread(() -> {
        try {
            Thread.sleep(1000); // Simulate blocking I/O
            System.out.println("Task " + taskId + " done in " + Thread.currentThread());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    });
}
```

- Creating **thousands** of virtual threads like this is usually fine.
- Doing the same with platform threads would be **very expensive** and often impossible in production.

### 3. Virtual thread executors

Java provides an executor factory that uses **a new virtual thread per task**:

```java
try (var executor = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> {
        System.out.println("Running in: " + Thread.currentThread());
    });
}
```

- This is useful when you want to **integrate virtual threads with existing code** that expects an `ExecutorService`.

---

## Example: Blocking-style I/O using virtual threads

Imagine a typical blocking operation like calling a remote HTTP API or a database query:

```java
Thread.startVirtualThread(() -> {
    // Pseudo-code for a blocking remote call
    String result = blockingHttpCall("https://api.example.com/data");
    System.out.println("Received: " + result + " on " + Thread.currentThread());
});
```

- With platform threads, creating one thread per request does not scale well.
- With virtual threads, you can **write this blocking code naturally**, and the JVM will:
  - Park the virtual thread while it waits for I/O.
  - Run other virtual threads on the same carrier thread.

---

## Example class in this project

This lab includes a runnable example:

- `com.yilmaznaslan.threads.basics.VirtualThreadsExample`

### What the example does

- Starts a **fixed number of platform threads** for comparison.
- Starts **many virtual threads** that simulate blocking work with `Thread.sleep`.
- Prints the current thread type and name so you can see:
  - That each task is running on a virtual thread.
  - How they map onto a smaller number of carrier (platform) threads.

### How to run the example

From the `threads-and-async` project directory:

```bash
# Make sure your JAVA_HOME is Java 25
export JAVA_HOME=/Users/yilmaznaciaslan/Library/Java/JavaVirtualMachines/openjdk-25.0.2/Contents/Home
export PATH="$JAVA_HOME/bin:$PATH"

# Build the project
./gradlew clean build

# Run the virtual threads example
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.basics.VirtualThreadsExample
```

You should see output similar to:

```text
[virtual] Task 1 running on VirtualThreadsExample-virtual-1
[virtual] Task 2 running on VirtualThreadsExample-virtual-2
...
```

Try increasing the number of virtual threads in the example and observe that:
- The application still starts quickly.
- Memory usage remains reasonable.
- Threads are scheduled efficiently even when there are many concurrent tasks.

---

## Good practices with virtual threads

- **Use for I/O-bound work**:
  - Network calls, DB access, file operations.
- **Use structured concurrency APIs** (e.g., `java.util.concurrent.StructuredTaskScope`) when you:
  - Want to run multiple tasks in parallel.
  - Need to cancel all tasks if one fails.
  - Need to collect results in a controlled way.
- **Avoid CPU-bound overload**:
  - Many CPU-heavy virtual threads will still compete for the same cores.
  - Limit concurrency for CPU-heavy sections.

---

## Summary

- **Virtual threads** make it practical to write **simple, blocking-style code** that still scales to **massive concurrency**.
- They are ideal for **I/O-bound** workloads (HTTP servers, DB access, messaging systems).
- This project’s `VirtualThreadsExample` class gives you a **hands-on demo** you can run with Java 25 to see them in action.

