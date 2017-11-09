package com.example.hulksmash.dhadkan.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hulksmash.dhadkan.R;
import com.example.hulksmash.dhadkan.doctorActivities.PatientDetailRow;
import com.example.hulksmash.dhadkan.doctorActivities.PatientRow;

import java.util.Collections;
import java.util.List;

/**
 * Created by hulksmash on 10/9/17.
 */

public class PatientDetailCustomAdapter extends RecyclerView.Adapter<PatientDetailCustomAdapter.PatientDetailViewHolder> {

    List<PatientDetailRow> data = Collections.emptyList();
    private LayoutInflater inflater;


    public PatientDetailCustomAdapter(Context context, List<PatientDetailRow> data) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PatientDetailCustomAdapter.PatientDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.patient_detail_row, parent, false);
        PatientDetailCustomAdapter.PatientDetailViewHolder viewHolder = new PatientDetailCustomAdapter.PatientDetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PatientDetailCustomAdapter.PatientDetailViewHolder holder, int position) {
        PatientDetailRow current = data.get(position);
        holder.date_date.setText(current.date_date + "th");
        holder.date_month_year.setText(current.date_month + " " + current.date_year);
        holder.time.setText("" + current.time_hour + ":" + current.time_min + " " + current.time_period);
        holder.weight.setText("" + current.weight + " kg");
        holder.heart_rate.setText("" + current.heart_rate + " bpm");
        holder.systolic.setText("" + current.systolic +" mmHg");
        holder.diastolic.setText("" + current.diastolic + " mmHg");

    }


    @Override
    public int getItemCount() {
        Log.d("TAG", ""+data.size());
        return data.size();
    }

    class PatientDetailViewHolder extends RecyclerView.ViewHolder{

        TextView date_date, date_month_year, time, weight, heart_rate, systolic, diastolic;
        public PatientDetailViewHolder(View itemView) {
            super(itemView);
            date_date = itemView.findViewById(R.id.textView);
            date_month_year = itemView.findViewById(R.id.textView2);
            time = itemView.findViewById(R.id.textView3);
            weight = itemView.findViewById(R.id.textView17);
            heart_rate = itemView.findViewById(R.id.textView18);
            systolic = itemView.findViewById(R.id.textView19);
            diastolic = itemView.findViewById(R.id.textView20);

        }
    }
}
