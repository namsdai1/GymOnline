package com.example.assignment_final.DAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_final.model.Discount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DAO_Discount {
    public static boolean insert_Discount_Firebase(Context context, Discount item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("Discount");

        myRef.push().setValue(
                new Discount( item.getNgaykt() , item.getGiamgia() , item.getNgaybt() , item.getTypeDiscount() ,item.getMaKhuyenmmai() ),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }
    public static boolean update_Discount_Firebase(Context context, final String ID, final Discount item){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "Discount" ).child( ID ).setValue(new Discount( item.getNgaykt() , item.getGiamgia() , item.getNgaybt() , item.getTypeDiscount() ,item.getMaKhuyenmmai()),
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean delete_Discount_Firebase( Context context,String ID){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.child("Discount").orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }

}
