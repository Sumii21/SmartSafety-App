package firstapp.apps.kirti.smartsafetyapp;



import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

public class MyCustomDialog2 extends DialogFragment
{
    ImageView im1,im2;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Dialog d=new Dialog(getActivity());

        d.setContentView(R.layout.mycustomdialog);

        d.setTitle("No Internet Connection");
        im1= (ImageView) d.findViewById(R.id.imageView4);
        im2= (ImageView) d.findViewById(R.id.imageView5);

        im1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //getActivity().getSystemService()
                Intent myintent=new Intent(Settings.ACTION_SETTINGS);
                startActivity(myintent);
            }
        });

        im2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                d.dismiss();
            }
        });


        return d;


    }


}
