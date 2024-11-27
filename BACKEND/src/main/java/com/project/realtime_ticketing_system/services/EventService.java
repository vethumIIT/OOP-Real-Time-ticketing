package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Ticket;
import com.project.realtime_ticketing_system.repositories.EventRepository;
import com.project.realtime_ticketing_system.repositories.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventService {


    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    public final EventRepository eventRepository;
    public final TicketRepository ticketRepository;

    @Autowired
    public EventService(EventRepository eventRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    public Event getEventById(Long id){
        Event event = eventRepository.getEventById(id);

        Map<String, List<Double>> categories = ticketRepository.getCategoriesByEvent(id);
        event.setTicketCategory(categories);

        Map<String, List<Double>> soldCategories = ticketRepository.getCategorySalesByEvent(id);
        event.setSoldTicketsCategory(soldCategories);

        return event;
    }

    public List<Event> getEventByVendorId(Long id){
        List<Event> events = eventRepository.getEventsByVendorId(id);

        for (Event event : events){
            Map<String, List<Double>> categories = ticketRepository.getCategoriesByEvent(event.getEventId());
            event.setTicketCategory(categories);

            Map<String, List<Double>> soldCategories = ticketRepository.getCategorySalesByEvent(event.getEventId());
            event.setSoldTicketsCategory(soldCategories);
            /*for (String c: soldCategories.keySet()){
                logger.debug("{}: {}",c,soldCategories.get(c));
            }*/

        }

        return events;
    }

    public String addEvent(Event event){

        ServiceLock.lock.lock();
        String result;
        try{
            result = eventRepository.createEvent(event);
        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            ServiceLock.lock.unlock();
        }
        return result;

    }


    public String setEventSoldCount(Long event_id, int tickets_sold){
        return eventRepository.setEventSoldCount(event_id, tickets_sold);
    }

    public String setTotalTicketCount(Long event_id, int tickets_sold){
        return eventRepository.setTotalTicketCount(event_id, tickets_sold);
    }


    public List<Event> getAvailableEvents(){
        return eventRepository.getAvailableEvents();
    }

    public Event getEventByName(String name){
        Event event = eventRepository.getEventByName(name);
        Map<String, List<Double>> categories = ticketRepository.getCategoriesByEvent(event.getEventId());
        event.setTicketCategory(categories);
        return event;
    }
}
