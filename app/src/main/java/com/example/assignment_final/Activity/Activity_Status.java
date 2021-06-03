package com.example.assignment_final.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Status;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Hoadon;
import com.example.assignment_final.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Status extends AppCompatActivity {
Adapter_Status adapter;
ArrayList<Hoadon> list;
RecyclerView recyclerView;
ArrayList<User> listuser;
TextView tvNotification;
Button btnStore;
String fullname,phone,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__status);
        recyclerView=findViewById(R.id.rcv_status);
        btnStore=findViewById(R.id.btnStore);
        tvNotification=findViewById(R.id.tvNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listuser=new ArrayList<>();
        listUser();
        list=new ArrayList<>();
        DatabaseReference mdate = FirebaseDatabase.getInstance().getReference().child("Hoadon");
        mdate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    String id=datas.getKey();
                    String dataHD=datas.child("dateHD").getValue().toString();
                    String status=datas.child("status").getValue().toString();
                    int tongtien=Integer.parseInt(datas.child("tongtien").getValue().toString());
                    String iduser=datas.child("user").getValue().toString();
                    if(iduser.equals(DbHelper.ID) && status.equals("Status")){
                        for(int i=0;i<listuser.size();i++){
                            if(iduser.equals(listuser.get(i).getIDUser())){
                                fullname=listuser.get(i).getFullName();
                                phone=listuser.get(i).getPhoneNumber();
                                address=listuser.get(i).getAddress();
                            }
                        }
                        list.add(new Hoadon(id,dataHD,tongtien,iduser,status,fullname,phone,address));
                        adapter=new Adapter_Status(list, Activity_Status.this);
                        recyclerView.setAdapter(adapter);
                    }
                }
                if(list.size()!=0){
                    btnStore.setVisibility( View.GONE );
                    tvNotification.setVisibility( View.GONE );

                }
                btnStore.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( Activity_Status.this,Activity_Add_Store.class );
                        startActivity( intent );
                        finish();
                    }
                } );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void listUser(){
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference().child("User");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                     String id=datas.getKey();
                     String fullname = datas.child("fullName").getValue().toString();
                     String address=datas.child("address").getValue().toString();
                     String phone=datas.child("phoneNumber").getValue().toString();
                    listuser.add(new User(id,fullname,address,phone));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}