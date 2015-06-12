package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TripRecord;

import java.util.ArrayList;


public class TripFriendlistActivity extends Activity {

    FriendListAdapter mFriendListAdapter;
    ArrayList<Friends>  mFriendList;
    private static LayoutInflater inflater=null;
    Button btnSubmit;
    ArrayList<Friends>  mSelectedFriendList;
    String tripName, tripDesc;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_friendlist);
        final Global gbl = (Global) getApplicationContext();

        mFriendList = gbl.getFriends();
        Intent intent = getIntent();
        tripName = intent.getExtras().get("tripName").toString();
        tripDesc = intent.getExtras().get("tripDesc").toString();



        mFriendListAdapter = new FriendListAdapter(getApplicationContext(), mFriendList);
        ListView listView = (ListView) findViewById(R.id.tripFriendList);
        listView.setAdapter(mFriendListAdapter);

        mSelectedFriendList = new ArrayList<Friends>();
        btnSubmit = (Button) findViewById(R.id.BTBSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");


                for(int i=0;i<mFriendList.size();i++){
                    Friends friend = mFriendList.get(i);
                    if(friend.isSelected()){
                        mSelectedFriendList.add(friend);
                    }
                }
                TripRecord trp = new TripRecord();
                trp.setDestination(tripDesc);
                trp.setTripName(tripName);
                trp.setFriends(mSelectedFriendList);
                gbl.addTripRecord(trp);
                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_friendlist, menu);
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
    private class FriendListAdapter extends BaseAdapter {
       // public  ArrayList<Friends> mFriendList;

        public FriendListAdapter(Context applicationContext, ArrayList<Friends> mFriendList) {
         //   this.mFriendList = mFriendList;
            inflater = (LayoutInflater)applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mFriendList.size();
        }

        @Override
        public Object getItem(int position) {
            return mFriendList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vi = convertView;
            if (convertView == null)
                vi = inflater.inflate(R.layout.friend_info, null);

            CheckBox chk = (CheckBox) vi.findViewById(R.id.checkUserId);
            TextView txtUserName = (TextView) vi.findViewById(R.id.userCheckName);

            txtUserName.setText(mFriendList.get(position).getName());
            final int pos = position;
            /*txtUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    mFriendList.get(pos).setSelected(true);
                }
            });*/


            chk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mFriendList.get(pos).isSelected()){
                        mFriendList.get(pos).setSelected(false);
                    }else{
                        mFriendList.get(pos).setSelected(true);
                    }

                }
            });
            return  vi;
        }
    }
}
