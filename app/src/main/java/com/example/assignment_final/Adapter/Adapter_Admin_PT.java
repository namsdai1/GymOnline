package com.example.assignment_final.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.Activity.Activity_Admin_PT;
import com.example.assignment_final.DAO.DAO_PT;
import com.example.assignment_final.Dialog.BottomSheet_Add_PT;
import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_Admin_PT extends RecyclerView.Adapter<Adapter_Admin_PT.PTHolder> {
    public ArrayList<PT> list_PT;
    public Context context;
    public DAO_PT dao_pt;
    public Adapter_Admin_PT(ArrayList<PT> list_PT, Context context) {
        this.list_PT = list_PT;
        this.context = context;
    }

    public static class PTHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvTenPT,tvTien,tvNgaySinh,tvMoTa;
        ImageView imgDelete,imgEdit;
        CircleImageView img_row;
        public PTHolder(View view){
            super(view);
            tvTenPT= view.findViewById( R.id.tvTenPT );
            tvTien= view.findViewById( R.id.tvTien );
            tvNgaySinh= view.findViewById( R.id.tvNgaySinh );
            tvMoTa= view.findViewById( R.id.tvMoTa );
            imgDelete= view.findViewById( R.id.imgDelete );
            imgEdit= view.findViewById( R.id.imgEdit );
            img_row= view.findViewById( R.id.img_row );
        }
    }
    @NonNull
    @Override
    public Adapter_Admin_PT.PTHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_admin_pt,parent,false );
        PTHolder ptHolder= new PTHolder( view );
        return ptHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_Admin_PT.PTHolder holder, final int position) {
        holder.tvTenPT.setText( list_PT.get( position ).getName() );
        holder.tvNgaySinh.setText( list_PT.get( position ).getDate() );
        holder.tvTien.setText( list_PT.get( position ).getMoney()+" USD" );

        int Tien =  list_PT.get( position ).getMoney() ;
        DecimalFormat decimalFormat= (DecimalFormat) NumberFormat.getInstance( Locale.US);
        decimalFormat.applyPattern( "#,###,###,###" );
        final String formattedString = decimalFormat.format( Tien );
        holder.tvTien.setText(formattedString+" USD" );


        holder.tvMoTa.setText( list_PT.get( position ).getNote() );

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


        holder.imgDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PT");
                myRef.orderByChild( "ID2" ).equalTo(list_PT.get( position ).getID2()  ).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Delete");
                        builder.setMessage("Ban co muon xoa "+list_PT.get( position ).getName()+ " khong?");
                        builder.setCancelable(true);
                        builder.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Goi function Delete
                                        dao_pt = new DAO_PT(context);
                                        dao_pt.delete_Firebase( context,list_PT.get( position ).getID2() );
                                        list_PT.remove( position );
                                        Toast.makeText(context, "Xóa thành công ",Toast.LENGTH_SHORT ).show();
                                        notifyDataSetChanged();

                                    }
                                });

                        builder.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.show();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        } );
        holder.imgEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("ID", list_PT.get(position).getID2()+"");
                args.putString("name", list_PT.get(position).getName()+"");
                args.putString("date", list_PT.get(position).getDate()+"");
                args.putString("money", list_PT.get(position).getMoney()+"");
                args.putString("note", list_PT.get(position).getNote());
                args.putString("img", list_PT.get(position).getImages2());
                Log.e( "TAG", "onClick: "+ list_PT.get(position).getImages2() );
                BottomSheet_Add_PT bottomSheet_Add_PT= new BottomSheet_Add_PT();
                bottomSheet_Add_PT.setArguments( args );
                bottomSheet_Add_PT.show(((AppCompatActivity) context).getSupportFragmentManager(),bottomSheet_Add_PT.getTag());;

            }
        } );
    }




    @Override
    public int getItemCount() {
        return list_PT.size();
    }
}
