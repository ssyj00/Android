package com.ceb.dcpms.android.entity;

public class User {

    public static final String Table = "user";
    public static final String Id = "id";
    public static final String Name = "name";

    private String id;
    private String name;

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
}
