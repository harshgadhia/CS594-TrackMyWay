package com.trackmyway.trackmyway.fragement;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.trackmyway.trackmyway.MainView;
import com.trackmyway.trackmyway.R;
import com.trackmyway.trackmyway.TripActivity;
import com.trackmyway.trackmyway.adapter.FriendAdapter;
import com.trackmyway.trackmyway.util.Friends;
import com.trackmyway.trackmyway.util.WebService;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishakha14 on 5/29/2015.
 */
public class FriendFragment extends Fragment {

    ArrayList<Friends> mFriends;
    ListView listView;
    FriendAdapter fAdapter;
    Button addTrip;
    String csvEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.trip_mode_listing, container, false);

        listView = (ListView) rootView.findViewById(R.id.tripListView);
        csvEmail = "";
        mFriends = getNameEmailDetails();

        fAdapter = new FriendAdapter(getActivity(), mFriends);
        listView.setAdapter(fAdapter);
        // new EmailSynch().execute();

        addTrip = (Button) rootView.findViewById(R.id.btnAddTrip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TripActivity.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Intent intent = new Intent(getActivity(), TripActivity.class);
                intent.putExtra("test", mFriends.get(position).toString());
                startActivity(intent);*/

            }
        });
        return rootView;
    }

    public ArrayList<Friends> getNameEmailDetails() {
        ArrayList<Friends> friends = new ArrayList<Friends>();
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("Name :", id);
                Cursor cur1 = cr.query(
                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                        new String[]{id}, null);
                while (cur1.moveToNext()) {
                    //to get the contact names
                    String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Log.d("Name :", name);
                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    Log.d("Email", email);
                    if (email != null) {
                        Friends friend = new Friends();
                        friend.setName(name);
                        friend.setEmail(email);
                        csvEmail = csvEmail + email + ",";
                        friends.add(friend);
                    }
                }
                cur1.close();
            }
        }
        return friends;
    }

    class EmailSynch extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String apiurl = new WebService().getConatctSynchURL();
            HttpURLConnection conn = null;
            URL dataUrl = null;
            try {
                dataUrl = new URL(apiurl);
                conn = (HttpURLConnection) dataUrl.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                List<NameValuePair> post = new ArrayList<NameValuePair>();
                post.add(new BasicNameValuePair("emailList", csvEmail));

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

        protected void onPostExecute(String result) {
            try{
                Log.d("Response ", result);
                JSONObject res = new JSONObject(result);

                String resCode = res.getString("code");
                if(resCode.equals("200")){
                    ArrayList<Friends> tempFriends = new ArrayList<Friends>();
                    JSONArray synchData = res.getJSONArray("data");
                    JSONArray requestRes = res.getJSONArray("requestsRecieved");
                    JSONArray requestSend = res.getJSONArray("requestsSent");
                    for(int i = 0; i< synchData.length();i++){
                        Friends tempFriend = new Friends();
                        JSONObject userRow = synchData.getJSONObject(i);
                        tempFriend.setName(userRow.getString("name"));
                        tempFriend.setEmail(userRow.getString("email"));
                        tempFriend.setRequestStatus(userRow.getString("friendRequestStatus"));
                        tempFriend.setRequestType("approved");
                        tempFriends.add(tempFriend);
                    }

                    for(int i = 0; i < mFriends.size(); i++){
                        for(int j = 0; j< tempFriends.size();j++){
                            if(mFriends.get(i).getEmail().equals(tempFriends.get(j).getEmail())){
                                mFriends.get(i).setRequestType(tempFriends.get(j).getRequestType());
                                mFriends.get(i).setRequestStatus(tempFriends.get(j).getRequestStatus());
                            }
                        }
                    }

                    for(int i = 0; i< requestRes.length();i++){
                        Friends tempFriend = new Friends();
                        JSONObject userRow = requestRes.getJSONObject(i);
                        tempFriend.setName(userRow.getString("name"));
                        tempFriend.setEmail(userRow.getString("email"));
                        tempFriend.setRequestStatus(userRow.getString("friendRequestStatus"));
                        tempFriend.setRequestType("received");
                        mFriends.add(tempFriend);
                    }

                    for(int i = 0; i< requestSend.length();i++){
                        Friends tempFriend = new Friends();
                        JSONObject userRow = requestSend.getJSONObject(i);
                        tempFriend.setName(userRow.getString("name"));
                        tempFriend.setEmail(userRow.getString("email"));
                        tempFriend.setRequestStatus(userRow.getString("friendRequestStatus"));
                        tempFriend.setRequestType("send");
                        mFriends.add(tempFriend);
                    }

                    fAdapter = new FriendAdapter(getActivity(), mFriends);
                    listView.setAdapter(fAdapter);

                }else{
                    String message = res.getString("message");
                    Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity().getApplicationContext(), "Contact Synch fail", Toast.LENGTH_LONG).show();
            }

        }

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
