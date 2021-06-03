package com.example.assignment_final.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;


import com.example.assignment_final.R;


import java.util.ArrayList;

public class Activity_Sale extends AppCompatActivity {
    EditText etngaybt, etngaykt, etmakhuyenmai, etmoney;
    Spinner spbusiness;
    Button save, cancel;
    int position1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity__sale );
        setTitle( "SALE" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        etngaybt = findViewById( R.id.etdatebt );
        etngaykt = findViewById( R.id.etdatekt );
        etmakhuyenmai = findViewById( R.id.etnamediscount );
        etmoney = findViewById( R.id.etmoneydiscount );
        spbusiness = findViewById( R.id.sp_discount );
        cancel = findViewById( R.id.canceldiscound );
        save = findViewById( R.id.savediscount );
        ArrayList<String> listBusiness = new ArrayList<>();
        listBusiness.add( "PRODUCT" );
        listBusiness.add( "COURSE" );
        ArrayAdapter arrayAdapter = new ArrayAdapter<>( this, android.R.layout.simple_spinner_item, listBusiness );
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        spbusiness.setAdapter( arrayAdapter );
        spbusiness.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_position = String.valueOf( position );
                position1 = Integer.valueOf( item_position ) + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
    }
}