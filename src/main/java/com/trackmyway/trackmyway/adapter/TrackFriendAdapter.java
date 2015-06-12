package com.trackmyway.trackmyway.adapter;

/**
 * Created by vishakha14 on 5/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.util.Friends;
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

public class TrackFriendAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Friends> mArrayList ;
    private static LayoutInflater inflater=null;
    public int selectedUserPosition;

  //  private  int pos;
    public TrackFriendAdapter(Activity a, ArrayList<Friends> aList) {
        activity = a;
        mArrayList = aList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedUserPosition= -1;

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
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.friend_row_with_radio, null);

        TextView name = (TextView)vi.findViewById(R.id.friendName);
        RadioButton radioButton = (RadioButton) vi.findViewById(R.id.radioButtton1);
        name.setText(mArrayList.get(position).getName());
        final int pos = position;

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedUserPosition = pos;
            }
        });

        return vi;
    }


}