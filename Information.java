package com.example.firebasedemo;

public class Information {

    private String email;
    private String name;

    public Information() {
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

    public Information(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
