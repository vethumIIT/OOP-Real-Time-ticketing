package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Ticket;
import com.project.realtime_ticketing_system.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);
    private final TicketRepository ticketRepository;
    private final EventService eventService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventService eventService){
        this.ticketRepository = ticketRepository;
        this.eventService = eventService;
    }


    public List<Ticket> getTicketsByEventId(Long id){
        return this.ticketRepository.getAvailableTicketByEventId(id);
    }

    public List<Ticket> getTicketsByEventIdAndCategory(Long id, String category){
        return this.ticketRepository.getAvailableTicketByEventIdAndCategory(id, category);
    }

    public List<Ticket> getTicketsByBookingId(Long booking_id){
        return this.ticketRepository.getTicketByBookingId(booking_id);
    }

    public String bookTicket(Long booking_id, Long ticket_id){

        return this.ticketRepository.bookTicket(booking_id, ticket_id);
    }

    public String unBookTicket(Long ticket_id){
        return  this.ticketRepository.unBookTicket(ticket_id);
    }

    public String unBookTickets(Long booking_id){
        return  this.ticketRepository.unBookTickets(booking_id);
    }

    public String createTicket(Ticket ticket, Integer ticketCount){
        String result="";
        ServiceLock.lock.lock();
        try{
            for(int i=0;i<ticketCount; i++) {
                result = ticketRepository.createTicket(ticket);

                Long eventId = ticket.getEventId();

                if (Objects.equals(result, "Success")) {
                    Event ticketEvent = eventService.getEventById(eventId);

                    eventService.setTotalTicketCount(eventId, ticketEvent.getTotalTickets() + 1);
                }
            }

            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            ServiceLock.lock.unlock();
        }
    }

    public Map<String, List<Double>> getTicketsCategories(Long eventId){
        return ticketRepository.getCategoriesByEvent(eventId);
    }

    public String deleteTicket(Ticket ticket){
        String result;
        ServiceLock.lock.lock();
        try{
            result = ticketRepository.deleteTicket(ticket.getTicketId());

            Long eventId = ticket.getEventId();

            Event ticketEvent = eventService.getEventById(eventId);

            eventService.setTotalTicketCount(eventId,ticketEvent.getTotalTickets()+1);

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            ServiceLock.lock.unlock();
        }
        return result;
    }

}
