package com.example.assignment_final.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_TOP extends RecyclerView.Adapter<Adapter_TOP.TOPHolder> {
    public ArrayList<PT> list_PT;
    public Context context;

    public Adapter_TOP(ArrayList<PT> list_PT, Context context) {
        this.list_PT = list_PT;
        this.context = context;
    }
    public static class TOPHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvTenPT,tvNumTop;
        ImageView img_row;
        public TOPHolder(View view){
            super(view);
            tvTenPT= view.findViewById( R.id.tvTenPT );
            img_row= view.findViewById( R.id.img_row );
            tvNumTop= view.findViewById( R.id.tvNumTop );
        }
    }
    @NonNull
    @Override
    public Adapter_TOP.TOPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_user_top_pt,parent,false );
        Adapter_TOP.TOPHolder topHolder= new Adapter_TOP.TOPHolder( view );
        return topHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull final Adapter_TOP.TOPHolder holder, int position) {
        holder.tvTenPT.setText( list_PT.get( position ).getName() );
        if(list_PT.get( position ).getImages2()!=null){
            String imgView= list_PT.get( position ).getImages2();
            Picasso.get().load(imgView).into( new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    holder.img_row.setImageBitmap( bitmap );
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}
            });

        }else{
            holder.img_row.setImageResource( R.drawable.user );
        }
        int count=++position;
        holder.tvNumTop.setText( "TOP "+ count);
    }


    @Override
    public int getItemCount() {
        return list_PT.size();
    }
}
