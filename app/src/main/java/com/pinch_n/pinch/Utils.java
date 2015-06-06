package com.pinch_n.pinch;

import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kdeal on 6/6/15.
 */
public class Utils {

    public static boolean setTextViewText(int resource, View rootView, String text) {
        TextView textView = (TextView) rootView.findViewById(resource);
        textView.setText(text);
        return true;
    }

    public static String formatTime(Calendar time) {
        int hour = time.get(Calendar.HOUR);
        int min = time.get(Calendar.MINUTE);
        String am_pm = time.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.US);
        return String.format("%d:%2d %s", hour, min, am_pm);
    }

    public static String durationString(long seconds) {
       return String.format("%d secs", seconds);
    }
}
