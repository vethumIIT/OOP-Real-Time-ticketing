package com.project.realtime_ticketing_system.models;

public class Event {

    private Long eventId;
    private Long vendorId;
    private String eventName;

    public Event(Long eventId, Long vendorId, String eventName) {
        this.setEventId(eventId);
        this.setVendorId(vendorId);
        this.setEventName(eventName);
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
