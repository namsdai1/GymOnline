package com.example.assignment_final.Activity;

import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Purchased;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Hoadon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_purchased extends AppCompatActivity {
RecyclerView rcv;
ArrayList<Hoadon> list;
ArrayList<Hoadon> list2;
Adapter_Purchased adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased);
        rcv=findViewById(R.id.rcv_purchased);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        list2=new ArrayList<>();
        list=new ArrayList<>();
        DatabaseReference mdate = FirebaseDatabase.getInstance().getReference().child("Hoadon");
        mdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    String id=datas.getKey();
                    String dataHD=datas.child("dateHD").getValue().toString();
                    String status=datas.child("status").getValue().toString();
                    String iduser=datas.child("user").getValue().toString();
                    if(iduser.equals(DbHelper.ID) && status.equals("Purchased")){
                        list2.add(new Hoadon(id,dataHD,status));
                    }
                }
                adapter=new Adapter_Purchased(Activity_purchased.this,list2);
                rcv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        update();

    }
    private void update(){
        DatabaseReference mdate = FirebaseDatabase.getInstance().getReference().child("Hoadon");
        mdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    String id=datas.getKey();
                    String dataHD=datas.child("dateHD").getValue().toString();
                    String status=datas.child("status").getValue().toString();
                    String iduser=datas.child("user").getValue().toString();
                    if(iduser.equals(DbHelper.ID) && status.equals("Status")){
                        list.add(new Hoadon(id,dataHD,status));
                    }
                }
                try{
                for(int i=0;i<list.size();i++){
                    String dateB=list.get(i).getDateHD();
                    Calendar c1=Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date dau = sdf.parse(dateB);
                    c1.setTime(dau);
                    c1.add(Calendar.DAY_OF_MONTH,2);
                    if(sdf.format(c1.getTime()).compareTo(sdf.format(Calendar.getInstance().getTime()))<=0){
                        if( DAO_HoaDon.updateStatic(Activity_purchased.this,list.get(i).getIDHD())){
                            Toast.makeText(Activity_purchased.this, "Thành công", Toast.LENGTH_SHORT).show();

                            adapter.notifyDataSetChanged();
                        }

                    }
                }
                }catch (Exception ex){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}