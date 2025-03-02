package com.evenro.evenroworkers.ui.model;

import java.io.Serializable;

public class User implements Serializable {
    private String email,name;

    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
