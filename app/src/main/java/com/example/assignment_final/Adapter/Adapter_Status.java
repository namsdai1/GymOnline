package com.example.assignment_final.Adapter;

import android.content.Context;
import android.icu.util.Calendar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Adapter_Status extends RecyclerView.Adapter<Adapter_Status.Status_Holder> {
    ArrayList<Hoadon> list;
    Context context;
    ArrayList<HoaDonChiTiet> listBill;
    ArrayList<Product> listProduct;
    Adapter_statuschild adapter_statuschild;
     String nameProduct,  imgProduct;
     int moneyProduct1;
    String IDHoaDon;
    RecyclerView.RecycledViewPool viewPool =new RecyclerView.RecycledViewPool();
    public Adapter_Status(ArrayList<Hoadon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Status_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_status, parent, false);
        return (new Adapter_Status.Status_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Status_Holder holder, final int position) {
         holder.tvmoney.setText(String.valueOf(list.get(position).getTongtien()));
         holder.tvngay.setText(list.get(position).getDateHD());
         holder.tvma.setText(String.valueOf(list.get(position).getIDHD()));

         holder.ll.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 openDialogProduct(context,list.get(position).getDateHD(),list.get(position).getIDHD(),list.get(position).getFullname(),list.get(position).getPhone(),list.get(position).getAddress());
             }
         });
    }

    protected void openDialogProduct(final Context context1, final String dateB, final String maHD, String fullname, String phone, String address){
        final TextView tenUser,phoneUser,addressUser,dateBil,datestatic,txt_maid;



        final RecyclerView rcv;
        //custom dialog
        final BottomSheetDialog dialog = new BottomSheetDialog( context1 );
        dialog.setContentView(R.layout.bottom_sheet_booked );
        listProduct=new ArrayList<>();
        listproduct();
        txt_maid=dialog.findViewById(R.id.txt_maid);
         rcv=dialog.findViewById(R.id.rcvstatusvc);
        tenUser=dialog.findViewById(R.id.tvuserstatic);
        phoneUser=dialog.findViewById(R.id.tvphonestatic);
        addressUser=dialog.findViewById(R.id.tvadressstatic);
        dateBil=dialog.findViewById(R.id.tvdatestatus);
        datestatic=dialog.findViewById(R.id.tvdate);
        txt_maid.setText(maHD);
        dateBil.setText(dateB);
        try {
        Calendar c1=Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dau = sdf.parse(dateB);
        c1.setTime(dau);
        c1.add(Calendar.DAY_OF_MONTH,2);
        datestatic.setText(""+dateB+"-"+sdf.format(c1.getTime()));

        tenUser.setText(fullname);
        phoneUser.setText(phone);
        addressUser.setText(address);
        LinearLayoutManager layoutManager = new  LinearLayoutManager(context1,LinearLayoutManager.VERTICAL,false);
        rcv.setLayoutManager(layoutManager);
        listBill=new ArrayList<>();
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference().child("HoaDonChiTiet");
        mdata.orderByChild("idcourse").equalTo("0").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    String id=datas.getKey();
                    String idproduct=datas.child("idproduct").getValue().toString();
                    int soluong=Integer.parseInt(datas.child("soluongproduct").getValue().toString());
                    String idcourse=datas.child("idcourse").getValue().toString();
                    IDHoaDon=datas.child("idhoaDon").getValue().toString();
                    String trangThai=datas.child("trangThai").getValue().toString();
                    String username=datas.child("nameUsser").getValue().toString();
                    if(IDHoaDon.equals(maHD) && trangThai.equals("Đã thanh toán") && username.equals(DbHelper.Username)){
                        for(int i=0;i<listProduct.size();i++){
                            if(listProduct.get(i).getIdProduct().equals(idproduct)){
                                moneyProduct1=listProduct.get(i).getMoneyProduct();
                                nameProduct=listProduct.get(i).getNameProduct();
                                imgProduct=listProduct.get(i).getImagesproduct2();
                            }
                        }
                        listBill.add(new HoaDonChiTiet(moneyProduct1,soluong,nameProduct,imgProduct));
                        adapter_statuschild=new Adapter_statuschild(listBill,context);
                        rcv.setAdapter(adapter_statuschild);
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        } catch (ParseException e) {
            e.printStackTrace();
        }
        dialog.show();

    }
    public void listproduct(){
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference().child("PRODUCT");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    String id=datas.getKey();
                    String img=datas.child("imagesproduct2").getValue().toString();
                    String name=datas.child("nameProduct").getValue().toString();
                    int money=Integer.parseInt(datas.child("moneyProduct").getValue().toString());
                    listProduct.add(new Product(id,name,money,img));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Status_Holder extends RecyclerView.ViewHolder {
        TextView tvma,tvngay,tvmoney;
        LinearLayout ll;

        public Status_Holder(@NonNull View itemView) {
            super(itemView);
            tvma=itemView.findViewById(R.id.txtma);
            tvngay=itemView.findViewById(R.id.txtdatestatus);
            tvmoney=itemView.findViewById(R.id.txtmoneystatus);
            ll=itemView.findViewById(R.id.llstatus);
        }
    }
}
