package com.example.assignment_final.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import android.view.Window;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.assignment_final.Dialog.BottomSheet_Inf;
import com.example.assignment_final.R;


import com.example.assignment_final.fragment.Fragment_User_Course;
import com.example.assignment_final.fragment.Fragment_User_MyCourse;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class Activity_User_Course extends AppCompatActivity {
    private SpaceNavigationView spaceNavigationView;
    BottomSheet_Inf bottomSheet_inf1 = new BottomSheet_Inf();
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_user_course );
        spaceNavigationView = findViewById(R.id.space_navigation_course);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) spaceNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        spaceNavigationView.addSpaceItem(new SpaceItem("COURSE", R.drawable.item_course));
        spaceNavigationView.addSpaceItem(new SpaceItem("MY COURSE", R.drawable.my_course));
        spaceNavigationView.shouldShowFullBadgeText(true);

        if (savedInstanceState == null){
            loadFragment(new Fragment_User_Course());
        }
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                bottomSheet_inf1.show(((AppCompatActivity)Activity_User_Course.this).getSupportFragmentManager(),bottomSheet_inf1.getTag());
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment;
                if(itemName.equalsIgnoreCase( "COURSE" )){
                    fragment = new Fragment_User_Course();
                    loadFragment(fragment);
                }else if(itemName.equalsIgnoreCase( "MY COURSE" )){
                    fragment = new Fragment_User_MyCourse();
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
                        android.R.anim.slide_out_right);
        transaction.replace(R.id.fr_, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
