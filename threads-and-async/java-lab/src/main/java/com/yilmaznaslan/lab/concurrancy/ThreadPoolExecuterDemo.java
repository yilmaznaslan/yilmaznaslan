package com.yilmaznaslan.lab.concurrancy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecuterDemo {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolExecuterDemo.class);


    private final ThreadPoolExecutor threadPoolExecutorBounded = new ThreadPoolExecutor(
            2,
            4,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10), // 👈 queue (bounded)
            // new ThreadPoolExecutor.AbortPolicy()  // 👈 rejection policy -> Throws RejectedExecutionException
            new ThreadPoolExecutor.CallerRunsPolicy() // -> The calling thread executes the task
            // new ThreadPoolExecutor.DiscardOldestPolicy() // -> Removes oldest task in queue, adds the new task
            // new ThreadPoolExecutor.DiscardPolicy() -> Silently drops the task
    );


    public Future<Boolean> startTaskFuture(int i) {
        return threadPoolExecutorBounded.submit(() -> someMethod(i));
    }

    private boolean someMethod(int i){
        logger.info(String.format( "Executing task %d", i));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public void stop (){
        threadPoolExecutorBounded.shutdown();
        try {
            if (!threadPoolExecutorBounded.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPoolExecutorBounded.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutorBounded.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
