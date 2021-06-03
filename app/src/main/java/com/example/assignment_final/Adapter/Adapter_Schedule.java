package com.example.assignment_final.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.R;
import com.example.assignment_final.model.Schedule;

import java.util.ArrayList;

public class Adapter_Schedule extends RecyclerView.Adapter<Adapter_Schedule.ScheduleHolder> {
    ArrayList<Schedule> list_Schedule;
    public Context context;

    public Adapter_Schedule(ArrayList<Schedule> list_Schedule, Context context) {
        this.list_Schedule = list_Schedule;
        this.context = context;
    }
    public static class ScheduleHolder extends RecyclerView.ViewHolder{
        public TextView tvDateCourse,tvHour;
        public ScheduleHolder(View view){
            super(view);
            tvDateCourse= view.findViewById( R.id.tvDateCourse );
            tvHour= view.findViewById( R.id.tvHour );

        }
    }
    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_schedule,parent,false );
        ScheduleHolder scheduleHolder= new ScheduleHolder( view );
        return scheduleHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.tvDateCourse.setText( list_Schedule.get( position ).getDateSchedule() );
        holder.tvHour.setText( list_Schedule.get( position ).getHourSchedule() );
    }

    @Override
    public int getItemCount() {
        return list_Schedule.size();
    }
}
