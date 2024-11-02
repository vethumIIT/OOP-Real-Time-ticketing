package com.project.realtime_ticketing_system.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @RequestMapping("/register")// create
    public String register(){
        return "This is where the backend starts registering customers";
    }

    @RequestMapping("/login") // read
    public String log_in(){
        return "This is where the backend starts validating credentials and logging in the customer";
    }

    @RequestMapping("/logout")
    public ResponseEntity<String> logout(){
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
