package com.example.assignment_final.Activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;


import com.example.assignment_final.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;

public class Activity_Location extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_location );
        Toolbar myToolbar = (Toolbar)findViewById( R.id.my_toolbar );
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowHomeEnabled( true );

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(this, "xx", Toast.LENGTH_SHORT).show();
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "xin duoc quyen roi", Toast.LENGTH_SHORT).show();
            xulyQuyen();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        UiSettings uisetting=map.getUiSettings();
        uisetting.setCompassEnabled(true);

        uisetting.setZoomControlsEnabled(true);
        uisetting.setScrollGesturesEnabled(true);
        uisetting.setTiltGesturesEnabled(true);
        uisetting.setMyLocationButtonEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {//bang M
            if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
            {
                xulyQuyen();
                Toast.makeText(this, "e", Toast.LENGTH_SHORT).show();
            } else
            {
                ActivityCompat.requestPermissions(Activity_Location.this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
                Toast.makeText(this, "d", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            xulyQuyen();
            Toast.makeText(this, "f", Toast.LENGTH_SHORT).show();
        }
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    public void xulyQuyen() {
        //map.setMyLocationEnabled(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.normal:
                map.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.hybrid:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.satellite:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.terrain:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_map, menu);
        return true;
    }
}
