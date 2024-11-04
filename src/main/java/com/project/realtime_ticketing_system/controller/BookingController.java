package com.project.realtime_ticketing_system.controller;


import com.project.realtime_ticketing_system.models.Booking;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.realtime_ticketing_system.services.BookingService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/booking")
public class BookingController {


    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @RequestMapping("/test") // Create
    public CompletableFuture<ResponseEntity<String>> test(@RequestBody Booking booking){
        System.out.println("/api/booking/test");
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e){
            System.out.println("sleep was interrupted");
        }
        return bookingService.test(booking).thenApply(ResponseEntity::ok);
    }

    @RequestMapping({"/create/","/create"})
    public CompletableFuture<ResponseEntity<String>> create(@RequestBody Booking booking){
        System.out.println("/api/booking/create");

        // verify user account
        // verify the ticket availability
        // book ticket if available
        // else return no tickets available

        return bookingService.create(booking).thenApply(ResponseEntity::ok);

        //return ResponseEntity.ok("This page appears when you hit confirm for the booking");
    }

    @RequestMapping("/update/")
    public ResponseEntity<String> update(){
        System.out.println("/api/booking/update/");
        return ResponseEntity.ok("Update booking page");
    }

    @RequestMapping("/cancel/") // Delete
    public ResponseEntity<String> cancel(@PathVariable Long id){
        System.out.println("/api/booking/cancel");
        return ResponseEntity.ok("This is when you confirm the cancellation of a ticket booking");
    }

    @RequestMapping("/get_booking_info/") // Read
    public ResponseEntity<String> get_info(@PathVariable Long id){
        System.out.println("/api/booking/get_booking_info");
        return ResponseEntity.ok("This will return the info about specific bookings");
    }

}
