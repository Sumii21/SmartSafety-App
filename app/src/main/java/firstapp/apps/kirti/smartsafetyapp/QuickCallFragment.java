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
import java.util.ArrayList;
import java.util.List;

public class QuickCallFragment extends android.support.v4.app.Fragment {
    ArrayList<String> names;
    ListView l;
    ArrayList<String> contact;
    Wrapper w;
    ProgressDialog p;
    myadapter obj2;
    String finalcontacts;
    ImageButton img;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.quickcall,container,false);
        l = (ListView)v. findViewById(R.id.listview3);
        names = new ArrayList<>();
        contact = new ArrayList<>();
        w=new Wrapper(getActivity());

        p=new ProgressDialog(getActivity());
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setMessage("Loading.....");

        try {
            new mydbclass().execute("http://smartdata.netne.net/fetchquick.php");
            obj2 = new myadapter(getActivity(), android.R.layout.simple_list_item_1, names);
            l.setAdapter(obj2);

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
            View row=l1.inflate(R.layout.numbersrow2,parent,false);
            TextView t=(TextView)row.findViewById(R.id.textView4);
            img= (ImageButton) row.findViewById(R.id.imageButton);
            t.setText(names.get(position));
            for(int i=0;i<contact.toArray().length; i++)
            {

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


            }
            return row;
        }
    }
    private class mydbclass extends AsyncTask<String, Void,  Wrapper>

    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p.show();
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
                        finalcontacts=blocks.getString("contact");
                        names.add(finalresut);
contact.add(finalcontacts);

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

            p.dismiss();
            obj2.notifyDataSetChanged();
        }

    }

}
