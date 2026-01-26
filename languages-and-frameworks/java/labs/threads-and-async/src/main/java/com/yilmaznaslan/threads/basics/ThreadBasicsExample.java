package com.yilmaznaslan.threads.basics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadBasicsExample {
    private static final Logger logger = LoggerFactory.getLogger(ThreadBasicsExample.class);

    public static void main(String[] args) {
        logger.info("Main thread: {}", Thread.currentThread().getName());
        
        final var thread = new Thread(() -> {
            logger.info("New thread: {}", Thread.currentThread().getName());
            logger.info("Thread ID: {}", Thread.currentThread().getId());
        });
        
        thread.start();
        
        try {
            thread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Interrupted", e);
        }
        
        logger.info("Main thread finished");
    }
}

