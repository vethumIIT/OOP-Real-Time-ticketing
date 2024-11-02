package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service

public class BookingService {

    private final ReentrantLock lock = new ReentrantLock();

    //@Autowired
    //private final BookingRepository bookingRepository;

    /*@Autowired
    public BookingService(BookingRepository bRepository) {
        bookingRepository = bRepository;
    }*/

    public CompletableFuture<String> getBookings(Long id){
        return CompletableFuture.completedFuture("");
    }

    @Async
    public CompletableFuture<String> test(Booking booking){
        System.out.println("Test: "+booking.getBookingId());
        return CompletableFuture.completedFuture( "Test: "+booking);
    }

}
