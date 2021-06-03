package com.example.assignment_final.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Schedule;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Adapter_User_My_Course extends RecyclerView.Adapter<Adapter_User_My_Course.HoadonHolder> {
    public ArrayList<HoaDonChiTiet> list_HoaDon;
    public ArrayList<Schedule> list_Schedule= new ArrayList<>(  );
    public ArrayList<Schedule> list_All_Schedule= new ArrayList<>(  );
    public Context context;
    DAO_HoaDon dao_hoaDon;
    DatabaseReference myRef;
    DAO_Schedule dao_schedule;
    public TextView tvTien, tvTieuDeSchedule;
    Button btnCancel;
    DbHelper dbHelper;
    RecyclerView rcvSchedule;
    Adapter_Schedule adapter_schedule;
    public String IDCourse;
    public SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );
    public String date = df.format( Calendar.getInstance().getTime() );

    public Adapter_User_My_Course(Context context, ArrayList<HoaDonChiTiet> list_HoaDon) {
        this.list_HoaDon = list_HoaDon;
        this.context = context;
        dao_hoaDon = new DAO_HoaDon( context );
    }

    public static class HoadonHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView tvTenKhoaHoc, tvTien, tvNgayThanhToan, tvTenPT, tvCount, tvNgayMoHoc, tvTrangThai;

        public HoadonHolder(View view) {
            super( view );
            tvTenKhoaHoc = view.findViewById( R.id.tvTenKhoaHoc );
            tvTien = view.findViewById( R.id.tvTien );
            tvNgayThanhToan = view.findViewById( R.id.tvNgayThanhToan );
            tvTenPT = view.findViewById( R.id.tvTenPT );
            tvTrangThai = view.findViewById( R.id.tvTrangThai );

            this.tvCount = view.findViewById( R.id.tvCount );
            this.tvNgayMoHoc = view.findViewById( R.id.tvNgayMoHoc );
        }
    }

    @NonNull
    @Override
    public Adapter_User_My_Course.HoadonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_user_my_course, parent, false );
        HoadonHolder courseHolder = new HoadonHolder( view );
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_User_My_Course.HoadonHolder holder, final int position) {
        final HoaDonChiTiet HoaDonChiTiet = list_HoaDon.get( position );
        String dateSchedule;

        final int id;
        if (list_HoaDon != null) {
            holder.tvTenKhoaHoc.setText( HoaDonChiTiet.getNameCourse() );
            holder.tvNgayThanhToan.setText( HoaDonChiTiet.getNgayThanhToan() );
            holder.tvTenPT.setText( " - " + HoaDonChiTiet.getNamePT() );


            int Tien = HoaDonChiTiet.getTongTien();
            DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance( Locale.US );
            decimalFormat.applyPattern( "#,###,###,###" );
            final String formattedString = decimalFormat.format( Tien );

            holder.tvTien.setText( formattedString + " USD" );


            holder.tvCount.setText( "(" + HoaDonChiTiet.getLesson() + "day)" );
            holder.tvNgayMoHoc.setText( HoaDonChiTiet.getNgayMoKhoa() );
            if (!dbHelper.Username.equalsIgnoreCase( "Admin" )) {
                holder.itemView.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        IDCourse = list_HoaDon.get( position ).getIDCourse();
                        String nameCourse = list_HoaDon.get( position ).getNameCourse();
                        openDialogSchedule( context, IDCourse, nameCourse );

                    }
                } );
            }


            IDCourse = list_HoaDon.get( position ).getIDCourse();
            dao_schedule = new DAO_Schedule( context );

            myRef = FirebaseDatabase.getInstance().getReference("Schedule");
            myRef.orderByChild( "courseID" ).equalTo( IDCourse ).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot datas : dataSnapshot.getChildren()) {
                        Schedule schedule = datas.getValue( Schedule.class );
                        schedule.setScheduleID( datas.getKey() );

                        list_All_Schedule.add( schedule );
                    }
                    if(dbHelper.Username.equalsIgnoreCase( "Admin" )){
                        holder.tvTrangThai.setVisibility( View.GONE );
                    }else{
                        if (list_All_Schedule.get( list_All_Schedule.size() - 1 ).getDateSchedule().compareTo( date ) < 0) {
                            holder.tvTrangThai.setText( "Complete" );
                            holder.tvTrangThai.setTextColor( Color.parseColor( "#00FF00" ) );
                            @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation( context, R.anim.animal );
                            holder.tvTrangThai.startAnimation( animation );
                        } else if (list_All_Schedule.get( list_All_Schedule.size() - 1 ).getDateSchedule().compareTo( date ) >= 0) {
                            if (HoaDonChiTiet.getNgayMoKhoa().compareTo( date ) < 0) {
                                holder.tvTrangThai.setText( "Studying" );
                                holder.tvTrangThai.setTextColor( Color.parseColor( "#0033FF" ) );
                                @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation( context, R.anim.animal );
                                holder.tvTrangThai.startAnimation( animation );
                            }
                            if (HoaDonChiTiet.getNgayMoKhoa().compareTo( date ) > 0) {
                                holder.tvTrangThai.setText( "Coming soon" );
                                holder.tvTrangThai.setTextColor( Color.parseColor( "#FFD700" ) );
                                @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation( context, R.anim.animal );
                                holder.tvTrangThai.startAnimation( animation );
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list_HoaDon.size();
    }

    @SuppressLint("SetTextI18n")
    protected void openDialogSchedule(final Context context, String IDCourse, String nameCourse){
        //custom dialog
        final BottomSheetDialog dialog = new BottomSheetDialog( context );
        dialog.setContentView(R.layout.bottom_sheet_schedule );

        btnCancel = dialog.findViewById( R.id.btnCancel );
        rcvSchedule= dialog.findViewById( R.id.rcvSchedule );
        tvTieuDeSchedule=dialog.findViewById( R.id.tvTieuDeSchedule );

        tvTieuDeSchedule.setText("Course schedule "+nameCourse);
        rcvSchedule.setLayoutManager( new LinearLayoutManager( context ) );
        DAO_Schedule dao_schedule= new DAO_Schedule( context );

        myRef = FirebaseDatabase.getInstance().getReference("Schedule");
        myRef.orderByChild( "courseID" ).equalTo( IDCourse ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    Schedule schedule = datas.getValue( Schedule.class );
                    schedule.setScheduleID( datas.getKey() );

                    if(schedule.getDateSchedule().compareTo( date )>0){
                        list_Schedule.add( schedule );
                    }
                }
                adapter_schedule= new Adapter_Schedule(list_Schedule, context);
                rcvSchedule.setAdapter( adapter_schedule );
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            });

        dialog.show();

    }
}
