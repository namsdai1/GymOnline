package com.example.assignment_final.Activity;


import android.content.Intent;

import android.os.Bundle;

import android.view.Window;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.assignment_final.Dialog.BottomSheet_Inf;
import com.example.assignment_final.R;
import com.example.assignment_final.fragment.Fragment_Activity_Home_Admin;
import com.example.assignment_final.fragment.Fragment_Statistical;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;


public class Activity_Admin extends AppCompatActivity {
    private SpaceNavigationView spaceNavigationView;
    BottomSheet_Inf bottomSheet_inf = new BottomSheet_Inf();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_admin );

        spaceNavigationView = findViewById(R.id.space_navigation_admin);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) spaceNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.home));
        spaceNavigationView.addSpaceItem(new SpaceItem("STATISTICAL", R.drawable.statistical));
        spaceNavigationView.shouldShowFullBadgeText(true);


        spaceNavigationView.shouldShowFullBadgeText(true);

        if (savedInstanceState == null){
            loadFragment(new Fragment_Activity_Home_Admin());
        }
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                bottomSheet_inf.show(((AppCompatActivity) Activity_Admin.this).getSupportFragmentManager(),bottomSheet_inf.getTag());
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment;
                if(itemName.equalsIgnoreCase( "HOME" )){
                    fragment = new Fragment_Activity_Home_Admin();
                    loadFragment(fragment);
                }else if(itemName.equalsIgnoreCase( "STATISTICAL" )){
                    fragment = new Fragment_Statistical();
                    loadFragment(fragment);
                }

            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        android.R.anim.slide_in_left,  // enter
                        R.anim.fadeout,  // exit
                        R.anim.fadein,   // popEnter
                        android.R.anim.slide_out_right); // popExit
        transaction.replace(R.id.fr_admin, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }
    @Override
    public void onBackPressed() {
        Intent intentLogin = new Intent( this, Activity_Login.class);
        startActivity( intentLogin );

        finish();
    }
}
