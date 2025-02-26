package com.evenro.evenroworkers.ui.model;

public class Location {


    private String name;
    private String latlng;

    public Location() {
    }

    public Location(String name, String latlng) {
        this.name = name;
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }
}
