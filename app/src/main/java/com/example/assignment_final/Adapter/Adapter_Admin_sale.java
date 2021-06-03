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

import com.example.assignment_final.DAO.DAO_Discount;
import com.example.assignment_final.R;

import com.example.assignment_final.model.Discount;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Adapter_Admin_sale extends RecyclerView.Adapter<Adapter_Admin_sale.Admin_sale_Holder> {
    ArrayList<Discount> list;
    Context context;

    public Adapter_Admin_sale(ArrayList<Discount> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Admin_sale_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate( R.layout.item_admin_sale, parent, false);
        return (new Adapter_Admin_sale.Admin_sale_Holder(view));
    }

    @Override
    public void onBindViewHolder(@NonNull Admin_sale_Holder holder, final int position) {
        holder.ma.setText(list.get(position).getMaKhuyenmmai());
        holder.discount.setText(String.valueOf(list.get(position).getGiamgia())+"%");
        holder.type.setText(list.get(position).getTypeDiscount());
        holder.ngay.setText(list.get(position).getNgaybt()+" - "+list.get(position).getNgaykt());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DAO_Discount.delete_Discount_Firebase(context,list.get(position).getIDdiscout())){
                    Toast.makeText(context, "Delete Succesfully", Toast.LENGTH_SHORT).show();
                    list.remove( position );
                    notifyDataSetChanged();
                }
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog(context,list.get(position).getNgaybt(),list.get(position).getNgaykt(),list.get(position).getMaKhuyenmmai(),list.get(position).getGiamgia(),list.get(position).getTypeDiscount(),list.get(position).getIDdiscout(),position);
            }
        });
    }

    private void opendialog(final Context context, String date1, String date2, String name, float giamgia, final String namebusiness, final String ma, final int position) {
        FloatingActionButton btcreate;
        int position1;
        final EditText etngaybt,etngaykt,etmakhuyenmai,etmoney;
        final Spinner spbusiness;
        Button save ,cancel;

        final BottomSheetDialog dialog =new BottomSheetDialog(context);
        dialog.setContentView( R.layout.activity__sale2);
        final ArrayList<String> listBusinessString=new ArrayList<>();
        etngaybt=dialog.findViewById( R.id.etdatebt2);
        etngaykt=dialog.findViewById( R.id.etdatekt2);
        etmakhuyenmai=dialog.findViewById( R.id.etnamediscount2);
        etmoney=dialog.findViewById( R.id.etmoneydiscount2);
        spbusiness=dialog.findViewById( R.id.sp_discount2);
        etngaybt.setText(date1);
        etngaykt.setText(date2);
        etmakhuyenmai.setText(name);
        etmoney.setText(String.valueOf(giamgia));
        cancel=dialog.findViewById( R.id.canceldiscound2);
        save=dialog.findViewById( R.id.savediscount2);
        listBusinessString.add("Course");
        listBusinessString.add("PRODUCT");
        final ArrayAdapter arrayAdapter=new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,listBusinessString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spbusiness.setAdapter(arrayAdapter);
        int vitri=listBusinessString.indexOf(namebusiness);
        spbusiness.setSelection(vitri);

//        final DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Business");
//        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot datas: dataSnapshot.getChildren()){
//                    final String id = datas.getKey();
//                    String nameBusiness=datas.child("nameBusiness").getValue().toString();
//                    listBuissiness.add(new Bussiness(id,nameBusiness));
//                    listBusinessString.add(nameBusiness);
//
//                    Log.d("AAAA", "onDataChange: "+listBuissiness.size());

//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idbusiness=spbusiness.getSelectedItem().toString();


                String ngaybt=etngaybt.getText().toString();
                String nhaykt=etngaykt.getText().toString();
                String namekhuyenmai = etmakhuyenmai.getText().toString();
                Float money=Float.parseFloat(etmoney.getText().toString());

                Discount discount=new Discount(nhaykt,money,ngaybt,idbusiness,namekhuyenmai);
                Log.d("AAA", "onClick: "+etngaykt.getText().toString()+Float.parseFloat(etmoney.getText().toString())+etngaybt.getText().toString());
                if(DAO_Discount.update_Discount_Firebase(context,ma,discount)){
                    Toast.makeText(context, "Thanh cong", Toast.LENGTH_SHORT).show();

                    list.set(position, discount);
                    notifyDataSetChanged();
                }
            }
        });
        dialog.show();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class Admin_sale_Holder extends RecyclerView.ViewHolder {
        TextView ma,ngay,discount,type;
        ImageView edit,delete;
        public Admin_sale_Holder(@NonNull View itemView) {
            super(itemView);
            ma=itemView.findViewById( R.id.tv_codediscount);
            ngay=itemView.findViewById( R.id.tv_datediscount);
            discount=itemView.findViewById( R.id.tv_discount);
            type=itemView.findViewById( R.id.tv_typediscount);
            edit=itemView.findViewById( R.id.imgEditDiscount);
            delete=itemView.findViewById( R.id.imgDeleteDiscount);
        }
    }
}
