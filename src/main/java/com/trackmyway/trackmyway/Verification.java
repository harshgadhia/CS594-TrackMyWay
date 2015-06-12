package com.trackmyway.trackmyway;

import android.app.Activity;
import android.content.Context;
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

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Verification extends Activity {

    public final static String api_key = "droids";
    public final static String apiURL = "http://trackmyway.uphero.com/index.php/api/verify";
    String emailId;
    String name;
    String vCode;


    private class CallAPI extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0]; // URL to call
            String resultToDisplay = "";
            InputStream in = null;
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

        protected void onPostExecute(String result) {
            try{
                JSONObject res = new JSONObject(result);
                Log.d("Response ", result);
                String resCode = res.getString("code");
                if(resCode.equals("200")){
                    int userId = res.getInt("userId");
                    SharedPreferences.Editor editor = getSharedPreferences("MyLogFile", MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.putInt("userId", userId);
                    editor.putString("email", emailId);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainView.class);
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


    } // end CallAPI


    EditText etVCode;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        emailId = intent.getExtras().get("emailId").toString();
        vCode = intent.getExtras().get("verificationCode").toString();
        name = intent.getExtras().get("name").toString();
        setContentView(R.layout.verification);
        getActionBar().hide();
        etVCode = (EditText) findViewById(R.id.ETVerificationCode);
        btn = (Button) findViewById(R.id.BTNNext);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempCode = etVCode.getText().toString();
                Log.d("PassedCode : " , vCode);
                if(tempCode.equals(vCode)){
                    String urlString = apiURL + "?name=" + name + "&email=" + emailId + "&vCode=" + vCode;
                    new CallAPI().execute(urlString);

                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Verification Code" , Toast.LENGTH_LONG).show();
                }
            }
        });
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

    // This is the method that is called when the submit button is clicked
    public void verifyCode(View view) {
        EditText codeEditText = (EditText) findViewById(R.id.ETVerificationCode);
        String email = emailId;
        String code = codeEditText.getText().toString();

        if( email != null && !email.isEmpty()) {
            email = "harshgadhia@gmail.com";
            String urlString = apiURL + "api_key=" + api_key + "&email=" + email + "&code=" + code;
            Log.i("API: ", apiURL);
            new CallAPI().execute(urlString);
        }
    }
}