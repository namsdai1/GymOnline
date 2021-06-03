package com.example.assignment_final.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_final.Activity.Activity_Add_Store;
import com.example.assignment_final.Activity.Activity_Admin_Course;
import com.example.assignment_final.Activity.Activity_Admin_PT;
import com.example.assignment_final.Activity.Activity_Admin_Users;
import com.example.assignment_final.Activity.Activity_Sale;
import com.example.assignment_final.Activity.Activity_admin_sale;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Activity_Home_Admin extends Fragment {
    ImageView imgCourse,imgPT,imgstore,imgUser,imgDiscount;
    TextView tvTaiKhoan,tvNameAD;
    DAO_HoaDon dao_hoaDon;
    DAO_User dao_user;
    DbHelper dbHelper;
    public static CircleImageView imgAvarta;
    LinearLayout layoutTOP;
    LineChart lc ;
    TextView course,product;
    ArrayList<Integer> listtongpd=new ArrayList<>();
    ArrayList<HoaDonChiTiet> listProduct;
    float tong=0,tong2,tong3,tong4,tongPRODCUT,tongCOURSE;
    float tongcourse=0,tongcourse2,tongcourse3,tongcourse4;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(  R.layout.fragment_admin_home, container, false );
        imgCourse= view.findViewById( R.id.imgCourse );
        imgPT= view.findViewById( R.id.imgTOP );
        imgUser= view.findViewById( R.id.imgUser );
        imgDiscount=view.findViewById( R.id.imgDiscount);
        imgstore= view.findViewById( R.id.imgstore );
        course=view.findViewById( R.id.revenuecourse);
        product=view.findViewById( R.id.retailsales);
        lc=view.findViewById( R.id.lcadmin);
        dao_hoaDon = new DAO_HoaDon( getContext() );
        dao_user = new DAO_User( getContext() );


        Toast.makeText(getContext(), ""+listtongpd.size(), Toast.LENGTH_SHORT).show();

        imgDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCourse = new Intent( getContext(), Activity_admin_sale.class);
                startActivity( intentCourse );
            }
        });
        imgCourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCourse = new Intent( getContext(), Activity_Admin_Course.class);
                startActivity( intentCourse );
            }
        } );
        imgPT.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPT = new Intent(  getContext(), Activity_Admin_PT.class);
                startActivity( intentPT );
            }
        } );
        imgstore.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentstore = new Intent(  getContext(), Activity_Add_Store.class);
                startActivity( intentstore );
            }
        } );

        imgUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentNEWS = new Intent(  getContext(), Activity_Admin_Users.class);
                startActivity( intentNEWS );
            }
        } );

        linecharts();
        return view;
    }
    private void linecharts(){
        final SimpleDateFormat sdf=new SimpleDateFormat("YYYY/MM/dd");
        DatabaseReference mdata = FirebaseDatabase.getInstance().getReference().child("HoaDonChiTiet");
        mdata.orderByChild("trangThai").equalTo("Đã thanh toán").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas:dataSnapshot.getChildren()){
                    int tongtien=Integer.parseInt(datas.child("tongTien").getValue().toString());
                    String date=datas.child("ngayThanhToan").getValue().toString();
                    String idcourse=datas.child("idcourse").getValue().toString();
                    if(idcourse.equals("0")){
                        tongPRODCUT+=tongtien;
                    }else {
                        tongCOURSE+=tongtien;
                    }
                    Calendar calendar = Calendar.getInstance();
                    int nhay = Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)));
                    int nam=calendar.get(Calendar.YEAR);

                    if(getDated(nhay,nam).compareTo((date))<0 && idcourse.equals("0") ) {
                        tong += tongtien;
                    }else if(getDated(nhay-1,nam).compareTo((date))<0  && idcourse.equals("0")){
                        tong2+=tongtien;
                    }else if(getDated(nhay-2,nam).compareTo((date))<0  && idcourse.equals("0") ){
                        tong3+=tongtien;
                    }else if(getDated(nhay-3,nam).compareTo((date))<0  && idcourse.equals("0")){
                        tong4+=tongtien;
                    }else if(getDated(nhay,nam).compareTo((date))<0 && getDatec(nhay,nam).compareTo((date))>0 && !idcourse.equals("0")){
                        tongcourse+=tongtien;
                    }else if(getDated(nhay-1,nam).compareTo((date))<0 && getDatec(nhay-1,nam).compareTo((date))>0 && !idcourse.equals("0")){
                        tongcourse2+=tongtien;
                    }else if(getDated(nhay-2,nam).compareTo((date))<0 && getDatec(nhay-2,nam).compareTo((date))>0 && !idcourse.equals("0")){
                        tongcourse3+=tongtien;
                    }else if(getDated(nhay-3,nam).compareTo((date))<0 && getDatec(nhay-3,nam).compareTo((date))>0 && !idcourse.equals("0")){
                        tongcourse4+=tongtien;
                    }
                }
                product.setText(tongPRODCUT+"VND");
                course.setText(tongCOURSE+"VND");

                Calendar calendar = Calendar.getInstance();
                int nhay = Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)));

                ArrayList<Entry> vis = new ArrayList<>();
                vis.add(new Entry(nhay-2,tong4));
                vis.add(new Entry(nhay-1,tong3));
                vis.add(new Entry(nhay, tong2));
                vis.add(new Entry(nhay+1,tong));

                ArrayList<Entry> vis2 = new ArrayList<>();
                vis2.add(new Entry(nhay-2,tongcourse4));
                vis2.add(new Entry(nhay-1,tongcourse3));
                vis2.add(new Entry(nhay, tongcourse2));
                vis2.add(new Entry(nhay+1,tongcourse));

                LineDataSet lndatanew = new LineDataSet(vis, "doanh thu Product");
                LineDataSet lndatanew2 = new LineDataSet(vis2, "doanh thu Course");
                ArrayList<ILineDataSet> dataSets=new ArrayList<>();
                dataSets.add(lndatanew);
                dataSets.add(lndatanew2);
                LineData data=new LineData(dataSets);

                lc.setData(data);
                lc.invalidate();
                lndatanew.setColor(Color.RED);
                lndatanew.setDrawCircles(true);
                lndatanew.setLineWidth(5);
                lndatanew.setCircleRadius(5);
                lndatanew.setValueTextSize(15);
                lndatanew2.setColor(Color.BLUE);
                lndatanew2.setDrawCircles(true);
                lndatanew2.setLineWidth(5);
                lndatanew2.setCircleRadius(5);
                lndatanew2.setValueTextSize(15);
                ;
                XAxis xAxis = lc.getXAxis();
                XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
                xAxis.setPosition(position);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private static String getDatec(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        calendar.set(year, month , 1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY/MM/dd");
        return DATE_FORMAT.format(date);
    }
    private static String getDated(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        // passing month-1 because 0-->jan, 1-->feb... 11-->dec
        calendar.set(year, month , 1);
        calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
        Date date = calendar.getTime();
        DateFormat DATE_FORMAT = new SimpleDateFormat("YYYY/MM/dd");
        return DATE_FORMAT.format(date);
    }
}