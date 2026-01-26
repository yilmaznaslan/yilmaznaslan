package com.yilmaznaslan.threads.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class SupplyAsyncVsThenAcceptExample {
    private static final Logger logger = LoggerFactory.getLogger(SupplyAsyncVsThenAcceptExample.class);

    public static void main(String[] args) {
        logger.info("=== Understanding supplyAsync vs thenAccept ===\n");

        demonstrateSupplyAsync();
        demonstrateThenAccept();
        demonstrateChaining();
    }

    private static void demonstrateSupplyAsync() {
        logger.info("--- supplyAsync() - STARTS async work ---");
        logger.info("Main thread: {}", Thread.currentThread().getName());

        final var future = CompletableFuture.supplyAsync(() -> {
            logger.info("Inside supplyAsync - Thread: {}", Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Result from supplyAsync";
        });

        logger.info("supplyAsync returned immediately! Future created.");
        logger.info("Main thread continues...\n");

        future.join();
    }

    private static void demonstrateThenAccept() {
        logger.info("--- thenAccept() - CONSUMES result (no return value) ---");

        final var future = CompletableFuture.supplyAsync(() -> {
            logger.info("SupplyAsync thread: {}", Thread.currentThread().getName());
            return "Hello World";
        });

        final var acceptFuture = future.thenAccept(result -> {
            logger.info("thenAccept thread: {}", Thread.currentThread().getName());
            logger.info("Consuming result: {}", result);
        });

        logger.info("thenAccept returns CompletableFuture<Void> - no value to return");
        acceptFuture.join();
        logger.info("");
    }

    private static void demonstrateChaining() {
        logger.info("--- Chaining supplyAsync + thenAccept ---");

        CompletableFuture.supplyAsync(() -> {
            logger.info("Step 1 - supplyAsync: {}", Thread.currentThread().getName());
            return 10;
        })
        .thenApply(number -> {
            logger.info("Step 2 - thenApply (transforms): {}", Thread.currentThread().getName());
            return number * 2;
        })
        .thenAccept(result -> {
            logger.info("Step 3 - thenAccept (consumes): {}", Thread.currentThread().getName());
            logger.info("Final result: {}", result);
        })
        .join();

        logger.info("\n=== Key Differences ===");
        logger.info("supplyAsync: STARTS async work, RETURNS a value");
        logger.info("thenAccept: CONSUMES a value, RETURNS nothing (CompletableFuture<Void>)");
        logger.info("thenApply: TRANSFORMS a value, RETURNS a new value");
    }
}

