package com.yilmaznaslan.threads.completablefuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    private static final Logger logger = LoggerFactory.getLogger(CompletableFutureExample.class);

    public static void main(String[] args) {
        logger.info("Servlet thread: {}", Thread.currentThread().getName());
        
        final var future = CompletableFuture.supplyAsync(() -> {
            logger.info("Background thread: {}", Thread.currentThread().getName());
            
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            
            return "Result from async operation";
        });
        future.thenAccept(result -> {
            logger.info("Result: {}", result);
            logger.info("Callback thread: {}", Thread.currentThread().getName());
        });
        
        future.join();
        logger.info("Main thread finished");
    }
}

