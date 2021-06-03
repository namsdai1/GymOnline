package com.example.assignment_final.Dialog;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.assignment_final.Adapter.Adapter_Admin_PT;
import com.example.assignment_final.DAO.DAO_PT;
import com.example.assignment_final.R;
import com.example.assignment_final.model.PT;
import com.example.assignment_final.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.assignment_final.Activity.Activity_Admin_PT.rcvPTAdmin;


public class BottomSheet_Add_PT extends BottomSheetDialogFragment {
    private static final int GALLER_ACTION_PICK_CODE = 100;
    EditText edtTenPT,edtTien,edtMoTa;
    Adapter_Admin_PT adapter_admin_pt;
    TextView tvNgaySinh,tvErrorImg;
    Button btnUp;
    DAO_PT dao_pt;
    ArrayList<PT> list_PT = new ArrayList<>(  );
    public PT pt;
    CircleImageView imgUser;
    DatabaseReference mdata;

    public String id;
    public BottomSheet_Add_PT(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate( R.layout.bottom_sheet_edit_pt,container,false );
        edtTenPT= view.findViewById( R.id.edtTenPT );
        edtTien  = view.findViewById( R.id.edtTien );
        edtMoTa  = view.findViewById( R.id.edtMoTa);
        tvNgaySinh   = view.findViewById( R.id.tvNgaySinh);
        btnUp  = view.findViewById( R.id.btnAdd );
        imgUser= view.findViewById( R.id.imgUser );
        tvErrorImg  = view.findViewById( R.id.tvErrorImg );

        imgUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimePermission();
            }
        } );
        Bundle args = getArguments();
        id =args.getString("ID");
        String name = args.getString("name");
        String date = args.getString("date");
        String money = args.getString("money");
        String note = args.getString("note");
        String img = args.getString("img");

        edtTenPT.setText( name );
        edtTien.setText( money+"" );
        edtMoTa.setText( note+"" );
        tvNgaySinh.setText( date );

        dao_pt= new DAO_PT( getContext() );
        if(img!=null){
            Picasso.get().load(img).into( new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imgUser.setImageBitmap( bitmap );
                }
                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                }
                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {}
            });

        }else{
            imgUser.setImageResource( R.drawable.user );
        }

        btnUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgUser.getDrawable()!=null){
                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    final StorageReference storageReference = storage.getReferenceFromUrl("gs://duan1lt15304nhom1.appspot.com");
                    java.util.Calendar calendar = java.util.Calendar.getInstance();
                    StorageReference mountainsRef=storageReference.child("image"+calendar.getTimeInMillis()+".png");
                    imgUser.setDrawingCacheEnabled(true);
                    imgUser.buildDrawingCache();
                    final byte[] data =imageViewToByte(imgUser);

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText( getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                            // ...
                            Task<Uri> dowloadURl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            dowloadURl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    //createNewPost(imageUrl);
                                    dao_pt= new DAO_PT( getContext() );
                                    pt = new PT( );
                                    pt.setName( edtTenPT.getText().toString() );
                                    pt.setDate( tvNgaySinh.getText().toString() );
                                    pt.setMoney( Integer.valueOf( edtTien.getText().toString() ) );
                                    pt.setNote( edtMoTa.getText().toString() );
                                    pt.setImages2( imageUrl);

                                    dao_pt.update_Firebase(id,pt);
                                    capNhat();
                                    Toast.makeText( getContext(), "Sửa thông tin thành công"+id, Toast.LENGTH_SHORT ).show();
                                    dismiss();

                                }

                            });

                        }

                    });
                }else if(imgUser.getDrawable()==null){
                    tvErrorImg.setError( "Bạn chưa chọn hình." );
                }


            }
        } );


        Date today= new Date();
        final Calendar calendar=Calendar.getInstance();
        calendar.setTime( today );

        final int dayOfWeek= calendar.get(Calendar.DAY_OF_WEEK);
        final int month= calendar.get(Calendar.MONTH);
        final int year= calendar.get(Calendar.YEAR);
        tvNgaySinh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog= new DatePickerDialog( getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat( "dd/MM/yyyy" );
                        calendar.set( i,i1,i2 );
                        tvNgaySinh.setText( simpleDateFormat.format( calendar.getTime() ) );

                    }
                }, year,month,dayOfWeek);
                datePickerDialog.show();
            }
        });

        return view;
    }
    public void runTimePermission(){
        Dexter.withContext( getContext()).withPermission( Manifest.permission.READ_EXTERNAL_STORAGE).withListener( new PermissionListener() {
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

    //Convert Byte To BitMap
    public static Bitmap convertCompressedByteArrayToBitmap(byte[] src){
        return BitmapFactory.decodeByteArray(src, 0, src.length);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLER_ACTION_PICK_CODE){
                Uri imageUri = data.getData();
                imgUser.setImageURI(imageUri);
            }
        }
    }
    public void capNhat(){
        list_PT.add( pt );
        adapter_admin_pt= new Adapter_Admin_PT(list_PT, getContext());
        rcvPTAdmin.setAdapter(adapter_admin_pt);
        adapter_admin_pt.notifyDataSetChanged();
    }

}
