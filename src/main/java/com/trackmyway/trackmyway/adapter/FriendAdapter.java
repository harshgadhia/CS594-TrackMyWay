package com.trackmyway.trackmyway.adapter;

/**
 * Created by vishakha14 on 5/29/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TrackDetailActivity;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.Global;
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

public class FriendAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Friends> mArrayList ;
    private static LayoutInflater inflater=null;
    Global gbl;

  //  private  int pos;
    public FriendAdapter(Activity a, ArrayList<Friends> aList) {
        activity = a;
        mArrayList = aList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        gbl = (Global) a.getApplicationContext();


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
            vi = inflater.inflate(R.layout.friend_row, null);

        TextView name = (TextView)vi.findViewById(R.id.userName);
        TextView email = (TextView)vi.findViewById(R.id.userEmail);
        ImageView reject = (ImageView) vi.findViewById(R.id.imgRejectRequest);
        final ImageView accept = (ImageView) vi.findViewById(R.id.imgAcceptRequest);
        ImageView send = (ImageView) vi.findViewById(R.id.imgSendRequest);

        name.setText(mArrayList.get(position).getName());
        email.setText(mArrayList.get(position).getEmail());
        accept.setVisibility(View.VISIBLE);
        send.setVisibility(View.GONE);
        reject.setVisibility(View.GONE);
        final int pos = position;

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setVisibility(View.GONE);
                Friends friend = mArrayList.get(pos);
                gbl.addFriend(friend);
            }
        });

        return vi;
    }

    class APICall extends AsyncTask<String, String, String>{

        @Override
        public String doInBackground(String... params){
            String url = params[0];
            String email = params[1];
            HttpURLConnection conn = null;
            URL dataUrl = null;
            try {
                dataUrl = new URL(url);
                conn = (HttpURLConnection) dataUrl.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                List<NameValuePair> post = new ArrayList<NameValuePair>();
                post.add(new BasicNameValuePair("email", email));
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getQuery(post));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                int status = conn.getResponseCode();
                if (status == 200) {
                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String responseString;
                    StringBuilder sb = new StringBuilder();
                    while ((responseString = reader.readLine()) != null) {
                        sb = sb.append(responseString);
                    }
                    String resString = sb.toString();
                    return resString;
                } else {
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
        {
            StringBuilder result = new StringBuilder();
            boolean first = true;

            for (NameValuePair pair : params)
            {
                if (first)
                    first = false;
                else
                    result.append("&");

                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
            }

            return result.toString();
        }
    }
}