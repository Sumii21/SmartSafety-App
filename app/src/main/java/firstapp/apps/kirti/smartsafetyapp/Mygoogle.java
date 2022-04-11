package firstapp.apps.kirti.smartsafetyapp;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

public class Mygoogle extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mymanager;
    LatLng mylLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygoogle);

        View v = this.findViewById(R.id.map);
        myconnection con = new myconnection(Mygoogle.this);
        final Boolean mycon = con.isnetconneted();

        if (mycon) {
            Snackbar s = Snackbar.make(v, " connected", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).setActionTextColor(Color.GREEN);
            View view = s.getView();
            TextView t = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            view.setBackgroundColor(Color.MAGENTA);
            t.setTextColor(Color.YELLOW);
            s.show();
        } else {


            Snackbar s = Snackbar.make(v, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mycon) {
                                Snackbar s = Snackbar.make(v, " connected", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Dismiss", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        }).setActionTextColor(Color.GREEN);
                                View view = s.getView();
                                TextView t = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                view.setBackgroundColor(Color.MAGENTA);
                                t.setTextColor(Color.YELLOW);
                                s.show();
                            } else {


                                Snackbar s = Snackbar.make(v, "No internet connection", Snackbar.LENGTH_INDEFINITE)
                                        .setAction("Retry", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (mycon) {
                                                    Toast.makeText(Mygoogle.this, "connected", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).setActionTextColor(Color.GREEN);
                                View view = s.getView();
                                TextView t = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                                view.setBackgroundColor(Color.MAGENTA);
                                t.setTextColor(Color.YELLOW);
                                s.show();

                            }
                        }
                    }).setActionTextColor(Color.GREEN);
            View view = s.getView();
            TextView t = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            view.setBackgroundColor(Color.MAGENTA);
            t.setTextColor(Color.YELLOW);
            s.show();
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mymanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        if(checkconnection()==true)
        {
            Location lastLocation = mymanager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(lastLocation!=null)
            {
                double lat=lastLocation.getLatitude();
                double longi=lastLocation.getLongitude();
                mylLatLng=new LatLng(lat,longi);
            }}
        else
        {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("PLEASE TURN ON GPS AND INTERNET");
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 1);
                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1)
        {
            if(resultCode==0)
            {
                updateplaces();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        updateplaces();
        // Add a marker in Sydney and move the camera


    }
    void updateplaces()
    {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        if(mylLatLng!=null) {
            mMap.addMarker(new MarkerOptions().position(mylLatLng).title("My home").snippet("guru nanak pura"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylLatLng, 15));
        }   }
    boolean checkconnection()
    {boolean flag=false;
        boolean gps_con;
        boolean network_enabled;
        try{
            gps_con=mymanager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            try{
                network_enabled=mymanager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if(gps_con && network_enabled)
                {
                    flag=true;
                }
            }
            catch (Exception e)
            {
            }
        }
        catch (Exception e) {

        }
        if(flag)
        {
            return true;
        }
        else {
            return false;
        }
    }
}
