package com.example.assignment_final.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Admin_sale;
import com.example.assignment_final.DAO.DAO_Discount;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Discount;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Activity_admin_sale extends AppCompatActivity {
    RecyclerView rcv;
    Adapter_Admin_sale adapter;
    FloatingActionButton btcreate;
    ArrayList<Discount> list;
    DAO_Discount dao_discount;
    EditText etngaybt,etngaykt,etmakhuyenmai,etmoney;
    Spinner spbusiness;
    Button save ,cancel;
    int position1;
    public SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );
    public String date = df.format( java.util.Calendar.getInstance().getTime() );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_admin_sale);
        list=new ArrayList<>();
        rcv=findViewById( R.id.rcvSale);
        btcreate=findViewById( R.id.flPTSale);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        listDiscount();

        btcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialog( Activity_admin_sale.this);
            }
        });
    }

    private void opendialog(final Context context) {

        final BottomSheetDialog dialog =new BottomSheetDialog(context);
        dialog.setContentView( R.layout.bottom_sheet_sale);

        setTitle("SALE");
        etngaybt=dialog.findViewById( R.id.etdatebt);
        etngaykt=dialog.findViewById( R.id.etdatekt);
        etmakhuyenmai=dialog.findViewById( R.id.etnamediscount);
        etmoney=dialog.findViewById( R.id.etmoneydiscount);
        spbusiness=dialog.findViewById( R.id.sp_discount);
        cancel=dialog.findViewById( R.id.canceldiscound);
        save=dialog.findViewById( R.id.savediscount);

        ArrayList<String> listBusinessString=new ArrayList<>();
            listBusinessString.add("Course");
            listBusinessString.add("PRODUCT");

        final ArrayAdapter arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,listBusinessString);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spbusiness.setAdapter(arrayAdapter);
        spbusiness.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_position = String.valueOf(position);
                position1 = Integer.valueOf(item_position)+1;
                Toast.makeText(context, "value is "+ position1, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Date today= new Date(  );
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime( today );
        final int dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        final int month= calendar.get(Calendar.MONTH);
        final int year= calendar.get(Calendar.YEAR);
        etngaybt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog= new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "yyyy/MM/dd" );
                        calendar.set( i,i1,i2 );
                        etngaybt.setText( simpleDateFormat.format( calendar.getTime() ) );

                    }
                }, year,month,dayOfWeek);
                datePickerDialog.show();
            }
        } );
        etngaykt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog= new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "yyyy/MM/dd" );
                        calendar.set( i,i1,i2 );
                        etngaykt.setText( simpleDateFormat.format( calendar.getTime() ) );
                    }
                }, year,month,dayOfWeek);
                datePickerDialog.show();
            }
        } );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    DAO_Discount.insert_Discount_Firebase(context,new Discount(etngaykt.getText().toString(), Float.parseFloat(etmoney.getText().toString()),
                            etngaybt.getText().toString(),spbusiness.getSelectedItem().toString(),etmakhuyenmai.getText().toString()));
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                    list.add(new Discount(etngaykt.getText().toString(), Float.parseFloat(etmoney.getText().toString()),
                            etngaybt.getText().toString(),spbusiness.getSelectedItem().toString(),etmakhuyenmai.getText().toString()));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }catch (Exception ex){
                    Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        dialog.show();
    }

    private void listDiscount(){
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Discount");
        mdata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dao_discount= new DAO_Discount();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Discount discount= new Discount(  );
                    discount.setIDdiscout(  datas.getKey());
                    discount.setNgaybt( datas.child("ngaybt").getValue().toString() );
                    discount.setNgaykt( datas.child("ngaykt").getValue().toString() );
                    discount.setMaKhuyenmmai( datas.child("maKhuyenmmai").getValue().toString() );
                    discount.setGiamgia( Float.parseFloat(datas.child("giamgia").getValue().toString()) );;
                    discount.setTypeDiscount( datas.child("typeDiscount").getValue().toString() );
                    if(datas.child( "ngaykt" ).getValue().toString().compareTo( date )<0){
                        dao_discount.delete_Discount_Firebase(Activity_admin_sale.this,datas.getKey());
                    }else {
                        list.add(discount);
                    }

                }
                adapter=new Adapter_Admin_sale(list, Activity_admin_sale.this);
                rcv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
