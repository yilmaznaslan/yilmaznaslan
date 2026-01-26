# CompletableFuture Cheat Sheet

## Core Methods

### Starting Async Work
- **`supplyAsync(Supplier)`** - Start async work, returns `CompletableFuture<T>`
- **`runAsync(Runnable)`** - Start async work, returns `CompletableFuture<Void>`
- **`supplyAsync(Supplier, Executor)`** - Start with custom thread pool

### Transforming Values
- **`thenApply(Function)`** - Transform value: `T → U`, returns `CompletableFuture<U>`
- **`thenCompose(Function)`** - Flatten nested futures: `T → CompletableFuture<U>`, returns `CompletableFuture<U>`

### Consuming Values
- **`thenAccept(Consumer)`** - Consume value, returns `CompletableFuture<Void>`
- **`thenRun(Runnable)`** - Run after completion (no value), returns `CompletableFuture<Void>`

### Combining Futures
- **`thenCombine(CompletableFuture, BiFunction)`** - Combine two futures: `(T, U) → V`
- **`thenCombineBoth(CompletableFuture, BiFunction)`** - Same as thenCombine

### Multiple Futures
- **`allOf(CompletableFuture...)`** - Wait for ALL to complete, returns `CompletableFuture<Void>`
- **`anyOf(CompletableFuture...)`** - Wait for ANY to complete, returns `CompletableFuture<Object>`

### Error Handling
- **`exceptionally(Function)`** - Handle error, return default: `Throwable → T`
- **`handle(BiFunction)`** - Handle both success and error: `(T, Throwable) → U`
- **`whenComplete(BiConsumer)`** - Always runs (like finally): `(T, Throwable) → void`

### Blocking (Getting Results)
- **`join()`** - Block until complete, throws unchecked `CompletionException`
- **`get()`** - Block until complete, throws checked `ExecutionException`, `InterruptedException`
- **`get(timeout, TimeUnit)`** - Block with timeout, throws `TimeoutException`

### Timeout
- **`orTimeout(timeout, TimeUnit)`** - Complete with `TimeoutException` if too slow
- **`completeOnTimeout(value, timeout, TimeUnit)`** - Return default value on timeout

### Async Variants
All methods have async variants that run in a different thread:
- `thenApplyAsync()` - Runs in ForkJoinPool
- `thenAcceptAsync()` - Runs in ForkJoinPool
- `thenComposeAsync()` - Runs in ForkJoinPool
- `thenCombineAsync()` - Runs in ForkJoinPool

## Common Patterns

### Pattern 1: Simple Chain
```java
CompletableFuture.supplyAsync(() -> fetchData())
    .thenApply(data -> transform(data))
    .thenAccept(result -> consume(result))
    .join();
```

### Pattern 2: Error Handling
```java
CompletableFuture.supplyAsync(() -> riskyOperation())
    .exceptionally(ex -> {
        logger.error("Error", ex);
        return defaultValue;
    })
    .thenAccept(result -> use(result))
    .join();
```

### Pattern 3: Parallel Execution
```java
CompletableFuture<String> user = fetchUserAsync();
CompletableFuture<String> config = fetchConfigAsync();

user.thenCombine(config, (u, c) -> combine(u, c))
    .thenAccept(result -> use(result))
    .join();
```

### Pattern 4: Wait for All
```java
CompletableFuture.allOf(task1, task2, task3)
    .thenRun(() -> {
        // All done
        var r1 = task1.join();
        var r2 = task2.join();
        var r3 = task3.join();
    })
    .join();
```

### Pattern 5: Flatten Nested Futures
```java
// BAD: Nested CompletableFuture
CompletableFuture<CompletableFuture<String>> bad = 
    fetchId().thenApply(id -> fetchUser(id));

// GOOD: Flattened
CompletableFuture<String> good = 
    fetchId().thenCompose(id -> fetchUser(id));
```

## Return Types Summary

| Method | Returns | Purpose |
|--------|---------|---------|
| `supplyAsync()` | `CompletableFuture<T>` | Start work, produce value |
| `thenApply()` | `CompletableFuture<U>` | Transform value |
| `thenCompose()` | `CompletableFuture<U>` | Flatten nested future |
| `thenAccept()` | `CompletableFuture<Void>` | Consume value |
| `thenRun()` | `CompletableFuture<Void>` | Run after completion |
| `thenCombine()` | `CompletableFuture<V>` | Combine two futures |
| `allOf()` | `CompletableFuture<Void>` | Wait for all |
| `anyOf()` | `CompletableFuture<Object>` | Wait for any |
| `exceptionally()` | `CompletableFuture<T>` | Handle error |
| `handle()` | `CompletableFuture<U>` | Handle success/error |

