package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.data_transfer_objects.RepoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class BookingRepository {

    DatabaseManager db = new DatabaseManager();
    private static final Logger logger = LoggerFactory.getLogger(BookingRepository.class);
    private final JdbcTemplate jdbcTemplate;
    private final String url = "jdbc:sqlite:mydatabase.db";

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RepoResponse createBooking(Booking booking){

        List<Object> parameters = new ArrayList<>();

        parameters.add(booking.getCustomerId());
        parameters.add(booking.getEventId());
        parameters.add(booking.getTicketCount());
        parameters.add(booking.getBookingDate());
        parameters.add(booking.getTicketCategory());

        String status = db.writeDatabase("INSERT INTO bookings(customer_id, event_id, number_of_tickets, booking_date, ticket_category) VALUES (?, ?, ?, ?, ?)",
                parameters);


        List<Map<String, Object>> result = db.readDatabase("SELECT id FROM bookings ORDER BY id DESC LIMIT 1",null);

        switch (status) {
            case "Success" -> logger.debug("Created the booking Record Successfully!");
            case "Failed" -> logger.debug("Failed to create Booking");
        }

        if(status=="Success") {
            RepoResponse response = new RepoResponse(status, ((Number) result.get(0).get("id")).longValue());
            return response;
        }else{
            return new RepoResponse(status, null);
        }


    }

    public String deleteBooking(Long booking_id){

        List<Object> parameters = new ArrayList<>();
        parameters.add(booking_id);

        String response = db.writeDatabase("DELETE FROM bookings WHERE id=?", parameters);

        switch (response) {
            case "Success" -> logger.debug("Successfully deleted record from bookings");
            case "Error" -> logger.debug("Could not delete record in booking {} due to error", booking_id);
        }
        return response;
    }

    public Booking getBookingByBookingId(Long booking_id){
        List<Object> parameters = new ArrayList<>();
        parameters.add(booking_id);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM bookings WHERE id=?", parameters);

        if (result==null){
            return null;
        }else {
            return this.toBooking(result.get(0));
        }

    }

    public List<Booking> getBookingsByUserId(Long userId){
        List<Object> parameters = new ArrayList<>();
        parameters.add(userId);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM bookings WHERE customer_id=?", parameters);

        System.out.println("bookingRepository");
        System.out.println(result);
        if (result==null){
            logger.debug("Returned a null value");
            return null;
        }else {
            List<Booking> bookings = new ArrayList<>();
            for (Map<String, Object> record : result){
                bookings.add(this.toBooking(record));
            }
            return bookings;

        }

    }

    public Booking toBooking(Map<String, Object> record){
        return new Booking(
                ((Number) record.get("id")).longValue(),
                ((Number) record.get("customer_id")).longValue(),
                ((Number) record.get("event_id")).longValue(),
                (int) record.get("number_of_tickets"),
                (String) record.get("booking_date"),
                (String) record.get("ticket_category")
        );
    }

    public List<Booking> getBookings(){

        List<Map<String, Object>> result= db.readDatabase("SELECT * FROM bookings", null);

        if (result==null){
            return null;
        }else {
            List<Booking> bookings = new ArrayList<>();
            for (Map<String, Object> record : result){
                bookings.add(this.toBooking(record));
            }
            return bookings;
        }
    }


}
