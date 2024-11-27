package com.project.realtime_ticketing_system.services;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ServiceThread<T> extends Thread {
    private FutureTask<T> futureTask;// The task to execute
    private T result;

    // Constructor to accept a task (Runnable)
    public ServiceThread(Callable<T> task) {
        this.futureTask = new FutureTask<>(task);
    }

    @Override
    public void run() {
        // Execute the passed task
        futureTask.run();
    }

    public T getResult() {
        try {
            return futureTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
