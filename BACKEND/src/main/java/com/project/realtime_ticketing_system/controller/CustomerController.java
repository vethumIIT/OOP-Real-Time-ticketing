package com.project.realtime_ticketing_system.controller;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping("/register")// create
    public CompletableFuture<ResponseEntity<String>> register(@RequestBody Customer customer, HttpSession session){
        // check availability of username
        System.out.println("Registering: "+customer.getName());
        return customerService.register(customer, session).thenApply(ResponseEntity::ok);
    }

    @RequestMapping("/login") // read
    public CompletableFuture<ResponseEntity<String>> log_in(@RequestBody Customer customer, HttpSession session){
        return customerService.login(customer, session).thenApply(ResponseEntity::ok);
    }

    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        return ResponseEntity.ok("This is where the vendor logs out");
    }

    @RequestMapping("/update") // update
    public String update(){
        return "This is where the process of changing customer info starts";
    }

    @RequestMapping("/delete") // delete
    public String delete(){
        return "This is where the process of deleting a customer starts";
    }

    @RequestMapping("/get_bookings")
    public ResponseEntity<String> get_bookings(@PathVariable Long id){
        // calls the booking service
        return ResponseEntity.ok("get all the bookings relating to this user");
    }

    @RequestMapping("/get_tickets")
    public ResponseEntity<String> get_tickets(@PathVariable Long id){
        // calls the booking service
        return ResponseEntity.ok("get all the tickets relating to this user");
    }


}
