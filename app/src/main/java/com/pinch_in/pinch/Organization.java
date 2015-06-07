package com.pinch_in.pinch;

import java.util.Calendar;

/**
 * Created by kdeal on 6/6/15.
 */
public class Organization {

    private int id;
    private String name;
    private Calendar created_at;
    private Calendar updated_at;

    public Organization(){

    }

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
