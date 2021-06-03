package com.example.assignment_final.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.PT;
import com.example.assignment_final.model.Schedule;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class DAO_Schedule {
    private SQLiteDatabase db;
    DbHelper dbHelper;

    public DAO_Schedule(Context context) {

    }



    public static boolean insert_Firebase( Schedule item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Schedule");

        myRef.push().setValue(
                new Schedule( item.getDateSchedule(), item.getCourseID(), item.getHourSchedule()),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }



}



