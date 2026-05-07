Java Lab - quick run instructions

This folder contains small demo classes to experiment with threads, executors, MDC propagation and CompletableFuture.

Run a specific demo with Gradle's `runExample` task. Examples live under `com.yilmaznaslan.threads.lab`.

Examples:
- `DemoLab` - a runnable main that demonstrates manual threads, fixed and bounded pools, a MDC-aware executor wrapper, CompletableFuture pipelines and a scheduled task.

Run DemoLab or interactive runner:
```bash
cd java-lab
# Run the interactive launcher (choose demo by number)
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.examples.ExampleRunner

# Or run a specific demo directly:
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.examples.ExamplesDemo
./gradlew runExample -PmainClass=com.yilmaznaslan.threads.basics.VirtualThreadsExample
./gradlew runExample -PmainClass=com.yilmaznaslan.lab.concurrancy.CompletableFutureCompleteGuide
```

Notes:
- The project uses Java 17 via the Gradle toolchain.
- The demo uses SLF4J MDC to show context propagation; output prints the MDC value and thread names.

