package com.yilmaznaslan.lab.concurrancy;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MainApplication {



    public static void main(final String[] args){
        final var mainApplication = new MainApplication();

       mainApplication.runManualThreadPoolDemo();
       mainApplication.runThreadPoolDemo();
       mainApplication.runSchedulerDemo();

    }

    private void runExecutorDemo(){
        // Create Threads using ExecutionService
        ExecuterServiceDemo executerServiceDemo = new ExecuterServiceDemo();
        executerServiceDemo.startTask();
        final var futureResult = executerServiceDemo.startTaskFuture();
        while(!futureResult.isDone()){
            System.out.println("waiting for result");
        }
        try {
            System.out.println(futureResult.get());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        executerServiceDemo.stop();
    }

    private void runThreadPoolDemo(){
        ThreadPoolExecuterDemo executor = new ThreadPoolExecuterDemo();

        try {
            List<Future<Boolean>> futureResults = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                futureResults.add(executor.startTaskFuture(i));
            }

            for (Future<Boolean> futureResult : futureResults) {
                while (!futureResult.isDone()) {
                    System.out.println("waiting for result");
                    Thread.sleep(100);
                }

                System.out.println(futureResult.get());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executor.stop();
        }
    }

    private void runManualThreadPoolDemo(){
        ManualThreadCreationDemo sample = new ManualThreadCreationDemo();
        sample.runDemo();
    }

    private void runSchedulerDemo(){
        SchedulerDemo schedulerDemo = new SchedulerDemo();
        schedulerDemo.startSchedulerFixedDelay();
    }
}
