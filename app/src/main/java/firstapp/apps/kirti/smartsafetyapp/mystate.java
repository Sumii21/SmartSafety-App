package firstapp.apps.kirti.smartsafetyapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;

public class mystate
{
    private SharedPreferences sp;
    ArrayList<String> fav=new ArrayList<>();
    mystate(Context c)
    {
        sp= PreferenceManager.getDefaultSharedPreferences(c);
    }
    public void setusername(String username)
    {
        sp.edit().putString("u",username).commit();
    }
    public String getName()
    {
        String username=sp.getString("u","default");
        return username;
    }
    public void removeusername()
    {
        sp.edit().remove("u").commit();
    }
}
