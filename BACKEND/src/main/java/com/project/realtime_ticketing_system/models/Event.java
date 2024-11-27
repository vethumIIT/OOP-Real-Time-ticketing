package com.project.realtime_ticketing_system.models;

import java.util.List;
import java.util.Map;

public class Event {

    private Long eventId;
    private Long vendorId;
    private String eventName;
    private String eventDescription;
    private String eventLocation;
    private String eventDate;
    private int ticketsSold;
    private int totalTickets;
    private double price;

    private Map<String, List<Double>> ticketCategory;
    private Map<String, List<Double>> soldTicketsCategory;
    private String vendorName;

    public Event(Long eventId,
                 Long vendorId,
                 String eventName,
                 String eventDescription,
                 String eventLocation,
                 String eventDate,
                 int ticketsSold,
                 int totalTickets) {

        this.setEventId(eventId);
        this.setVendorId(vendorId);
        this.setEventName(eventName);
        this.setEventDescription(eventDescription);
        this.setEventLocation(eventLocation);
        this.setEventDate(eventDate);
        this.setTicketsSold(ticketsSold);
        this.setTotalTickets(totalTickets);
        //this.setPrice(price);
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

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public Map<String, List<Double>> getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(Map<String, List<Double>> ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        System.out.println("Set Vendor Name: "+vendorName);
        this.vendorName = vendorName;
    }

    public Map<String, List<Double>> getSoldTicketsCategory() {
        return soldTicketsCategory;
    }

    public void setSoldTicketsCategory(Map<String, List<Double>> soldTicketsCategory) {
        this.soldTicketsCategory = soldTicketsCategory;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", vendorId=" + vendorId +
                ", eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", eventDate='" + eventDate + '\'' +
                ", ticketsSold=" + ticketsSold +
                ", totalTickets=" + totalTickets +
                ", price=" + price +
                ", ticketCategory=" + ticketCategory +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }
}
