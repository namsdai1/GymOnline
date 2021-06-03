package com.example.assignment_final.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.DAO.DAO_PT;
import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Adapter_User extends RecyclerView.Adapter<Adapter_User.UserHolder> {
    ArrayList<User> list_User;
    public Context context;
    DAO_User dao_user;
    public Adapter_User(ArrayList<User> list_User, Context context) {
        this.list_User = list_User;
        this.context = context;
    }
    public static class UserHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvTenNguoiDung,tvPhoneNumber,tvAddress;
        ImageView img_row,imgDelete;

        public UserHolder(View view){
            super(view);
            tvTenNguoiDung= view.findViewById( R.id.tvTenNguoiDung );
            tvPhoneNumber= view.findViewById( R.id.tvPhoneNumber );
            tvAddress= view.findViewById( R.id.tvAddress );
            img_row= view.findViewById( R.id.img_row );
            imgDelete= view.findViewById( R.id.imgDelete );
        }
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_admin_users,parent,false );
        UserHolder userHolder= new UserHolder( view );
        return userHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final UserHolder holder, final int position) {
        holder.tvTenNguoiDung.setText( list_User.get( position ).getFullName() );
        holder.tvPhoneNumber.setText( list_User.get( position ).getPhoneNumber() );
        holder.tvAddress.setText( list_User.get( position ).getAddress() );
        if(list_User.get( position ).getImages()!=null){
            String imgView= list_User.get( position ).getImages();
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
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
                myRef.orderByChild( "IDUser" ).equalTo(list_User.get( position ).getIDUser()  ).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Delete");
                        builder.setMessage("Ban co muon xoa "+list_User.get( position ).getFullName()+ " khong?");
                        builder.setCancelable(true);
                        builder.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Goi function Delete
                                        dao_user = new DAO_User(context);
                                        dao_user.delete_Firebase( context,list_User.get( position ).getIDUser() );
                                        list_User.remove( position );
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
    }

    @Override
    public int getItemCount() {
        return list_User.size();
    }
}
