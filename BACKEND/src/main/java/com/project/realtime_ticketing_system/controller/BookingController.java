package com.project.realtime_ticketing_system.controller;


import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.services.CustomerService;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.realtime_ticketing_system.services.BookingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/booking")
public class BookingController {


    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;
    private final CustomerService customerService;

    @Autowired
    public BookingController(BookingService bookingService, CustomerService customerService) {
        this.bookingService = bookingService;
        this.customerService = customerService;
    }

    @RequestMapping({"/create/","/create"})
    public ResponseEntity<String> create(@RequestBody Booking booking, HttpSession session){

        // verify user account
        // verify the ticket availability
        // book ticket if available
        // else return no tickets available

        if (!SessionManager.isLoggedIn(session, "customer")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In!");
        }
        booking.setCustomerId(customerService.getCustomer((String) session.getAttribute("username"), (String) session.getAttribute("username")).getId());
        return ResponseEntity.ok(bookingService.create(booking));

        //return ResponseEntity.ok("This page appears when you hit confirm for the booking");
    }

    @RequestMapping("/update/")
    public ResponseEntity<String> update(){
        return ResponseEntity.ok("Update booking page");
    }

    @RequestMapping({"/cancel/", "/cancel"}) // Delete
    public ResponseEntity<String> cancel(@RequestBody Booking booking, HttpSession session){
        if(!SessionManager.isLoggedIn(session)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        }
        return ResponseEntity.ok(bookingService.cancelBooking(booking.getBookingId()));
        //return ResponseEntity.ok("This is when you confirm the cancellation of a ticket booking");
    }

    @RequestMapping("/get_booking_info/") // Read
    public ResponseEntity<String> get_info(@PathVariable Long id){
        return ResponseEntity.ok("This will return the info about specific bookings");
    }

}
