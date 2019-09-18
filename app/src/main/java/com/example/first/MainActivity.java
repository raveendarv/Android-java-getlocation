package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE =123;
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    LocationManager mlocationManager;
    LocationListener mlocationListener;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Test", "onResume() Called");
        Log.d("Test", "Getting Current Location");
        getCurrentLocation();
    }

    private void getCurrentLocation() {
        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                TextView TextView1 = findViewById(R.id.textView2);
                TextView TextView2 = findViewById(R.id.textView4);
                Log.d("Test", "onLocationChanged() Callback Received");
                String Longitude =String.valueOf(location.getLongitude());
                String Latitude =String.valueOf(location.getLatitude());

                Log.d("Test", "Longitude :"+Longitude);
                TextView1.setText("Longitude : "+Longitude);
                Log.d("Test", "Latitude :"+Latitude);
                TextView2.setText("Latitude : "+Latitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.d("Test", "onProviderDisabled() Callback Received");
            }
        };

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mlocationManager.requestLocationUpdates(LOCATION_PROVIDER, 5000, 1000, mlocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Test","onRequestPermissionsResult() PERMISSION_GRANTED");
                getCurrentLocation();
            }else Log.d("Test","onRequestPermissionsResult() PERMISSION_DENIED");
        }
    }
}
