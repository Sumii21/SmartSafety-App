package firstapp.apps.kirti.smartsafetyapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class NotificationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
    List<Address> address;
    EditText e1;
    TextView t1;
    Address location;
    Button b;
    //String mylocation = null;
    Double lat,lng,lat2,lng2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t1 = (TextView) findViewById(R.id.textView6);
        b = (Button) findViewById(R.id.button10);
        e1 = (EditText) findViewById(R.id.editText11);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Geocoder selected_place_geocoder = new Geocoder(NotificationActivity.this);

                        address = selected_place_geocoder.getFromLocationName(e1.getText().toString(), 5);
                               if(address == null) {
                        //do nothing
                    } else {
                        location = address.get(0);
                        lat2= location.getLatitude();
                         lng2 = location.getLongitude();


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Location l1=new Location("One");
                l1.setLatitude(lat);
                l1.setLongitude(lng);

                Location l2=new Location("Two");
                l2.setLatitude(lat2 );
                l2.setLongitude(lng2);
                float distance_bw_one_and_two=(l1.distanceTo(l2))/1000;

                t1.setText(distance_bw_one_and_two+" Kms");


            }
        });
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation!=null)
        {
            lat=mLastLocation.getLatitude();
            lng=mLastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
