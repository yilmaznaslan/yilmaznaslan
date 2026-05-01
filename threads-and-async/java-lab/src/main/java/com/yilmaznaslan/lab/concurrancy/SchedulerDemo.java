package com.yilmaznaslan.lab.concurrancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SchedulerDemo {

    ScheduledExecutorService scheduledExecutorService =  Executors.newScheduledThreadPool(1);
    ScheduledExecutorService scheduledExecutorServices =  Executors.newSingleThreadScheduledExecutor();

    private static final Logger logger = LoggerFactory.getLogger(SchedulerDemo.class);


    public void startSchedulerFixedDelay() {
        scheduledExecutorService.scheduleWithFixedDelay(
                () -> logger.info("Scheduler started"),
                1,
                2,
                TimeUnit.SECONDS
        );
    }
}
