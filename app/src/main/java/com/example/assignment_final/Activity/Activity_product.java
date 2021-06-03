package com.example.assignment_final.Activity;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_final.Dialog.BottomSheet_Inf;
import com.example.assignment_final.R;
import com.example.assignment_final.fragment.Fragment_Activity_Store;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class Activity_product extends AppCompatActivity {

    private SpaceNavigationView spaceNavigationView;
    BottomSheet_Inf bottomSheet_inf1 = new BottomSheet_Inf();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_product );
        spaceNavigationView = findViewById(R.id.spacenavigation_product);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) spaceNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);

        spaceNavigationView.addSpaceItem(new SpaceItem("STORE", R.drawable.home));
        spaceNavigationView.addSpaceItem(new SpaceItem("CART", R.drawable.shopping_cart));
        spaceNavigationView.shouldShowFullBadgeText(true);

        if (savedInstanceState == null){
            loadFragment(new Fragment_Activity_Store());
        }
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                bottomSheet_inf1.show(((AppCompatActivity)Activity_product.this).getSupportFragmentManager(),bottomSheet_inf1.getTag());
            }


            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment;
                if(itemName.equalsIgnoreCase( "STORE" )){
                    fragment = new Fragment_Activity_Store();
                    loadFragment(fragment);
                }else if(itemName.equalsIgnoreCase( "CART")){
                    fragment = new Activity_thanhtoan();
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
        transaction.replace(R.id.fr_product, fragment);
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
        finish();
    }
}
