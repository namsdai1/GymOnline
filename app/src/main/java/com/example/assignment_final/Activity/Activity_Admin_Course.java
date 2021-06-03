package com.example.assignment_final.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Admin_Course;
import com.example.assignment_final.DAO.DAO_Course;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.Schedule;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import java.util.Locale;
import java.util.Random;


public class Activity_Admin_Course extends AppCompatActivity {
    EditText edtNameCourse,edtMoneyCourse,edtLesson;
    TextView tvDateCourse,tvNewItem;
    Button btnAdd;
    DAO_Course dao_course;

    Course course;
    Adapter_Admin_Course adapter_admin_course;
    public static FloatingActionButton flADCourse;
    public static RecyclerView rcvADCourse;
    public Adapter_Admin_Course course_adapter;
    public ArrayList<Course> list_Course;

    DAO_Schedule dao_schedule;
    String id;
    String dateSchedule;
    Schedule schedule;
    public int hourStart;
    public int hourEnd;
    int countButton=0;
    public String hour;
    public Random random = new Random();
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        setContentView( R.layout.activity_admin_course );
        flADCourse=findViewById( R.id.flADCourse );


        flADCourse.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog( Activity_Admin_Course.this );
            }
        } );
        rcvADCourse= findViewById( R.id.rcvADCourse );
        //recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( Activity_Admin_Course.this);
        rcvADCourse.setLayoutManager(layoutManager);

        list_Course= new ArrayList<Course>();

        myRef = FirebaseDatabase.getInstance().getReference("Course");
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
                    list_Course.add( course );

                }
                if(countButton!=0){
                    String date=list_Course.get( list_Course.size()-1 ).getDate();
                    int day= Integer.parseInt( date .substring( 8 ) );
                    int month= Integer.parseInt( date .substring( 5,7 ) );
                    int year= Integer.parseInt( date .substring( 0,4 ) );

                    id= list_Course.get( list_Course.size()-1 ).getID();

                    hourStart=7+random.nextInt(8);
                    hourEnd=hourStart+2;
                    hour = hourStart+"h - "+hourEnd+"h";


                    for (int j = 0; j < list_Course.get( list_Course.size()-1 ).getLesson(); j++) {
                        if (day > 30) {
                            month++;
                            day = day - 30;
                            if (month > 12) {
                                year++;
                                month = month - 12;
                            }
                        }
                        if (day < 10 && month < 10) {
                            dateSchedule = year + "/0" + month + "/0" + day + "\n";
                        } else if (day < 10 && month > 10){
                            dateSchedule = year + "/" + month + "/0" + day + "\n";
                        } else if (day > 10 && month < 10) {
                            dateSchedule = year + "/0" + month + "/" + day + "\n";
                        } else {
                            dateSchedule = year + "/" + month + "/" + day + "\n";
                        }

                        schedule = new Schedule( dateSchedule, id,hour );
                        dao_schedule.insert_Firebase( schedule );
                        day += 2;
                    }
                }
                course_adapter= new Adapter_Admin_Course( list_Course, Activity_Admin_Course.this );
                rcvADCourse.setAdapter( course_adapter );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    public void openDialog(Context context) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_edit_admin_course );
        edtNameCourse= bottomSheetDialog.findViewById( R.id.edtNameCourse );
        edtMoneyCourse  = bottomSheetDialog.findViewById( R.id.edtMoneyCourse );
        edtLesson  = bottomSheetDialog.findViewById( R.id.edtLesson);
        tvDateCourse   = bottomSheetDialog.findViewById( R.id.tvDateCourse);
        btnAdd  = bottomSheetDialog.findViewById( R.id.btnAdd );
        tvNewItem= bottomSheetDialog.findViewById( R.id.tvNewItem );
        tvNewItem.setText( "ADD COURSE" );
        btnAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    countButton++;
                    String nameCourse=edtNameCourse.getText().toString();
                    int moneyCourse = Integer.parseInt( edtMoneyCourse.getText().toString() );
                    int lesson = Integer.parseInt( edtLesson.getText().toString() );
                    final String dateCourse= tvDateCourse.getText().toString();
                    dao_course= new DAO_Course( Activity_Admin_Course.this );
                    course = new Course( nameCourse,dateCourse, moneyCourse,lesson);
                    dao_course.insert_Firebase(course );
                    capNhat();
                    bottomSheetDialog.dismiss();

                    Toast.makeText( Activity_Admin_Course.this, "Thêm thông tin thành công", Toast.LENGTH_SHORT ).show();}
                catch (NullPointerException e){
                    Toast.makeText( Activity_Admin_Course.this, "Bạn chưa nhập đủ thông tin!!!", Toast.LENGTH_SHORT ).show();
                }catch (NumberFormatException e){
                    Toast.makeText( Activity_Admin_Course.this, "Bạn chưa nhập đủ thông tin!!!", Toast.LENGTH_SHORT ).show();
                }
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

                DatePickerDialog datePickerDialog= new DatePickerDialog( Activity_Admin_Course.this, new DatePickerDialog.OnDateSetListener() {
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
        adapter_admin_course= new Adapter_Admin_Course(list_Course, Activity_Admin_Course.this);
        rcvADCourse.setAdapter(adapter_admin_course);
        adapter_admin_course.notifyDataSetChanged();
    }

}
