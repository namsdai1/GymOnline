package com.example.assignment_final.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.Activity.Activity_Add_Store;
import com.example.assignment_final.Activity.Activity_Status;
import com.example.assignment_final.Adapter.Adapter_Status;
import com.example.assignment_final.Adapter.Adapter_User_My_Course;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Hoadon;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Fragment_Statistical extends Fragment {
    RecyclerView rcUserMyCourse;
    public Adapter_User_My_Course adapter_user_my_course;
    public ArrayList<HoaDonChiTiet> list_HoaDonChiTiet= new ArrayList<>(  );
    public ArrayList<Hoadon> list_HoaDon= new ArrayList<>(  );
    Button btn_date1, btn_date2;
    TextView tv_total,tv_date;
    Spinner spStatistical;
    PieChart pieChart;
    ArrayList<String> list_Sp= new ArrayList<>(  );
    public  DAO_HoaDon dao_hoaDon;
    Adapter_Status adapter_status;
    DecimalFormat formatter = new DecimalFormat("#,###");
    ImageView imgRefresh;
    DatabaseReference myRef;
    int total;
    float totalByIDCousrse=0;
    float totalByIDProduct=0;
    String nameByIDCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_admin_bill, container, false);
        rcUserMyCourse= view.findViewById( R.id.rcvUserMyCourse );

        btn_date1 = view.findViewById(R.id.btn_date1);
        btn_date2 = view.findViewById(R.id.btn_date2);
        tv_total = view.findViewById(R.id.tv_total);
        tv_date = view.findViewById(R.id.tv_date);
        pieChart= view.findViewById( R.id.piechart_1 );
        imgRefresh= view.findViewById( R.id.imgRefresh );
        spStatistical = view.findViewById(R.id.spStatistical);

        list_Sp.add( "Course" );
        list_Sp.add( "Product" );
        rcUserMyCourse.setLayoutManager( new LinearLayoutManager( getContext() ) );
        dao_hoaDon= new DAO_HoaDon( getContext());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_Sp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatistical.setAdapter(adapter);

        btn_date1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date(btn_date1);
            }
        } );
        btn_date2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date(btn_date2);
            }
        } );
        spStatistical.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spStatistical.getSelectedItem().toString().equalsIgnoreCase( "Course" )){
                    setData_Course();
                    imgRefresh.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            total=0;
                            String date1 = btn_date1.getText().toString();
                            String date2 = btn_date2.getText().toString();
                            if (date1.equals( "From Date" ) || date2.equals( "To Date" )) {
                                setData_Course();
                            } else {
                                setDataByDate_Course();

                            }
                            tv_date.setText( date1 + " - " + date2 );
                        }
                    } );

                }else if(spStatistical.getSelectedItem().toString().equalsIgnoreCase( "Product" )){
                    setData_Product();
                    imgRefresh.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            total=0;
                            String date1 = btn_date1.getText().toString();
                            String date2 = btn_date2.getText().toString();
                            if (date1.equals( "From Date" ) || date2.equals( "To Date" )) {
                                setData_Product();
                            } else {
                                setDataByDate_Product();

                            }
                            tv_date.setText( date1 + " - " + date2 );
                        }
                    } );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        return view;
    }
    public void setPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels( true );
        pieChart.setHoleRadius( 20f );
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(10f);
        pieChart.setTransparentCircleRadius(30f);
        pieChart.setHoleColor( Color.WHITE);
        pieChart.getLegend().setTextColor( Color.WHITE );
        pieChart.getLegend().setOrientation( Legend.LegendOrientation.VERTICAL );
        pieChart.getLegend().setTextSize( 15f );
        pieChart.animateY(3000, Easing.EaseInOutCubic);
    }
    public void setDataChart(ArrayList yValues){
        PieDataSet dataSet = new PieDataSet(yValues, "Tỉ lệ phi thu khóa học");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(0.3f);
        dataSet.setColors( ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(Color.WHITE);
        pieChart.setData(pieData);
    }
    public void setDataByDate_Course(){

        final ArrayList<PieEntry> yValues = new ArrayList<>();
        total=0;

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "HoaDonChiTiet" ).addValueEventListener(new ValueEventListener() {
            DecimalFormat formatter = new DecimalFormat("#,###");
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_HoaDonChiTiet.clear();
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    HoaDonChiTiet hoaDonChiTiet = datas.getValue( HoaDonChiTiet.class );
                    hoaDonChiTiet.setMaHD( datas.getKey() );
                    if(!datas.child( "idcourse" ).getValue().equals( "0" ) && btn_date1.getText().toString().compareTo( datas.child( "ngayThanhToan" ).getValue().toString() )<=0 &&
                            btn_date2.getText().toString().compareTo( datas.child( "ngayThanhToan" ).getValue().toString()  )>=0){
                        total+=Integer.valueOf( datas.child("tongTien").getValue().toString() );
                        list_HoaDonChiTiet.add( hoaDonChiTiet );
                    }

                }

                adapter_user_my_course= new Adapter_User_My_Course( getContext(),list_HoaDonChiTiet);
                rcUserMyCourse.setAdapter( adapter_user_my_course );
                btn_date2.setText( "To Date" );
                btn_date1.setText( "From Date" );
                tv_total.setText( formatter.format( total)+" USD" );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "Course" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    int count=0;
                    for(int i=0;i<list_HoaDonChiTiet.size();i++) {
                        if (datas.getKey().equals( list_HoaDonChiTiet.get( i ).getIDCourse() )) {
                            totalByIDCousrse += Float.valueOf( list_HoaDonChiTiet.get( i ).getTongTien() );
                            count++;
                        }
                    }
                    if(count!=0){
                        float tiLe= (totalByIDCousrse/ total)*100;
                        yValues.add(new PieEntry( tiLe  ,datas.child( "name" ).getValue().toString()));
                        setPieChart(  );
                        setDataChart(yValues);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setDataByDate_Product(){
        list_HoaDon.clear();
        final ArrayList<PieEntry> yValues = new ArrayList<>();
        total=0;
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Hoadon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    Hoadon hoadon= new Hoadon(  );
                    hoadon.setIDHD(  datas.getKey());
                    hoadon.setDateHD( datas.child("dateHD").getValue().toString() );
                    hoadon.setStatus( datas.child("status").getValue().toString() );
                    hoadon.setTongtien( Integer.parseInt(datas.child("tongtien").getValue().toString()) );
                    hoadon.setUser( datas.child("user").getValue().toString() );
                    if(btn_date1.getText().toString().compareTo( datas.child( "dateHD" ).getValue().toString() )<=0 &&
                            btn_date2.getText().toString().compareTo( datas.child( "dateHD" ).getValue().toString()  )>=0){
                        list_HoaDon.add( hoadon );
                        total+=Integer.valueOf( datas.child("tongtien").getValue().toString() );
                    }

                }
                adapter_status= new Adapter_Status(list_HoaDon, getContext());
                rcUserMyCourse.setAdapter( adapter_status );
                btn_date2.setText( "To Date" );
                btn_date1.setText( "From Date" );
                tv_total.setText( formatter.format( total)+" USD" );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        myRef.child( "PRODUCT" ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int count=0;
                final ArrayList<PieEntry> yValues = new ArrayList<>();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    for(int i=0;i<list_HoaDonChiTiet.size();i++){
                        if (datas.getKey().equals( list_HoaDonChiTiet.get( i ).getIDProduct() )) {
                            totalByIDProduct += Float.valueOf( list_HoaDonChiTiet.get( i ).getTongTien() );
                            count++;
                        }
                    }
                    if(count!=0){
                        float tiLe= (totalByIDCousrse/ total)*100;
                        yValues.add(new PieEntry( tiLe  ,datas.child( "nameProduct" ).getValue().toString()));
                        setPieChart(  );
                        setDataChart(yValues);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }
    public void setData_Course(){
        total=0;
        final ArrayList<PieEntry> yValues = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "HoaDonChiTiet" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_HoaDonChiTiet.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    HoaDonChiTiet hoaDonChiTiet= new HoaDonChiTiet(  );
                    hoaDonChiTiet.setNameCourse(datas.child("nameCourse").getValue().toString()  );
                    hoaDonChiTiet.setMaHD(  datas.getKey());
                    hoaDonChiTiet.setTongTien( Integer.valueOf( datas.child("tongTien").getValue().toString() ) );
                    hoaDonChiTiet.setNgayThanhToan( datas.child("ngayThanhToan").getValue().toString() );
                    hoaDonChiTiet.setNamePT(datas.child("namePT").getValue().toString()  );
                    hoaDonChiTiet.setNgayMoKhoa( datas.child("ngayMoKhoa").getValue().toString() );
                    hoaDonChiTiet.setLesson( Integer.valueOf( datas.child("lesson").getValue().toString() ) );
                    hoaDonChiTiet.setIDCourse(datas.child("idcourse").getValue().toString()  );
                    if(!datas.child( "idcourse" ).getValue().equals( "0" )){
                        total+=Integer.valueOf( datas.child("tongTien").getValue().toString() );
                        list_HoaDonChiTiet.add( hoaDonChiTiet );
                    }

                }
                adapter_user_my_course= new Adapter_User_My_Course( getContext(),list_HoaDonChiTiet);
                rcUserMyCourse.setAdapter( adapter_user_my_course );
                btn_date2.setText( "To Date" );
                btn_date1.setText( "From Date" );
                tv_total.setText( formatter.format( total)+" USD" );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "Course" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    int count=0;
                    for(int i=0;i<list_HoaDonChiTiet.size();i++) {
                        if (datas.getKey().equals( list_HoaDonChiTiet.get( i ).getIDCourse() )) {
                            totalByIDCousrse += Float.valueOf( list_HoaDonChiTiet.get( i ).getTongTien() );
                            count++;
                        }
                    }
                    if(count!=0){
                        float tiLe= (totalByIDCousrse/ total)*100;
                        yValues.add(new PieEntry( tiLe  ,datas.child( "name" ).getValue().toString()));
                        setPieChart(  );
                        setDataChart(yValues);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setData_Product(){
        list_HoaDon.clear();
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Hoadon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    Hoadon hoadon= new Hoadon(  );
                    hoadon.setIDHD(  datas.getKey());
                    hoadon.setDateHD( datas.child("dateHD").getValue().toString() );
                    hoadon.setStatus( datas.child("status").getValue().toString() );
                    hoadon.setTongtien( Integer.parseInt(datas.child("tongtien").getValue().toString()) );
                    hoadon.setUser( datas.child("user").getValue().toString() );
                    list_HoaDon.add( hoadon );
                    total+=Integer.valueOf( datas.child("tongtien").getValue().toString() );
                }
                adapter_status= new Adapter_Status(list_HoaDon, getContext());
                rcUserMyCourse.setAdapter( adapter_status );
                btn_date2.setText( "To Date" );
                btn_date1.setText( "From Date" );
                tv_total.setText( formatter.format( total)+" USD" );
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "HoaDonChiTiet" ).addValueEventListener(new ValueEventListener() {
            DecimalFormat formatter = new DecimalFormat("#,###");
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_HoaDonChiTiet.clear();
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    HoaDonChiTiet hoaDonChiTiet= new HoaDonChiTiet(  );
                    hoaDonChiTiet.setIDProduct( datas.child( "idproduct" ).getValue().toString() );
                    if(!datas.child( "idproduct" ).getValue().equals( "0" )){
                        total+=Integer.valueOf( datas.child("tongTien").getValue().toString() );
                        list_HoaDonChiTiet.add( hoaDonChiTiet );
                    }

                }
                myRef.child( "PRODUCT" ).addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        int count=0;
                        final ArrayList<PieEntry> yValues = new ArrayList<>();
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            for(int i=0;i<list_HoaDonChiTiet.size();i++){
                                if (datas.getKey().equals( list_HoaDonChiTiet.get( i ).getIDProduct() )) {
                                    totalByIDProduct += Float.valueOf( list_HoaDonChiTiet.get( i ).getTongTien() );
                                    count++;
                                }
                            }
                            if(count!=0){
                                float tiLe= (totalByIDCousrse/ total)*100;
                                yValues.add(new PieEntry( tiLe  ,datas.child( "nameProduct" ).getValue().toString()));
                                setPieChart(  );
                                setDataChart(yValues);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void Date(final Button btn){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);

        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        final int months = cal.get(Calendar.MONTH);
        final int years = cal.get(Calendar.YEAR);
        final Calendar calendar = Calendar.getInstance();


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                calendar.set(i,i1,i2);
                btn.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },years,months,dayOfWeek);


        datePickerDialog.show();

    }
}
