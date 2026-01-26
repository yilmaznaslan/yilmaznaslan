# Java Threads and Async Practice

Practice exercises for Java threads, multithreading, and asynchronous programming.

## Structure

**One project for all exercises** - organized by topic packages:
- `src/main/java/com/yilmaznaslan/threads/` - Practice code organized by topic
  - `basics/` - Thread creation, lifecycle examples
  - `threadpools/` - ExecutorService, ForkJoinPool examples
  - `completablefuture/` - Async programming examples
  - `blocking/` - Blocking vs non-blocking examples
  - `mdc/` - MDC context propagation examples
  - `performance/` - Performance testing examples
- `src/test/java/` - Unit tests for practice exercises

## Topics Covered

Based on `../THREADS_AND_ASYNC_GUIDE.md`:

1. Thread basics and lifecycle
2. Thread pools (ExecutorService, ForkJoinPool)
3. CompletableFuture
4. Blocking vs non-blocking operations
5. MDC context propagation
6. Performance implications
7. Common pitfalls

## Running Examples

### Method 1: Using Gradle (Recommended)

**Run the default example:**
```bash
./gradlew run
```

**Run a specific example:**
```bash
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.basics.ThreadBasicsExample
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.completablefuture.CompletableFutureExample
```

### Method 2: Using your IDE (Easiest for development)

**IntelliJ IDEA:**
1. Open the project
2. Right-click on any class with `main()` method
3. Select "Run 'ClassName'"

**VS Code:**
1. Install Java Extension Pack
2. Open any `.java` file with `main()` method
3. Click "Run" button above the main method
4. Or press `F5` to debug

### Method 3: Build and test

```bash
./gradlew build    # Compile everything
./gradlew test     # Run all tests
./gradlew clean    # Clean build artifacts
```

### Quick Reference

```bash
# Build the project
./gradlew build

# Run default example (ThreadBasicsExample)
./gradlew run

# Run specific example
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.basics.ThreadBasicsExample
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.completablefuture.CompletableFutureExample

# Run tests
./gradlew test

# Clean build
./gradlew clean build
```

### Examples Available

- `com.yilmaznaslan.threads.basics.ThreadBasicsExample` - Basic thread creation
- `com.yilmaznaslan.threads.completablefuture.CompletableFutureExample` - Async programming

