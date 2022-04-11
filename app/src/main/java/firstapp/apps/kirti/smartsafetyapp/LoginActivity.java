package firstapp.apps.kirti.smartsafetyapp;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.squareup.seismic.ShakeDetector;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Mysignupfragment mysignupfragment=new Mysignupfragment();
        Myloginfragment myloginfragment=new Myloginfragment();
        android.app.FragmentManager mymanager=getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction=mymanager.beginTransaction();
        FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(LoginActivity.this, fragmentTransaction,mysignupfragment , myloginfragment, R.id.fragment_place);
        fragmentTransactionExtended.addTransition(FragmentTransactionExtended.CUBE);
        fragmentTransactionExtended.commit();
    }

}
