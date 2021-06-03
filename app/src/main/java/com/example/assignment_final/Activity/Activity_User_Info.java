package com.example.assignment_final.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.assignment_final.R;
import com.example.assignment_final.fragment.Fragment_Change_Pass;
import com.example.assignment_final.fragment.Fragment_Info;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Activity_User_Info extends AppCompatActivity {

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_user_info );
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_navigation_inf );
        if (savedInstanceState == null) {
            loadFragment( new Fragment_Info() );
        }
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.botInfo:
                        fragment = new Fragment_Info();
                        loadFragment( fragment );
                        break;
                    case R.id.botChangeInfo:
                        fragment = new Fragment_Change_Pass();
                        loadFragment( fragment );
                        break;
                }
                return false;
            }
        } );
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
        transaction.replace( R.id.fr_, fragment )
                .setCustomAnimations(
                        android.R.anim.slide_in_left,  // enter
                        R.anim.fadeout,  // exit
                        R.anim.fadein,   // popEnter
                        android.R.anim.slide_out_right);
        transaction.addToBackStack( null );
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}

