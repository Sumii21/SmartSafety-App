package firstapp.apps.kirti.smartsafetyapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {
ImageView img,img2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
img= (ImageView) findViewById(R.id.myimageview);
        img2= (ImageView) findViewById(R.id.imageView3);
        Glide.with(this).load(R.drawable.bull).into(img2);
        Glide.with(this).load(R.drawable.maxresdefault).centerCrop().into(img);
//        SharedPreferences set= PreferenceManager.getDefaultSharedPreferences(this);
//        boolean x=set.getBoolean("splash", true);
//        String name=set.getString("name","guest");
//        Toast.makeText(FullscreenActivity.this, "WELCOME"+name, Toast.LENGTH_SHORT).show();
//        if(x==true) {
            if (actionBar != null) {
                actionBar.hide();
            }

            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {



                   Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                   startActivity(i);

                    SplashActivity.this.finish();
                }
            }, 5000);
        //}
//        else {
//
//            Intent i = new Intent(FullscreenActivity.this, MainActivity2.class);
//            startActivity(i);
//            FullscreenActivity.this.finish();
//        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder mybuilder=new AlertDialog.Builder(SplashActivity.this);
        mybuilder.setMessage("Do you want to exit?");
        mybuilder.setCancelable(false);
        mybuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SplashActivity.this.finish();
            }
        });
        mybuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog mydialog=mybuilder.create();
        mydialog.show();

    }

}
