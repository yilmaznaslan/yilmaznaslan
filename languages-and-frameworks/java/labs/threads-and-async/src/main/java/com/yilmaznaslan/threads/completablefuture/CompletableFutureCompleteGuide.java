package com.yilmaznaslan.threads.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureCompleteGuide {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureCompleteGuide.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        logger.info("=== CompletableFuture Complete Guide ===\n");

        demonstrateBasicMethods();
        demonstrateChaining();
        demonstrateComposition();
        demonstrateCombining();
        demonstrateMultipleFutures();
        demonstrateErrorHandling();
        demonstrateBlocking();
        demonstrateTimeout();
        demonstrateCustomExecutor();
    }

    private static void demonstrateBasicMethods() {
        logger.info("--- 1. BASIC METHODS ---\n");

        logger.info("supplyAsync() - Start async work, returns value:");
        final var future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Executing in: {}", Thread.currentThread().getName());
            return "Hello";
        });
        logger.info("  Result: {}\n", future1.join());

        logger.info("thenApply() - Transform value:");
        final var future2 = future1.thenApply(s -> {
            logger.info("  Transforming in: {}", Thread.currentThread().getName());
            return s.toUpperCase();
        });
        logger.info("  Result: {}\n", future2.join());

        logger.info("thenAccept() - Consume value (no return):");
        future2.thenAccept(s -> {
            logger.info("  Consuming in: {}", Thread.currentThread().getName());
            logger.info("  Value: {}\n", s);
        }).join();

        logger.info("thenRun() - Run after completion (no value needed):");
        CompletableFuture.supplyAsync(() -> "Task done")
                .thenRun(() -> logger.info("  Cleanup task executed\n"))
                .join();
    }

    private static void demonstrateChaining() {
        logger.info("--- 2. CHAINING METHODS ---\n");

        logger.info("Chaining: supplyAsync → thenApply → thenAccept → thenRun");
        CompletableFuture.supplyAsync(() -> {
            logger.info("  Step 1: Fetch data");
            return 10;
        })
        .thenApply(number -> {
            logger.info("  Step 2: Transform (multiply by 2)");
            return number * 2;
        })
        .thenAccept(result -> {
            logger.info("  Step 3: Consume result: {}", result);
        })
        .thenRun(() -> {
            logger.info("  Step 4: Final cleanup\n");
        })
        .join();
    }

    private static void demonstrateComposition() {
        logger.info("--- 3. COMPOSITION (thenCompose) ---\n");

        logger.info("thenCompose() - Flatten nested futures (like flatMap):");
        CompletableFuture.supplyAsync(() -> {
            logger.info("  Step 1: Get user ID");
            return 123;
        })
        .thenCompose(userId -> {
            logger.info("  Step 2: Fetch user details for ID: {}", userId);
            return CompletableFuture.supplyAsync(() -> "User-" + userId);
        })
        .thenAccept(user -> {
            logger.info("  Step 3: Got user: {}\n", user);
        })
        .join();

        logger.info("Without thenCompose (nested - BAD):");
        final var nested = CompletableFuture.supplyAsync(() -> 123)
                .thenApply(userId -> CompletableFuture.supplyAsync(() -> "User-" + userId));
        logger.info("  Result type: CompletableFuture<CompletableFuture<String>> - BAD!\n");

        logger.info("With thenCompose (flat - GOOD):");
        final var flat = CompletableFuture.supplyAsync(() -> 123)
                .thenCompose(userId -> CompletableFuture.supplyAsync(() -> "User-" + userId));
        logger.info("  Result type: CompletableFuture<String> - GOOD!\n");
    }

    private static void demonstrateCombining() {
        logger.info("--- 4. COMBINING FUTURES ---\n");

        logger.info("thenCombine() - Combine two independent futures:");
        final var future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Fetching user data...");
            return "John";
        });
        final var future2 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Fetching user config...");
            return "admin";
        });

        future1.thenCombine(future2, (user, role) -> {
            logger.info("  Combined: User={}, Role={}\n", user, role);
            return user + ":" + role;
        }).join();

        logger.info("thenCombineBoth() - Both must complete:");
        CompletableFuture.supplyAsync(() -> "A")
                .thenCombine(CompletableFuture.supplyAsync(() -> "B"), (a, b) -> a + b)
                .thenAccept(result -> logger.info("  Combined result: {}\n", result))
                .join();
    }

    private static void demonstrateMultipleFutures() {
        logger.info("--- 5. MULTIPLE FUTURES ---\n");

        logger.info("allOf() - Wait for ALL futures to complete:");
        final var future1 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Task 1 running...");
            return "Result 1";
        });
        final var future2 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Task 2 running...");
            return "Result 2";
        });
        final var future3 = CompletableFuture.supplyAsync(() -> {
            logger.info("  Task 3 running...");
            return "Result 3";
        });

        CompletableFuture.allOf(future1, future2, future3)
                .thenRun(() -> {
                    logger.info("  All tasks completed!");
                    logger.info("  Results: {}, {}, {}\n", future1.join(), future2.join(), future3.join());
                })
                .join();

        logger.info("anyOf() - Wait for ANY future to complete:");
        final var fast = CompletableFuture.supplyAsync(() -> {
            logger.info("  Fast task completes");
            return "Fast";
        });
        final var slow = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            logger.info("  Slow task completes");
            return "Slow";
        });

        final var first = CompletableFuture.anyOf(fast, slow);
        logger.info("  First to complete: {}\n", first.join());
    }

    private static void demonstrateErrorHandling() {
        logger.info("--- 6. ERROR HANDLING ---\n");

        logger.info("exceptionally() - Handle errors, return default:");
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Something went wrong!");
        })
        .exceptionally(ex -> {
            logger.info("  Error caught: {}", ex.getMessage());
            return "Default value";
        })
        .thenAccept(result -> logger.info("  Result: {}\n", result))
        .join();

        logger.info("handle() - Handle both success and error:");
        CompletableFuture.supplyAsync(() -> "Success")
                .handle((result, ex) -> {
                    if (ex != null) {
                        logger.info("  Error: {}", ex.getMessage());
                        return "Error default";
                    }
                    logger.info("  Success: {}", result);
                    return result;
                })
                .thenAccept(r -> logger.info("  Final: {}\n", r))
                .join();

        logger.info("whenComplete() - Always runs (like finally):");
        CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Error!");
        })
        .whenComplete((result, ex) -> {
            if (ex != null) {
                logger.info("  whenComplete: Error occurred: {}\n", ex.getMessage());
            } else {
                logger.info("  whenComplete: Success: {}\n", result);
            }
        })
        .exceptionally(ex -> "Recovered")
        .join();
    }

    private static void demonstrateBlocking() {
        logger.info("--- 7. BLOCKING METHODS ---\n");

        final var future = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Result";
        });

        logger.info("join() - Block until complete (unchecked exception):");
        final var result1 = future.join();
        logger.info("  Result: {}\n", result1);

        logger.info("get() - Block until complete (checked exception):");
        try {
            final var result2 = future.get();
            logger.info("  Result: {}\n", result2);
        } catch (ExecutionException e) {
            logger.error("  Error: {}", e.getMessage());
        }

        logger.info("get(timeout) - Block with timeout:");
        try {
            final var result3 = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Slow result";
            }).get(1, TimeUnit.SECONDS);
            logger.info("  Result: {}\n", result3);
        } catch (TimeoutException e) {
            logger.info("  Timeout! Task took too long\n");
        } catch (ExecutionException e) {
            logger.error("  Error: {}", e.getMessage());
        }
    }

    private static void demonstrateTimeout() {
        logger.info("--- 8. TIMEOUT METHODS ---\n");

        logger.info("orTimeout() - Complete with TimeoutException if too slow:");
        try {
            CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return "Slow result";
            })
            .orTimeout(1, TimeUnit.SECONDS)
            .join();
        } catch (Exception e) {
            logger.info("  Timeout occurred: {}\n", e.getClass().getSimpleName());
        }

        logger.info("completeOnTimeout() - Return default value on timeout:");
        final var result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "Slow result";
        })
        .completeOnTimeout("Default value", 1, TimeUnit.SECONDS)
        .join();
        logger.info("  Result: {}\n", result);
    }

    private static void demonstrateCustomExecutor() {
        logger.info("--- 9. CUSTOM EXECUTOR ---\n");

        logger.info("supplyAsync() with custom ExecutorService:");
        final ExecutorService customExecutor = Executors.newFixedThreadPool(5);

        CompletableFuture.supplyAsync(() -> {
            logger.info("  Running in custom executor: {}", Thread.currentThread().getName());
            return "Custom thread";
        }, customExecutor)
        .thenAccept(result -> logger.info("  Result: {}\n", result))
        .join();

        customExecutor.shutdown();
    }
}

