package com.example.assignment_final.Activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.assignment_final.Adapter.Adapter_User;

import com.example.assignment_final.R;

import com.example.assignment_final.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_Admin_Users extends AppCompatActivity {
    RecyclerView rcvUserAdmin;
    public Adapter_User user_adapter;
    public ArrayList<User> list_User;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_users );
        rcvUserAdmin= findViewById( R.id.rcvUserAdmin );

        rcvUserAdmin.setLayoutManager( new LinearLayoutManager( this ) );
        list_User= new ArrayList<>(  );
        myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_User.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    User user = new User();
                    user=datas.getValue(User.class);
                    user.setIDUser( datas.getKey() );
                    list_User.add( user );

                }

                user_adapter= new Adapter_User( list_User,Activity_Admin_Users.this );
                rcvUserAdmin.setAdapter( user_adapter );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}
