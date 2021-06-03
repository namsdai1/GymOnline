package com.example.assignment_final.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_final.Activity.Activity_Login;
import com.example.assignment_final.Activity.Activity_Status;
import com.example.assignment_final.Activity.Activity_User_Info;
import com.example.assignment_final.Activity.Activity_purchased;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;


public class BottomSheet_Inf extends BottomSheetDialogFragment {
    private static final int GALLER_ACTION_PICK_CODE = 100;
    TextView tvTaiKhoan,tvNameAD;
    DAO_HoaDon dao_hoaDon;
    DAO_User dao_user;
    DbHelper dbHelper;
    ImageView imgView;
    DatabaseReference myRef;
    LinearLayout layoutPurchased,layoutStatus,layoutCart,layoutShare,layoutLogOut;
    public static CircleImageView imgUser;

    public BottomSheet_Inf(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate( R.layout.bottom_sheet_inf,container,false );
        tvNameAD= view.findViewById( R.id.tvNameAD );
        tvTaiKhoan= view.findViewById( R.id.tvTaiKhoan );
        imgUser= view.findViewById( R.id.imgUser );
        imgView= view.findViewById( R.id.imgView );
        layoutPurchased=view.findViewById( R.id.layoutPurchased );
        layoutStatus=view.findViewById( R.id.layoutStatus );
        layoutLogOut=view.findViewById( R.id.layoutLogOut );



        dao_hoaDon = new DAO_HoaDon( getContext() );
        dao_user = new DAO_User( getContext() );
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "User" ).orderByKey().equalTo( dbHelper.ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotsIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotsIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = iterator.next();
                        User user = next.getValue( User.class );
                        if(user.getImages()!=null) {
                            String imgView = user.getImages();
                            Picasso.get().load( imgView ).into( new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    imgUser.setImageBitmap( bitmap );
                                }

                                @Override
                                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }
                            } );
                        }
                        tvNameAD.setText(user.getFullName() );
                        if(dbHelper.Username.equalsIgnoreCase( "Admin" )){
                            layoutPurchased.setVisibility( View.GONE );
                            layoutStatus.setVisibility( View.GONE );
                            layoutCart.setVisibility( View.GONE );
                            myRef = FirebaseDatabase.getInstance().getReference();
                            myRef.child( "HoaDonChiTiet" ).addValueEventListener(new ValueEventListener() {
                                int total=0;
                                DecimalFormat formatter = new DecimalFormat( "#,###,###" );
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                                        total+=Integer.valueOf( datas.child( "tongTien" ).getValue().toString() );
                                    }
                                    tvTaiKhoan.setText(formatter.format( total)+" USD");
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });


                        }else{
                            tvTaiKhoan.setVisibility( View.GONE );
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(  getContext(), Activity_User_Info.class);
                startActivity( intent );
            }
        } );
        layoutPurchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), Activity_purchased.class);
                startActivity(i);
            }
        });
        layoutStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Activity_Status.class);
                startActivity(intent);
            }
        });
        layoutLogOut.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent( getContext(), Activity_Login.class );
                startActivity( i );
            }
        } );

        return view;
    }


}
