package com.project.realtime_ticketing_system.cli_traffic_simulator;

public class MainSimulator {
    public static void main(String[] args){
        Consumer consumer1 = new Consumer(0L,"con1","con1@email.com","con1_123");
        Producer producer1 = new Producer(0L, "pro1", "pro1@email.com", "pro1_123");

        System.out.println(consumer1.register());
        System.out.println(producer1.register());

        System.out.println(consumer1.login());
        System.out.println("producer1 logged in");
        System.out.println();
        System.out.println(producer1.login());
        System.out.println("consumer1 logged in");
        System.out.println();

        System.out.println(producer1.createEvent("pro1_event1",500.0));
        System.out.println();
        System.out.println(producer1.addTicket("pro1_event1"));
    }

}
