package com.trackmyway.trackmyway.adapter;

/**
 * Created by vishakha14 on 5/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TrackDetailActivity;
import com.trackmyway.trackmyway.TripDetailActivity;
import com.trackmyway.trackmyway.util.TrackRecord;
import com.trackmyway.trackmyway.util.TripRecord;

import java.util.ArrayList;

/**
 * Created by vishakha14 on 5/29/2015.
 */

public class TrackAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<TrackRecord> mArrayList ;
    private static LayoutInflater inflater=null;

  //  private  int pos;
    public TrackAdapter(Activity a, ArrayList<TrackRecord> aList) {
        activity = a;
        mArrayList = aList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return mArrayList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.friend_row, null);

        TextView name = (TextView) vi.findViewById(R.id.userName);
        name.setText(mArrayList.get(position).getFriend().getName());
        final int pos = position;
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TrackDetailActivity.class);
                intent.putExtra("position", pos);
                activity.startActivity(intent);
            }
        });
        return vi;
    }
}