package com.project.realtime_ticketing_system.cli_traffic_simulator;

import com.google.gson.Gson;
import com.project.realtime_ticketing_system.models.Booking;

import java.util.HashMap;
import java.util.Map;

public class Consumer extends UserCli{

    public Consumer(Long id, String name, String email, String password) {
        super(id, name, email, password, "customer");
    }

    public String book_event(Long eventId, int ticketCount){
        HashMap<String, Object> map = new HashMap<>();

        map.put("bookingId",0);
        map.put("customerId",this.getId());
        map.put("eventId",eventId);
        map.put("ticketCount",1);
        map.put("bookingDate","1/1/2024");

        Map<String, Object> reply = trafficSimulator.sendPostRequest(this.hashMapToJson(map), "api/event/get_by_name", this.getCookies());

        return reply.get("stringResponse").toString();


    }

}
