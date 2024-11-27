package com.project.realtime_ticketing_system.repositories;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sqlite.SQLiteErrorCode;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class EventRepository {

    DatabaseManager db = new DatabaseManager();
    private static final Logger logger = LoggerFactory.getLogger(EventRepository.class);

    private final String url = "jdbc:sqlite:mydatabase.db";

    public String createEvent(Event event){
        List<Object> parameters = new ArrayList<>();
        parameters.add(event.getVendorId());
        parameters.add(event.getEventName());
        parameters.add(event.getEventDescription());
        parameters.add(event.getEventLocation());
        parameters.add(event.getEventDate());
        parameters.add(0);
        parameters.add(0);
        //parameters.add(event.getPrice());

        String result = db.writeDatabase(
                "INSERT INTO event (vendor_id, event_name, event_description, event_location, event_date, tickets_sold, total_tickets) VALUES (?, ?, ?, ?, ?, ?, ?)",
                parameters
        );
        switch (result) {
            case "Success" -> System.out.println("Successfully created event!");
            case "Error" -> System.out.println("Failed to create event");
            case "Exists" -> System.out.println("Duplicate record in events");
        }
        return result;
    }

    public List<Event> getAvailableEvents(){
        List<Event> events = new ArrayList<>();

        List<Object> parameters = new ArrayList<>();

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM event WHERE tickets_sold<total_tickets", null);

        for (Map<String, Object> record : result){
            events.add(this.toEvent(record));
        }

        return events;
    }

    public Event getEventById(Long id){
        Event event = null;

        List<Object> parameters = new ArrayList<>();

        parameters.add(id);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM event WHERE id=?", parameters);

        if (result==null){
            return null;
        }else {
            Map<String, Object> record= result.get(0);
            event = this.toEvent(record);
        }

        return event;
    }

    public List<Event> getEventsByVendorId(Long id){
        List<Event> events = new ArrayList<>();

        List<Object> parameters = new ArrayList<>();

        parameters.add(id);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM event WHERE vendor_id=?", parameters);

        if (result==null){
            return null;
        }else {
            for (Map<String, Object> record : result){
                events.add(this.toEvent(record));
            }
        }

        return events;
    }

    public String setEventSoldCount(Long event_id, int event_sold_count){

        List<Object> parameters = new ArrayList<>();

        parameters.add(event_sold_count);
        parameters.add(event_id);

        String response = db.writeDatabase("UPDATE event SET tickets_sold=? WHERE id=?", parameters);

        if (response.equals("Success")){
            System.out.println("Event ticket sales count was changed successfully");
        }else if (response.equals("Error")){
            System.out.println("Could not update Event ticket sales count due to an unexpected error.");
        }

        return response;
    }

    public String setTotalTicketCount(Long event_id, int total_ticket_count){

        List<Object> parameters = new ArrayList<>();

        parameters.add(total_ticket_count);
        parameters.add(event_id);

        String response = db.writeDatabase("UPDATE event SET total_tickets=? WHERE id=?", parameters);

        if (response.equals("Success")){
            System.out.println("Event ticket sales count was changed successfully");
        }else if (response.equals("Error")){
            System.out.println("Could not update Event ticket sales count due to an unexpected error.");
        }

        return response;
    }

    public Event getEventByName(String name){
        Event event = null;

        List<Object> parameters = new ArrayList<>();

        parameters.add(name);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM event WHERE event_name=?", parameters);

        if (result==null){
            return null;
        }else {
            Map<String, Object> record = result.get(0);
            event = this.toEvent(record);
        }

        return event;
    }

    public Event toEvent(Map<String, Object> record){
        Object price = record.get("price");
        Double doublePrice;
        System.out.println();
        if (price==null){
            doublePrice=0.0;
        }
        else {
            doublePrice=((Number) price).doubleValue();
        }
        return new Event(
                ((Number) record.get("id")).longValue(),
                ((Number) record.get("vendor_id")).longValue(),
                (String) record.get("event_name"),
                (String) record.get("event_description"),
                (String) record.get("event_location"),
                (String) record.get("event_date"),
                (Integer) record.get("tickets_sold"),
                (Integer) record.get("total_tickets")
                //doublePrice
        );
    }
}
