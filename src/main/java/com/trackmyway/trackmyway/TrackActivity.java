package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.trackmyway.trackmyway.adapter.TrackFriendAdapter;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TrackRecord;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/22/2015.
 */
public class TrackActivity extends Activity {

    Button btn;
    ListView trackFriendListView;
    TrackFriendAdapter mFriendListAdapter;
    ArrayList<Friends> mFriendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_track_mode);
        final Global gbl = (Global) getApplicationContext();

        btn = (Button) findViewById(R.id.BTNext);
        trackFriendListView = (ListView) findViewById(R.id.TrackFriendList);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedIndex =   mFriendListAdapter.selectedUserPosition;
                if(selectedIndex == -1){
                    Toast.makeText(getApplicationContext(), "Please select friend", Toast.LENGTH_LONG).show();
                }else{
                    TrackRecord trc = new TrackRecord();
                    trc.setFriend(mFriendList.get(selectedIndex));
                    gbl.addTrackRecord(trc);
                    finish();
                }
            }
        });

        mFriendList = gbl.getFriends();

        mFriendListAdapter = new TrackFriendAdapter(TrackActivity.this, mFriendList);
        trackFriendListView = (ListView) findViewById(R.id.TrackFriendList);
        trackFriendListView.setAdapter(mFriendListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
