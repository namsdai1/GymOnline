package com.example.assignment_final.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment_final.R;

public class Activity_Odersuccess extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ordersuccess);
        Button btHDct=findViewById(R.id.btHDct);
        Button btnhome=findViewById(R.id.btnhome);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent( Activity_Odersuccess.this,Activity_User.class );
                startActivity( intent );
                finish();
            }
        });
        btHDct.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent( Activity_Odersuccess.this,Activity_Status.class );
                startActivity( intent );
                finish();
            }
        } );
    }
}
