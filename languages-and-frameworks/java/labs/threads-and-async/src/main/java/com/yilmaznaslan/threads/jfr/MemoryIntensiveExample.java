package com.yilmaznaslan.threads.jfr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryIntensiveExample {
    private static final Logger logger = LoggerFactory.getLogger(MemoryIntensiveExample.class);
    private static final Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        logger.info("Starting memory-intensive example");
        logger.info("This will create many objects to demonstrate memory consumption");
        
        final var dataStructures = new ArrayList<List<LargeObject>>();
        
        for (int i = 0; i < 10; i++) {
            logger.info("Creating data structure batch {}", i);
            final var batch = createDataBatch(1000);
            dataStructures.add(batch);
            
            Thread.sleep(1000);
            
            if (i % 3 == 0) {
                logger.info("Clearing some batches to simulate normal operation");
                dataStructures.removeIf(list -> random.nextBoolean());
            }
        }
        
        logger.info("Memory-intensive operations completed");
        logger.info("Final data structures count: {}", dataStructures.size());
        
        Thread.sleep(5000);
        logger.info("Application finished");
    }
    
    private static List<LargeObject> createDataBatch(int size) {
        final var batch = new ArrayList<LargeObject>();
        
        for (int i = 0; i < size; i++) {
            final var largeObject = new LargeObject(
                generateRandomString(100),
                generateRandomString(200),
                generateRandomArray(50)
            );
            batch.add(largeObject);
        }
        
        return batch;
    }
    
    private static String generateRandomString(int length) {
        final var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final var sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
    }
    
    private static int[] generateRandomArray(int size) {
        final var array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }
    
    static class LargeObject {
        private final String field1;
        private final String field2;
        private final int[] array;
        
        LargeObject(String field1, String field2, int[] array) {
            this.field1 = field1;
            this.field2 = field2;
            this.array = array;
        }
        
        public String getField1() {
            return field1;
        }
        
        public String getField2() {
            return field2;
        }
        
        public int[] getArray() {
            return array;
        }
    }
}

