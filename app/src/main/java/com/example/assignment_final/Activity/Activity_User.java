package com.example.assignment_final.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_final.DAO.DAO_Discount;
import com.example.assignment_final.Dialog.BottomSheet_Inf;
import com.example.assignment_final.R;
import com.example.assignment_final.fragment.Fragment_Activity_Home_User;
import com.example.assignment_final.fragment.Fragment_Notification;
import com.example.assignment_final.model.Discount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Activity_User extends AppCompatActivity {
    private SpaceNavigationView spaceNavigationView;
    BottomSheet_Inf bottomSheet_inf1 = new BottomSheet_Inf();
    Button btnExercise;
    DAO_Discount dao_discount;
    ArrayList<Discount> list;
    public SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );
    public String date = df.format( java.util.Calendar.getInstance().getTime() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_user );
        spaceNavigationView = findViewById(R.id.spacenavigation_user);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) spaceNavigationView.getLayoutParams();
        layoutParams.setBehavior(new ScrollHandler());

        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
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
                        dao_discount.delete_Discount_Firebase(Activity_User.this,datas.getKey());
                    }else {
                        list.add(discount);
                    }
                }
                final Dialog dialog = new Dialog( Activity_User.this );
                dialog.setContentView( R.layout.customdialog );
                btnExercise=dialog.findViewById( R.id.btnExercise );
                btnExercise.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    spaceNavigationView.showBadgeAtIndex(1, list.size(), Color.DKGRAY);
                        dialog.cancel();
                    }
                } );
                dialog.show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        spaceNavigationView.addSpaceItem(new SpaceItem("HOME", R.drawable.home));
        spaceNavigationView.addSpaceItem(new SpaceItem("NOTIFICATION", R.drawable.notification));
        spaceNavigationView.shouldShowFullBadgeText(true);

        if (savedInstanceState == null){
            loadFragment(new Fragment_Activity_Home_User());

        }
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                bottomSheet_inf1.show(((AppCompatActivity)Activity_User.this).getSupportFragmentManager(),bottomSheet_inf1.getTag());
            }


            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Fragment fragment;
                if(itemName.equalsIgnoreCase( "HOME" )){
                    fragment = new Fragment_Activity_Home_User();
                    loadFragment(fragment);
                }else if(itemName.equalsIgnoreCase( "NOTIFICATION")){
                    fragment = new Fragment_Notification();
                    loadFragment(fragment);
                    spaceNavigationView.hideBadgeAtIndex( 1 );
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
        transaction.replace(R.id.fr_, fragment);
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

