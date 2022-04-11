package firstapp.apps.kirti.smartsafetyapp;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MyGoogleSearch extends Fragment{
    EditText e;
    Button b;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mygooglesearch,container,false);
        b= (Button) v.findViewById(R.id.button8);
        e=(EditText)v.findViewById(R.id.editText8);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,e.getText().toString());
                startActivity(i);
            }
        });
        return v;
    }
}
