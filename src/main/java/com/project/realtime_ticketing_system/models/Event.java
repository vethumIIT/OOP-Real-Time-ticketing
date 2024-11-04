package com.project.realtime_ticketing_system.models;

public class Event {

    private Long eventId;
    private Long vendorId;
    private String eventName;
    private String eventDate;
    private int ticketsSold;
    private int totalTickets;
    private double price;

    public Event(Long eventId, Long vendorId, String eventName, String eventDate, int ticketsSold, int totalTickets, double price) {
        this.setEventId(eventId);
        this.setVendorId(vendorId);
        this.setEventName(eventName);
        this.setEventDate(eventDate);
        this.setTicketsSold(ticketsSold);
        this.setTotalTickets(totalTickets);
        this.setPrice(price);
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
