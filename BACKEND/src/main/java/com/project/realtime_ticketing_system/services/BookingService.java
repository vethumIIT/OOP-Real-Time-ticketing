package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.repositories.BookingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;

@Service

public class BookingService {



//    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    HttpSession session;


    @Autowired
    public BookingService(BookingRepository bRepository) {
        this.bookingRepository = bRepository;
    }

    public CompletableFuture<String> getBookings(Long id){
        return CompletableFuture.completedFuture("");
    }

    @Async
    public CompletableFuture<String> test(Booking booking){
        System.out.println("Test: "+booking.getBookingId());
        return CompletableFuture.completedFuture( "Test: "+booking);
    }

    @Async
    public CompletableFuture<String> create(Booking booking){
        if (!this.isLoggedIn()){
            return CompletableFuture.completedFuture(null);
        }

        String out_booking = "did not even run";
        ServiceLock.lock.lock();
        try {
            out_booking = bookingRepository.createBooking(booking);
        }finally {
            ServiceLock.lock.unlock();
        }
        return CompletableFuture.completedFuture("Booking Creation "+out_booking);
    }

    public CompletableFuture<List<Booking>> getBookingsByUserId(Long id){
        if (!this.isLoggedIn()){
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture(bookingRepository.getBookingsByUserId(id));
    }

    public CompletableFuture<String> update(Booking booking){
        if (!this.isLoggedIn()){
            return CompletableFuture.completedFuture(null);
        }
        return CompletableFuture.completedFuture("Incomplete function");
    }


    public boolean isLoggedIn(){
        return session.getAttribute("username") != null;
    }

}
