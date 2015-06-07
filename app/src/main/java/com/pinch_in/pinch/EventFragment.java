package com.pinch_in.pinch;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.pinch_in.pinch.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {
    private static final String EVENT_ID = "eventID";

    private int mEventID;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventID ID of the particular event.
     * @return A new instance of fragment EventFragment.
     */
    public static EventFragment newInstance(int eventID) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt(EVENT_ID, eventID);
        fragment.setArguments(args);
        return fragment;
    }

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEventID = getArguments().getInt(EVENT_ID);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        final LinearLayout eventLayout = (LinearLayout) rootView.findViewById(R.id.event_show);
        APICall.getEvent(mEventID, rootView.getContext(), new APICall.OnAPICallComplete<Event>() {
            @Override
            public void callComplete(Event event) {
                if (event == null) {
                    return;
                }

                Utils.setTextViewText(R.id.event_show_title, rootView, event.getTitle());
                Utils.setTextViewText(R.id.event_show_description, rootView, event.getDescription());
                Utils.setTextViewText(R.id.event_show_time, rootView, String.format("%s - %s",
                        event.startTimeString(), event.endTimeString()));
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
