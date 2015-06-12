package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TripRecord;

import java.util.ArrayList;


public class TripDetailActivity extends Activity {

    TextView txt;
    TripRecord tripRecord;
    ArrayList<TripRecord> tripRecords;

    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        txt = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        int pos =  intent.getIntExtra("position", 0 );
        final Global gbl = (Global) getApplicationContext();
        tripRecords = gbl.getTripRecord();
        tripRecord = tripRecords.get(pos);
        TextView tvName = (TextView) findViewById(R.id.tripDetailName);
        TextView tripDestination = (TextView) findViewById(R.id.tripDestination);
        tvName.setText(tripRecord.getTripName());
        tripDestination.setText(tripRecord.getDestination());

        Log.d("total Friends", tripRecord.getFriends().size() + "");


        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.friendMap)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }

        if (googleMap != null){
            double latitude = gbl.getLat();
            double longitude = gbl.getLon();
            MarkerOptions marker;
            for(int i=0; i<tripRecord.getFriends().size() ; i++){
                marker = new MarkerOptions().position(new LatLng(latitude + Math.random(), longitude + Math.random())).title(tripRecord.getFriends().get(i).getName());
                googleMap.addMarker(marker);
            }


            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(5).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_detail, menu);
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
