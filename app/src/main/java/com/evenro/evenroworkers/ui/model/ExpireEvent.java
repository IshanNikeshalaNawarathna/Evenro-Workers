package com.evenro.evenroworkers.ui.model;

import java.io.Serializable;

public class ExpireEvent implements Serializable {
    private String expireEventId,expireEventName,expireEventOrganizerName,expireEventDate,expireEventQty,expireEvenImage;

    public ExpireEvent(String expireEventId, String expireEventName, String expireEventOrganizerName, String expireEventDate, String expireEventQty, String expireEvenImage) {
        this.expireEventId = expireEventId;
        this.expireEventName = expireEventName;
        this.expireEventOrganizerName = expireEventOrganizerName;
        this.expireEventDate = expireEventDate;
        this.expireEventQty = expireEventQty;
        this.expireEvenImage = expireEvenImage;
    }

    public ExpireEvent() {
    }

    public String getExpireEventId() {
        return expireEventId;
    }

    public void setExpireEventId(String expireEventId) {
        this.expireEventId = expireEventId;
    }

    public String getExpireEventName() {
        return expireEventName;
    }

    public void setExpireEventName(String expireEventName) {
        this.expireEventName = expireEventName;
    }

    public String getExpireEventOrganizerName() {
        return expireEventOrganizerName;
    }

    public void setExpireEventOrganizerName(String expireEventOrganizerName) {
        this.expireEventOrganizerName = expireEventOrganizerName;
    }

    public String getExpireEventDate() {
        return expireEventDate;
    }

    public void setExpireEventDate(String expireEventDate) {
        this.expireEventDate = expireEventDate;
    }

    public String getExpireEventQty() {
        return expireEventQty;
    }

    public void setExpireEventQty(String expireEventQty) {
        this.expireEventQty = expireEventQty;
    }

    public String getExpireEvenImage() {
        return expireEvenImage;
    }

    public void setExpireEvenImage(String expireEvenImage) {
        this.expireEvenImage = expireEvenImage;
    }
}
