package com.example.assignment_final.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Product;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Product;
import com.example.assignment_final.model.Producttype;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_Activity_Store extends Fragment {

    Adapter_Product adapter;
    RecyclerView recyclerView;
    ArrayList<Product> list;
    LinearLayout ln1,ln2,ln3,ln4;
    ImageView all;
    TextView textView;
    DatabaseReference mdata;
    String idproducttype;
    String nameProductType;
    ArrayList<Producttype> listProducttype;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity__mua_hang, container, false);


        recyclerView=view.findViewById(R.id.rcv_product);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        list=new ArrayList<>();
        listProducttype=new ArrayList<>();
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    String ID=datas.getKey();
                    String idProducttype=datas.child("idProducttype").getValue().toString();
                    String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                    String name=datas.child("nameProduct").getValue().toString();
                    int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                    String motaProduct=datas.child("motaProduct").getValue().toString();
                    int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                    boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                    for(int i=0;i<listProducttype.size();i++){
                        if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                            nameProductType=listProducttype.get(i).getNameProducttype();
                        }
                    }
                    Product product=new Product(ID,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);

                    list.add(product);
                    adapter=new Adapter_Product(getContext(),list);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        all=view.findViewById(R.id.imgproduct);
        ln1=view.findViewById(R.id.ln1);
        ln2=view.findViewById(R.id.ln2);
        ln3=view.findViewById(R.id.ln3);
        ln4=view.findViewById(R.id.ln4);
        textView = view.findViewById(R.id.tvTieuDeSP);
        ln1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Protein");

            }
        });
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                listProducttype=new ArrayList<>();
                final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("PRODUCT");
                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String ID=datas.getKey();
                            String idProducttype=datas.child("idProducttype").getValue().toString();
                            String imagesproduct2=datas.child("imagesproduct2").getValue().toString();
                            String name=datas.child("nameProduct").getValue().toString();
                            int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                            String motaProduct=datas.child("motaProduct").getValue().toString();
                            int soluong=Integer.parseInt(datas.child("soluong").getValue().toString());
                            boolean rateProduct=Boolean.parseBoolean(datas.child("rateProduct").getValue().toString());
                            for(int i=0;i<listProducttype.size();i++){
                                if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                                    nameProductType=listProducttype.get(i).getNameProducttype();

                                }
                            }
                            Log.d("AAAA", "onDataChange: "+listProducttype.size());
                            Product product=new Product(ID,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);

                            list.add(product);
                            adapter=new Adapter_Product(getContext(),list);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        ln2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Bcaa");
            }
        });
        ln3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Accessories");
            }
        });
        ln4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                readAllType();
                readAllProduct("Milk");
            }
        });

        return view;
    }
    private void readAllProduct(final String ten){
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

                    for(int i=0;i<listProducttype.size();i++){
                        if(idProducttype.equals(listProducttype.get(i).getIdProducttype())){
                            nameProductType=listProducttype.get(i).getNameProducttype();
                        }
                    }
                    Product product=new Product(Id,name,motaProduct,money,soluong,rateProduct,idProducttype,imagesproduct2,nameProductType);
                    if(product.getNameProductType().equals(ten)){
                        list.add(product);
                    }

                    adapter=new Adapter_Product(getContext(),list);
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void readAllType(){
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Producttype");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    final String id = datas.getKey();
                    String nameProducttype=datas.child("nameProducttype").getValue().toString();
                    listProducttype.add(new Producttype(id,nameProducttype));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}