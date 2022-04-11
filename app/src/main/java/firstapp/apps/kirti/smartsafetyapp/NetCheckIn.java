package firstapp.apps.kirti.smartsafetyapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class NetCheckIn
{
    private Context mcontext;
    NetCheckIn(Context mycontext)
    {
        mcontext=mycontext;
    }

    public boolean isconnected()
    {
        Boolean flag=false;
        ConnectivityManager cm= (ConnectivityManager) mcontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Object o=mcontext.getSystemService(Context.WIFI_SERVICE);

        if(cm!=null)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            {
                Network[] net=cm.getAllNetworks();
                NetworkInfo netinfo;
                for(Network mnet:net)
                {
                    netinfo=cm.getNetworkInfo(mnet);
                    if(netinfo.getState().equals(NetworkInfo.State.CONNECTED))
                    {
                        return true;
                    }
                }
            }
            else
            {
                NetworkInfo[] myinfo = cm.getAllNetworkInfo();
                if (myinfo != null)
                {
                    for (int i = 0; i < myinfo.length; i++)
                    {
                        if (myinfo[i].getState() == NetworkInfo.State.CONNECTED)
                        {
                            return true;
                        }
                    }
                }


            }
        }
        else
        {
            Toast.makeText(mcontext, "error in connectivity", Toast.LENGTH_SHORT).show();

        }
        return false;
    }
}
