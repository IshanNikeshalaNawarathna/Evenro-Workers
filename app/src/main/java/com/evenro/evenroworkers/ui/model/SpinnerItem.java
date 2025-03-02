package com.evenro.evenroworkers.ui.model;

public class SpinnerItem {

    private int itemResourceId;
    private String name;

    public SpinnerItem(int itemResourceId, String name) {
        this.itemResourceId = itemResourceId;
        this.name = name;
    }


    public int getItemResourceId() {
        return itemResourceId;
    }

    public void setItemResourceId(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
