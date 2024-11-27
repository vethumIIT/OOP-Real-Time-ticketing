package com.project.realtime_ticketing_system.cli_traffic_simulator;

import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Ticket;
import io.micrometer.observation.Observation;

import java.util.HashMap;
import java.util.Map;

public class Producer extends UserCli{

    public Producer(Long id, String name, String email, String password) {
        super(id, name, email, password, "vendor");
    }

    public String addTicket(String eventName){

        Long eventId = this.getEventIdByName(eventName);

        HashMap<String, Object> ticket = new HashMap<>();

        ticket.put("ticketId",0L);
        ticket.put("eventId", eventId);
        ticket.put("bookingId",0L);
        ticket.put("isAvailable",1);

        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.hashMapToJson(ticket), "api/ticket/create", this.getCookies());

        return reply.get("stringResponse").toString();
    }

    public String createEvent(String name, Double price){
        HashMap<String, Object> event = new HashMap<>();

        event.put("eventId",0L);
        event.put("vendorId", this.getId());
        event.put("eventName", name);
        event.put("eventDate", "10/1/2024");
        event.put("ticketsSold", 0);
        event.put("totalTickets", 0);
        event.put("price", price);

        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.hashMapToJson(event), "api/event/create", this.getCookies());

        System.out.println("Event created");
        return reply.get("stringResponse").toString();

    }

}
