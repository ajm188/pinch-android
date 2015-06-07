package com.pinch_in.pinch;

import java.util.Calendar;
import java.util.Locale;

import android.app.ListFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class EventsFinder extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventsFinder newInstance(int sectionNumber) {
        EventsFinder fragment = new EventsFinder();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events_finder, container, false);
        final LinearLayout eventList = (LinearLayout) rootView.findViewById(R.id.events_list);
        APICall.getEvents(rootView.getContext(), new APICall.OnAPICallComplete<Event[]>() {
                                             @Override
                                             public void callComplete(Event[] result) {
                if (result == null) {
                    return;
                }
                boolean border = false;

                for (Event event : result) {
                    if (border) {
                        View borderView = inflater.inflate(R.layout.divider, eventList, false);
                        eventList.addView(borderView);
                    } else {
                        border = true;
                    }
                    eventList.addView(createEventView(event, eventList));
                }
        }
    });

        return rootView;
    }

    public View createEventView(Event event, View rootView) {
        LayoutInflater inflater = LayoutInflater.from(rootView.getContext());
        View relLay = inflater.inflate(R.layout.event_list_event,
                null, false);

        Utils.setTextViewText(R.id.event_name, relLay, event.getTitle());
        Utils.setTextViewText(R.id.event_org, relLay, event.getOrg().getName());
        Utils.setTextViewText(R.id.event_loc, relLay, event.getLocation().getName());
        Utils.setTextViewText(R.id.event_time, relLay, String.format("%s -\n%s",
                event.startTimeString(), event.endTimeString()));
        Utils.setTextViewText(R.id.event_duration, relLay, Utils.durationString(event.duration()));
        return relLay;
    }
}
