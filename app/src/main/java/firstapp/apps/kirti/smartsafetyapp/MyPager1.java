package firstapp.apps.kirti.smartsafetyapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


/**
 * Created by abc on 4/21/2016.
 */
public class MyPager1 extends FragmentStatePagerAdapter {
    String tabtitles[];

    int NumbOfTabs;


    public MyPager1(FragmentManager fm, String titles[], int numboftabs) {
        super(fm);
        this.tabtitles = titles;
        this.NumbOfTabs = numboftabs;
    }


    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            Fragment1 tab1 = new Fragment1();
            return tab1;
        }
        else
        {
            Fragment2 tab2 = new Fragment2();
            return tab2;
        }

    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


    public String getPageTitle(int position) {

        return tabtitles[position];
    }
}