package com.example.assignment_final.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Activity.Activity_Admin_PT;
import com.example.assignment_final.Activity.Activity_User_Course;
import com.example.assignment_final.Activity.Activity_product;
import com.example.assignment_final.Adapter.Adapter_Admin_PT;
import com.example.assignment_final.Adapter.Adapter_TOP;
import com.example.assignment_final.DAO.DAO_PT;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class Fragment_Activity_Home_User  extends Fragment {
    private static final int REQUEST_CALL = 1;
    ImageView imgCourse,imgStore,imgPT;
    public static RecyclerView rcvTOPActivityUser;
    ViewFlipper vfUser;
    Adapter_TOP top_adapter;

    public ArrayList<PT> list_PT;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate( R.layout.fragment_user_home, container, false );
        imgCourse=view.findViewById( R.id.imgCourse );
        imgStore= view.findViewById( R.id.imgStore );
        imgPT= view.findViewById( R.id.imgTOP );
        rcvTOPActivityUser= view.findViewById( R.id.rcvTOPActivityUser );
        vfUser=view.findViewById( R.id.vfUser );
        vfUser.setFlipInterval( 3000 );
        vfUser.setAutoStart( true );


        imgCourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCourse = new Intent( getContext(), Activity_User_Course.class);
                startActivity( intentCourse );
            }
        } );
        imgStore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStore = new Intent( getContext(), Activity_product.class);
                startActivity( intentStore );
            }
        } );
        imgPT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPT = new Intent( getContext(), Activity_Admin_PT.class);
                startActivity( intentPT );
            }
        } );


        rcvTOPActivityUser.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        list_PT= new ArrayList<>(  );
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_PT.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()) {
                    PT pt = datas.getValue( PT.class );
                    pt.setID2( datas.getKey() );
                    if(pt.getRate()!=0){
                        list_PT.add( pt );
                    }
                }
                Collections.sort( list_PT, new Comparator<PT>() {
                    @Override
                    public int compare(PT pt1, PT pt2) {
                        return pt1.getName().compareTo( pt2.getName() );
                    }
                } );
                top_adapter = new Adapter_TOP( list_PT, getContext() );
                rcvTOPActivityUser.setAdapter( top_adapter );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        return view;
    }

}
