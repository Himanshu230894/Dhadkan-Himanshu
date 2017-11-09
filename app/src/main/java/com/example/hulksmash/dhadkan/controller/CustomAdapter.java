package com.example.hulksmash.dhadkan.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hulksmash.dhadkan.doctorActivities.PatientRow;
import com.example.hulksmash.dhadkan.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hulksmash on 6/9/17.
 */

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder>{

    List<PatientRow> data = Collections.emptyList();
    private LayoutInflater inflater;


    public CustomAdapter(Context context, List<PatientRow> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.patient_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String[] gender_map= {"Female" , "Male"};
        String[] months = {"" ,"Jan" , "Feb" , "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        PatientRow current = data.get(position);
        String[] _age = current.age.split("-");
        holder.name.setText(current.name);
        Log.d("TAG", _age.toString());
        holder.gender.setText("GENDER " + ": " + gender_map[Integer.parseInt(current.gender)]);
        holder.age.setText("DOB " + ": " + _age[2] + " " + months[Integer.parseInt(_age[1])] + " " + _age[0]);
        holder.id.setText(current.id);
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", data.size()+"");
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, age, gender, id;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.TextView17);
            gender = itemView.findViewById(R.id.TextView19);
            age = itemView.findViewById(R.id.TextView18);
            id = itemView.findViewById(R.id.TextView20);

        }
    }
}
