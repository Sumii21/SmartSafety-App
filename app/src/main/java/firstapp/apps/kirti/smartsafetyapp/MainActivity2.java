package firstapp.apps.kirti.smartsafetyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ArrayList<String> names;
    ListView l;
    ArrayList<String> contact;
    ArrayList<String> address;
    Wrapper w;
    ProgressDialog p;
    Button b1;
    myadapter obj2;
    String finalcontacts;
    boolean f=false;


    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        l = (ListView) findViewById(R.id.listView2);
        names = new ArrayList<>();
        contact = new ArrayList<>();
        address = new ArrayList<>();
        w=new Wrapper(this);
        p=new ProgressDialog(this);

        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setMessage("Loading.....");

        if(this.getIntent().getExtras()!=null)
        {
            category=getIntent().getExtras().getString("names");


            try {
                if(category.equalsIgnoreCase("emergency services"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchemergency.php");
                }
                else if(category.equalsIgnoreCase("hospital"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchhospital.php");
                }
                else if(category.equalsIgnoreCase("blood banks"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchblood.php");
                }
                else if(category.equalsIgnoreCase("electricity complaints"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchelectricity.php");
                }
                else if(category.equalsIgnoreCase("NGO'S"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchngo.php");
                }
                else if(category.equalsIgnoreCase("police service"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchpolice.php");
                }
                else if(category.equalsIgnoreCase("nursing homes"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchnurse.php");
                }
                else if(category.equalsIgnoreCase("banquet hall"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchbanquet.php");
                }
                else if(category.equalsIgnoreCase("ayurveda centres"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchayurveda.php");
                }
                else if(category.equalsIgnoreCase("chemists"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchchemist.php");
                }
                else if(category.equalsIgnoreCase("cremation houses"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchcremation.php");
                }
                else if(category.equalsIgnoreCase("banks"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchbanks.php");
                }
                else if(category.equalsIgnoreCase("religious places"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchreligious.php");
                }
                else if(category.equalsIgnoreCase("press contacts"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchpress.php");
                }
                else if(category.equalsIgnoreCase("municipal corporation"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchmunicipal.php");
                }
                else if(category.equalsIgnoreCase("entertainment"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchentertainment.php");
                }
                else if(category.equalsIgnoreCase("welfare societies"))
                {
                    new mydbclass().execute("http://smartdata.netne.net/fetchwelfare.php");
                }

                obj2 = new myadapter(getBaseContext(), android.R.layout.simple_list_item_1, names);
                l.setAdapter(obj2);
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Error1" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }




        }

    }
    private class myadapter extends ArrayAdapter<String> {

        public myadapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater l1=(LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=l1.inflate(R.layout.numbersrow,parent,false);
            TextView t=(TextView)row.findViewById(R.id.textView4);
            ImageButton img1,img2;
            b1= (Button) row.findViewById(R.id.button4);
            img1= (ImageButton)row. findViewById(R.id.imageButton);
            img2= (ImageButton)row. findViewById(R.id.imageButton2);
            b1.setBackgroundResource(android.R.drawable.btn_star_big_off);


            t.setText(names.get(position));


//            for(int i=0;i<contact.toArray().length; i++)
//            {

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {


                            new mydbclass1().execute("http://smartdata.netne.net/insertf.php", names.get(position), address.get(position), contact.get(position));
                        int x=position;
                        b1.setBackgroundResource(android.R.drawable.star_big_on);

                        //b1.setVisibility(View.INVISIBLE);
                        f=true;
                    }
                });

//                else
//                {
//                    new mydbclass1().execute("http://smartdata.netne.net/deletefavourites.php",names.get(position),address.get(position),contact.get(position));
//                }

                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:"+ contact.get(position)));
                        if (ActivityCompat.checkSelfPermission(MainActivity2.this, android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                            startActivity(i);
                        }
                    }
                });

                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"+ contact.get(position)));
                        i.putExtra("sms_body","Emergency");
                        startActivity(i);
                    }
                });
//            }






            return row;
        }
    }
    private class mydbclass extends AsyncTask<String, Void,  Wrapper>

    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Wrapper doInBackground(String... params) {
            String response1="";
            try {

                URL myurl=new URL(params[0]);
                HttpURLConnection myconnection= (HttpURLConnection) myurl.openConnection();
                myconnection.setRequestMethod("POST");
                myconnection.setDoOutput(true);
                myconnection.setDoInput(true);
                OutputStream myoutputstream=myconnection.getOutputStream();
                BufferedWriter mywriter=new BufferedWriter(new OutputStreamWriter(myoutputstream,"UTF-8"));
//                String data= URLEncoder.encode("uname", "UTF-8")+"="+URLEncoder.encode(params[0],"UTF-8")+"&"+ URLEncoder.encode("password","UTF-8") + "=" + URLEncoder.encode(params[1],"UTF-8");
//                mywriter.write(data);
                mywriter.flush();
                mywriter.close();
                InputStream myinputstream=myconnection.getInputStream();
                BufferedReader myreader=new BufferedReader(new InputStreamReader(myinputstream,"UTF-8"));

                String line="";

                while((line=myreader.readLine())!=null)
                {
                    response1 += line;
                }
                myreader.close();
                myinputstream.close();
                myconnection.disconnect();


                try {
                    JSONObject myobject=new JSONObject(response1);
                    JSONArray myresult=myobject.getJSONArray("result");

                    for(int i=0;i<myresult.length();i++) {
                        JSONObject blocks = myresult.getJSONObject(i);
                        String finalresut=blocks.getString("name");
                        String finaladdress=blocks.getString("address");
                        finalcontacts=blocks.getString("contact");
                        names.add(finalresut);
                        address.add(finaladdress);
                        contact.add(finalcontacts);
                        w.numbers1=contact;
                        w.names1=names;
                        w.address1=address;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            catch (MalformedURLException e)
            {
                Log.e("Connectivity", "Error in URL", e);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Connectivity", "Error in IO", e);
            }
            catch(Exception e)
            {
                Log.e("Connectivity","Error",e);
            }
            return w;

        }

        @Override
        protected void onPostExecute(Wrapper jsonString) {
            super.onPostExecute(jsonString);


            obj2.notifyDataSetChanged();
        }

    }
    private class mydbclass1 extends AsyncTask<String, Void,  Wrapper>

    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Wrapper doInBackground(String... params) {
            String response1="";
            try {

                URL myurl=new URL(params[0]);
                HttpURLConnection myconnection= (HttpURLConnection) myurl.openConnection();
                myconnection.setRequestMethod("POST");
                myconnection.setDoOutput(true);
                myconnection.setDoInput(true);
                OutputStream myoutputstream=myconnection.getOutputStream();
                BufferedWriter mywriter=new BufferedWriter(new OutputStreamWriter(myoutputstream,"UTF-8"));
               String data= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"+ URLEncoder.encode("address","UTF-8") + "=" + URLEncoder.encode(params[2],"UTF-8")+"&"+URLEncoder.encode("contact", "UTF-8")+"="+URLEncoder.encode(params[3],"UTF-8");
               mywriter.write(data);
                mywriter.flush();
                mywriter.close();
                InputStream myinputstream=myconnection.getInputStream();
                BufferedReader myreader=new BufferedReader(new InputStreamReader(myinputstream,"UTF-8"));

                String line="";

                while((line=myreader.readLine())!=null)
                {
                    response1 += line;
                }
                myreader.close();
                myinputstream.close();
                myconnection.disconnect();
                JSONObject myobject = null;
                String msg;
                try {
                    myobject = new JSONObject(response1);
                    if(myobject!=null) {
                        msg = myobject.getString("message");

w.message=msg;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            catch (MalformedURLException e)
            {
                Log.e("Connectivity", "Error in URL", e);
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Connectivity", "Error in IO", e);
            }
            catch(Exception e)
            {
                Log.e("Connectivity","Error",e);
            }
            return w;

        }

        @Override
        protected void onPostExecute(Wrapper jsonString) {
            super.onPostExecute(jsonString);
if(w.message!=null) {
    if (w.message.equalsIgnoreCase("success")) {
        Toast.makeText(MainActivity2.this, "ADDED TO FAVOURITES", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(MainActivity2.this, "Already in Favourites", Toast.LENGTH_SHORT).show();
    }
    obj2.notifyDataSetChanged();
}
        else {
    Toast.makeText(MainActivity2.this, "Already in Favourites", Toast.LENGTH_SHORT).show();
}
        }

    }

}
