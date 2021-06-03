package com.example.assignment_final.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.assignment_final.Activity.Activity_Admin_Course;
import com.example.assignment_final.DAO.DAO_Course;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.PT;
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
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.assignment_final.Activity.Activity_Admin_Course.rcvADCourse;


public class Adapter_Admin_Course extends RecyclerView.Adapter<Adapter_Admin_Course.CourseADHolder>  {
    public ArrayList<Course> list_Course;
    public Context context;
    public DAO_Course dao_course;
    EditText edtNameCourse,edtMoneyCourse,edtLesson;
    TextView tvDateCourse;
    Button btnAdd;

    Course course;
    Adapter_Admin_Course adapter_admin_course;



    public String id;

    public Adapter_Admin_Course(ArrayList<Course> list_Course, Context context) {
        this.list_Course = list_Course;
        this.context = context;
    }




    public static class CourseADHolder extends RecyclerView.ViewHolder{
        public View view;
        public TextView tvTenKhoaHoc,tvTien,tvNgayMoHoc,tvCount;
        public ImageView imgDelete,imgEdit;
        public CourseADHolder(View view){
            super(view);
            tvTenKhoaHoc= view.findViewById( R.id.tvTenKhoaHoc );
            tvTien= view.findViewById( R.id.tvTien );
            tvNgayMoHoc= view.findViewById( R.id.tvNgayMoHoc );
            tvCount= view.findViewById( R.id.tvCount );
            imgDelete= view.findViewById( R.id.imgDelete );
            imgEdit= view.findViewById( R.id.imgEdit );
        }
    }
    @NonNull
    @Override
    public Adapter_Admin_Course.CourseADHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.item_admin_course,parent,false );
        CourseADHolder courseHolder= new CourseADHolder( view );
        return courseHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Admin_Course.CourseADHolder holder, final int position) {
        final Course course = list_Course.get( position );
        holder.tvTenKhoaHoc.setText( list_Course.get( position ).getName() );
        holder.tvNgayMoHoc.setText( list_Course.get( position ).getDate() );
        int Tien = 0;
        try {
            Tien =  list_Course.get( position ).getMoney() ;
        } catch (Exception e){
            Log.d("e", e.toString());
        }
        DecimalFormat decimalFormat= (DecimalFormat) NumberFormat.getInstance( Locale.US);
        decimalFormat.applyPattern( "#,###,###,###" );
        final String formattedString = decimalFormat.format( Tien );

        holder.tvTien.setText(formattedString+" USD" );

        holder.tvCount.setText("(" +list_Course.get( position ).getLesson()+ " Buổi)" );
        holder.imgDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PT");
                myRef.orderByChild( "ID2" ).equalTo(list_Course.get( position ).getID()  ).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Delete");
                        builder.setMessage("Bạn có muôn xóa "+list_Course.get( position ).getName()+" ("+list_Course.get( position ).getLesson()+")"+ "khong?");
                        builder.setCancelable(true);
                        builder.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //Goi function Delete
                                        dao_course = new DAO_Course(context);
                                        dao_course.delete_Firebase( context,list_Course.get(position).getID()+"");
                                        list_Course.remove( position );
                                        //ds_khoanTC.clear();
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
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        } );
        holder.imgEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTextChangedListener();
                openDialog(context,position,course.getID(),course.getName(),course.getDate(), list_Course.get( position ).getMoney() ,course.getLesson() );
            }
        } );
    }
    public void openDialog(final Context context, int position, final String ID, final String name, final String date, final int money, final Integer lesson) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_edit_admin_course );
        edtNameCourse= bottomSheetDialog.findViewById( R.id.edtNameCourse );
        edtMoneyCourse  = bottomSheetDialog.findViewById( R.id.edtMoneyCourse );
        edtLesson  = bottomSheetDialog.findViewById( R.id.edtLesson);
        tvDateCourse   = bottomSheetDialog.findViewById( R.id.tvDateCourse);
        btnAdd  = bottomSheetDialog.findViewById( R.id.btnAdd );


        edtNameCourse.setText( name );
        edtMoneyCourse.setText( money+"" );
        edtLesson.setText( lesson+"" );
        tvDateCourse.setText( date );
        tvDateCourse.setEnabled( false );
        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dao_course= new DAO_Course( context );
                course = new Course( edtNameCourse.getText().toString(),tvDateCourse.getText().toString(),Integer.valueOf( edtMoneyCourse.getText().toString() ),Integer.valueOf( edtLesson.getText().toString() ) );
                dao_course.update_Firebase( ID,course );
                capNhat();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Course");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        list_Course.clear();
                        for(DataSnapshot datas: dataSnapshot.getChildren()){
                            String id = datas.getKey();
                            String date = datas.child("date").getValue().toString();
                            String name = datas.child("name").getValue().toString();
                            int money = Integer.parseInt(datas.child("money").getValue().toString());
                            int lesson = Integer.parseInt(datas.child("lesson").getValue().toString());
                            Course course = new Course(id,name,date,money,lesson);
                            course.setID( datas.getKey() );
                            list_Course.add( course );
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

                Toast.makeText( context, "Sửa thông tin thành công"+ID, Toast.LENGTH_SHORT ).show();
                bottomSheetDialog.dismiss();
            }
        } );
        Date today= new Date(  );
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime( today );

        final int dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        final int month= calendar.get(Calendar.MONTH);
        final int year= calendar.get(Calendar.YEAR);
        tvDateCourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog= new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "yyyy/MM/dd" );
                        calendar.set( i,i1,i2 );
                        tvDateCourse.setText( simpleDateFormat.format( calendar.getTime() ) );

                    }
                }, year,month,dayOfWeek);
                datePickerDialog.show();
            }
        } );
        bottomSheetDialog.show();
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
                edtMoneyCourse.removeTextChangedListener(this);

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
                    edtMoneyCourse.setText(formattedString);
                    edtMoneyCourse.setSelection(edtMoneyCourse.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtMoneyCourse.addTextChangedListener(this);
            }
        };
    }
    public void capNhat(){
        list_Course.add( course );
        adapter_admin_course= new Adapter_Admin_Course(list_Course, context);
        rcvADCourse.setAdapter(adapter_admin_course);
        adapter_admin_course.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list_Course.size();
    }

}
