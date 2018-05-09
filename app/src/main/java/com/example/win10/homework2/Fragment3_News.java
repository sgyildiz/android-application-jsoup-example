package com.example.win10.homework2;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Fragment3_News extends Fragment {


    private ListView lv;
    public ArrayList arraylist_News = new ArrayList();

    public ArrayList arrayList_Links = new ArrayList();

    private ArrayAdapter<String> adapter;

    private static String URL = "http://ybu.edu.tr/muhendislik/bilgisayar/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment3_news_layout, container, false);

        lv = (ListView) view.findViewById(R.id.listView_news);

        new Fragment3_News.GetNews().execute();

        adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, arraylist_News);


        return view;
    }


    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            arraylist_News.clear();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document doc = Jsoup.connect(URL).timeout(30*1000).get();

                Element title = doc.select("div.cnContent").first();

                Iterator<Element> ite = title.select("div.cncItem").iterator();

                Iterator<Element> ite2 = title.select("div.cnButton").iterator();

                ite.next();


                while (ite.hasNext()){
                    Element div = ite.next();

                    arraylist_News.add((div.text()));
                    arrayList_Links.add(div.select("a").attr("href"));
                }
                while(ite2.hasNext()){
                    Element div = ite2.next();

                    arraylist_News.add((div.text()));
                    arrayList_Links.add(div.select("a").attr("href"));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ArrayList<String> a = new ArrayList<String>();

                    for(int i=0; i<arrayList_Links.size(); i++){
                        a.add("http://www.ybu.edu.tr/muhendislik/bilgisayar/" +arrayList_Links.get(i).toString());
                    }

                    Uri uri = Uri.parse(a.get(position));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }
            });


        }
    }
}


