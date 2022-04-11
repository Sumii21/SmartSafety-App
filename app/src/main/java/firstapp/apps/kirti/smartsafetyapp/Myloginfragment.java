package firstapp.apps.kirti.smartsafetyapp;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.squareup.seismic.ShakeDetector;

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

public class Myloginfragment extends Fragment implements ShakeDetector.Listener,View.OnClickListener {
    EditText e1,e2;
    Button b1;
    ImageButton img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myview=inflater.inflate(R.layout.login_fragment,container,false);
        e1= (EditText) myview.findViewById(R.id.editText2);
        e2= (EditText) myview.findViewById(R.id.editText3);
        b1=(Button)myview.findViewById(R.id.button);
        img= (ImageButton) myview.findViewById(R.id.imageButton3);


        b1.setOnClickListener(this);


        return myview;
    }

    @Override
    public void hearShake() {



       // new checklogin().execute("http://smartdata.netne.net/login.php", e1.getText().toString(), e2.getText().toString());
//        Intent i = new Intent(getActivity(), MainActivity.class);
//        startActivity(i);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SensorManager sensorManager = (SensorManager) getActivity().getSystemService(getActivity().SENSOR_SERVICE);
        ShakeDetector sd = new ShakeDetector(this);
        sd.start(sensorManager);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mysignupfragment mysignupfragment = new Mysignupfragment();
                Myloginfragment myloginfragment = new Myloginfragment();
                android.app.FragmentManager mymanager = getActivity().getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = mymanager.beginTransaction();
                FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(getActivity(), fragmentTransaction,myloginfragment , mysignupfragment, R.id.fragment_place);
                fragmentTransactionExtended.addTransition(FragmentTransactionExtended.CUBE);
                fragmentTransactionExtended.commit();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            new checklogin().execute("http://smartdata.netne.net/login.php", e1.getText().toString(), e2.getText().toString());

            //  new checklogin().execute("http://smartdata.netne.net/login.php", e1.getText().toString(), e2.getText().toString());
 //         Toast.makeText(getActivity(), "shake shake shake", Toast.LENGTH_SHORT).show();
//
//            android.app.AlertDialog.Builder d=new android.app.AlertDialog.Builder(getActivity())
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle("Login")
//                    .setMessage("Shake your phone to login")
//                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which)
//                        {
//
//
//                        }
//                    });
//            android.app.AlertDialog a=d.create();
//            a.show();

//            View view = getActivity().getCurrentFocus();
//            if (view != null)
//            {
//                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//
//            }

        }
    }
        private class checklogin extends AsyncTask<String, Void, String> {
            String path = "";
            String response1 = "";

            @Override
            protected String doInBackground(String... params) {

                try {
                    URL myurl = new URL(params[0]);
                    HttpURLConnection myconnection = (HttpURLConnection) myurl.openConnection();
                    myconnection.setRequestMethod("POST");
                    myconnection.setDoOutput(true);
                    myconnection.setDoInput(true);
                    OutputStream myoutputstream = myconnection.getOutputStream();
                    BufferedWriter mywriter = new BufferedWriter(new OutputStreamWriter(myoutputstream, "UTF-8"));
                    String data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8") + "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[2], "UTF-8");
                    mywriter.write(data);
                    mywriter.flush();
                    mywriter.close();
          /*  JSONObject jsonObject = new JSONObject();
            jsonObject.put("uname", params[0]);
            jsonObject.put("password", params[1]);
            DataOutputStream wr = new DataOutputStream(myconnection.getOutputStream());
            wr.writeBytes(jsonObject.toString());
            wr.close();*/
                    InputStream myinputstream = myconnection.getInputStream();
                    BufferedReader myreader = new BufferedReader(new InputStreamReader(myinputstream, "UTF-8"));

                    String line = "";

                    while ((line = myreader.readLine()) != null) {
                        response1 += line;
                    }
                    myreader.close();
                    myinputstream.close();
                    myconnection.disconnect();
                } catch (MalformedURLException e) {
                    Log.e("Connectivity", "Error in URL", e);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Connectivity", "Error in IO", e);
                } catch (Exception e) {
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
                    if (myobject != null) {
                        msg = myobject.getString("message");
                        if (msg.equalsIgnoreCase("correct")) {
                            Toast.makeText(getActivity(), "Logged in Successful", Toast.LENGTH_SHORT).show();

                            Intent i = new Intent(getActivity(), MainActivity.class);


                            getActivity().finish();
                            startActivity(i);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), msg+" user name & password", Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
    }
