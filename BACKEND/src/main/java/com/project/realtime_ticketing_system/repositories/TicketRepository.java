package com.project.realtime_ticketing_system.repositories;


import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TicketRepository {

    private final String url = "jdbc:sqlite:mydatabase.db";

    private final DatabaseManager db = new DatabaseManager();
    private static final Logger logger = LoggerFactory.getLogger(TicketRepository.class);

    public List<Ticket> getAvailableTicketByEventId(Long id){

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM tickets WHERE event_id=? AND is_Available=true", parameters);

        if (result==null){
            return null;
        }else {
            List<Ticket> tickets = new ArrayList<>();
            for (Map<String, Object> record : result){
                tickets.add(this.toTicket(record));
            }
            return tickets;
        }

    }

    public List<Ticket> getAvailableTicketByEventIdAndCategory(Long id, String category){

        List<Object> parameters = new ArrayList<>();
        parameters.add(id);
        parameters.add(category);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM tickets WHERE event_id=? AND is_Available=true AND ticket_category=?", parameters);

        if (result==null){
            return null;
        }else {
            List<Ticket> tickets = new ArrayList<>();
            for (Map<String, Object> record : result){
                tickets.add(this.toTicket(record));
            }
            return tickets;
        }

    }

    public List<Ticket> getTicketByBookingId(Long id){
        List<Object> parameters = new ArrayList<>();
        parameters.add(id);

        List<Map<String, Object>> result = db.readDatabase("SELECT * FROM tickets WHERE id=? AND is_Available=false", parameters);

        if (result==null){
            return null;
        }else {
            List<Ticket> tickets = new ArrayList<>();

            for (Map<String, Object> record : result){
                tickets.add(this.toTicket(record));
            }
            return tickets;
        }
    }

    public String bookTicket(Long booking_id, Long ticket_id){

        List<Object> parameters = new ArrayList<>();
        parameters.add(booking_id);
        parameters.add(ticket_id);

        String response = db.writeDatabase("UPDATE tickets SET booking_id=?, is_Available=false WHERE id=?", parameters);

        if (response=="Success"){
            System.out.println("Successfully booked this ticket");
        } else if (response=="Failed") {
            logger.error("Could not book this ticket due to an error");
        }
        return response;
    }

    public String unBookTicket(Long ticket_id){
        List<Object> parameters = new ArrayList<>();

        parameters.add(ticket_id);

        String response = db.writeDatabase("UPDATE tickets SET booking_id=NULL, is_Available=true WHERE id=?", parameters);

        if (response.equals("Success")){
            System.out.println("Successfully unbooked ticket booking");
        } else if (Objects.equals(response, "Failed")) {
            System.out.println("An error occurred when trying to un book ticket!");
        }
        return response;
    }

    public String unBookTickets(Long booking_id){

        List<Object> parameters = new ArrayList<>();

        parameters.add(booking_id);

        String response = db.writeDatabase("UPDATE tickets SET booking_id=NULL, is_Available=true WHERE booking_id=?", parameters);

        if (response.equals("Success")){
            System.out.println("Successfully unbooked ticket booking");
        } else if (Objects.equals(response, "Failed")) {
            System.out.println("An error occurred when trying to un book ticket!");
        }
        return response;

    }

    public Ticket toTicket(Map<String, Object> record){
        Object price = record.get("price");
        Double doublePrice;
        System.out.println();
        if (price==null){
            doublePrice=0.0;
        }
        else {
            doublePrice=((Number) price).doubleValue();
        }
        return new Ticket(
                ((Number) record.get("id")).longValue(),
                ((Number) record.get("event_id")).longValue(),
                ((Number) record.get("booking_id")).longValue(),
                (String) record.get("ticket_category"),
                doublePrice,
                Boolean.TRUE.equals(record.get("is_Available"))
        );
    }

    public String createTicket(Ticket ticket){
        List<Object> parameters = new ArrayList<>();
        parameters.add(ticket.getEventId());
        parameters.add(0L);
        parameters.add(ticket.getTicketCategory());
        parameters.add(ticket.getPrice());

        String result = db.writeDatabase(
                "INSERT INTO tickets (event_id, booking_id, ticket_category, price, is_Available) VALUES (?, ?, ?, ?, true)",
                parameters
        );
        switch (result) {
            case "Success" -> System.out.println("Successfully created ticket!");
            case "Error" -> System.out.println("Failed to create ticket");
            case "Exists" -> System.out.println("Duplicate record in ticket");
        }
        return result;
    }

    public Map<String, List<Double>> getCategoriesByEvent(Long eventId){
        List<Object> parameters = new ArrayList<>();
        parameters.add(eventId);

        List<Map<String, Object>> result = db.readDatabase("SELECT ticket_category, price, COUNT(*) AS Available_tickets_count FROM tickets WHERE event_id=? GROUP BY ticket_category", parameters);

        if (result==null){
            return null;
        }else {
            Map<String, List<Double>> response = new HashMap<>();
            for(Map<String, Object> record: result){
                //Map<String, List<Double>> map = new HashMap<>();
                List<Double> return_List = new ArrayList<>();
                return_List.add(((Number) record.get("price")).doubleValue());
                return_List.add(((Number) record.get("Available_tickets_count")).doubleValue());
                response.put((String) record.get("ticket_category"), return_List);

            }
            return response;
        }
    }

    public Map<String, List<Double>> getCategorySalesByEvent(Long eventId){
        List<Object> parameters = new ArrayList<>();
        parameters.add(eventId);

        List<Map<String, Object>> result = db.readDatabase("SELECT ticket_category, price, COUNT(*) AS Sold_tickets FROM tickets WHERE event_id=? AND is_Available=false GROUP BY ticket_category", parameters);

        if (result==null){
            return null;
        }else {
            Map<String, List<Double>> response = new HashMap<>();
            for(Map<String, Object> record: result){
                //Map<String, List<Double>> map = new HashMap<>();
                List<Double> return_List = new ArrayList<>();
                return_List.add(((Number) record.get("price")).doubleValue());
                return_List.add(((Number) record.get("Sold_tickets")).doubleValue());
                response.put((String) record.get("ticket_category"), return_List);

            }
            return response;
        }
    }

    public String deleteTicket(Long ticket_id){

        List<Object> parameters = new ArrayList<>();
        parameters.add(ticket_id);

        String response = db.writeDatabase("DELETE FROM tickets WHERE id=?", parameters);

        switch (response) {
            case "Success" -> System.out.println("Successfully deleted ticket");
            case "Error" -> System.out.println("Could not delete ticket due to error");
        }
        return response;
    }
}
