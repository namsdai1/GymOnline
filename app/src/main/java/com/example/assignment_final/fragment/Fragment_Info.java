package com.example.assignment_final.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Info extends Fragment {
    public DAO_User userDAO;
    private static final int GALLER_ACTION_PICK_CODE = 100;
    TextView tvName,tvPhoneNumber,tvAddress,tvUser,tvErrorImg;
    EditText edtFullName,edtAddress,edtPhoneNumber,edtUser;
    CircleImageView imgUser,imgUserBt;
    DbHelper dbHelper;
    User user;
    Button btnChangeIf,btnChange;
    DatabaseReference mdata;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_info, container, false );
        userDAO = new DAO_User( getContext() );
        tvName = view.findViewById( R.id.tvName );
        tvPhoneNumber = view.findViewById( R.id.tvPhoneNumber );
        tvAddress = view.findViewById( R.id.tvAddress );
        tvUser = view.findViewById( R.id.tvUser );
        btnChangeIf= view.findViewById( R.id.btnChangeIf );
        imgUser= view.findViewById( R.id.imgUser );
        tvErrorImg= view.findViewById( R.id.tvErrorImg );

        tvUser.setText( dbHelper.Username );

        setData();
        btnChangeIf.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog( getContext() );
            }
        } );
        return view;
    }
    public void openDialog(final Context context){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog( context );
        bottomSheetDialog.setContentView( R.layout.bottom_sheet_edit_inf );
        final String userName= dbHelper.Username;
        btnChange = bottomSheetDialog.findViewById( R.id.btnChange );
        edtFullName = bottomSheetDialog.findViewById( R.id.edtFullName );
        edtAddress = bottomSheetDialog.findViewById( R.id.edtAddress );
        edtPhoneNumber = bottomSheetDialog.findViewById( R.id.edtPhoneNumber );
        edtUser= bottomSheetDialog.findViewById( R.id.edtUser );
        imgUserBt=bottomSheetDialog.findViewById( R.id.imgUser );

        imgUserBt.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimePermission();
            }
        } );

        edtPhoneNumber.setText( tvPhoneNumber.getText());
        edtUser.setText( tvUser.getText() );
        edtFullName.setText( tvName.getText());
        edtAddress.setText( tvAddress.getText() );
        imgUserBt.setImageDrawable(imgUser.getDrawable()  );

        btnChange.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullName =edtFullName.getText().toString();
                final String adress = edtAddress.getText().toString();
                final String phoneNumber = edtPhoneNumber.getText().toString();

                if(phoneNumber.length()==10 && imgUserBt.getDrawable()!=null) {

                    FirebaseStorage storage= FirebaseStorage.getInstance();
                    final StorageReference storageReference = storage.getReferenceFromUrl("gs://duan1lt15304nhom1.appspot.com");
                    Calendar calendar = Calendar.getInstance();
                    StorageReference mountainsRef=storageReference.child("image"+calendar.getTimeInMillis()+".png");
                    imgUserBt.setDrawingCacheEnabled(true);
                    imgUserBt.buildDrawingCache();
                    final byte[] data =imageViewToByte(imgUserBt);

                    UploadTask uploadTask = mountainsRef.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText( context, "Fail", Toast.LENGTH_SHORT).show();
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
                                    user = new User( );
                                    user.setUserName( userName );
                                    user.setFullName( fullName );
                                    user.setAddress( adress );
                                    user.setPhoneNumber( phoneNumber );
                                    user.setImages( imageUrl);
                                    user.setPassword( dbHelper.PassUser );

                                    mdata= FirebaseDatabase.getInstance().getReference();
                                    mdata.child( "User" ).child( dbHelper.ID ).setValue( user );
                                    Toast.makeText( getContext(), "Đổi thông tin thành công", Toast.LENGTH_SHORT ).show();
                                    bottomSheetDialog.dismiss();
                                    setData();

                                }

                            });

                        }

                    });

                }else if(phoneNumber.length()!=10 || imgUserBt.getDrawable()==null){
                    if(phoneNumber.length()!=10){
                        edtPhoneNumber.setError( "Số điện thoại chỉ có 10 số, hãy kiểm tra lại." );
                    }
                    if(imgUserBt.getDrawable()==null){
                        tvErrorImg.setError( "Bạn chưa chọn hình." );
                    }
                }
            }
        } );
        bottomSheetDialog.show();
    }
    public void setData(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
        myRef.orderByKey().equalTo( dbHelper.ID ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Iterable<DataSnapshot> snapshotsIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotsIterator.iterator();
                    while (iterator.hasNext()) {
                        DataSnapshot next = iterator.next();
                        User user = next.getValue( User.class );
                        tvName.setText( user.getFullName());
                        tvPhoneNumber.setText( user.getPhoneNumber());
                        tvAddress.setText( user.getAddress() );
                        if(user.getImages()!=null){
                            String imgView= user.getImages();
                            Picasso.get().load(imgView).into( new Target() {
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

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );


    }
    public  void runTimePermission(){
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
                imgUserBt.setImageURI(imageUri);
            }
        }
    }
}
