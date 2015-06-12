package com.trackmyway.trackmyway.adapter;

/**
 * Created by vishakha14 on 5/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TripActivity;
import com.trackmyway.trackmyway.TripDetailActivity;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.TripRecord;
import com.trackmyway.trackmyway.util.WebService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishakha14 on 5/29/2015.
 */

public class TripAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<TripRecord> mArrayList ;
    private static LayoutInflater inflater=null;

  //  private  int pos;
    public TripAdapter(Activity a, ArrayList<TripRecord> aList) {
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
        TextView email = (TextView) vi.findViewById(R.id.userEmail);
        ImageView reject = (ImageView) vi.findViewById(R.id.imgRejectRequest);
        ImageView accept = (ImageView) vi.findViewById(R.id.imgAcceptRequest);
        ImageView send = (ImageView) vi.findViewById(R.id.imgSendRequest);

        name.setText(mArrayList.get(position).getTripName());
        final int pos = position;

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TripDetailActivity.class);
                intent.putExtra("position", pos);
                activity.startActivity(intent);
            }
        });

        return vi;
    }
}