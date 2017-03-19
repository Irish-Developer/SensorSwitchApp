package com.project.youcef.sensorswitchapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**************************************************
 * Title: Main Activity
 * Name: Youcef O'Connor
 * Student number: x13114557
 * Date: 20/02/2017
 **************************************************/
public class MainActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener{

    private RequestQueue mQueue;
    private TextView view;
    HashMap<String, String> myHashmap = new HashMap();
    ToggleButton toggle1, toggle2, toggle3, toggle4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggle1 = (ToggleButton) findViewById(R.id.lightTBtn);
        toggle2 = (ToggleButton) findViewById(R.id.micTBtn);
        toggle3 = (ToggleButton) findViewById(R.id.tempTBtn);
        toggle4 = (ToggleButton) findViewById(R.id.analogTBtn);

        view = (TextView) findViewById(R.id.textView);
        String url = "https://dweet.io/get/latest/dweet/for/youcef_iot?";                   //this is where the dweets are received from

        mQueue = Queue.getInstance(this.getApplicationContext())                            //This code was provided my lecturer Dominic Carr
                .getRequestQueue();                                                         //It uses the Volley HTTP networking library to make calls to dweet

        final MyJSONRequest jsonRequest = new MyJSONRequest(Request.Method.GET, url,        //This code was provided my lecturer Dominic Carr
                new JSONObject(), this, this);                                              //Requests JSON data from my dweet thing
        jsonRequest.setTag("test");
        mQueue.add(jsonRequest);
    }

    ////////////////////////////////  #1 Light Sensor Switch //////////////////////////////////
    public void lightTBtn (View v) {
        Boolean myLight = Boolean.valueOf(myHashmap.get("light"));                          // this simply takes the current state of the sensor (stored in myHashmap) and the toggle button.
        myHashmap.put("light", String.valueOf(!myLight));                                   //When the toggle button is pressed the value and button state is flipped or switched to its opposite

        JSONObject outputJson = new JSONObject(myHashmap);                                  //The new state is stored in myHashmap

        mQueue = Queue.getInstance(this.getApplicationContext())                            //It uses the Volley HTTP networking library to make calls to dweet
                .getRequestQueue();

        String url = "https://dweet.io/dweet/for/youcef_iot";                               //This is where the JSON (dweet)is sent too

        final MyJSONRequest jsonRequest = new MyJSONRequest(Request.Method.POST, url,       //Posts sensor boolean commands in JSON to my dweet thing
                outputJson, this, this);
        jsonRequest.setTag("test");
        mQueue.add(jsonRequest);
    }
    //////////////////////////// #2 Microphone Sensor Switch ///////////////////////////
    public void micTBtn (View v) {
        Boolean myMic = Boolean.valueOf(myHashmap.get("mic"));
        myHashmap.put("mic", String.valueOf(!myMic));

        JSONObject outputJson = new JSONObject(myHashmap);
        String url = "https://dweet.io/dweet/for/youcef_iot";

        mQueue = Queue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final MyJSONRequest jsonRequest = new MyJSONRequest(Request.Method.POST, url,
                outputJson, this, this);
        jsonRequest.setTag("test");
        mQueue.add(jsonRequest);
    }

    ///////////////////////////// #3 Temperature Sensor Switch  ///////////////////////////
    public void tempTBtn (View v){
        Boolean myTemp = Boolean.valueOf(myHashmap.get("temp"));
        myHashmap.put("temp", String.valueOf(!myTemp));

        JSONObject outputJson = new JSONObject(myHashmap);
        String url = "https://dweet.io/dweet/for/youcef_iot";

        mQueue = Queue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final MyJSONRequest jsonRequest = new MyJSONRequest(Request.Method.POST, url,
                outputJson, this, this);
        jsonRequest.setTag("test");
        mQueue.add(jsonRequest);
    }

    ///////////////////////////// #4 Analog Sensor Switch  ///////////////////
    public void analogTBtn (View v){

        Boolean myAna = Boolean.valueOf(myHashmap.get("analog"));
        myHashmap.put("analog", String.valueOf(!myAna));

        JSONObject outputJson = new JSONObject(myHashmap);
        String url = "https://dweet.io/dweet/for/youcef_iot";

        mQueue = Queue.getInstance(this.getApplicationContext())
                .getRequestQueue();

        final MyJSONRequest jsonRequest = new MyJSONRequest(Request.Method.POST, url,
                outputJson, this, this);
        jsonRequest.setTag("test");
        mQueue.add(jsonRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {                       //I had help with developing the onResponse and myToggle methods from student Rich Mangain
        view.setText(response.toString());                          //please note; he did not give me code and I did not copy any code
        String jsonString = response.toString();                    //Rich told me the direction and steps I needed to take and I coded it myself
        try {
            JSONObject obj1 = new JSONObject(jsonString);           //declaring an instance of JSONObject - Received Dweet
            JSONArray myArray = obj1.getJSONArray("with");          //Opening the array layer named "with" of the dweet
            JSONObject layer2 = myArray.getJSONObject(0);           //obj2 is being positioned at position 0 of the array
            JSONObject layer3 = layer2.getJSONObject("content");    //declaring the JSONObject "content" that is in position 0 of the array. this is the data use

            String objLight = layer3.getString("light");            //Gets the string "light" and it's value from the JSONObtect "content"
            String objMic = layer3.getString("mic");                //Gets the string "mic" and it's value from the JSONObtect "content"
            String objTemp = layer3.getString("temp");              //Gets the temp "mic" and it's value from the JSONObtect "content"
            String objAna = layer3.getString("analog");             //Gets the analog "mic" and it's value from the JSONObtect "content"

            myHashmap.put("light", objLight );                      //Then the string and their values are stored in a hashmap (myHashmap)
            myHashmap.put("mic", objMic );
            myHashmap.put("temp", objTemp );
            myHashmap.put("analog", objAna);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        myToggle();
    }

    public void myToggle (){
        if (myHashmap.containsKey("light")){                        //
            String Temp = myHashmap.get("light");
            if (Temp.equals("true")){
                toggle1.setChecked(true);
            } else {
                toggle1.setChecked(false);
            }
        }
        if (myHashmap.containsKey("mic")){
            String Temp = myHashmap.get("mic");
            if (Temp.equals("true")){
                toggle2.setChecked(true);
            } else {
                toggle2.setChecked(false);
            }
        }
        if (myHashmap.containsKey("temp")){
            String Temp = myHashmap.get("temp");
            if (Temp.equals("true")){
                toggle3.setChecked(true);
            } else {
                toggle3.setChecked(false);
            }
        }
        if (myHashmap.containsKey("analog")){
            String Temp = myHashmap.get("analog");
            if (Temp.equals("true")){
                toggle4.setChecked(true);
            } else {
                toggle4.setChecked(false);
            }
        }
    }



    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                //Sign out
                AuthUI.getInstance().signOut(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
