package com.evenro.evenroworkers.ui.model;

import java.io.Serializable;

public class InvoiceData implements Serializable {
    private String id, event_name,event_date,event_time,event_price,event_qty,imageUri;


    public InvoiceData(String id, String event_name, String event_date, String event_time, String event_price, String event_qty, String imageUri) {
        this.id = id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_time = event_time;
        this.event_price = event_price;
        this.event_qty = event_qty;
        this.imageUri = imageUri;
    }

    public InvoiceData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public void setEvent_date(String event_date) {
        this.event_date = event_date;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_price() {
        return event_price;
    }

    public void setEvent_price(String event_price) {
        this.event_price = event_price;
    }

    public String getEvent_qty() {
        return event_qty;
    }

    public void setEvent_qty(String event_qty) {
        this.event_qty = event_qty;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
