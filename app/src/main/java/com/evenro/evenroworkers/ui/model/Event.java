package com.evenro.evenroworkers.ui.model;

import java.io.Serializable;

public class Event implements Serializable {

    private String id,eventName,eventOrganizerName,eventDate,eventTime,eventPrice,eventQty,eventLocation,imageUri;


    public Event() {
    }

    public Event(String id, String eventName, String eventOrganizerName, String eventDate, String eventTime, String eventPrice, String eventQty, String eventLocation, String imageUri) {
        this.id = id;
        this.eventName = eventName;
        this.eventOrganizerName = eventOrganizerName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventPrice = eventPrice;
        this.eventQty = eventQty;
        this.eventLocation = eventLocation;
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventOrganizerName() {
        return eventOrganizerName;
    }

    public void setEventOrganizerName(String eventOrganizerName) {
        this.eventOrganizerName = eventOrganizerName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventQty() {
        return eventQty;
    }

    public void setEventQty(String eventQty) {
        this.eventQty = eventQty;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
