package com.trackmyway.trackmyway;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.trackmyway.trackmyway.adapter.FriendAdapter;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/26/2015.
 */
public class Friend_list extends Activity {
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        ListView listView = (ListView) findViewById(R.id.friendListing);

        /*ArrayList<String> values = new ArrayList<>();
        values.add("Vish 1");
        values.add("Vish 2");
        values.add("Vish 3");
        values.add("Vish 4");
        values.add("Vish 5");
        FriendAdapter fAdapter = new FriendAdapter(Friend_list.this, values );
     //   ArrayAdapter<String> adapter = new ArrayAdapter<String>(Friend_list.this, android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(fAdapter);*/


        /*btn = (Button) findViewById(R.id.BTNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Friend_list.this, Verification.class);

                startActivity(intent);

            }
        });*/
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