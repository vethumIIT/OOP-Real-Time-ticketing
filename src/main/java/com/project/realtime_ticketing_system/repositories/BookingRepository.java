package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {

    private final JdbcTemplate jdbcTemplate;
    private final String url = "jdbc:sqlite:mydatabase.db";

    @Autowired
    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String createBooking(Booking booking){
        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement("INSERT INTO bookings(customer_id, event_id, number_of_tickets, booking_date) VALUES (?, ?, ?)");

            stmt.setLong(1, booking.getCustomerId());
            stmt.setLong(2, booking.getEventId());
            stmt.setInt(3, booking.getTicketCount());
            stmt.setString(4, booking.getBookingDate());

            stmt.executeUpdate();
            stmt.close();
            c.close();

            System.out.println("Created the Record Successfully!");
        } catch (Exception e) {
            System.out.println("Failed to create record");
            System.out.println(e.toString());
            return "Failed!";
        }
        return "Success!";
    }

    public List<Booking> getBookingsByUserId(Long userId){
        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement("SELECT * FROM bookings WHERE user_id=?");

            ResultSet rs = stmt.executeQuery();

            List<Booking> bookings= new ArrayList<>();

            while (rs.next()){
                bookings.add(
                        new Booking(
                                rs.getLong("id"),
                                rs.getLong("customer_id"),
                                rs.getLong("event_id"),
                                rs.getInt("number_of_tickets"),
                                rs.getString("booking_date")
                        )
                );
            }
            return bookings;
        } catch (Exception e){
            System.out.println("Failed!");
            return null;
        }
    }


}
