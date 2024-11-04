package com.project.realtime_ticketing_system.services;

import java.util.concurrent.locks.ReentrantLock;

public class ServiceLock {
    public static final ReentrantLock lock = new ReentrantLock();
}
