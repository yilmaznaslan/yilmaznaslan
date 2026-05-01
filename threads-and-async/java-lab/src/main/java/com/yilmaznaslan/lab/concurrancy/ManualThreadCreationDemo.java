package com.yilmaznaslan.lab.concurrancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ManualThreadCreationDemo {

    private static final Logger logger = LoggerFactory.getLogger(ManualThreadCreationDemo.class);

    public void runDemo() {
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


