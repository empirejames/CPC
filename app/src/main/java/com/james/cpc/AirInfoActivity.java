package com.james.cpc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.adapter.AirListAdapter;
import com.james.cpc.adapter.GasTotalListAdapter;
import com.james.cpc.dataBase.TinyDB;
import com.james.cpc.item.AirItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/11/22.
 */

public class AirInfoActivity extends AppCompatActivity {
    private String TAG = AirInfoActivity.class.getSimpleName();
    private String[] date, oil, avgoil, addoil, runKm, totalKm;

    final List<String> alldate = new ArrayList<String>();
    final List<String> alloil = new ArrayList<String>();
    final List<String> allavgoil = new ArrayList<String>();
    final List<String> alladdoil = new ArrayList<String>();
    final List<String> allrunKm = new ArrayList<String>();
    final List<String> alltotalKm = new ArrayList<String>();
    ArrayList<AirItem> myListData = new ArrayList<AirItem>();
    private AirListAdapter myAdapter;
    private ListView listView;
    private TinyDB tinydb;
    private String AQI, CO, CO_8hr, County, NO, NO2, NOx, O3, O3_8hr, PM10, PM10_AVG, PM25, PM25_AVG, PublishTime, SiteName, SO2, Status, WindDirec, WindSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_air_info);
        tinydb = new TinyDB(AirInfoActivity.this);
        readDBValue();
        addData();
    }

    public void readDBValue() {
        AQI = tinydb.getString("AQI");
        CO = tinydb.getString("CO");
        CO_8hr = tinydb.getString("CO_8hr");
        County = tinydb.getString("County");
        NO = tinydb.getString("NO");
        NO2 = tinydb.getString("NO2");
        NOx = tinydb.getString("NOx");
        O3 = tinydb.getString("O3");
        O3_8hr = tinydb.getString("O3_8hr");
        PM10 = tinydb.getString("PM10");
        PM10_AVG = tinydb.getString("PM10_AVG");
        PM25 = tinydb.getString("PM25");
        PM25_AVG = tinydb.getString("PM25_AVG");
        PublishTime = tinydb.getString("PublishTime");
        SiteName = tinydb.getString("SiteName");
        SO2 = tinydb.getString("SO2");
        Status = tinydb.getString("Status");
        WindDirec = tinydb.getString("WindDirec");
        WindSpeed = tinydb.getString("WindSpeed");
    }

    public void addData() {

        myListData.add(new AirItem(AQI, CO, CO_8hr, County, NO, NO2, NOx, O3, O3_8hr, PM10
                , PM10_AVG, PM25, PM25_AVG, PublishTime, SiteName, SO2, Status, WindDirec, WindSpeed));
        myAdapter = new AirListAdapter(getApplicationContext(), myListData);
        listView = (ListView) findViewById(R.id.listView_info);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(onClickListView);

    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Toast.makeText(getBaseContext(), "click # " + (position + 1) + "\n", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent();
//            intent.putExtra("QRdata",myListData.get(position).getDetail());
//            intent.setClass(InfoTotalActivity.this, QRCodeGenActivity.class);
//            startActivity(intent);

        }
    };
}
