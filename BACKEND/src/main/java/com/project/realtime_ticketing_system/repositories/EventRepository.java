package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.models.Event;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;

@Repository
public class EventRepository {

    private final String url = "jdbc:sqlite:mydatabase.db";

    public Event getEventById(Long id){
        Event event = null;
        try{
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection(this.url);

            PreparedStatement stmt = c.prepareStatement("SELECT * FROM event WHERE id=?");

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                event = new Event(
                        rs.getLong("id"),
                        rs.getLong("vendor_id"),
                        rs.getString("event_name"),
                        rs.getString("event_date"),
                        rs.getInt("tickets_sold"),
                        rs.getInt("total_tickets"),
                        rs.getDouble("price")
                );
            }else {
                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return event;
    }
}
