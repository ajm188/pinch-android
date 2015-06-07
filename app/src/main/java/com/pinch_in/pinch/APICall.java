package com.pinch_in.pinch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.ServiceConfigurationError;

/**
 * Created by kdeal on 6/6/15.
 */
public class APICall extends AsyncTask<String, Void, String> {

    private static final String SERVER_URL = "http://10.3.17.219:3000/";
    private static final String EVENT_URL = "events.json";

    private Context context;
    private OnAPICallComplete onCallComplete;

    APICall(Context context, OnAPICallComplete onCallComplete) {
        this.context = context;
        this.onCallComplete = onCallComplete;
    }

    public boolean hasNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public String doInBackground(String... urls) {
        try {
            return downloadUrl(urls[0], urls[1], urls[2]);
        } catch (IOException e) {
            Log.e("API Call", "Exception in download", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        onCallComplete.callComplete(result);
    }

    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl, String method, String body) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod(method);
            if (body != null) {
                OutputStream os = conn.getOutputStream();
                os.write(body.getBytes(Charset.defaultCharset()));
            }
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("API Call", String.format("Response code was %d", response));
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = Utils.readInputStream(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static boolean getEvents(Context context, final OnAPICallComplete<Event[]> onCallComplete) {
        APICall apiCall = new APICall(context, new OnAPICallComplete<String>() {

            @Override
            public void callComplete(String result) {
                Event[] result_obj = Utils.getGson().fromJson(result, Event[].class);
                onCallComplete.callComplete(result_obj);
            }
        });
        if (!apiCall.hasNetwork()) {
            return false;
        }

        apiCall.execute(APICall.SERVER_URL + APICall.EVENT_URL, "GET", null);
        return true;
    }

    public interface OnAPICallComplete<O> {
        public void callComplete(O result);
    }
}
