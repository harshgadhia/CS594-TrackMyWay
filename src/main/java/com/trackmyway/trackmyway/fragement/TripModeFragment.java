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

import com.trackmyway.trackmyway.FollowActivity;
import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TripActivity;
import com.trackmyway.trackmyway.TripDetailActivity;
import com.trackmyway.trackmyway.adapter.FriendAdapter;
import com.trackmyway.trackmyway.adapter.TripAdapter;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TripRecord;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/29/2015.
 */


public class TripModeFragment extends Fragment {

    ArrayList<TripRecord> values;
    ListView listView;
    TripAdapter fAdapter;
    Button addFollow;
    Global gbl;
    @Override
    public void onResume() {
        super.onResume();
        values = gbl.getTripRecord();
        fAdapter = new TripAdapter(getActivity(), values );
        listView.setAdapter(fAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_mode_listing, container, false);

        listView = (ListView) rootView.findViewById(R.id.tripListView);
        addFollow = (Button) rootView.findViewById(R.id.btnAddTrip);

        gbl = (Global) getActivity().getApplicationContext();

        addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TripActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TripDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });
        return rootView;
    }

}