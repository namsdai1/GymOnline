package com.example.assignment_final.Dialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.assignment_final.Adapter.Adapter_Notification;
import com.example.assignment_final.Adapter.Adapter_TOP;
import com.example.assignment_final.DAO.DAO_Discount;
import com.example.assignment_final.DAO.DAO_HoaDon;
import com.example.assignment_final.DAO.DAO_PT;
import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.Course;
import com.example.assignment_final.model.Discount;
import com.example.assignment_final.model.HoaDonChiTiet;
import com.example.assignment_final.model.PT;
import com.example.assignment_final.model.Schedule;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
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
import java.util.Date;
import java.util.Locale;
import java.util.Random;


import static com.example.assignment_final.fragment.Fragment_Activity_Home_User.rcvTOPActivityUser;


public class BottomSheet_Add_Course_KH extends BottomSheetDialogFragment {
    TextView tvTenKhoaHoc,tvNgayMoKhoa,tvCount,tvNotifi,tvCodeDiscount;
    Spinner spPT;
    Button btnUp,btnEnter;
    EditText tvTien;
    ArrayList<PT> list_PT;
    DAO_PT dao_pt;

    DAO_HoaDon dao_hoaDon;
    HoaDonChiTiet HoaDonChiTiet;
    DbHelper dbHelper;

