package com.example.assignment_final.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Discount;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Adapter_Notification extends RecyclerView.Adapter<Adapter_Notification.Admin_sale_Holder> {
    ArrayList<Discount> list;
    Context context;

    public Adapter_Notification(ArrayList<Discount> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Admin_sale_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return (new Adapter_Notification.Admin_sale_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_sale_Holder holder, final int position) {
        holder.ma.setText(list.get(position).getMaKhuyenmmai());
        holder.discount.setText(String.valueOf(list.get(position).getGiamgia())+"%");
        holder.type.setText(list.get(position).getTypeDiscount());
        holder.ngay.setText("(From "+list.get(position).getNgaybt()+" to "+list.get(position).getNgaykt()+")");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Admin_sale_Holder extends RecyclerView.ViewHolder {
        TextView ma,ngay,discount,type;
        public Admin_sale_Holder(@NonNull View itemView) {
            super(itemView);
            ma=itemView.findViewById(R.id.tv_codediscount);
            ngay=itemView.findViewById(R.id.tv_datediscount);
            discount=itemView.findViewById(R.id.tv_discount);
            type=itemView.findViewById(R.id.tv_typediscount);

        }
    }
}
