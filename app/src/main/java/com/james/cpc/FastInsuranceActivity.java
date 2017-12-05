package com.james.cpc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.adapter.FastInsuranceAdapter;
import com.james.cpc.adapter.SpecialShopAdapter;
import com.james.cpc.dataBase.TinyDB;
import com.james.cpc.item.FastListItem;
import com.james.cpc.item.SpecialListItem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by 101716 on 2017/11/22.
 */

public class FastInsuranceActivity extends AppCompatActivity {
    private String[] stationNameArray, stationLocationArray, stationTelArray, serviceArray;
    final List<String> stationName = new ArrayList<String>();
    final List<String> stationLocation = new ArrayList<String>();
    final List<String> stationTel = new ArrayList<String>();
    final List<String> service = new ArrayList<String>();
    ArrayList<FastListItem> myListData = new ArrayList<FastListItem>();
    private FastInsuranceAdapter myAdapter;
    private ListView listView;
    private String[] stationlist;
    private String contryName;
    private String TAG = FastInsuranceActivity.class.getSimpleName();
    private TinyDB tinydb;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastinsurance);
        tinydb = new TinyDB(FastInsuranceActivity.this);
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
            reader = new CSVReader(new BufferedReader(new InputStreamReader(getAssets().open("fast.csv"), "UTF-8")));
            while ((stationlist = reader.readNext()) != null) {

                if (location.equals(parserLocation(stationlist[2]))) {
                    myListData.add(new FastListItem(stationlist[1], stationlist[4], stationlist[3], stationlist[2]));
                }
            }
            if(myListData.size()==0){
                myListData.add(new FastListItem("無資訊","無" ,"無" ,"無" ));
            }
            myAdapter = new FastInsuranceAdapter(getApplicationContext(), myListData);
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(onClickListView);
        } catch (Exception e) {
            Log.e(TAG, "Exception : " + e.toString());
        }
        Log.e(TAG, "CSV store finish . ");
    }
    public String parserLocation(String path){
        String [] temp = new String[3];
        if(path.split(" ").length==1){
            temp[0] = path.substring(0,3);
        }else{
            temp = path.split(" ");
            temp[1] = temp[1].substring(0,3);
        }
        Log.e(TAG,"temp " + temp[1]);
        return temp[1];
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
