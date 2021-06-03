package com.example.assignment_final.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_final.Adapter.Adapter_Admin_PT;
import com.example.assignment_final.DAO.DAO_PT;

import com.example.assignment_final.DAO.DAO_Schedule;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;

import com.example.assignment_final.model.PT;


import com.example.assignment_final.model.Schedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Admin_PT extends AppCompatActivity {
    private static final int GALLER_ACTION_PICK_CODE = 100;
    public static FloatingActionButton flPT;
    public static RecyclerView rcvPTAdmin;
    public Adapter_Admin_PT pt_adapter;
    public ArrayList<PT> list_PT;
    CircleImageView imageUser;
    EditText edtTenPT,edtTien,edtMoTa;
    TextView tvNgaySinh,tvNewItem;
    Button btnUp;
    DatabaseReference mdata;
    PT pt;
    DAO_PT dao_pt;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_pt );
        flPT=findViewById( R.id.flPT );

        if(dbHelper.Username.equalsIgnoreCase( "Admin" )){
            flPT.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialog( Activity_Admin_PT.this );
                }
            } );
        }else{
            flPT.setVisibility( View.GONE );
        }


        rcvPTAdmin= findViewById( R.id.rcvPTAdmin );
        list_PT= new ArrayList<>(  );


        rcvPTAdmin.setLayoutManager( new LinearLayoutManager( this ) );

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("PT");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_PT.clear();
                if (dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotsIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotsIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = iterator.next();
                        PT pt = next.getValue( PT.class );
                        pt.setID2( next.getKey() );
                        list_PT.add( pt );
                        Collections.sort(list_PT, new Comparator<PT>() {
                            @Override
                            public int compare(PT pt1, PT pt2) {
                                return pt1.getName().compareTo(pt2.getName());
                            }
                        });
                        pt_adapter= new Adapter_Admin_PT( list_PT,Activity_Admin_PT.this );
                        rcvPTAdmin.setAdapter( pt_adapter );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    public void openDialog(final Context context){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_edit_pt );
        edtTenPT= bottomSheetDialog.findViewById( R.id.edtTenPT );
        edtTien  = bottomSheetDialog.findViewById( R.id.edtTien );
        edtMoTa  = bottomSheetDialog.findViewById( R.id.edtMoTa);
        tvNgaySinh   = bottomSheetDialog.findViewById( R.id.tvNgaySinh);
        btnUp  = bottomSheetDialog.findViewById( R.id.btnAdd );
        tvNewItem=bottomSheetDialog.findViewById( R.id.tvNewItem );
        imageUser = bottomSheetDialog.findViewById( R.id.imgUser );
        tvNewItem.setText( "THÊM PT" );

        imageUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimePermission();
            }
        });

        Date today= new Date(  );
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime( today );

        final int dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        final int month= calendar.get(Calendar.MONTH);
        final int year= calendar.get(Calendar.YEAR);
        tvNgaySinh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog= new DatePickerDialog( context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "yyyy/MM/dd" );
                        calendar.set( i,i1,i2 );
                        tvNgaySinh.setText( simpleDateFormat.format( calendar.getTime() ) );

                    }
                }, year,month,dayOfWeek);
                datePickerDialog.show();
            }
        } );

        bottomSheetDialog.show();

        btnUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference( "User" );
                mDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            mdata= FirebaseDatabase.getInstance().getReference();
                            FirebaseStorage storage=FirebaseStorage.getInstance();
                            final StorageReference storageReference = storage.getReferenceFromUrl("gs://duan1lt15304nhom1.appspot.com");
                            java.util.Calendar calendar = java.util.Calendar.getInstance();
                            StorageReference mountainsRef=storageReference.child("image"+calendar.getTimeInMillis()+".png");
                            imageUser.setDrawingCacheEnabled(true);
                            imageUser.buildDrawingCache();
                            byte[] data =imageViewToByte(imageUser);

                            UploadTask uploadTask = mountainsRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(Activity_Admin_PT.this, "Loi", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    // ...
                                    Task<Uri> dowloadURl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                    Toast.makeText(Activity_Admin_PT.this, "Thanh cong ", Toast.LENGTH_SHORT).show();
                                    dowloadURl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageUrl = uri.toString();
                                            //createNewPost(imageUrl);

                                            pt = new PT();

                                            pt.setName( edtTenPT.getText().toString() );
                                            pt.setMoney( Integer.valueOf( edtTien.getText().toString() ) );
                                            pt.setNote( edtMoTa.getText().toString() );
                                            pt.setDate( tvNgaySinh.getText().toString() );
                                            pt.setImages2( imageUrl);
                                            dao_pt = new DAO_PT( context );
                                            dao_pt.insert_Firebase(context, pt);
                                            capNhat();

                                            Toast.makeText( context, "Thêm PT thành công!", Toast.LENGTH_SHORT ).show();
                                            bottomSheetDialog.dismiss();
                                        }

                                    });

                                }

                            });




                        }catch (Exception e){
                            Toast.makeText( Activity_Admin_PT.this, "Bạn chưa nhập đủ thông tin!!!", Toast.LENGTH_SHORT ).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        } );
    }
    public void capNhat(){

        list_PT.add( pt );
        pt_adapter= new Adapter_Admin_PT( list_PT,Activity_Admin_PT.this );
        rcvPTAdmin.setAdapter( pt_adapter );
        pt_adapter.notifyDataSetChanged();

    }

    public void runTimePermission(){
        Dexter.withContext(Activity_Admin_PT.this).withPermission( Manifest.permission.READ_EXTERNAL_STORAGE).withListener( new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                galleryIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    //Pick Image From Gallery
    private void galleryIntent() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,GALLER_ACTION_PICK_CODE);
    }
    //Convert Bitmap To Byte
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap= getResizedBitmap( bitmap,1024 );
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float bitmapRatio = (float) width / height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLER_ACTION_PICK_CODE){
                Uri imageUri = data.getData();
                imageUser.setImageURI(imageUri);
            }
        }
    }

}
