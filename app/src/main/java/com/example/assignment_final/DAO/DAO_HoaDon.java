package com.example.assignment_final.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Hoadon;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class DAO_HoaDon {

    public DAO_HoaDon(Context context) {

    }


    public static boolean insert_Course_Firebase( HoaDonChiTiet item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        myRef.child("HoaDonChiTiet").push().setValue(
                new HoaDonChiTiet( item.getIDProduct(),item.getLesson(  ),item.getIDPT(),item.getIDCourse(),item.getAccountID(),item.getTongTien(),item.getNameUsser(),item.getNamePT(),item.getNameCourse(),item.getNgayThanhToan(),item.getTrangThai(),item.getNgayMoKhoa()),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }
    public static boolean insert_Course_Firebase2( HoaDonChiTiet item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        myRef.child("HoaDonChiTiet").push().setValue(
                new HoaDonChiTiet( item.getIDHoaDon(),item.getIDProduct(),item.getLesson(  ),item.getIDPT(),item.getIDCourse(),item.getAccountID(),item.getTongTien(),item.getNameUsser(),item.getNamePT(),item.getNameCourse(),item.getNgayThanhToan(),item.getTrangThai(),item.getNgayMoKhoa(),1),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }
    public static boolean insert_Hoadon_Firebase(Context context, Hoadon item){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();

        myRef.child("Hoadon").push().setValue(
                new Hoadon(item.getDateHD(), item.getTongtien() , item.getUser() , item.getStatus()),
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

                    }
                });
        return true;
    }

    public static boolean updateStatic(Context context,final String ID){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "Hoadon" ).child( ID ).child("status").setValue("Purchased");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean updateTonggiamgia(Context context, final String ID, final float tong){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "Hoadon" ).child( ID ).child("tongtien").setValue(tong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean updatettSoluong(Context context,final String ID, final int tt){
        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("tongTien").setValue(tt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean updateSoluong(Context context,final String ID, final int soluong){

        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("soluongproduct").setValue(soluong);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean updatettTrangthaidathanhtoan(Context context, final String ID , final String date, final String idhd ){
        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("trangThai").setValue("Đã thanh toán");
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("ngayThanhToan").setValue(date);
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("idhoaDon").setValue(idhd);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }
    public static boolean updatettTrangthaidathanhtoancogiamgia(Context context, final String ID , final String date, final String idhd, final float  tongtien ){
        final DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("trangThai").setValue("Đã thanh toán");
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("ngayThanhToan").setValue(date);
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("idhoaDon").setValue(idhd);
                myRef.child( "HoaDonChiTiet" ).child( ID ).child("tongTien").setValue(tongtien);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
        return true;
    }


    public static boolean delete_Firebase(final Context context,String ID){

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
        myRef.child("HoaDonChiTiet").orderByKey().equalTo( ID ).addListenerForSingleValueEvent( new ValueEventListener() {
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



