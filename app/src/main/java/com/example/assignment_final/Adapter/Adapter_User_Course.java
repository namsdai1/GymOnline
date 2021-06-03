package com.example.assignment_final.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.Dialog.BottomSheet_Add_Course_KH;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.Schedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class Adapter_User_Course extends RecyclerView.Adapter<Adapter_User_Course.CourseADHolder> {
    public ArrayList<Course> list_Course;
    public List<HoaDonChiTiet> list_HoaDon= new ArrayList<>(  );
    DAO_HoaDon dao_hoaDon;
    public Context context;
    DbHelper dbHelper;
    public BottomSheet_Add_Course_KH bottomSheet_add_course_kh= new BottomSheet_Add_Course_KH();
    public TextView tvTien;
    public Adapter_User_Course(ArrayList<Course> list_Course, Context context) {
        this.list_Course = list_Course;
        this.context = context;

    }

    public static class CourseADHolder extends RecyclerView.ViewHolder{
        public View view;
        Button btnDangKy;
        public TextView tvTenKhoaHoc,tvTien,tvNgayMoHoc,tvCount;
        public CourseADHolder(View view){
            super(view);
            tvTenKhoaHoc= view.findViewById( R.id.tvTenKhoaHoc );
            tvTien= view.findViewById( R.id.tvTien );
            tvNgayMoHoc= view.findViewById( R.id.tvNgayMoHoc );
            tvCount= view.findViewById( R.id.tvCount );
            btnDangKy= view.findViewById( R.id.btnDangKy );
        }
    }
    @NonNull
    @Override
    public Adapter_User_Course.CourseADHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_user_course,parent,false );
        CourseADHolder courseHolder= new CourseADHolder( view );
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter_User_Course.CourseADHolder holder, final int position) {
        holder.tvTenKhoaHoc.setText( list_Course.get( position ).getName() );
        holder.tvNgayMoHoc.setText( list_Course.get( position ).getDate() );
        final String IDCourse = list_Course.get( position ).getID();
        dao_hoaDon= new DAO_HoaDon( context );
        int Tien =  list_Course.get( position ).getMoney() ;
        DecimalFormat decimalFormat= (DecimalFormat) NumberFormat.getInstance( Locale.US);
        decimalFormat.applyPattern( "#,###,###,###" );
        final String formattedString = decimalFormat.format( Tien );

        holder.tvTien.setText(formattedString+" USD" );
        holder.tvTien.addTextChangedListener( onTextChangedListener() );

        holder.tvCount.setText("(" +list_Course.get( position ).getLesson()+ " Buổi)" );


            holder.btnDangKy.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("HoaDonChiTiet");
                    myRef.orderByChild( "nameUsser" ).equalTo( dbHelper.Username ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot datas: dataSnapshot.getChildren()){
                                HoaDonChiTiet hoaDon= new HoaDonChiTiet(  );
                                hoaDon.setNameCourse(datas.child("nameCourse").getValue().toString()  );
                                hoaDon.setMaHD(  datas.getKey());
                                hoaDon.setTongTien( Integer.valueOf( datas.child("tongTien").getValue().toString() ) );
                                hoaDon.setNgayThanhToan( datas.child("ngayThanhToan").getValue().toString() );
                                hoaDon.setNamePT(datas.child("namePT").getValue().toString()  );
                                hoaDon.setNgayMoKhoa( datas.child("ngayMoKhoa").getValue().toString() );
                                hoaDon.setLesson( Integer.valueOf( datas.child("lesson").getValue().toString() ) );
                                hoaDon.setIDCourse(datas.child("idcourse").getValue().toString()  );
                                if(!datas.child( "idcourse" ).getValue().equals( "0" )){
                                    list_HoaDon.add( hoaDon );
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                    int count = 0;
                    for (int i = 0; i < list_HoaDon.size(); i++) {
                        if (IDCourse.equalsIgnoreCase( String.valueOf( list_HoaDon.get( i ).getIDCourse() ) ) ) {
                            count++;
                        }
                    }
                    if (count == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString( "ID", list_Course.get( position ).getID() );
                        bundle.putString( "TenKhoaHoc", list_Course.get( position ).getName() );
                        bundle.putString( "NgayMoHoc", list_Course.get( position ).getDate() );
                        bundle.putInt( "Tien", list_Course.get( position ).getMoney() );
                        bundle.putInt( "lesson", list_Course.get( position ).getLesson() );
                        bottomSheet_add_course_kh.setArguments( bundle );
                        openDialog( context );
                    } else if(count != 0){
                        Toast.makeText( context, "Khóa học này bạn đã đăng ký rồi!!!", Toast.LENGTH_SHORT ).show();
                    }
                }
            } );
        }


    public void openDialog(Context context){
        bottomSheet_add_course_kh.show(((AppCompatActivity) context).getSupportFragmentManager(),bottomSheet_add_course_kh.getTag());
    }
    @Override
    public int getItemCount() {
        return list_Course.size();
    }
    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvTien.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance( Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    tvTien.setText(formattedString);
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                tvTien.addTextChangedListener(this);
            }
        };
    }
}
