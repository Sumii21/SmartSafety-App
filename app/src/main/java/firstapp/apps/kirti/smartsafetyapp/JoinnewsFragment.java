package firstapp.apps.kirti.smartsafetyapp;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class JoinnewsFragment extends Fragment{

    ViewPager pager;
    MyPager1 adapter;
    SlidingTabLayout tabs;
    String Titles[]={"ENGLISH","HINDI"};
    int Numboftabs =2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.joinnewsfragment,container,false);

        adapter =  new MyPager1(getFragmentManager(), Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager)v. findViewById(R.id.myviewpager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout)v. findViewById(R.id.myslidingtab);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View

        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                if (position == 0) {
                    return Color.GREEN;
                } else  {
                    return Color.RED;
                }
            }
        });
//        ImageView img= (ImageView) v.findViewById(R.id.myimageview);
//
//        Glide.with(this).load(R.drawable.myback).into(img);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

        return v;
    }
}
