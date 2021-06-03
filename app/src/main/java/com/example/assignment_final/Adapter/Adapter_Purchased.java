package com.example.assignment_final.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.R;
import com.example.assignment_final.model.Hoadon;

import java.util.ArrayList;

public class Adapter_Purchased extends RecyclerView.Adapter<Adapter_Purchased.Purchased_Holder> {
    Context context;
    ArrayList<Hoadon> list;

    public Adapter_Purchased(Context context, ArrayList<Hoadon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Purchased_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_purchased, parent, false);
        return (new Adapter_Purchased.Purchased_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Purchased_Holder holder, int position) {
        holder.ma.setText(String.valueOf(list.get(position).getIDHD()));
        holder.ngay.setText(list.get(position).getDateHD());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Purchased_Holder extends RecyclerView.ViewHolder {
        TextView ma,ngay,trangthai;

        public Purchased_Holder(@NonNull View itemView) {
            super(itemView);
            ma=itemView.findViewById(R.id.tv_mapurchased);
            ngay=itemView.findViewById(R.id.tv_datepurchased);
            trangthai=itemView.findViewById(R.id.tv_statispurchased);

        }
    }
}
