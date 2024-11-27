package com.project.realtime_ticketing_system.models;


public class Booking {

    private Long bookingId;
    private Long customerId;
    private Long eventId;
    private int ticketCount;
    private String bookingDate;
    private String ticketCategory;

    private String eventName;

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customerId=" + customerId +
                ", eventId=" + eventId +
                ", ticketCount=" + ticketCount +
                ", bookingDate='" + bookingDate + '\'' +
                ", ticketCategory='" + ticketCategory + '\'' +
                ", eventName='" + eventName + '\'' +
                '}';
    }

    public Booking(Long bookingId, Long customerId, Long eventId, int ticketCount, String bookingDate, String ticketCategory) {
        this.setBookingId(bookingId);
        this.setCustomerId(customerId);
        this.setEventId(eventId);
        this.setTicketCount(ticketCount);
        this.setBookingDate(bookingDate);
        this.setTicketCategory(ticketCategory);
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

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
    }
}
