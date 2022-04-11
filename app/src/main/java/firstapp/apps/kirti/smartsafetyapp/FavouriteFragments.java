package firstapp.apps.kirti.smartsafetyapp;


import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
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

public class FavouriteFragments extends android.support.v4.app.Fragment{


    String msg;

    ArrayList<String> names;
    ListView l;
    ArrayList<String> contact;
    ArrayList<String> address;
    Wrapper w;
    ProgressDialog p;

    myadapter obj2;
    String finalcontacts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.favourites,container,false);
        l = (ListView)v. findViewById(R.id.listView5);
        names = new ArrayList<>();
        contact = new ArrayList<>();
        address = new ArrayList<>();
        w=new Wrapper(getActivity());
        p=new ProgressDialog(getActivity());

        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setMessage("Loading.....");
        try {
            new mydbclass().execute("http://smartdata.netne.net/fetchfavourites.php");
            obj2 = new myadapter(getActivity(), android.R.layout.simple_list_item_1, names);
            l.setAdapter(obj2);
            obj2.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error1" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return v;
    }
    private class myadapter extends ArrayAdapter<String> {

        public myadapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater l1=(LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=l1.inflate(R.layout.numbersrow3,parent,false);
            ImageButton img,img2;
            img= (ImageButton) row.findViewById(R.id.imageButton);
            img2= (ImageButton) row.findViewById(R.id.imageButton2);
            TextView t=(TextView)row.findViewById(R.id.textView4);
Button b1= (Button) row.findViewById(R.id.button14);


            t.setText(names.get(position));

            for(int i=0;i<contact.toArray().length; i++)
            {
                if((names!= null && names.size() !=0) && (address!= null && address.size() !=0)&& (contact!= null && contact.size() !=0))
                {

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new mydbclass1().execute("http://smartdata.netne.net/deletefavourites.php", names.get(position), address.get(position), contact.get(position));

                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "elsepart", Toast.LENGTH_SHORT).show();
                }

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_CALL);
                        i.setData(Uri.parse("tel:" + contact.get(position)));
                        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
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


            }
            return row;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        obj2.notifyDataSetChanged();
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
w.address1=address;
                        w.names1=names;
                        w.numbers1=contact;
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
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
if(msg!=null) {
    if (msg.equalsIgnoreCase("success")) {
        Toast.makeText(getActivity(), " Deleted from Favourites", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
    }
    obj2.notifyDataSetChanged();

}       }

    }
}
