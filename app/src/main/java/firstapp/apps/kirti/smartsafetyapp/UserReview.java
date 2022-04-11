package firstapp.apps.kirti.smartsafetyapp;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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

public class UserReview extends Fragment {
    EditText e1,e2;
    Button b;
    RatingBar r;
    ArrayList<String> names;
    ListView l;
    ArrayList<String> review;
    ArrayList<String> rating;
    myadapter obj2;

    Wrapper w;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.userreview,container,false);
        e1= (EditText) v.findViewById(R.id.editText9);
        e2= (EditText) v.findViewById(R.id.editText10);
        r=(RatingBar)v.findViewById(R.id.ratingBar);
        b=(Button)v.findViewById(R.id.button9);
        l = (ListView)v. findViewById(R.id.listView);
        names = new ArrayList<>();
        review = new ArrayList<>();
        rating = new ArrayList<>();
        w=new Wrapper(getActivity());

b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(e1.getText().toString().equals("") || e2.getText().toString().equals(""))
        {
            Toast.makeText(getActivity(), "Fill all the fields !", Toast.LENGTH_SHORT).show();
        }
        else
        {
            new mydbclass().execute("http://smartdata.netne.net/insertreview.php", e1.getText().toString(), e2.getText().toString(),String.valueOf(r.getRating()));
e1.setText("");
            e2.setText("");
            r.setRating(0);
        }
    }
});
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {
            new mydbclass1().execute("http://smartdata.netne.net/fetchreview.php");
            obj2 = new myadapter(getActivity(), android.R.layout.simple_list_item_1, names);
            l.setAdapter(obj2);
            obj2.notifyDataSetChanged();

        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error1" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class myadapter extends ArrayAdapter<String> {

        public myadapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater l1=(LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=l1.inflate(R.layout.myreviews,parent,false);

            TextView t1=(TextView)row.findViewById(R.id.textView13);
            TextView t2=(TextView)row.findViewById(R.id.textView15);
            RatingBar r=(RatingBar)row.findViewById(R.id.ratingBar2);
            t1.setText(names.get(position));
            t2.setText(review.get(position));
            r.setRating(Float.parseFloat(rating.get(position)));
            return row;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        names.clear();
        review.clear();
        rating.clear();
        obj2.notifyDataSetChanged();
    }

    private class mydbclass extends AsyncTask<String,Void,String>
    {

        String path="";
        String response1="";
        @Override
        protected String doInBackground(String... params) {

            try {
                URL myurl=new URL(params[0]);
                HttpURLConnection myconnection= (HttpURLConnection) myurl.openConnection();
                myconnection.setRequestMethod("POST");
                myconnection.setDoOutput(true);
                myconnection.setDoInput(true);
                OutputStream myoutputstream=myconnection.getOutputStream();
                BufferedWriter mywriter=new BufferedWriter(new OutputStreamWriter(myoutputstream,"UTF-8"));
                String data= URLEncoder.encode("name", "UTF-8")+"="+URLEncoder.encode(params[1],"UTF-8")+"&"+ URLEncoder.encode("review","UTF-8") + "=" + URLEncoder.encode(params[2],"UTF-8")+"&"+ URLEncoder.encode("rate","UTF-8") + "=" + URLEncoder.encode(params[3],"UTF-8");
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
            return response1;



        }

        @Override
        protected void onPostExecute(String s) {
            JSONObject myobject = null;
            String msg;
            try {
                myobject = new JSONObject(s);
                if(myobject!=null) {
                    msg = myobject.getString("message");
                    if(msg.equalsIgnoreCase("success")) {
                        Toast.makeText(getActivity(), "Review Submit Successfully", Toast.LENGTH_SHORT).show();

                    }

                    else
                    {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


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
                        String finalreview=blocks.getString("review");
                        String finalrate=blocks.getString("rate");
                        names.add(finalresut);
                        review.add(finalreview);
                        rating.add(finalrate);


                        w.names1=names;
                        w.review1=review;
                        w.rate1=rating;
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

}
