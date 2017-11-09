package com.example.hulksmash.dhadkan.controller;

/**
 * Created by hulksmash on 5/9/17.
 */
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.hulksmash.dhadkan.ChooseActivity;

public class SessionManager {
    SharedPreferences pref;

    Editor editor;

    Context _context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "DhadkanPref";

    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_TOKEN = "Token";

    public static final String KEY_USER = "u_id";

    public static final String KEY_TYPE = "type";

    public static final String KEY_ID = "id";

    public static final String KEY_FCM = "fcm";

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String token, int u_id, String type, int id){
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_TOKEN, token);

        editor.putInt(KEY_USER, u_id);

        editor.putString(KEY_TYPE, type);

        editor.putInt(KEY_ID, id);

        editor.commit();
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, ChooseActivity.class);
            _context.startActivity(i);
        }

    }




    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        user.put(KEY_USER, Integer.toString(pref.getInt(KEY_USER, 0)));

        user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));

        user.put(KEY_ID, Integer.toString(pref.getInt(KEY_ID, 0)));

        Log.d("DATA", user.toString());


        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, ChooseActivity.class);

        _context.startActivity(i);
    }

    public void addFCM(String fcm) {
        editor.putString(KEY_FCM, fcm);
        editor.commit();
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}