package firstapp.apps.kirti.smartsafetyapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;

public class myconnection {
    private Context mycontext;
    myconnection(Context mcontext)
    {
        mycontext=mcontext;
    }
    public Boolean isnetconneted()
    {
        ConnectivityManager cm=(ConnectivityManager)mycontext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm!=null) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                Network[] n = cm.getAllNetworks();
                NetworkInfo networkInfo = null;
                for (Network network : n) {
                    networkInfo = cm.getNetworkInfo(network);
                    if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                        return true;
                    }

                }


            } else {
                NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
                if (networkInfos != null) {
                    for (int i = 0; i < networkInfos.length; i++) {
                        if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }


            return false;
        }
        return false;
    }
}
