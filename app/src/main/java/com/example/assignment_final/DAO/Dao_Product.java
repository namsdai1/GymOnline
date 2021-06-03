package com.example.assignment_final.DAO;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_final.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dao_Product {

    public static boolean insert_Product_Firebase(Context context, Product item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference("PRODUCT");

        myRef.push().setValue(
                new Product(  item.getNameProduct() , item.getMotaProduct() , item.getMoneyProduct() , item.getSoluong() , item.isRateProduct() ,item.getIdProducttype() , item.getImagesproduct2()),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }
    public static boolean update_PRODUCT_Firebase(Context context,final String ID, final Product item){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "PRODUCT" ).child( ID ).setValue(new Product( item.getNameProduct() , item.getMotaProduct() , item.getMoneyProduct() , item.getSoluong() , item.isRateProduct() ,item.getIdProducttype() , item.getImagesproduct2() ),
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

}
