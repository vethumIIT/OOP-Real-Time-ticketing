package com.project.realtime_ticketing_system.models;

public class Ticket {

    private Long ticketId;
    private Long eventId;
    private Long bookingId;
    private String ticketCategory;
    private Double price;
    private boolean isAvailable;

    public Ticket(Long ticketId, Long eventId, Long bookingId, String ticketCategory, Double price, boolean isAvailable) {
        this.setTicketId(ticketId);
        this.setEventId(eventId);
        this.setBookingId(bookingId);
        this.setTicketCategory(ticketCategory);
        this.setPrice(price);
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

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCatagory) {
        this.ticketCategory = ticketCatagory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
