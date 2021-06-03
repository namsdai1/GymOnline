package com.example.assignment_final.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.Adapter.Adapter_User_Course;
import com.example.assignment_final.Adapter.Adapter_User_My_Course;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Fragment_User_MyCourse extends Fragment {
    RecyclerView rcUserMyCourse;
    public Adapter_User_My_Course adapter_user_my_course;
    public ArrayList<HoaDonChiTiet> list_HoaDon;
    DbHelper dbHelper;
    Button btnCancel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_user_my_course, container, false );
        rcUserMyCourse= view.findViewById( R.id.rcvUserMyCourse );

        rcUserMyCourse.setLayoutManager( new LinearLayoutManager( getContext() ) );
        DAO_HoaDon dao_hoaDon= new DAO_HoaDon( getContext() );
        dbHelper= new DbHelper(  getContext() );

        list_HoaDon= new ArrayList<>(  );
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
        myRef.orderByChild( "nameUsser" ).equalTo(dbHelper.Username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_HoaDon.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    HoaDonChiTiet hoaDon= new HoaDonChiTiet(  );
                    hoaDon.setNameCourse(datas.child("nameCourse").getValue().toString()  );
                    hoaDon.setMaHD(  datas.getKey());
                    hoaDon.setTongTien( Integer.valueOf( datas.child("tongTien").getValue().toString() ) );
                    hoaDon.setNgayThanhToan( datas.child("ngayThanhToan").getValue().toString() );
                    hoaDon.setNamePT(datas.child("namePT").getValue().toString()  );
                    hoaDon.setNgayMoKhoa( datas.child("ngayMoKhoa").getValue().toString() );
                    hoaDon.setLesson( Integer.valueOf( datas.child("lesson").getValue().toString() ) );
                    hoaDon.setIDCourse(datas.child("idcourse").getValue().toString()  );
                    if(!datas.child( "idcourse" ).getValue().equals( "0" )){
                        list_HoaDon.add( hoaDon );
                    }
                }

                adapter_user_my_course= new Adapter_User_My_Course( getContext(),list_HoaDon);
                rcUserMyCourse.setAdapter( adapter_user_my_course );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        return view;
    }

}
