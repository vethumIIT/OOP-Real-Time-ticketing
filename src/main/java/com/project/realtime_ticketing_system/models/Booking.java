package com.project.realtime_ticketing_system.models;


import java.util.Objects;

public class Booking {

    private Long bookingId;
    private Long customerId;
    private Long ticketId;
    private int ticketCount;

    public Booking(Long bookingId, Long customerId, Long ticketId, int ticketCount) {
        this.setBookingId(bookingId);
        this.setCustomerId(customerId);
        this.setTicketId(ticketId);
        this.setTicketCount(ticketCount);
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

}
