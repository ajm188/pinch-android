package com.pinch_in.pinch;

import java.util.Calendar;

/**
 * Created by kdeal on 6/6/15.
 */
public class Event {

    private int id;
    private String title;
    private String description;
    private Organization non_profit;
    private EventLocation location;
    private Calendar start_time;
    private Calendar end_time;
    private String url;

    public Event() {

    }

    public Event(String title, String description, Organization org, EventLocation location, Calendar start_time, Calendar end_time) {
        this.title = title;
        this.description = description;
        this.non_profit = org;
        this.location = location;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public Organization getOrg() {
        return non_profit;
    }

    public EventLocation getLocation() {
        if (location == null) {
            location = new EventLocation("Not Specified");
        }
        return location;
    }

    public Calendar getStartTime() {
        return start_time;
    }

    public Calendar getEndTime() {
        return end_time;
    }

    public long duration() {
        return (end_time.getTimeInMillis() - start_time.getTimeInMillis()) / 1000;
    }

    public String endTimeString() {
        return Utils.formatTime(this.end_time);
    }

    public String startTimeString() {
        return Utils.formatTime(this.start_time);
    }

    public String timeString() {
        return String.format("%s - %s", this.startTimeString(), this.endTimeString());
    }
}
