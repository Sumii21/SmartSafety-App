package firstapp.apps.kirti.smartsafetyapp;


import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

public class PhoneFragment extends android.support.v4.app.ListFragment{
    ArrayList<String> names;
    ArrayList<String> images1;
    Wrapper w=new Wrapper(getActivity());
    ListView l;
    myadapter obj2;
    String finalresut;
    ProgressDialog p;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.phonefragment,container,false);
        l = (ListView)v. findViewById(android.R.id.list);
p=new ProgressDialog(getActivity());

        names = new ArrayList<>();
        images1 = new ArrayList<>();
p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setMessage("Loading.....");


        try {
            new mydbclass().execute("http://smartdata.netne.net/fetchcategory1.php");
            obj2 = new myadapter(getActivity(), android.R.layout.simple_list_item_1, names);
            l.setAdapter(obj2);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error1" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent i=new Intent(getActivity(),MainActivity2.class);
                    i.putExtra("names",names.get(position));
                    startActivity(i);


            }
        });
    }

    private class myadapter extends ArrayAdapter<String> {

        public myadapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater l1=(LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=l1.inflate(R.layout.listrow,parent,false);
            ImageView img=(ImageView)row.findViewById(R.id.imageView6);
            TextView t=(TextView)row.findViewById(R.id.textView7);
//            new DownloadImageTask(img).execute(images1.get(position));
            t.setText(names.get(position));
            Glide
                    .with(getActivity())
                    .load(images1.get(position))
                    .into(img);
            return row;
        }
    }

    @Override
    public void onResume() {
        super.onResume();


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
                         finalresut=blocks.getString("category");
                        String finalimages=blocks.getString("image");
                        names.add(finalresut);
                        images1.add(finalimages);

                        w.images=images1;
                        w.names1=names;
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
//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//            bmImage.setImageBitmap(result);
//
//
//        }
//    }

}
