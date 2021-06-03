package com.example.assignment_final.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment_final.R;


public class Activity_Hello extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity__hello );

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent( Activity_Hello.this, Activity_Main.class));
                finish();
            }
        }, 1500);
    }
}
