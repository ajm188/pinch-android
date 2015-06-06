package com.pinch_n.pinch;

import android.provider.CalendarContract;
import android.provider.ContactsContract;

import java.util.Calendar;

/**
 * Created by kdeal on 6/6/15.
 */
public class Event {

    private String name;
    private Organization org;
    private EventLocation location;
    private Calendar startTime;
    private Calendar endTime;

    Event(String name, Organization org, EventLocation location, Calendar startTime, Calendar endTime) {
        this.name = name;
        this.org = org;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public Organization getOrg() {
        return org;
    }

    public EventLocation getLocation() {
        return location;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public long duration() {
        return endTime.getTimeInMillis() - startTime.getTimeInMillis();
    }

    public String endTimeString() {
        return Utils.formatTime(this.endTime);
    }

    public String startTimeString() {
        return Utils.formatTime(this.startTime);
    }

    public String timeString() {
        return String.format("%s - %s", this.startTimeString(), this.endTimeString());
    }
}
