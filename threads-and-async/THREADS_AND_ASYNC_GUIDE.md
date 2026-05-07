# Java Threads, Performance & Async — a concise guide

TL;DR
- Use async (CompletableFuture) for I/O-bound or long-running work so servlet/request threads are freed.
- Prefer a dedicated I/O executor (not ForkJoinPool.commonPool) for blocking I/O.
- Always copy MDC (logging context) into background threads and clear it after use.
- Don't async very fast (sub-ms) CPU-bound operations — the overhead outweighs the benefit.

## Quick contents
- What threads are
- Blocking vs non-blocking
- Thread pools & executors
- CompletableFuture: patterns and methods
- MDC (thread-local logging) and async
- When to use async (rules of thumb)
- Pitfalls and recommendations

---

## What is a thread (short)
A thread is a software worker. All Java threads are the same type — the difference is which pool created them and what they do (servlet/request thread vs background worker vs JVM/system thread).

Key point: servlet threads are a limited, relatively expensive resource managed by the container. Freeing them quickly (for I/O-bound work) improves throughput and responsiveness.

---

## Blocking vs non-blocking
- Blocking: the thread waits (network, DB, file I/O). Example: a 500ms SOAP call.
- Non-blocking (async): the request thread returns immediately and work runs on a background thread.

## Thread pools & executors (practical)

This section expands on how to create threads and thread pools, what happens internally and how to choose the right pool.

1) Manual thread creation (why it's usually a bad practice)

```java
new Thread(() -> {
    // do work
}).start();
```

- Creates a new OS-level thread with its own stack (memory cost).
- Thread creation and teardown are relatively expensive and unpredictable under load.
- Harder to monitor, reuse or limit concurrency. Prefer pools in server apps.

2) Thread pools with ExecutorService (recommended)

Basic usage:

```java
ExecutorService executor = Executors.newFixedThreadPool(10);

executor.submit(() -> {
    doWork();
});
```

What happens internally when you submit a task:
- The task is wrapped (if needed) and handed to the ThreadPoolExecutor.
- ThreadPoolExecutor decides: if the number of running threads < corePoolSize → create a new worker thread and run the task.
- Otherwise it tries to enqueue the task in the configured BlockingQueue.
- If the queue is full and running threads < maximumPoolSize → a new thread is created up to max.
- If queue is full and max threads reached → the RejectedExecutionHandler handles the task (abort, discard, caller-runs, etc.).

Short lifecycle for a task:
- Submit/execute → Task placed into queue or immediately executed by a new/idle thread → Worker picks task → Runs the task → Thread returns to pool and waits for new tasks.

Execute vs Submit
- `execute(Runnable)` submits a Runnable and returns void. It's the low-level API.
- `submit(...)` wraps the task and returns a `Future` (so you can wait or cancel). For `Callable<T>` you get a `Future<T>`.

Types of pools (convenience factories)
- Fixed: `Executors.newFixedThreadPool(n)` — stable pool with an unbounded queue (or bounded if you construct explicitly) — good default for predictable concurrency.
- Cached: `Executors.newCachedThreadPool()` — creates threads as needed and reuses idle threads; can grow without bound and is dangerous under sustained high load.
- Single-thread: `Executors.newSingleThreadExecutor()` — runs tasks sequentially on a single worker.
- Scheduled: `Executors.newScheduledThreadPool(n)` — schedules delayed or periodic tasks.

Note: the convenience factory methods can create ThreadPoolExecutors with default queue policies; for production you often want to construct `ThreadPoolExecutor` directly so you can control the queue type and rejection policy.

Controlling the queue and rejection policy (advanced)

```java
int core = 10;
int max = 50;
BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(100); // bounded queue
ThreadPoolExecutor pool = new ThreadPoolExecutor(
    core, max, 60L, TimeUnit.SECONDS, queue,
    new ThreadPoolExecutor.AbortPolicy() // or CallerRunsPolicy, DiscardOldestPolicy, etc.
);
```

- With a bounded queue you limit memory use and enable predictable backpressure. Choose an appropriate RejectedExecutionHandler for your behavior.

3) Futures and async execution

Classic Future example (blocks when you call get):

```java
Future<String> future = executor.submit(() -> {
    return "result";
});

String result = future.get(); // blocks until complete
```

Modern, composable approach with CompletableFuture:

```java
CompletableFuture.supplyAsync(() -> fetchData(), executor)
    .thenApply(data -> process(data))
    .thenAccept(result -> save(result));
```

- `CompletableFuture` enables non-blocking composition, error propagation and combining multiple async flows. Prefer it for building async pipelines.

Common questions answered
- "When creating a threadpool you don't manually control the queue then?"
  - You typically don't manage the queue when using convenience factories; the ThreadPoolExecutor you create has an internal BlockingQueue. If you need precise control, construct a ThreadPoolExecutor and pass your preferred BlockingQueue implementation.
- "How are tasks put into the queue? Submit?"
  - Yes: `execute()` and `submit()` pass tasks to the ThreadPoolExecutor which then enqueues or dispatches them according to its rules.
- "Are there only execute and submit methods?"
  - Those are the primary methods for task submission. There are also scheduling methods on `ScheduledExecutorService` (schedule, scheduleAtFixedRate) and variants for invoking collections of tasks (`invokeAll`, `invokeAny`).

Recommendation
- Prefer a named, instrumented ThreadPoolExecutor you construct explicitly for production services (bounded queue, sensible max threads, monitored metrics, appropriate rejection policy). Use `CompletableFuture` with that executor for clean, composable async logic.

Notes:
- Use a named, instrumented executor (`ioExecutor`) so you can monitor queue depth and thread usage.
- Prefer bounded queues and sensible rejection policies in production.

---

## Short summary
- Async is a tool — use it for I/O and long-running tasks. Use dedicated executors. Preserve MDC. Keep code simple and document why you chose async for each endpoint.

If you'd like, I can:
1) produce a shorter README (one-page cheat-sheet),
2) refactor this file in-place (apply a replacement with the concise version), or
3) create a small Java utility helper (MDC + CompletableFuture wrapper) and unit tests.

Tell me which of (1)-(3) you want me to do next and I'll implement it.
