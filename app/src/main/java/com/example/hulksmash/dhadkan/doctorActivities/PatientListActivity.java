package com.example.hulksmash.dhadkan.doctorActivities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hulksmash.dhadkan.ControllerActivity;
import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.controller.AppController;
import com.example.hulksmash.dhadkan.controller.CustomAdapter;
import com.example.hulksmash.dhadkan.controller.RecyclerItemClickListener;
import com.example.hulksmash.dhadkan.controller.SessionManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PatientListActivity extends AppCompatActivity {
    RecyclerView patient_list_view;
    CustomAdapter adapter;
    static SessionManager session;
    final List<PatientRow> data = new ArrayList<PatientRow>();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.doc_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout(this);
                return true;
            case R.id.action_refresh:
                Intent i = new Intent(this, PatientListActivity.class);
                startActivity(i);
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(Context _c) {
        session = new SessionManager(_c);
        session.logoutUser();
        Intent i = new Intent(PatientListActivity.this, ControllerActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        getSupportActionBar().setTitle("Patient List");


        session = new SessionManager(this);

        patient_list_view = (RecyclerView) findViewById(R.id.patient_list);
        getData(new CallBack() {
            @Override
            public void onSuccess(List<PatientRow> patient_list) {
                adapter = new CustomAdapter(PatientListActivity.this, patient_list);
                Log.d("TAG", patient_list.toString());
                patient_list_view.setAdapter(adapter);
                patient_list_view.setLayoutManager(new LinearLayoutManager(PatientListActivity.this, LinearLayoutManager.VERTICAL, false));
                patient_list_view.setItemAnimator(new DefaultItemAnimator());
                patient_list_view.addOnItemTouchListener(
                        new RecyclerItemClickListener(PatientListActivity.this, patient_list_view, new RecyclerItemClickListener.OnItemClickListener() {

                            @Override
                            public void onItemClick(View view, int position) {
                                Intent i = new Intent(PatientListActivity.this, PatientDetailListActivity.class);
                                TextView ID_text = view.findViewById(R.id.TextView20);
                                i.putExtra("P_ID", ID_text.getText());
                                startActivity(i);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                // do whatever
                            }
                        }));
            }

            @Override
            public void onFail(String msg) {

            }
        });


    }

    public void getData(final CallBack onCallback) {


        String url = AppController.get_base_url() + "dhadkan/api/doctor/" + session.getUserDetails().get("id");
//        String url  = AppController.get_base_url() + "dhadkan/api/doctor/11";
//        Log.d(TAG, url);
//        Toast.makeText(PatientListActivity.this, url, Toast.LENGTH_LONG).show();
        final List<PatientRow> data = new ArrayList<PatientRow>();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            List<PatientRow> data = new ArrayList<PatientRow>();
                            JSONArray patients = response.getJSONArray("patients");
                            for (int i = 0; i < patients.length(); i++) {
                                JSONObject po = (JSONObject) patients.get(i);
                                PatientRow pr = new PatientRow(po.getString("name"), po.getString("date_of_birth"), po.getString("gender"), "" + po.getInt("pk"));
                                data.add(pr);
                            }
                            onCallback.onSuccess(data);

//                          edit.putInt("U_ID", (Integer) response.get("id"));
                            Log.d("TAG", "" + response.get("pk"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//                            edit.commit();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error Message: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + session.getUserDetails().get("Token"));
                Log.d("TAG", "Token " + session.getUserDetails().get("Token"));
//                params.put("Authorization", "Token f00a64734d608991730ccba944776c316c38c544");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        Log.d("TAG", "fuck");

    }

    public interface CallBack {
        void onSuccess(List<PatientRow> patient_list);

        void onFail(String msg);
    }

}
