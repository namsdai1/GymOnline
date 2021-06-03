package com.example.assignment_final.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_User_PT extends RecyclerView.Adapter<Adapter_User_PT.PTHolder> {
    public ArrayList<PT> list_PT;
    public Context context;


    public Adapter_User_PT(ArrayList<PT> list_PT, Context context) {
        this.list_PT = list_PT;
        this.context = context;
    }

    public static class PTHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvTenPT,tvTien,tvNgaySinh,tvMoTa;
        CircleImageView img_row;
        public PTHolder(View view){
            super(view);
            tvTenPT= view.findViewById( R.id.tvTenPT );
            tvTien= view.findViewById( R.id.tvTien );
            tvNgaySinh= view.findViewById( R.id.tvNgaySinh );
            tvMoTa= view.findViewById( R.id.tvMoTa );
            img_row=view.findViewById( R.id.img_row );
        }
    }
    @NonNull
    @Override
    public Adapter_User_PT.PTHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_user_pt,parent,false );
        PTHolder ptHolder= new PTHolder( view );
        return ptHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_User_PT.PTHolder holder, int position) {
        holder.tvTenPT.setText( list_PT.get( position ).getName() );
        holder.tvNgaySinh.setText( list_PT.get( position ).getDate() );
        holder.tvTien.setText( list_PT.get( position ).getMoney()+" USD" );
        holder.tvMoTa.setText( list_PT.get( position ).getNote() );
        if(list_PT.get( position ).getImages()!=null){
            byte[] imgView= list_PT.get( position ).getImages();
            Bitmap bitmap= BitmapFactory.decodeByteArray( imgView,0,imgView.length );
            holder.img_row.setImageBitmap( bitmap );
        }else{
            holder.img_row.setImageResource( R.drawable.user );
        }
    }


    @Override
    public int getItemCount() {
        return list_PT.size();
    }
}
