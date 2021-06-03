package com.example.assignment_final.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_statuschild extends RecyclerView.Adapter<Adapter_statuschild.statuschild_Hold> {
    ArrayList<HoaDonChiTiet> list;
    Context context;

    public Adapter_statuschild(ArrayList<HoaDonChiTiet> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public statuschild_Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statuschild, parent, false);
        return (new Adapter_statuschild.statuschild_Hold(view));
    }

    @Override
    public void onBindViewHolder(@NonNull statuschild_Hold holder, int position) {
        Picasso.get().load(list.get(position).getImgProduct()).into(holder.tvimg);
        Log.d("AAA", "onBindViewHolder: "+list.get(position).getNameProduct());
        holder.tvnamestatus.setText(list.get(position).getNameProduct());
        holder.tvmoneystatus.setText((list.get(position).getMoneyProduct())+"VND");
        holder.tvsoluong.setText((list.get(position).getSoluongproduct())+"");
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class statuschild_Hold extends RecyclerView.ViewHolder {
        ImageView tvimg;
        TextView tvnamestatus,tvsoluong,tvmoneystatus;
        public statuschild_Hold(@NonNull View itemView) {
            super(itemView);
            tvimg=itemView.findViewById(R.id.tvimg);
            tvnamestatus=itemView.findViewById(R.id.tvnamestatus);
            tvsoluong=itemView.findViewById(R.id.tvsoluongstatus);
            tvmoneystatus=itemView.findViewById(R.id.tvmoneystatus);
        }
    }
}
