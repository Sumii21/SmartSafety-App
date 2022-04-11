package firstapp.apps.kirti.smartsafetyapp;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.IInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    SearchView obj;
    Toolbar toolbar;
    String mydate;
    Bitmap mybitmap;
    Wrapper w;
    TextView t2;
    Location mLastLocation;
    GoogleApiClient mGoogleApiClient;
Double lat,lng;
    ArrayList<String> myresult;

    String mylocation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        navigationView1.setNavigationItemSelectedListener(this);

        View headerView = navigationView1.inflateHeaderView(R.layout.nav_header_main);
        TextView t= (TextView) headerView.findViewById(R.id.textView);
         t2= (TextView) headerView.findViewById(R.id.textView9);
        myresult = new ArrayList<>();
w=new Wrapper(MainActivity.this);
        t.setText("The Key to your Safety");


        obj= (SearchView) findViewById(R.id.Searchview);
        obj.setQueryHint("SEARCH");

        obj.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.makeText(MainActivity.this, "hello" + hasFocus, Toast.LENGTH_SHORT).show();

            }
        });

        obj.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "ho gea", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, ""+newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        JoinFragment joinFragment=new JoinFragment();
        FragmentManager mymanager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=mymanager.beginTransaction();
        fragmentTransaction.replace(R.id.mylayout,joinFragment,"joinfragment");
        fragmentTransaction.commit();





