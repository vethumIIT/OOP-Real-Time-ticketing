package com.project.realtime_ticketing_system.models;

public class Ticket {

    private Long ticketId;
    private Long eventId;
    private Long bookingId;
    private boolean isAvailable;

    public Ticket(Long ticketId, Long eventId, Long bookingId, boolean isAvailable) {
        this.setTicketId(ticketId);
        this.setEventId(eventId);
        this.setBookingId(bookingId);
        this.setAvailable(isAvailable);
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
