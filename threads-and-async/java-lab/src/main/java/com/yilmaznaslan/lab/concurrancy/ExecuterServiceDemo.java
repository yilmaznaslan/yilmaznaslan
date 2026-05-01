package com.yilmaznaslan.lab.concurrancy;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecuterServiceDemo {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void startTask() {
        executorService.execute(this::someMethod);
    }

    public Future<Boolean> startTaskFuture() {
        return executorService.submit(this::someMethod);
    }

    private boolean someMethod(){
        System.out.println("Hello");
        return true;
    }

    public void stop (){
        executorService.shutdown();
    }
}
