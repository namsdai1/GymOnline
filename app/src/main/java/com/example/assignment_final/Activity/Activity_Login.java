package com.example.assignment_final.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


;import com.example.assignment_final.DAO.DAO_User;
import com.example.assignment_final.Database.DbHelper;
import com.example.assignment_final.R;
import com.example.assignment_final.model.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Iterator;

public class Activity_Login extends AppCompatActivity {
    Button btnDangNhap,btnDangKy;
    EditText edtUser,edtPass;
    ImageView imgCall,imgLocation;
    TextView tvCall;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    public DAO_User userDAO;
    public DbHelper dbHelper;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        /* ull madng hinh */
        requestWindowFeature( Window.FEATURE_NO_TITLE);

        /* ẩn ActionBar */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView( R.layout.activity_login );
        btnDangNhap=findViewById( R.id.btnDangNhap );
        btnDangKy=findViewById( R.id.btnDangKy );
        edtUser=findViewById( R.id.edtUser );
        edtPass=findViewById( R.id.edtPass );
        imgCall= findViewById( R.id.imgCall );
        imgLocation= findViewById( R.id.imgLocation );
        tvCall= findViewById( R.id.tvCall );
        saveLoginCheckBox = (CheckBox)findViewById(R.id.saveLoginCheckBox);
        userDAO = new DAO_User( this );

        loginPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtUser.setText(loginPreferences.getString("username", ""));
            edtPass.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }
        final int[] count = {0};

        btnDangNhap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = edtUser.getText().toString();
                final String password = edtPass.getText().toString();

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("User");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            Iterable<DataSnapshot> snapshotsIterator = dataSnapshot.getChildren();
                            Iterator<DataSnapshot> iterator = snapshotsIterator.iterator();
                            while (iterator.hasNext()) {
                                DataSnapshot next = iterator.next();
                                User user = next.getValue( User.class );
                                if (edtUser.getText().toString().equalsIgnoreCase( user.getUserName() ) &&
                                        edtPass.getText().toString().equalsIgnoreCase( user.getPassword() ) &&
                                        !edtUser.getText().toString().equalsIgnoreCase( "Admin" )) {
                                    Intent intentManHinhKH = new Intent( Activity_Login.this, Activity_User.class );
                                    dbHelper.PassUser = user.getPassword();
                                    dbHelper.Username = user.getUserName();
                                    user.setIDUser(next.getKey());
                                    dbHelper.IDuser=next.getKey();
                                    count[0]++;
                                    if (saveLoginCheckBox.isChecked()) {
                                        loginPrefsEditor.putBoolean( "saveLogin", true );
                                        loginPrefsEditor.putString( "username", username );
                                        loginPrefsEditor.putString( "password", password );
                                        loginPrefsEditor.commit();
                                    } else {
                                        loginPrefsEditor.clear();
                                        loginPrefsEditor.commit();
                                    }
                                    startActivity( intentManHinhKH );
                                    overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
                                }else if (edtUser.getText().toString().equalsIgnoreCase( "Admin" ) &&
                                        edtPass.getText().toString().equals( user.getPassword() )) {
                                    Intent intentManHinhAdmin = new Intent( getBaseContext(), Activity_Admin.class );
                                    dbHelper.PassUser = user.getPassword();
                                    dbHelper.Username = "Admin";
                                    user.setIDUser(next.getKey());
                                    dbHelper.IDuser=next.getKey();
                                    count[0]++;
                                    if (saveLoginCheckBox.isChecked()) {
                                        loginPrefsEditor.putBoolean("saveLogin", true);
                                        loginPrefsEditor.putString("username", username);
                                        loginPrefsEditor.putString("password", password);
                                        loginPrefsEditor.commit();
                                    } else {
                                        loginPrefsEditor.clear();
                                        loginPrefsEditor.commit();
                                    }
                                    startActivity( intentManHinhAdmin );
                                    overridePendingTransition( android.R.anim.fade_in,android.R.anim.fade_out);
                                }
                            }
                            if(count[0]==0) {
                                edtUser.setError( "Bạn đã nhập sai thông tin User Name hoặc Password" );
                                edtPass.setError( "Bạn đã nhập sai thông tin User Name hoặc Password" );
                            }else{
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("User");
                                mDatabase.orderByChild("userName").equalTo(dbHelper.Username).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                            dbHelper.ID = childSnapshot.getKey();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

            }
        } );
        btnDangKy.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentActivitySignin = new Intent(getBaseContext(), Activity_Signin.class);
                startActivity( intentActivitySignin );
                overridePendingTransition( android.R.anim.slide_in_left,android.R.anim.fade_out);
            }
        } );
        imgCall.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        } );
        imgLocation.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLocation = new Intent( Activity_Login.this, Activity_Location.class);
                startActivity( intentLocation );
            }
        } );
    }
    private void makePhoneCall() {
        String number = tvCall.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(Activity_Login.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Activity_Login.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        }else {
            Toast.makeText(Activity_Login.this, "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(Activity_Login.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
