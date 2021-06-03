package com.example.assignment_final.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Card extends RecyclerView.Adapter<Adapter_Card.Card_Holder> {
    Context context;
    ArrayList<HoaDonChiTiet> list;
    public static String tong;
    ArrayList<Integer> listtong=new ArrayList<>();
    int tonghet=0;
    TextView textView;
    public Adapter_Card(Context context, ArrayList<HoaDonChiTiet> list, TextView textView) {
        this.context = context;
        this.list = list;
        this.textView=textView;
    }

    @NonNull
    @Override
    public Card_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thanhtoan, parent, false);
        return (new Adapter_Card.Card_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull final Card_Holder holder, final int position) {
        listhoadon();
        holder.name.setText(list.get(position).getNameProduct());
            holder.money.setText(""+list.get(position).getMoneyProduct()*list.get(position).getSoluongproduct());
            holder.soluong.setText(""+list.get(position).getSoluongproduct());
        Picasso.get().load(list.get(position).getImgProduct()).into(holder.imgcard2);
        final int[] so = {1};
        so[0]=list.get(position).getSoluongproduct();

        holder.cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                so[0]++;


                holder.soluong.setText(""+ so[0]);
                if(DAO_HoaDon.updateSoluong(context,list.get(position).getMaHD(),so[0])){
                    DAO_HoaDon.updatettSoluong(context,list.get(position).getMaHD(),so[0]*list.get(position).getMoneyProduct());
                    Log.d("AAAA", "onClick: "+listtong.size());
                    listhoadon();
                    holder.money.setText(""+(so[0]*list.get(position).getMoneyProduct()));
                }


            }
        });
        holder.tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (so[0]<1) {
                    Toast.makeText(context, "So bat dau tu 0", Toast.LENGTH_SHORT).show();
                }else {
                    so[0]--;
                    holder.soluong.setText(""+ so[0]);
                    if(DAO_HoaDon.updateSoluong(context,list.get(position).getMaHD(),so[0])){
                        DAO_HoaDon.updatettSoluong(context,list.get(position).getMaHD(),so[0]*list.get(position).getMoneyProduct());
                        listhoadon();
                        holder.money.setText(""+(so[0]*list.get(position).getMoneyProduct()));
                    }
                }


            }
        });


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DAO_HoaDon.delete_Firebase(context,list.get(position).getMaHD())){
                list.remove(position);
                notifyDataSetChanged();
            }else {
                Toast.makeText(context, "That bai", Toast.LENGTH_SHORT).show();
            }
            }
        });
        tong= (String) holder.soluong.getText();
    }
    public void listhoadon(){
        tonghet=0;
        listtong.clear();
        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    int tong=Integer.parseInt(datas.child("tongTien").getValue().toString());
                    String trangThai=datas.child("trangThai").getValue().toString();
                    String username = datas.child("nameUsser").getValue().toString();
                    if(trangThai.equals("Gio hang") && username.equals(DbHelper.Username)){
                        listtong.add(tong);
                    }

                }
                Log.d("AAAAB", "onDataChange: "+listtong.size());
                for(int i=0;i<listtong.size();i++){
                    tonghet+=listtong.get(i);
                    Toast.makeText(context, ""+tonghet, Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(context, ""+tonghet, Toast.LENGTH_SHORT).show();
                textView.setText(String.valueOf(tonghet));
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

    public static class Card_Holder extends RecyclerView.ViewHolder{
        TextView name,money,soluong;
        Button cong,tru;
        ImageView delete,imgcard2;
        public Card_Holder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.cardname);
            money=itemView.findViewById(R.id.moneycard);
            soluong=itemView.findViewById(R.id.cardsoluong);
            delete=itemView.findViewById(R.id.carddelete);
            tru=itemView.findViewById(R.id.cardtru);
            cong=itemView.findViewById(R.id.cardcong);
            imgcard2=itemView.findViewById(R.id.imgcard2);
        }
    }
}
