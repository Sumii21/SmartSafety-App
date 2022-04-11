package firstapp.apps.kirti.smartsafetyapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Fragment1 extends Fragment {
    ArrayList<String> news;
    ArrayList<String> des;
    ArrayList<String> date1;
    Wrapper w;
    ListView l;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.mylayout11, container, false);
        l= (ListView) v.findViewById(R.id.listView11);
        news=new ArrayList<>();
        des=new ArrayList<>();
        date1=new ArrayList<>();
w=new Wrapper(getActivity());

        try {

            new fetchnews().execute("http://www.tribuneindia.com/rss/feed.aspx?cat_id=22");
            l.setAdapter(new myadapter(getActivity(), android.R.layout.simple_list_item_1, news));


        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error1" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
    private class myadapter extends ArrayAdapter<String> {

        public myadapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater l1=(LayoutInflater)  getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row=l1.inflate(R.layout.newslayout,parent,false);

            TextView t1=(TextView)row.findViewById(R.id.textView13);
            TextView t2=(TextView)row.findViewById(R.id.textView15);
            TextView t3=(TextView)row.findViewById(R.id.textView16);

            t1.setText(news.get(position));
            t2.setText(des.get(position));
            t3.setText(date1.get(position));
            return  row;
        }
    }
    public class fetchnews extends AsyncTask<String,Void,Wrapper> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Wrapper doInBackground(String... params) {
            try {
                URL siteurl=new URL(params[0]);
                DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
                DocumentBuilder db=documentBuilderFactory.newDocumentBuilder();
                Document document=db.parse(new InputSource(siteurl.openStream()));
                document.normalizeDocument();
                NodeList nodeList=document.getElementsByTagName("item");
                for(int x=0; x<nodeList.getLength();x++)
                {
                    Node mynode=nodeList.item(x);
                    Element element=(Element)mynode;
                    NodeList myNodeList1=element.getElementsByTagName("title");
                    NodeList myNodeList2=element.getElementsByTagName("description");
                    NodeList myNodeList3=element.getElementsByTagName("pubDate");
                    Element element1=(Element)myNodeList1.item(0);
                    Element element2=(Element)myNodeList2.item(0);
                    Element element3=(Element)myNodeList3.item(0);
                    myNodeList1=element1.getChildNodes();
                    myNodeList2=element2.getChildNodes();
                    myNodeList3=element3.getChildNodes();
                    news.add(myNodeList1.item(0).getNodeValue());
                    des.add(myNodeList2.item(0).getNodeValue());
                    date1.add(myNodeList3.item(0).getNodeValue());
w.news1=news;
                    w.des1=des;
                    w.date2=date1;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return w;
        }

        @Override
        protected void onPostExecute(Wrapper strings) {

        }
    }

}
