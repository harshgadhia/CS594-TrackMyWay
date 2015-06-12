package com.trackmyway.trackmyway.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TrackActivity;
import com.trackmyway.trackmyway.TrackDetailActivity;
import com.trackmyway.trackmyway.adapter.FriendAdapter;
import com.trackmyway.trackmyway.adapter.TrackAdapter;
import com.trackmyway.trackmyway.adapter.TripAdapter;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TrackRecord;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/29/2015.
 */

public class TrackModeFragment extends Fragment {


    ArrayList<TrackRecord> values;
    ListView listView;
    TrackAdapter fAdapter;
    Button addTrack;
    Global gbl;

    @Override
    public void onResume() {
        super.onResume();
        values = gbl.getTrackRecord();
        fAdapter = new TrackAdapter(getActivity(), values );
        listView.setAdapter(fAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.track_mode_listing, container, false);

        listView = (ListView) rootView.findViewById(R.id.trackListView);

        values = new ArrayList<TrackRecord>();
        gbl = (Global) getActivity().getApplicationContext();
        values = gbl.getTrackRecord();
        fAdapter = new TrackAdapter(getActivity(), values );
        listView.setAdapter(fAdapter);

        addTrack = (Button) rootView.findViewById(R.id.btnAddTrack);
        addTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TrackActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TrackDetailActivity.class);
                intent.putExtra("position", values.get(position).toString());
                startActivity(intent);

            }
        });
        return rootView;
    }

}