package com.project.realtime_ticketing_system.controller;

import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Vendor;
import com.project.realtime_ticketing_system.services.EventService;
import com.project.realtime_ticketing_system.services.TicketService;
import com.project.realtime_ticketing_system.services.VendorService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/event/")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;
    private final TicketService ticketService;
    private final VendorService vendorService;

    @Autowired
    public EventController(EventService eventService, TicketService ticketService, VendorService vendorService){
        this.eventService = eventService;
        this.ticketService = ticketService;
        this.vendorService = vendorService;
    }

    @RequestMapping("/create")// create a new event (needs vendor login)
    public ResponseEntity<String> create_event(@RequestBody Event event, HttpSession session){
        if(!SessionManager.isLoggedIn(session, "vendor")){
            System.out.println("Vendor not logged in for create event");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        }

        Long vendorId = vendorService.getVendor((String) session.getAttribute("username")).getId();

        event.setVendorId(vendorId);

        return ResponseEntity.ok(eventService.addEvent(event));
    }

    @RequestMapping("/get")// get available events (no login)
    public ResponseEntity<List<Event>> get_events(){
        return ResponseEntity.ok(eventService.getAvailableEvents());
    }

    @RequestMapping("/get_by_name")// get event by name (no login)
    public ResponseEntity<Event> get_event_by_name(@RequestBody Event event){
        return ResponseEntity.ok(eventService.getEventByName(event.getEventName()));
    }

    @RequestMapping("/get_by_id")
    public ResponseEntity<Event> get_event_by_id(@RequestBody Event event){
        Event returnEvent = eventService.getEventById(event.getEventId());
        returnEvent.setVendorName(vendorService.getVendorById(returnEvent.getVendorId()).getName());

        return ResponseEntity.ok(returnEvent);
    }

    @RequestMapping("/get_vendor_events")
    public ResponseEntity<List<Event>> get_vendor_events(@RequestBody Vendor vendor){
        Long vendorId = vendorService.getVendor(vendor.getName()).getId();
        return ResponseEntity.ok(eventService.getEventByVendorId(vendorId));
    }

}
