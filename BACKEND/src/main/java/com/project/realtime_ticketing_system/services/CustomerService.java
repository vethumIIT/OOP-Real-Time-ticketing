package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.repositories.BookingRepository;
import com.project.realtime_ticketing_system.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingService bookingService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookingService bookingService){
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
    }

    public CompletableFuture<List<Booking>> getBookingsByUserId(Long id){
        return bookingService.getBookingsByUserId(id);
    }

}
