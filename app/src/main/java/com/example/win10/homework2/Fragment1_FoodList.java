package com.example.win10.homework2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Fragment1_FoodList extends Fragment {

    public ArrayList arraylist = new ArrayList();

    private TextView tv;

    private static String URL = "http://ybu.edu.tr/sks/";

    //private ProgressDialog progressDialog;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment1_foodlist_layout,container,false);

        tv = (TextView) view.findViewById(R.id.textView_foodList);

        new Fragment1_FoodList.GetData().execute();

        return view;
    }



    private class GetData extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            arraylist.clear();

            /*
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Foodlist");
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
            */


        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document doc = Jsoup.connect(URL).timeout(30*1000).get();

                Element table = doc.select("table").first();

                Iterator<Element> ite = table.select("td").iterator();

                ite.next();

                while(ite.hasNext()){
                    arraylist.add(ite.next().text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);

            String foodlist = "";

            for(int i =0; i<arraylist.size(); i++){
                foodlist = foodlist + "\n" + arraylist.get(i).toString();
            }

            tv.setText(foodlist);

            //progressDialog.dismiss();

        }


    }


}


