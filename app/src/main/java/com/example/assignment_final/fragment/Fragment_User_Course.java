package com.example.assignment_final.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.Activity.Activity_Admin_Course;
import com.example.assignment_final.Activity.Activity_User_Course;
import com.example.assignment_final.Adapter.Adapter_Admin_Course;
import com.example.assignment_final.Adapter.Adapter_User_Course;
import com.example.assignment_final.DAO.DAO_Course;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.PT;
import com.example.assignment_final.model.Schedule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;


public class Fragment_User_Course extends Fragment {

    RecyclerView rcUserCourse;
    public Adapter_User_Course course_adapter;
    public ArrayList<Course> list_Course;

    public String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_user_course, container, false );
        rcUserCourse= view.findViewById( R.id.rcvUserCourse );

        rcUserCourse.setLayoutManager( new LinearLayoutManager( getContext() ) );
        DAO_Course courseDAO= new DAO_Course( getContext() );

        list_Course= new ArrayList<>(  );

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Course");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_Course.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String id = datas.getKey();
                    String date = datas.child("date").getValue().toString();
                    String name = datas.child("name").getValue().toString();
                    int money = Integer.parseInt(datas.child("money").getValue().toString());
                    int lesson = Integer.parseInt(datas.child("lesson").getValue().toString());
                    Course course = new Course(id,name,date,money,lesson);
                    list_Course.add( course );
                }

                course_adapter= new Adapter_User_Course( list_Course, getContext() );
                rcUserCourse.setAdapter( course_adapter );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });





        return view;
    }


}
