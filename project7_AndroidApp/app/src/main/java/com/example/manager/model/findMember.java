package com.example.manager.model;

import retrofit2.Callback;


public class findMember {
    private String id;

    public findMember(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
