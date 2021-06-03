package com.example.assignment_final.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Activity.Activity_admin_sale;
import com.example.assignment_final.Adapter.Adapter_Admin_sale;
import com.example.assignment_final.Adapter.Adapter_Notification;
import com.example.assignment_final.DAO.DAO_Discount;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.Discount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Fragment_Notification extends Fragment {
    RecyclerView rcv;
    Adapter_Notification adapter;
    DAO_Discount dao_discount;
    ArrayList<Discount> list;
    public SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );
    public String date = df.format( java.util.Calendar.getInstance().getTime() );
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_notification, container, false );
        rcv=view.findViewById(R.id.rcvSale);
        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        list=new ArrayList<>();
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Discount");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dao_discount= new DAO_Discount();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Discount discount= new Discount(  );
                    discount.setIDdiscout(  datas.getKey());
                    discount.setNgaybt( datas.child("ngaybt").getValue().toString() );
                    discount.setNgaykt( datas.child("ngaykt").getValue().toString() );
                    discount.setMaKhuyenmmai( datas.child("maKhuyenmmai").getValue().toString() );
                    discount.setGiamgia( Float.parseFloat(datas.child("giamgia").getValue().toString()) );;
                    discount.setTypeDiscount( datas.child("typeDiscount").getValue().toString() );
                    if(datas.child( "ngaykt" ).getValue().toString().compareTo( date )<0){
                        dao_discount.delete_Discount_Firebase(getContext(),datas.getKey());
                    }else {
                        list.add(discount);
                    }
                }
                adapter=new Adapter_Notification(list,getContext());
                rcv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


       return view;

    }
}
