package com.example.assignment_final.Activity;

import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.assignment_final.Adapter.Adapter_User_PT;

import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class Activity_User_PT extends AppCompatActivity {
    RecyclerView rcvPTUser;
    public Adapter_User_PT pt_adapter;
    public ArrayList<PT> list_PT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_user_pt );
        rcvPTUser= findViewById( R.id.rcvPTUser );

        rcvPTUser.setLayoutManager( new LinearLayoutManager( this ) );

        list_PT= new ArrayList<>(  );

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_PT.clear();
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotsIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotsIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = iterator.next();
                        PT pt = next.getValue( PT.class );
                        pt.setID2( next.getKey() );
                        list_PT.add( pt );

                        pt_adapter= new Adapter_User_PT( list_PT,Activity_User_PT.this );
                        rcvPTUser.setAdapter( pt_adapter );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}