//GPS CODE

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    //Menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {




        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Drawer menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.contacts) {
String mydata="content://contacts//people/";
            Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(mydata));
            startActivity(i);
        }
        else if (id == R.id.Panic)
        {

            PackageManager pm = this.getPackageManager();

            if (!pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY) ||
                    !pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA)) {

                Intent i=new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: 7355915850" ));
                i.putExtra("sms_body","Emergency I am at" + mylocation);
                startActivity(i);

            }

            else{
                SmsManager sms = SmsManager.getDefault();
                //PendingIntent sentPI;
                //String SENT = "SMS_SENT";

                //sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
                sms.sendTextMessage("tel:+7355915850",null,"Emergency..i need your help...",null,null);
                Toast.makeText(MainActivity.this, "send successfully", Toast.LENGTH_SHORT).show();
            }


        }
        else if (id == R.id.home)
        {

         fragment=new JoinFragment();

        }
        else if (id == R.id.news)
        {

fragment=new JoinnewsFragment();
        }
        else if (id == R.id.starred)
        {

            fragment=new FavouriteFragments();
        }
        else if (id == R.id.maps)
        {

            Intent i=new Intent(MainActivity.this,Mygoogle.class);
            startActivity(i);
        }

        else if (id == R.id.nav_gallery)
        {
              fragment=new MyGallery();

        }
        else if (id == R.id.videos)
        {


        }

        else if (id == R.id.search)
        {
            fragment=new MyGoogleSearch();

        }

        else if (id == R.id.website)
        {

            fragment=new Mywebsite();
//String mystring="http://www.jalandharonline.in/city-guide";
//            Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(mystring));
//            startActivity(i);
        }

        else if (id == R.id.feedback)
        {
            Log.i("Send email", "");
            String[] TO = {"lubaanakirti1994@yahoo.in"};
            String[] CC = {"sumitkumar1303534@gmail.com"};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "");

            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                Log.i("Email Sent Successfully", "");
            }
            catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(MainActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.user_review) {
fragment=new UserReview();
        }
        if(fragment!=null) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.mylayout, fragment, "mygoogle");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        if (mLastLocation != null) {
            //latitude and longitude

            lat=mLastLocation.getLatitude();
            lng=mLastLocation.getLongitude();
            Toast.makeText(MainActivity.this, "lat"+lat+"long"+lng, Toast.LENGTH_SHORT).show();
            Geocoder geo=new Geocoder(getApplicationContext(), Locale.getDefault());


            if(Geocoder.isPresent())
            {
                try {
                    List<Address> addresses = geo.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(), 1);
                    if (addresses != null && addresses.size() > 0)
                    {
                        Address address = addresses.get(0);
                        String addressText = String.format("%s, %s, %s",
                                // If there's a street address, add it
                                address.getMaxAddressLineIndex() > 0 ?address.getAddressLine(0) : "",
                                // Locality is usually a city
                                address.getLocality(),
                                // The country of the address
                                address.getCountryName());
                        mylocation=addressText;//address
                         mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        Toast.makeText(MainActivity.this, mydate, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            NotificationCompat.BigPictureStyle notistyle=new NotificationCompat.BigPictureStyle();
            notistyle.setSummaryText("Longitude:"+ lng +"Latitude:"+lat );
            notistyle.bigPicture(mybitmap);

            Intent i=new Intent(getApplicationContext(),NotificationActivity.class);
            PendingIntent p=PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(),i,0);
            NotificationCompat.Builder mybuilder=new NotificationCompat.Builder(getApplicationContext());
            Notification mynotification;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN)
            {

                 mynotification=mybuilder.setContentTitle("You are at"+ mylocation)
                        .setTicker("Check your location now")
                        .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                        .setContentIntent(p)
                        .setAutoCancel(true)
                        .setStyle(notistyle)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).build();
            }
            else {
                 mynotification=mybuilder.setContentTitle("WHERE AM I !!!!!")
                        .setTicker("Check your location now")
                        .setSmallIcon(android.R.drawable.ic_menu_mylocation)
                        .setContentIntent(p)
                        .setAutoCancel(true)
                        .setStyle(notistyle)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).getNotification();

            }
            NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            nm.notify(1,mynotification);

            if((mylocation!=null && mylocation!="")&& (mydate!=null && mydate!="")&& (lng!=null && !lng.equals(""))&& (lat!=null && !lat.equals(""))) {
                new mydbclass().execute("http://smartdata.netne.net/insertlocation.php", "kirti", mylocation, mydate, String.valueOf(lng), String.valueOf(lat));
            }
            new fetchweather().execute("http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&units=metric&appid=caf7f490bedc4cd77fc8a51b5bbfd5d1");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    //AsyncTask to store Locations
    private class mydbclass extends AsyncTask<String,Void,Wrapper>
    {
        String response1="";
        @Override
        protected Wrapper doInBackground(String... params) {

            try {
                URL myurl=new URL(params[0]);
                HttpURLConnection myconnection= (HttpURLConnection) myurl.openConnection();
                myconnection.setRequestMethod("POST");
                myconnection.setDoOutput(true);
                myconnection.setDoInput(true);
                OutputStream myoutputstream=myconnection.getOutputStream();
                BufferedWriter mywriter=new BufferedWriter(new OutputStreamWriter(myoutputstream,"UTF-8"));
                String data= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"+ URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(params[2],"UTF-8")+"&"+ URLEncoder.encode("datetime", "UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8")+"&"+URLEncoder.encode("longitude", "UTF-8")+"="+URLEncoder.encode(params[4],"UTF-8")+"&"+URLEncoder.encode("latitude", "UTF-8")+"="+URLEncoder.encode(params[5],"UTF-8");
                mywriter.write(data);
                mywriter.flush();
                mywriter.close();
          /*  JSONObject jsonObject = new JSONObject();
            jsonObject.put("uname", params[0]);
            jsonObject.put("password", params[1]);
            DataOutputStream wr = new DataOutputStream(myconnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.close();*/
                InputStream myinputstream=myconnection.getInputStream();
                BufferedReader myreader=new BufferedReader(new InputStreamReader(myinputstream,"UTF-8"));

                String line="";

                while((line=myreader.readLine())!=null)
                {
                    response1 += line;
                }
                w.response2=response1;
                try {
                    mybitmap= BitmapFactory.decodeStream((InputStream)new URL("http://smartdata.netne.net/mylocation.jpg").getContent());
                w.bitmap=mybitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myreader.close();
                myinputstream.close();
                myconnection.disconnect();
            }  catch (MalformedURLException e)
            {
                Log.e("Connectivity", "Error in URL", e);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Connectivity", "Error in IO", e);
            }
            catch(Exception e)
            {
                Log.e("Connectivity", "Error", e);
            }
            return w;



        }

        @Override
        protected void onPostExecute(Wrapper s) {
            JSONObject myobject = null;
            String msg;
            try {
                myobject = new JSONObject(response1);
                if(myobject!=null) {
                    msg = myobject.getString("message");
                    if(msg.equalsIgnoreCase("success")) {


                    }

                    else
                    {
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    //AsyncTask for weather
    private class fetchweather extends AsyncTask<String, Void, Wrapper> {

        String jsonString;
        JSONObject queryobj;
        JSONArray resultarray;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Wrapper doInBackground(String... params) {

            try {

                URL siteurl = new URL(params[0]);
                BufferedReader myreader = new BufferedReader(new InputStreamReader(siteurl.openStream()));
                jsonString = myreader.readLine();
                JSONObject myobject = new JSONObject(jsonString);
                queryobj = myobject.getJSONObject("main");
                if (queryobj != null) {
                    String temp1 = queryobj.getString("temp");
                    myresult.add(temp1);
                }
w.temp1=myresult;




            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return w;

        }


        @Override
        protected void onPostExecute(Wrapper s) {
            if (myresult.size() > 0) {

t2.setText(new StringBuilder().append(myresult.get(0)).append(" °C").toString());
            }
            super.onPostExecute(s);
        }
    }
}
