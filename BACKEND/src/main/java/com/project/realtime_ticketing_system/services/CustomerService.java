package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Customer;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.repositories.CustomerRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BookingService bookingService;
    private final EventService eventService;




    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookingService bookingService, EventService eventService){
        this.customerRepository = customerRepository;
        this.bookingService = bookingService;
        this.eventService = eventService;
    }

    @Async
    public CompletableFuture<String> register(Customer customer, HttpSession session){ // DONE
        customer.setPassword(PasswordHash.hash(customer.getPassword()));
        String response = "Error Occurred";
        ServiceLock.lock.lock();
        try {
            response = customerRepository.registerUser(customer);// Database WRITE
        }finally {
            ServiceLock.lock.unlock();
        }
        return CompletableFuture.completedFuture(response);
    }

    @Async
    public CompletableFuture<List<Booking>> getBookingsByUserId(Long id, HttpSession session){ // DONE
        List<Booking> bookings = null;// Database Read
        try {
            bookings = bookingService.getBookingsByUserId(id).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        for (Booking booking : bookings){
            try {
                Event event = eventService.getEventById(booking.getEventId()).get();
                booking.setEventName(event.getEventName());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }

        }

        return CompletableFuture.completedFuture(bookings);
    }

    @Async
    public CompletableFuture<String> login(Customer customer, HttpSession session){ // DONE
        Customer returnedCustomer = customerRepository.getUser(customer.getName());// Database Read

        boolean correct_passwd;
        if (returnedCustomer == null){
            return CompletableFuture.completedFuture("Not Found");
        }else {
            correct_passwd=PasswordHash.checkPassword(customer.getPassword(), returnedCustomer.getPassword());
            if (correct_passwd){
                session.setAttribute("username",customer.getName());
                session.setAttribute("Usertype","customer");
                System.out.println("Test session completed");
                return CompletableFuture.completedFuture("Logged In");
            }else {
                return CompletableFuture.completedFuture("Incorrect Password");
            }
        }
    }

    @Async
    public CompletableFuture<String> logout(HttpSession session){
        session.invalidate();
        return CompletableFuture.completedFuture("Logged out");
    }

}
