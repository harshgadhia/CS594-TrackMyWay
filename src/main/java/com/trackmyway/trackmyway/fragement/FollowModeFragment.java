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
import com.trackmyway.trackmyway.adapter.FriendAdapter;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/29/2015.
 */


public class FollowModeFragment extends Fragment {

    ArrayList<String> values;
    ListView listView;
    FriendAdapter fAdapter;
    Button addFollow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.follow_mode_listing, container, false);

        listView = (ListView) rootView.findViewById(R.id.followListView);

        values = new ArrayList<>();
        values.add("Vishakha started Following you");
        values.add("Nishant started Following you");
        values.add("You have share trip route with  Nishant, Harsh and Vishakha ");
        values.add("Harsh started Following you");
        values.add("Vishakha started Following you");
        values.add("Nishant started Following you");
        values.add("You have share trip route with  Nishant, Harsh and Vishakha ");
        values.add("Harsh started Following you");

       /* fAdapter = new FriendAdapter(getActivity(), values );

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(fAdapter);*/

        addFollow = (Button) rootView.findViewById(R.id.btnAddFollow);

        addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FollowActivity.class);
                intent.putExtra("test", values.get(position).toString());
                startActivity(intent);

            }
        });
        return rootView;
    }

}