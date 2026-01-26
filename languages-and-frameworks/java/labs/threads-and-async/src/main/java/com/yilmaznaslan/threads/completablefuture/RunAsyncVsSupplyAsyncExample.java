package com.yilmaznaslan.threads.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class RunAsyncVsSupplyAsyncExample {
    private static final Logger logger = LoggerFactory.getLogger(RunAsyncVsSupplyAsyncExample.class);

    public static void main(String[] args) {
        logger.info("=== runAsync vs supplyAsync ===\n");

        demonstrateSupplyAsync();
        demonstrateRunAsync();
        demonstrateWhenToUseEach();
    }

    private static void demonstrateSupplyAsync() {
        logger.info("--- supplyAsync() - RETURNS a value ---");
        logger.info("Takes: Supplier<T> (function that returns a value)");
        logger.info("Returns: CompletableFuture<T>\n");

        final CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            logger.info("  supplyAsync executing in: {}", Thread.currentThread().getName());
            logger.info("  Doing some work...");
            return "Result from supplyAsync";
        });

        final String result = future.join();
        logger.info("  Got result: {}\n", result);
        logger.info("  Future type: CompletableFuture<String>\n");
    }

    private static void demonstrateRunAsync() {
        logger.info("--- runAsync() - NO return value ---");
        logger.info("Takes: Runnable (function that does work, returns nothing)");
        logger.info("Returns: CompletableFuture<Void>\n");

        final CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            logger.info("  runAsync executing in: {}", Thread.currentThread().getName());
            logger.info("  Doing some work (no return value)...");
        });

        future.join();
        logger.info("  Task completed (no value to get)\n");
        logger.info("  Future type: CompletableFuture<Void>\n");
    }

    private static void demonstrateWhenToUseEach() {
        logger.info("--- When to use each? ---\n");

        logger.info("Use supplyAsync() when you need a RESULT:");
        final CompletableFuture<String> data = CompletableFuture.supplyAsync(() -> {
            logger.info("  Fetching data from database...");
            return "User data";
        });
        logger.info("  Result: {}\n", data.join());

        logger.info("Use runAsync() when you just need to DO something (side effect):");
        CompletableFuture.runAsync(() -> {
            logger.info("  Sending email notification...");
            logger.info("  Logging audit event...");
        }).join();
        logger.info("  Task done (no result needed)\n");

        logger.info("--- Chaining Example ---\n");

        logger.info("supplyAsync can be chained (has value):");
        CompletableFuture.supplyAsync(() -> {
            logger.info("  Step 1: Fetch data");
            return 10;
        })
        .thenApply(n -> {
            logger.info("  Step 2: Transform: {} * 2 = {}", n, n * 2);
            return n * 2;
        })
        .thenAccept(result -> {
            logger.info("  Step 3: Use result: {}\n", result);
        })
        .join();

        logger.info("runAsync can be chained (but no value to transform):");
        CompletableFuture.runAsync(() -> {
            logger.info("  Step 1: Do something");
        })
        .thenRun(() -> {
            logger.info("  Step 2: Do something else (no value from step 1)\n");
        })
        .join();

        logger.info("--- Key Differences Summary ---");
        logger.info("supplyAsync:");
        logger.info("  - Input: Supplier<T> (returns T)");
        logger.info("  - Output: CompletableFuture<T>");
        logger.info("  - Use when: You need a result/value");
        logger.info("");
        logger.info("runAsync:");
        logger.info("  - Input: Runnable (returns void)");
        logger.info("  - Output: CompletableFuture<Void>");
        logger.info("  - Use when: You just need to do work (side effects)");
    }
}



