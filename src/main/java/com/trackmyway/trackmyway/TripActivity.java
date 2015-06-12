package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vishakha14 on 5/26/2015.
 */
public class TripActivity extends Activity {


    Button btn;
    EditText tripName, tripDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_trip_mode);

        tripName = (EditText) findViewById(R.id.TripName);
        tripDesc = (EditText) findViewById(R.id.TripDesc);
        btn = (Button) findViewById(R.id.BTNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TripActivity.this, TripFriendlistActivity.class);
                intent.putExtra("tripName", tripName.getText().toString());
                intent.putExtra("tripDesc", tripDesc.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
