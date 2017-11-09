package com.example.hulksmash.dhadkan.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by hulksmash on 9/9/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService  {
    SessionManager session;
    HashMap<String, String> user;
    Context _context;

    public MyFirebaseInstanceIDService() {
        super();
    }

    public  MyFirebaseInstanceIDService(Context context) {
        _context = context;
    }



    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG", "Refreshed token: " + refreshedToken);

        session = new SessionManager(_context);
        session.checkLogin();
        user = new HashMap<String, String>();
        user = session.getUserDetails();

//
//        // If you want to send messages to this application instance or
//        // manage this apps subscriptions on the server side, send the
//        // Instance ID token to your app server.
        if (refreshedToken != null) {
            sendRegistrationToServer(refreshedToken);
        }
    }

    private void sendRegistrationToServer(final String refreshedToken) {
        String url = AppController.get_base_url() + "dhadkan/api/device";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("DATA", response.toString());
                        try {
                            session.addFCM("" + response.getInt("pk"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error Message: " + error.getMessage());


            }
        }) {

            @Override
            public byte[] getBody() {
                JSONObject params = new JSONObject();
                try {
                    params.put("type", "" + session.getUserDetails().get("type"));
                    params.put("fcm", "" + refreshedToken);
                    params.put("id", "" + session.getUserDetails().get("id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return params.toString().getBytes();

            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

}



