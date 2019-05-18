package com.icesi.retodeezer.Entity;

public class User {
    private String id;
    private String name;
    private String lastname;
    private String firtsname;

    public User(String id, String name, String lastname, String firtsname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.firtsname = firtsname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirtsname() {
        return firtsname;
    }

    public void setFirtsname(String firtsname) {
        this.firtsname = firtsname;
    }
}
