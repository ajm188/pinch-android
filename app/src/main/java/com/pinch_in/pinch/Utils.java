package com.pinch_in.pinch;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pinch_in.pinch.gson.CalendarAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by kdeal on 6/6/15.
 */
public class Utils {

    private static final int EOF = -1;
    private static Gson gson;
    private static long[] duration_vals = {1, 60, 3600, 86400, 604800, Long.MAX_VALUE};
    private static String[] duration_labels = {"Sec", "Min", "Hr", "Day", "Week", "Year"};

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
        for(int i = 1; i < duration_labels.length; i++) {
            if (seconds < duration_vals[i + 1]) {
                double time = ((double) seconds) / duration_vals[i];
                time = Math.round(time * 4) / 4f;
                if (time == 1.0) {
                    return String.format("%s %s", prettyDouble(time), duration_labels[i]);
                } else {
                    return String.format("%s %ss", prettyDouble(time), duration_labels[i]);
                }
            }
        }
       return String.format("%d secs", seconds);
    }

    public static String prettyDouble(Double dbl) {
        if (dbl == Math.round(dbl)) {
            return String.valueOf(Math.round(dbl));
        }
        return String.valueOf(dbl);
    }

    public static String readInputStream(InputStream stream) throws IOException, UnsupportedEncodingException {
        StringBuilder string = new StringBuilder();
        Reader reader = new InputStreamReader(stream, "UTF-8");
        int read;
        while ((read = reader.read()) != EOF) {
           string.append((char) read);
        }
        return string.toString();
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().registerTypeHierarchyAdapter(Calendar.class, new CalendarAdapter())
                    .create();
        }
        return gson;
    }
}
