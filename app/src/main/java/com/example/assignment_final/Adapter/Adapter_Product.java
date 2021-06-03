package com.example.assignment_final.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Hoadon;
import com.example.assignment_final.model.Product;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Adapter_Product extends RecyclerView.Adapter<Adapter_Product.Product_Holder> {
    public ArrayList<Product> list;
    public Context context;
    ArrayList<HoaDonChiTiet> listHD;
    ArrayList<String> listIDHD=new ArrayList<>();
    int a;
    public Adapter_Product(Context context, ArrayList<Product> list) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public Product_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_muahang, parent, false);
        return (new Adapter_Product.Product_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Product_Holder holder, final int position) {
        listHD=new ArrayList<>();
        holder.tvnameproduct.setText(list.get(position).getNameProduct());
        Picasso.get().load(list.get(position).getImagesproduct2()).into(holder.imgproduct);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogProduct(context,list.get(position).getMoneyProduct(),list.get(position).getNameProduct(),list.get(position).getMotaProduct(),list.get(position).getIdProduct(),list.get(position).getImagesproduct2(),list.get(position).getSoluong(),position);
            }
        });
    }
    protected void openDialogProduct(final Context context, final int moneyProduct, final String tenProduct, String mota1, final String Idproduct, String hinhanh, final int soluong, final int position){
        Button btbuy,btadd;
        TextView ten,money,mota;
        ImageView img;
        //custom dialog
        final BottomSheetDialog dialog = new BottomSheetDialog( context );
        dialog.setContentView(R.layout.bottom_sheet_see_details );
        listHD=new ArrayList<>();
        img=dialog.findViewById(R.id.imageproduct_bot);
        btbuy = dialog.findViewById( R.id.buyproduct );
        btadd= dialog.findViewById( R.id.addtocart );
        ten=dialog.findViewById( R.id.titleproduct_bot );
        money=dialog.findViewById( R.id.moneyproduct_bot );
        mota=dialog.findViewById(R.id.motaProduct);
        Picasso.get().load(hinhanh).into(img);
        ten.setText(tenProduct);
        money.setText(moneyProduct+"đ");
        mota.setText(mota1);

        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleDateFormat sdf=new SimpleDateFormat("YYYY/MM/dd");
                final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            final String id = datas.getKey();
                            String nameusser=datas.child("nameUsser").getValue().toString();
                            String IDProduct=datas.child("idproduct").getValue().toString();
                            String trangThai=datas.child("trangThai").getValue().toString();
                            if(trangThai.equals("Gio hang") && nameusser.equals(DbHelper.Username)){
                                listHD.add(new HoaDonChiTiet("0",IDProduct,"0", DbHelper.IDuser,trangThai));
                            }
                        }

                        if(listHD.size()==0) {
                            if (DAO_HoaDon.insert_Course_Firebase2(new HoaDonChiTiet("0",Idproduct, 0, "0"
                                    , "0", DbHelper.IDuser, moneyProduct, DbHelper.Username, "0", "0",
                                    sdf.format(Calendar.getInstance().getTime()), "Gio hang", "0",1))) {

                                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            for (int i = 0; i < listHD.size(); i++) {
                                if (Idproduct.equals(listHD.get(i).getIDProduct())) {
                                    a = i;
                                }
                            }

                            if(!Idproduct.equals(listHD.get(a).getIDProduct())){
                                if( DAO_HoaDon.insert_Course_Firebase2(new HoaDonChiTiet( "0",Idproduct ,0, "0" , "0" , DbHelper.IDuser ,moneyProduct, DbHelper.Username,"0","0",sdf.format(Calendar.getInstance().getTime()),"Gio hang","0" ,1   ))){
                                    Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();

                                }
                            }else {
                                Toast.makeText(context, "Đã có", Toast.LENGTH_SHORT).show();
                            }

                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        btbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("YYYY/MM/dd");
                try {
                    DAO_HoaDon.insert_Hoadon_Firebase(context,new Hoadon(simpleDateFormat.format(Calendar.getInstance().getTime()),list.get(position).getMoneyProduct(), DbHelper.ID,"Status"));
                    Toast.makeText(context, "Thanh congHD", Toast.LENGTH_SHORT).show();
                }catch (Exception ex){
                    Toast.makeText(context, "Thatbai", Toast.LENGTH_SHORT).show();
                }
                final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Hoadon");
                mdata.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String Id=datas.getKey();
                            listIDHD.add(Id);
                        }
                        if(DAO_HoaDon.insert_Course_Firebase2(new HoaDonChiTiet(listIDHD.get(listIDHD.size()-1),Idproduct, 0, "0"
                                , "0", DbHelper.IDuser, moneyProduct, DbHelper.Username, "0", "0",
                                simpleDateFormat.format(Calendar.getInstance().getTime()), "Đã thanh toán", "0",1))){
                            Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    dialog.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        dialog.show();

    }

    private void cardchuathanhtoan(){
//


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Product_Holder extends RecyclerView.ViewHolder{
        TextView tvnameproduct;
        RelativeLayout relativeLayout;
        ImageView imgproduct;
        public Product_Holder(@NonNull View itemView) {
            super(itemView);
            tvnameproduct=itemView.findViewById(R.id.txt_itemproduct);
            relativeLayout=itemView.findViewById(R.id.rlimage);
            imgproduct=itemView.findViewById(R.id.imgproduct);
        }
    }
}
