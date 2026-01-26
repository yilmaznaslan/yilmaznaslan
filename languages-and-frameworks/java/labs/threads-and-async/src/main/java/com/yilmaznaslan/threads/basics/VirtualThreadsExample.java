package com.yilmaznaslan.threads.basics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class VirtualThreadsExample {
    private static final Logger logger = LoggerFactory.getLogger(VirtualThreadsExample.class);

    public static void main(String[] args) {
        logger.info("Main thread: {}", Thread.currentThread());

        int taskCount = 1_000;
        int simulatedWorkMillis = 200;

        logger.info("Starting {} tasks with platform threads (fixed pool)...", taskCount);
        runWithPlatformThreads(taskCount, simulatedWorkMillis);

        logger.info("Starting {} tasks with virtual threads...", taskCount);
        runWithVirtualThreads(taskCount, simulatedWorkMillis);

        logger.info("VirtualThreadsExample finished.");
    }

    private static void runWithPlatformThreads(int taskCount, int simulatedWorkMillis) {
        Instant start = Instant.now();

        try (ExecutorService executor = Executors.newFixedThreadPool(50)) {
            IntStream.range(0, taskCount).forEach(i ->
                    executor.submit(() -> simulateBlockingWork("platform", i, simulatedWorkMillis))
            );
        }

        Duration duration = Duration.between(start, Instant.now());
        logger.info("Platform threads run completed in {} ms", duration.toMillis());
    }

    private static void runWithVirtualThreads(int taskCount, int simulatedWorkMillis) {
        Instant start = Instant.now();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, taskCount).forEach(i ->
                    executor.submit(() -> simulateBlockingWork("virtual", i, simulatedWorkMillis))
            );
        }

        Duration duration = Duration.between(start, Instant.now());
        logger.info("Virtual threads run completed in {} ms", duration.toMillis());
    }

    private static void simulateBlockingWork(String type, int taskId, int millis) {
        Thread current = Thread.currentThread();
        logger.debug("[{}] Task {} running on {} (isVirtual={})",
                type,
                taskId,
                current,
                current.isVirtual()
        );

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("[{}] Task {} interrupted", type, taskId, e);
        }
    }
}

