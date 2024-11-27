package com.project.realtime_ticketing_system.controller;

import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.services.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/customer")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @RequestMapping("/get")
    public ResponseEntity<Customer> getCustomer(@RequestBody Customer customer, HttpSession session){// get customer info
        if (!SessionManager.isLoggedIn(session, "customer")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Customer c = customerService.getCustomer(customer.getName(), (String) session.getAttribute("username"));
        c.setPassword("");
        logger.info("retrieved user information successfully!");
        return ResponseEntity.ok(c);
    }

    @RequestMapping("/register")// create
    public ResponseEntity<String> register(@RequestBody Customer customer, HttpSession session){
        // check availability of username
        return ResponseEntity.ok(customerService.register(customer));
    }// DONE

    @RequestMapping("/login") // read
    public ResponseEntity<String> log_in(@RequestBody Customer customer, HttpSession session){
        logger.debug("Attempted log in");
        List<String> list = customerService.login(customer);
        if (list.get(0).equals("Logged In")){
            session.setAttribute("username",list.get(1));
            session.setAttribute("UserType","customer");
        }
        return ResponseEntity.ok(list.get(0));
    }// DONE

    @RequestMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        logger.debug("Logging Out");
        session.invalidate();
        return ResponseEntity.ok("Logged out");
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
    public ResponseEntity<?> get_bookings(@PathVariable Long id, HttpSession session){
        // calls the booking service
        if (!SessionManager.isLoggedIn(session, "customer")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        }
        return ResponseEntity.ok(customerService.getBookingsByUserId(id));
    }

    @RequestMapping("/get_bookings_by_name")
    public ResponseEntity<?> get_bookings_by_name(@RequestBody Customer customer, HttpSession session){
        // calls the booking service
        logger.debug("fetching bookings");
        if (!SessionManager.isLoggedIn(session, "customer")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        }
        logger.debug("is Logged In");
        List<Booking> bookingsList = customerService.getBookingsByUserId(customerService.getCustomer(customer.getName(), (String) session.getAttribute("username")).getId());
        return ResponseEntity.ok(bookingsList);
    }

    @RequestMapping("/get_tickets")
    public ResponseEntity<String> get_tickets(@PathVariable Long id){
        // calls the booking service
        return ResponseEntity.ok("get all the tickets relating to this user");
    }


}