    public String IDPT_Add;
    public int rate_Add;
    DatabaseReference myRef;
    public ArrayList<String> list_Name= new ArrayList<>(  );
    public ArrayList<Schedule> list_Schedule = new ArrayList<>(  );
    public String idCourse;
    ArrayList<Discount> list_Discount;
    DAO_Discount dao_discount;
    public SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );
    public String date = df.format( java.util.Calendar.getInstance().getTime() );
    public BottomSheet_Add_Course_KH(){
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate( R.layout.bottom_sheet_edit_user_course,container,false );
        tvTenKhoaHoc  = view.findViewById( R.id.tvTenKhoaHocBot  );
        tvTien  = view.findViewById( R.id.tvTienBot );
        tvNgayMoKhoa  = view.findViewById( R.id.tvNgayMoKhoaBot );
        tvCount  = view.findViewById( R.id.tvCountBot );
        btnUp  = view.findViewById( R.id.btnAdd );
        spPT  = view.findViewById( R.id.spPTBot );
        tvNotifi=view.findViewById( R.id.tvNotifi ) ;
        tvCodeDiscount=view.findViewById( R.id.tvCodeDiscount ) ;
        btnEnter=view.findViewById( R.id.btnEnter ) ;

        Bundle bundle =getArguments();
        idCourse =  bundle.getString( "ID" );
        final String TenKhoaHoc = bundle.getString( "TenKhoaHoc" );
        final int Tien = bundle.getInt( "Tien" );
        final String NgayMoKhoa = bundle.getString( "NgayMoHoc" );
        final int Lesson = bundle.getInt( "lesson" );

        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>( getContext(),android.R.layout.simple_spinner_item,list_Name );
        final DecimalFormat decimalFormat= (DecimalFormat) NumberFormat.getInstance( Locale.US);
        decimalFormat.applyPattern( "#,###,###,###" );
        final String[] formattedString = {decimalFormat.format( Tien )};

        final int[] Money = new int[1];
        tvTenKhoaHoc.setText( TenKhoaHoc);
        tvTien.setText( formattedString[0] +" USD");
        tvTien.addTextChangedListener( onTextChangedListener() );
        tvNgayMoKhoa.setText( NgayMoKhoa);
        tvCount.setText( Lesson +"" );


        list_PT = new ArrayList<>(  );
        dao_pt = new DAO_PT( getContext() );

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "PT" ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_PT.clear();
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    PT pt = datas.getValue( PT.class );
                    pt.setID2( datas.getKey() );

                    list_PT.add( pt );
                    String name = datas.child("name").getValue().toString();
                    list_Name.add( name );
                }

                arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spPT.setAdapter( arrayAdapter );
                spPT.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        for( int j=0;j<list_PT.size();j++){
                            if(adapterView.getItemAtPosition( i ).toString().equalsIgnoreCase( list_PT.get( j ).getName())){
                                Money[0] = Tien + list_PT.get( j ).getMoney() ;
                                formattedString[0] =decimalFormat.format( Money[0] );
                                tvTien.setText(formattedString[0] +" USD"  );
                                IDPT_Add=list_PT.get( j ).getID2();
                                rate_Add=list_PT.get( j ).getRate();
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                } );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dbHelper = new DbHelper( getContext() );
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child( "Schedule" ).orderByChild( "courseID" ).equalTo( idCourse ).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date today= new Date(  );
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "yyyy/MM/dd" );
                String dateToday = simpleDateFormat.format( today );

                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    Schedule schedule = new Schedule();
                    schedule.setScheduleID( datas.getKey() );
                    schedule.setDateSchedule( datas.child("dateSchedule").getValue().toString() );
                    schedule.setHourSchedule( datas.child("hourSchedule").getValue().toString() );

                    list_Schedule.add( schedule );
                }
                if(list_Schedule.size()>0) {
                    if (list_Schedule.get( 0 ).getDateSchedule().compareTo( dateToday ) < 0) {
                        btnUp.setEnabled( false );
                        btnUp.setVisibility( View.GONE );
                        tvNotifi.setTextColor( Color.parseColor( "#0000FF" ) );
                        tvNotifi.setText( "*********XIN LỖI! KHÓA HỌC ĐÃ ĐÓNG!********" );
                        @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation( getActivity(), R.anim.animal );
                        tvNotifi.startAnimation( animation );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        list_Discount= new ArrayList<>(  );
        btnEnter.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvCodeDiscount.getText().toString()!=""){
                    DatabaseReference mdata = FirebaseDatabase.getInstance().getReference("Discount");
                    mdata.orderByChild( "typeDiscount" ).equalTo( "Course" ).addListenerForSingleValueEvent(new ValueEventListener() {
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
                                    dao_discount.delete_Discount_Firebase(getContext(),datas.getKey());
                                    Toast.makeText( getContext(), "Mã không được áp dụng", Toast.LENGTH_SHORT ).show();
                                }else {
                                    if(tvCodeDiscount.getText().toString().equalsIgnoreCase( datas.child("maKhuyenmmai").getValue().toString() )){
                                        Money[0]=(Money[0]*(100-Integer.valueOf(datas.child("giamgia").getValue().toString()  )))/100;
                                        formattedString[0] =decimalFormat.format( Money[0] );
                                        tvTien.setText(formattedString[0] +" USD"  );
                                        Toast.makeText( getContext(), "Mã đã được áp dụng", Toast.LENGTH_SHORT ).show();
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
        } );

        btnUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int countRate=rate_Add+1;
                myRef.child( "PT" ).child( IDPT_Add ).child( "rate" ).setValue(countRate );
                SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                String date = df.format( Calendar.getInstance().getTime());
                dao_hoaDon= new DAO_HoaDon( getContext() );
                HoaDonChiTiet = new HoaDonChiTiet("0",Lesson,IDPT_Add,idCourse,dbHelper.IDuser ,Money[0],dbHelper.Username,spPT.getSelectedItem().toString(),TenKhoaHoc,date,"Đã thanh toán",NgayMoKhoa );
                dao_hoaDon.insert_Course_Firebase( HoaDonChiTiet );
                Toast.makeText( getContext(), "Đăng ký thành công ", Toast.LENGTH_SHORT ).show();
                dismiss();
            }
        } );
        return view;
    }

    //
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

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    tvTien.setText(formattedString);
                    tvTien.setSelection(tvTien.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                tvTien.addTextChangedListener(this);
            }
        };
    }

}
