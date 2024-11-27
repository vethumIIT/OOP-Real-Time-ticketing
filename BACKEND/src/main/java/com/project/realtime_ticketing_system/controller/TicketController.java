package com.project.realtime_ticketing_system.controller;

import com.project.realtime_ticketing_system.config.AllowedOrigins;
import com.project.realtime_ticketing_system.models.Ticket;
import com.project.realtime_ticketing_system.services.EventService;
import com.project.realtime_ticketing_system.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = AllowedOrigins.allowedOrigins, allowCredentials = "true")
@RequestMapping("/api/ticket/")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    private final EventService eventService;
    private final TicketService ticketService;

    @Autowired
    public TicketController(EventService eventService, TicketService ticketService){
        this.eventService = eventService;
        this.ticketService = ticketService;
    }

    @RequestMapping("/create")
    public ResponseEntity<String> create_ticket(@RequestBody Ticket ticket, @RequestParam(defaultValue = "1") Integer ticketCount){
        return ResponseEntity.ok(ticketService.createTicket(ticket, ticketCount));
    }

    @RequestMapping("/delete")
    public ResponseEntity<String> delete_ticket(@RequestBody Ticket ticket){
        return ResponseEntity.ok("");
    }

}
