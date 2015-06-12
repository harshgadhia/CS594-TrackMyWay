package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trackmyway.trackmyway.util.GPSTracker;
import com.trackmyway.trackmyway.util.Global;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    public final static String api_key = "droids";
    public final static String apiURL = "http://trackmyway.uphero.com/index.php/api/login";
    public final static String EXTRA_MESSAGE = "MESSAGE";

    public String emailId = "";
    public String name = "";


    private class emailVerificationResult {
        public String name;
        public String email;
        public String code;
    }

    private class CallAPI extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0]; // URL to call
            String resultToDisplay = "";
            InputStream in = null;
            emailVerificationResult result = null;

            // HTTP Get
            try {
                Log.d("API URL : " , urlString);
                HttpURLConnection connection = null;
                URL dataUrl = new URL(urlString);
                connection = (HttpURLConnection) dataUrl.openConnection();
                connection.connect();
                int status = connection.getResponseCode();
                if (status == 200) {
                    InputStream is = connection.getInputStream();
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
            } catch (Exception e ) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }

        }

        protected void onPostExecute(String resString) {

            Log.d("API Res : " , resString);

            try{
                JSONObject res = new JSONObject(resString);
                String resCode = res.getString("code");
                if(resCode.equals("200")){
                    String verificationCode = res.getString("verificationCode");
                    Intent intent = new Intent(getApplicationContext(), Verification.class);
                    intent.putExtra("emailId", emailId);
                    intent.putExtra("verificationCode", verificationCode);
                    intent.putExtra("name", name);
                    startActivity(intent);
                    finish();
                }else{
                    String message = res.getString("message");
                    Toast.makeText(getApplicationContext(), message , Toast.LENGTH_LONG).show();
                }
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "There is some problem please try again", Toast.LENGTH_LONG).show();
            }

        }

        private emailVerificationResult parseXML( XmlPullParser parser ) throws XmlPullParserException, IOException {
            int eventType = parser.getEventType();
            emailVerificationResult result = new emailVerificationResult();

            while( eventType!= XmlPullParser.END_DOCUMENT) {
                String name = null;
                switch(eventType)
                {
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if( name.equals("Error")) {
                            System.out.println("Web API Error!");
                        }
                        else if ( name.equals("email")) {
                            result.email = parser.nextText();
                        }
                        else if (name.equals("name")) {
                            result.name = parser.nextText();
                        }
                        else if (name.equals("code")) {
                            result.code = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                } // end switch

                eventType = parser.next();
            } // end while
            return result;
        }


    } // end CallAPI



    Button btn;
    EditText emailEditText;
    EditText nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().hide();
        final Global gbl = (Global) getApplicationContext();


        GPSTracker gpt = new GPSTracker(getApplicationContext());
        gpt.getLocation();
        gbl.setLat(gpt.getLatitude());
        gbl.setLon(gpt.getLongitude());
        SharedPreferences prefs = getSharedPreferences("MyLogFile", MODE_PRIVATE);
        String email = prefs.getString("email", null);
        if (email != null) {
            Intent intent = new Intent(getApplicationContext(), MainView.class);
            startActivity(intent);
            finish();
        }

        /*SharedPreferences.Editor editor = getSharedPreferences("MyLogFile", MODE_PRIVATE).edit();
        editor.putInt("userId", 1);
        editor.commit();
        gbl.setUserId(1);
        Intent intent = new Intent(getApplicationContext(), MainView.class);
        startActivity(intent);
        finish();*/


    }
    public void verifyEmail(View view) {
        emailEditText = (EditText) findViewById(R.id.ETEmail);
        nameEditText = (EditText) findViewById(R.id.FName);
        btn = (Button) findViewById(R.id.BTNNext);

        String email = emailEditText.getText().toString();
        emailId = email;
        name = nameEditText.getText().toString();

        if( email != null && !email.isEmpty()) {


        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String name = nameEditText.getText().toString();
                 String email = emailEditText.getText().toString();
                 String urlString = apiURL + "?name=" + name + "&email=" + email;
                new CallAPI().execute(urlString);
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
