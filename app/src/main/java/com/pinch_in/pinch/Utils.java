package com.pinch_in.pinch;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final long[] duration_vals = {1, 60, 3600, 86400, 604800, Long.MAX_VALUE};
    private static final String[] duration_labels = {"Sec", "Min", "Hr", "Day", "Week", "Year"};
    public static final String PREFS_FILE = "pinch_internal";

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

    public static void shortToast(int string, Context context) {
        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static boolean validEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public static String formatDate(Calendar day) {
        String month = day.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.US);
        String week_day = day.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.US);
        int day_month = day.get(Calendar.DAY_OF_MONTH);
        return String.format("%s, %s %d", week_day, month, day_month);
    }

    public static Typeface getTypeFace(AssetManager asset) {
        return Typeface.createFromAsset(asset, "fonts/Lobster_Regular.ttf");
    }

    public static boolean setTextViewText(int resource, View rootView, String text) {
        return setTextViewText(resource, rootView, text, null);
    }

    public static boolean setTextViewText(int resource, View rootView, String text, Typeface tf) {
        TextView textView = (TextView) rootView.findViewById(resource);
        if (tf != null) {
            textView.setTypeface(tf);
        }
        textView.setText(text);
        return true;
    }
}
