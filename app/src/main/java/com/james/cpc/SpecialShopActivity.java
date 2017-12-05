package com.james.cpc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.adapter.PackageAdapter;
import com.james.cpc.adapter.SpecialShopAdapter;
import com.james.cpc.dataBase.TinyDB;
import com.james.cpc.item.InvoiceItem;
import com.james.cpc.item.ListItem;
import com.james.cpc.item.SpecialListItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by 101716 on 2017/11/22.
 */

public class SpecialShopActivity extends AppCompatActivity {
    private String[] storeNameArray, specialDetailArray, shopTelArray, googlePilotArray;
    final List<String> storeName = new ArrayList<String>();
    final List<String> specialDetail = new ArrayList<String>();
    final List<String> shopTel = new ArrayList<String>();
    final List<String> googlePilot = new ArrayList<String>();
    ArrayList<SpecialListItem> myListData = new ArrayList<SpecialListItem>();
    private SpecialShopAdapter myAdapter;
    private ListView listView;
    private String[] storelsit;
    private String contryName;
    private String TAG = SpecialShopActivity.class.getSimpleName();
    private TinyDB tinydb;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialshop);
        tinydb = new TinyDB(SpecialShopActivity.this);
        readDB();
        CSVShopRead();
        //addData();
    }

    public void readDB() {
        location = tinydb.getString("country");
        Log.e(TAG, "country : " + location);
        //Log.e(TAG,"isWashCar : " + isWashCar + " isGasSelf : " + isGasSelf);
    }

    public void CSVShopRead() {
        CSVReader reader = null;
        Log.e(TAG, "CSVShopRead .... ");
        try {
            reader = new CSVReader(new BufferedReader(new InputStreamReader(getAssets().open("special.csv"), "UTF-8")));
            while ((storelsit = reader.readNext()) != null) {
                if (location.equals(storelsit[0])) {
                    myListData.add(new SpecialListItem(storelsit[2], storelsit[3], storelsit[5], storelsit[6]));
                }
            }
            if(myListData.size()==0){
                myListData.add(new SpecialListItem("無資訊","無" ,"無" ,"無" ));
            }
            myAdapter = new SpecialShopAdapter(getApplicationContext(), myListData);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(onClickListView);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.toString());
        }
        Log.e(TAG, "CSV store finish . ");
    }

    public void addData() {
//        storeName.add("黑橋牌-長安門市");
//        specialDetail.add("黑橋自製品95折");
//        shopTel.add("07-7105525");
//        googlePilot.add("高雄市鳳山區中山路146之4號");
//
//        storeNameArray = new String[storeName.size()];
//        specialDetailArray = new String[specialDetail.size()];
//        shopTelArray = new String[shopTel.size()];
//        googlePilotArray = new String[googlePilot.size()];
//
//        storeNameArray = storeName.toArray(storeNameArray);
//        specialDetailArray = specialDetail.toArray(specialDetailArray);
//        shopTelArray = shopTel.toArray(shopTelArray);
//        googlePilotArray = googlePilot.toArray(googlePilotArray);

//        for (int i = 0; i < storeName.size(); i++) {
//            myListData.add(new SpecialListItem(storeNameArray[i],specialDetailArray[i],shopTelArray[i],googlePilotArray[i]));
//        }
//        myAdapter = new SpecialShopAdapter(getApplicationContext(), myListData);
//        listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(myAdapter);
//        listView.setOnItemClickListener(onClickListView);

    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(getBaseContext(), "click # " + (position + 1) + "\n", Toast.LENGTH_SHORT).show();
            goToMap(myListData.get(position).getGooglePilot());
        }
    };

    public void goToMap(String getGpsLocation) {
        String vDirectionUrl = "https://maps.google.com/maps?q=" + getGpsLocation;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vDirectionUrl));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
}
