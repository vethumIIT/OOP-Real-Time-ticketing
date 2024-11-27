package com.project.realtime_ticketing_system.services;

import com.project.realtime_ticketing_system.controller.BookingController;
import com.project.realtime_ticketing_system.models.Booking;
import com.project.realtime_ticketing_system.models.Event;
import com.project.realtime_ticketing_system.models.Ticket;
import com.project.realtime_ticketing_system.models.data_transfer_objects.RepoResponse;
import com.project.realtime_ticketing_system.repositories.BookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service

public class BookingService {



//    @Autowired
    private final BookingRepository bookingRepository;
    private final EventService eventService;
    private final TicketService ticketService;

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);



    @Autowired
    public BookingService(
            BookingRepository bookingRepository,
            EventService eventService,
            TicketService ticketService) {

        this.bookingRepository = bookingRepository;
        this.eventService = eventService;
        this.ticketService = ticketService;
    }


    public String create(Booking booking){

        RepoResponse booking_response = new RepoResponse("did not run");
        String return_Value = "Error";
        logger.debug("locked Service Lock");
        ServiceLock.lock.lock();
        try {
            Event event;


            event = eventService.getEventById(booking.getEventId());// get event info of the booking


            if (event==null){// check if event was found
                return "Event not found";
            }
            if (event.getTotalTickets()-event.getTicketsSold()<booking.getTicketCount()){ // check if required ticket count is available
                return "Not enough tickets";
            }
            if (event.getTicketCategory().get(booking.getTicketCategory()).get(1)==0){
                return "Ticket Category out of stock";
            }


            booking_response = bookingRepository.createBooking(booking);// record the booking in the booking table

            if (booking_response.getResponseString()!="Success"){
                logger.error("An error occurred while trying to create booking");
                throw new RuntimeException("BookingService.create()::An error occurred while trying to create booking");
            }

            Long booking_id = booking_response.getResponseLong();
            List<Ticket> tickets = new ArrayList<>();



            tickets = ticketService.getTicketsByEventIdAndCategory(event.getEventId(), booking.getTicketCategory());// get available tickets for event

            if(tickets==null){
                logger.error("Tried to get tickets for event {} but received null value", event.getEventId());
                throw new RuntimeException("BookingService.create()::tried to get tickets for event but received null value");
            }else if(tickets.isEmpty()){
                logger.error("tried to get tickets for event {} but received empty ticket list", event.getEventId());
                throw new RuntimeException("BookingService.create()::tried to get tickets for event but received empty ticket list");
            }


            for (int i=0; i<booking.getTicketCount(); i++){
                ticketService.bookTicket(booking_id, tickets.get(i).getTicketId());// book the required number of tickets from the available tickets
                eventService.setEventSoldCount(event.getEventId(), event.getTicketsSold()+1);// update the sold ticket count in the event
            }



            return_Value = "Success";
            logger.debug("Booking Creation was successful!");
        }finally {
            ServiceLock.lock.unlock();
            logger.debug("Unlocked lock in booking!");
        }

        return return_Value;
    }

    public Booking getBookingById(Long booking_id){
        return bookingRepository.getBookingByBookingId(booking_id);
    }



    public String cancelBooking(Long booking_id){

        ServiceLock.lock.lock();
        try {
            Booking booking = bookingRepository.getBookingByBookingId(booking_id);

            if (booking==null){
                return "Not Found";
            }

            ticketService.unBookTickets(booking.getBookingId());// un-book tickets

            Event event;


            event = eventService.getEventById(booking.getEventId());


            eventService.setEventSoldCount(event.getEventId(), event.getTicketsSold()-booking.getTicketCount());

            bookingRepository.deleteBooking(booking.getBookingId());



        }finally {
            ServiceLock.lock.unlock();
        }
        return "Success";

    }

    public List<Booking> getBookingsByUserId(Long id){
        System.out.println("bookingService");
        List<Booking> bookings = bookingRepository.getBookingsByUserId(id);

        logger.debug("Bookings in getBookingsByUserId: {}", bookings);

        return bookings;
    }

    public String update(Booking booking){

        return "Incomplete function";
    }

}
