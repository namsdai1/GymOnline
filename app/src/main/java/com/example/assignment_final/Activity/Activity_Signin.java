package com.example.assignment_final.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;

import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class Activity_Signin extends AppCompatActivity {
    private static final int GALLER_ACTION_PICK_CODE = 100;
    private final int PICK_IMAGE_REQUEST = 71;
    EditText edtUser,edtPass,edtFullName,edtAddress,edtPhoneNumber;
    Button btnDangKy;
    DAO_User userDAO;
    User user;
    TextView tvErrorImg;
    DatabaseReference mdata;
    CircleImageView imageUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        edtUser= findViewById( R.id.edtUser );
        edtPass= findViewById( R.id.edtPass );
        edtFullName= findViewById( R.id.edtFullName );
        edtAddress= findViewById( R.id.edtAddress );
        edtPhoneNumber= findViewById( R.id.edtPhoneNumber );
        imageUser= findViewById( R.id.imgUser );
        btnDangKy=findViewById( R.id.btnDangKy );
        tvErrorImg=findViewById( R.id.tvErrorImg );
        imageUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runTimePermission();
            }
        });
        btnDangKy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] count = {0};
                userDAO = new DAO_User( Activity_Signin.this );
                final String User = edtUser.getText().toString();
                final String Pass = edtPass.getText().toString();
                final String FullName = edtFullName.getText().toString();
                final String Adress = edtAddress.getText().toString();
                final String PhoneNumber = edtPhoneNumber.getText().toString();

                if (!User.equals( "" ) && !Pass.equals( "" ) && !FullName.equals( "" ) && !Adress.equals( "" ) && !PhoneNumber.equals( "" ) && PhoneNumber.length()==10 && imageUser.getDrawable()!=null){
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference( "User" );
                    mDatabase.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                if(edtUser.getText().toString().equalsIgnoreCase( childSnapshot.child( "userName" ).getValue().toString())){
                                    count[0]++;
                                }
                            }
                            if(count[0] ==0) {

                                mdata= FirebaseDatabase.getInstance().getReference();
                                FirebaseStorage storage=FirebaseStorage.getInstance();
                                final StorageReference storageReference = storage.getReferenceFromUrl("gs://duan1lt15304nhom1.appspot.com");
                                Calendar calendar = Calendar.getInstance();
                                StorageReference mountainsRef=storageReference.child("image"+calendar.getTimeInMillis()+".png");
                                imageUser.setDrawingCacheEnabled(true);
                                imageUser.buildDrawingCache();
                                byte[] data =imageViewToByte(imageUser);

                                UploadTask uploadTask = mountainsRef.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(Activity_Signin.this, "Loi", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                        // ...
                                        Task<Uri> dowloadURl=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        Toast.makeText(Activity_Signin.this, "Thanh cong ", Toast.LENGTH_SHORT).show();
                                        dowloadURl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl = uri.toString();
                                                //createNewPost(imageUrl);
                                                user = new User();
                                                user.setUserName( User );
                                                user.setPassword( Pass );
                                                user.setFullName( FullName );
                                                user.setAddress( Adress );
                                                user.setPhoneNumber( PhoneNumber );
                                                user.setImages( imageUrl);


                                                userDAO.insert_Firebase(Activity_Signin.this, user);
                                                Toast.makeText( Activity_Signin.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                                                Intent intentManHinhLogin = new Intent( Activity_Signin.this, Activity_Login.class );
                                                startActivity( intentManHinhLogin );
                                                overridePendingTransition( android.R.anim.slide_out_right,android.R.anim.fade_out);
                                            }

                                        });

                                    }

                                });


                            }else if(count[0] !=0){
                                edtUser.setError( "User đã tồn tại, bạn hãy chọn tên mới" );
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    } );
                }else if(User.equals( "" ) || Pass.equals( "" ) || FullName.equals( "" ) || Adress.equals( "" ) || PhoneNumber.equals( "" )){
                    if(User.equals( "" )){
                        edtUser.setError( "Bạn chưa nhập User Name" );
                    }
                    if (Pass.equals( "" )){
                        edtPass.setError( "Bạn chưa nhập Password" );
                    }
                    if (FullName.equals( "" )){
                        edtFullName.setError( "Bạn chưa nhập Full Name" );
                    }
                    if (Adress.equals( "" )){
                        edtAddress.setError( "Bạn chưa nhập Address" );
                    }
                    if (PhoneNumber.equals( "" )){
                        edtPhoneNumber.setError( "Bạn chưa nhập Phone Number" );
                    }
                    if(imageUser.getDrawable()==null){
                        tvErrorImg.setError( "Bạn chưa chọn ảnh" );
                    }
                }else if(PhoneNumber.length()!=10){
                    edtPhoneNumber.setError( "Số điện thoại chỉ có 10 số" );
                }
            }

        } );

    }
    public  void runTimePermission(){
        Dexter.withContext(Activity_Signin.this).withPermission( Manifest.permission.READ_EXTERNAL_STORAGE).withListener( new PermissionListener() {
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
        startActivityForResult(i,PICK_IMAGE_REQUEST);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == PICK_IMAGE_REQUEST){
                Uri imageUri = data.getData();
                imageUser.setImageURI(imageUri);
            }
        }
    }
}
