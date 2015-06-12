package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trackmyway.trackmyway.util.Global;
import com.trackmyway.trackmyway.util.TrackRecord;


public class TrackDetailActivity extends Activity {

    TextView txt;
    Global gbl;
    TrackRecord mTrackRecord;
    Button btnFinish;
    int posion;
    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_detail);
        txt = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        posion = intent.getIntExtra("position", 0);
        Log.d("Track posion", posion + "");
        gbl = (Global) getApplicationContext();
        btnFinish = (Button) findViewById(R.id.finishButton);
        mTrackRecord = gbl.getTrackRecord().get(posion);

        txt.setText(mTrackRecord.getFriend().getName());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gbl.getTrackRecord().remove(posion);
                finish();
            }
        });

       /* MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.friendMap);*/
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
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title(mTrackRecord.getFriend().getName());
            googleMap.addMarker(marker);

           CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(latitude, longitude)).zoom(12).build();

            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_detail, menu);
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
