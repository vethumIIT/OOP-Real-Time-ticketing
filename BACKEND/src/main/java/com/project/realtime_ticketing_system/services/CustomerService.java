package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {


    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final BookingService bookingService;
    private final EventService eventService;


    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookingService bookingService, EventService eventService){
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
        this.eventService = eventService;
    }

    public String register(Customer customer){ // DONE
        customer.setPassword(PasswordHash.hash(customer.getPassword()));
        String response = "Error Occurred";
        ServiceLock.lock.lock();
        try {
            response = customerRepository.registerUser(customer);// Database WRITE
        }finally {
            ServiceLock.lock.unlock();
        }
        return response;
    }

    public List<Booking> getBookingsByUserId(Long id){ // DONE
        List<Booking> bookings = null;// Database Read

        bookings = bookingService.getBookingsByUserId(id);

        if(bookings==null){
            return new ArrayList<>();
        }

        for (Booking booking : bookings){

            Event event = eventService.getEventById(booking.getEventId());
            booking.setEventName(event.getEventName());

        }

        return bookings;
    }

    public List<String> login(Customer customer){ // DONE
        Customer returnedCustomer = customerRepository.getUser(customer.getName());// Database Read

        List<String> response = new ArrayList<>();

        boolean correct_passwd;
        if (returnedCustomer == null){
            return new ArrayList<String>(Arrays.asList("Not Found"));
        }else {
            correct_passwd=PasswordHash.checkPassword(customer.getPassword(), returnedCustomer.getPassword());
            if (correct_passwd){

                System.out.println("Test session completed");
                return new ArrayList<String>(Arrays.asList("Logged In", customer.getName()));
            }else {
                return new ArrayList<String>(Arrays.asList("Incorrect Password"));
            }
        }
    }

    public Customer getCustomer(String name, String sessionName){
        System.out.println("======= getCustomer : CustomerService ========");
        System.out.println("Session username: "+sessionName);
        System.out.println("Entered Name: "+name);
        if (sessionName.equals(name)) {
            return customerRepository.getUser(name);
        }
        return null;
    }

}
