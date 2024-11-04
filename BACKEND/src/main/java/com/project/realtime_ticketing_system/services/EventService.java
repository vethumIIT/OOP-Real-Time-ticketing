package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EventService {


    public final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Async
    public CompletableFuture<Event> getEventById(Long id){
        Event event = eventRepository.getEventById(id);

        return CompletableFuture.completedFuture(event);
    }

}
