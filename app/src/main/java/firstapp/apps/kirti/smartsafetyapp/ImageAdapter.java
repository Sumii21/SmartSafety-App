package firstapp.apps.kirti.smartsafetyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by mansharan on 08-05-2016.
 */
public class ImageAdapter extends BaseAdapter {
    Context m;
    ArrayList<String> images=new ArrayList<>();



    public ImageAdapter(Context c, ArrayList<String> k)  //class which call this constructor will send its own reference
    {
        m=c;
        images=k;
    }


    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView i=new ImageView(m);
        i.setLayoutParams(new GridView.LayoutParams(320,300));
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Glide
                .with(m)
                .load(images.get(position))
                .into(i);
        return i;


    }
}