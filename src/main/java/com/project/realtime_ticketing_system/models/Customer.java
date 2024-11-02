package com.project.realtime_ticketing_system.models;

public class Customer extends UserTemplate{

    public Customer(Long id, String name, String email, String passwordHash) {
        super(id, name, email, passwordHash);
    }
}
