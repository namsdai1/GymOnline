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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DAO_Course {


    public DAO_Course(Context context) {

    }


    public static boolean insert_Firebase( Course item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Course");

        myRef.push().setValue(
                new Course( item.getName(), item.getDate(), item.getMoney(), item.getLesson()),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }
    public static boolean update_Firebase(final String ID, final Course item){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "Course" ).child( ID ).setValue(new Course( item.getName(), item.getDate(), item.getMoney(), item.getLesson()),
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean delete_Firebase(final Context context,String ID){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.child("Course").orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
}



