package com.example.hulksmash.dhadkan;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hulksmash.dhadkan.controller.MyFirebaseInstanceIDService;
import com.example.hulksmash.dhadkan.controller.SessionManager;
import com.example.hulksmash.dhadkan.patientActivities.Entry;
import com.example.hulksmash.dhadkan.doctorActivities.PatientListActivity;

import java.util.HashMap;

public class ControllerActivity extends Activity{
    SessionManager session;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    MyFirebaseInstanceIDService fcm;
    Button sign_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);



                /* Create an Intent that will start the Menu-Activity. */



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                session = new SessionManager(ControllerActivity.this);
                session.checkLogin();
                fcm = new MyFirebaseInstanceIDService(ControllerActivity.this);
                final HashMap<String, String> user = session.getUserDetails();
                Log.d("DATA", user.toString());

                fcm.onTokenRefresh();
                if ("doctor".equals(user.get("type"))) {
                    Intent i = new Intent(ControllerActivity.this, PatientListActivity.class);
                    startActivity(i);
                    finish();
                } else if ("patient".equals(user.get("type"))) {
                    Intent i = new Intent(ControllerActivity.this, Entry.class);
                    startActivity(i);
                    finish();
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}

