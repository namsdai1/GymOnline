package com.example.assignment_final.Activity;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Card;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Discount;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Hoadon;
import com.example.assignment_final.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Activity_thanhtoan extends Fragment {
ArrayList<HoaDonChiTiet> list;
Adapter_Card adapter;
RecyclerView rcv;
Button thanhtoan;
TextView tong;
EditText etgiamgia;
    ArrayList<Integer> listtong;
    float tonghet=0;
ArrayList<Discount> listgiam=new ArrayList<>();
ArrayList<Product> listProduct;

    String nameProduct ,idProductType  ,imgProduct ;
    int moneyProduct ,soluong;
    ArrayList<String> listIDHD=new ArrayList<>();
int positon;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_thanhtoan, container, false);
        rcv=view.findViewById(R.id.rcvcard);
        tong=view.findViewById(R.id.tongcard);
        thanhtoan=view.findViewById(R.id.cartProduct);
        etgiamgia=view.findViewById(R.id.etgiamgia);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        rcv.setHasFixedSize(true);
        list=new ArrayList<>();
        listProduct=new ArrayList<>();
        listtong=new ArrayList<>();
        readAllProduct();
        readAllDiscount();


        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final String id = datas.getKey();
                    String ngayThanhToan=datas.child("ngayThanhToan").getValue().toString();
                    String IDProduct=datas.child("idproduct").getValue().toString();
                    String nameUsser=datas.child("nameUsser").getValue().toString();
                    String trangThai=datas.child("trangThai").getValue().toString();
                    int soluongproduct=Integer.parseInt(datas.child("soluongproduct").getValue().toString());
                    for(int i=0;i<listProduct.size();i++){
                        if(IDProduct.equals(listProduct.get(i).getIdProduct())){
                            nameProduct=listProduct.get(i).getNameProduct();
                            idProductType=listProduct.get(i).getIdProducttype();
                            imgProduct=listProduct.get(i).getImagesproduct2();
                            moneyProduct=listProduct.get(i).getMoneyProduct();
                            soluong=listProduct.get(i).getSoluong();
                        }
                    }
                    if(trangThai.equals("Gio hang") && nameUsser.equals(DbHelper.Username) ){
                        HoaDonChiTiet hd=new HoaDonChiTiet(id,ngayThanhToan,IDProduct,soluongproduct,nameUsser,trangThai,nameProduct,
                                moneyProduct,soluong,idProductType,imgProduct);
                        list.add(hd);

                    }
                    adapter=new Adapter_Card(getContext(),list,tong);
                    rcv.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        thanhtoan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listtong=new ArrayList<>();
                listhoadon();
                final Intent intent=new Intent(getContext(), Activity_Odersuccess.class);
                final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd");
                try {
                    DAO_HoaDon.insert_Hoadon_Firebase(getContext(),new Hoadon(simpleDateFormat.format(Calendar.getInstance().getTime()),0, DbHelper.ID,"Status"));
                }catch (Exception ex){
                    Toast.makeText(getContext(), "Thất bại", Toast.LENGTH_SHORT).show();
                }
                final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Hoadon");
                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String Id=datas.getKey();
                            listIDHD.add(Id);
                        }
                        for(int i=0;i<listgiam.size();i++){
                            if(listgiam.get(i).getMaKhuyenmmai().equals(etgiamgia.getText().toString())){
                                positon=i;
                                break;
                            }
                        }
                        if(etgiamgia.getText().toString().equals("")){
                            for(int j=0;j<list.size();j++){
                                if ( DAO_HoaDon.updatettTrangthaidathanhtoan(getContext(),list.get(j).getMaHD(), simpleDateFormat.format(Calendar.getInstance().getTime()),listIDHD.get(listIDHD.size()-1))){
                                    DAO_HoaDon.updateTonggiamgia(getContext(),listIDHD.get(listIDHD.size()-1),tonghet);
                                    startActivity(intent);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }else {
                            if(listgiam.get(positon).getMaKhuyenmmai().equals(etgiamgia.getText().toString())) {
                                if ((listgiam.get(positon).getNgaykt()).compareTo(simpleDateFormat.format(Calendar.getInstance().getTime())) > 0 && (listgiam.get(positon).getNgaybt()).compareTo(simpleDateFormat.format(Calendar.getInstance().getTime())) < 0) {
                                    tong.setText("thua");
                                    for(int j=0;j<list.size();j++) {
                                        if (DAO_HoaDon.updatettTrangthaidathanhtoancogiamgia(getContext(), list.get(j).getMaHD(), simpleDateFormat.format(Calendar.getInstance().getTime()),listIDHD.get(listIDHD.size()-1),list.get(j).getMoneyProduct()*list.get(j).getSoluongproduct()*(1-listgiam.get(positon).getGiamgia()/100))) {
                                            DAO_HoaDon.updateTonggiamgia(getContext(),listIDHD.get(listIDHD.size()-1),tonghet*(1-listgiam.get(positon).getGiamgia()/100));
                                            startActivity(intent);
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "Code đã hết hạn", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getContext(), "Không tìm thấy code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                adapter.notifyDataSetChanged();


            }
        });
        return view;
    }


    private void readAllProduct(){
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String Id=datas.getKey();
                    String idProducttype=datas.child("idProducttype").getValue().toString();
                    String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                    String name=datas.child("nameProduct").getValue().toString();
                    int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                    String motaProduct=datas.child("motaProduct").getValue().toString();
                    int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                    boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                    Product product=new Product(Id,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2);
                    listProduct.add(product);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void readAllDiscount(){
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Discount");
        mdata.orderByChild("typeDiscount").equalTo("PRODUCT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String Id=datas.getKey();
                    Float giamgia = Float.parseFloat(datas.child("giamgia").getValue().toString());
                    String makhuyenmai=datas.child("maKhuyenmmai").getValue().toString();
                    String ngaybt=datas.child("ngaybt").getValue().toString();
                    String ngaykt=datas.child("ngaykt").getValue().toString();
                    Discount discount=new Discount(ngaykt,giamgia,ngaybt,makhuyenmai);
                    listgiam.add(discount);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void listhoadon(){
        tonghet=0;
        listtong.clear();
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
        mdata.orderByChild("nameUsser").equalTo(DbHelper.Username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    int tong=Integer.parseInt(datas.child("tongTien").getValue().toString());
                    String trangThai=datas.child("trangThai").getValue().toString();
                    if(trangThai.equals("Gio hang") ){
                        listtong.add(tong);
                    }

                }
                for(int i=0;i<listtong.size();i++){
                    tonghet+=listtong.get(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    }
