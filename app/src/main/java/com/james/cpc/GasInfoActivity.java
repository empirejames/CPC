package com.james.cpc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.adapter.GasTotalListAdapter;
import com.james.cpc.adapter.NoticeListAdapter;
import com.james.cpc.item.GasTotalItem;
import com.james.cpc.item.InfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/11/22.
 */

public class GasInfoActivity extends AppCompatActivity {
    private String TAG = GasInfoActivity.class.getSimpleName();
    private String[] date,oil,avgoil,addoil,runKm,totalKm;

    final List<String> alldate = new ArrayList<String>();
    final List<String> alloil = new ArrayList<String>();
    final List<String> allavgoil = new ArrayList<String>();
    final List<String> alladdoil = new ArrayList<String>();
    final List<String> allrunKm = new ArrayList<String>();
    final List<String> alltotalKm = new ArrayList<String>();
    ArrayList<GasTotalItem> myListData = new ArrayList<GasTotalItem>();
    private GasTotalListAdapter myAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_gas_info);
        addData();
    }

    public void addData(){
        alldate.add("12/01");
        alloil.add("92");
        allavgoil.add("12.18km/L");
        alladdoil.add("41.48L");
        allrunKm.add("472Km");
        alltotalKm.add("128896公里");

        alldate.add("11/01");
        alloil.add("92");
        allavgoil.add("12.18km/L");
        alladdoil.add("41.48L");
        allrunKm.add("472Km");
        alltotalKm.add("128896公里");

        alldate.add("10/01");
        alloil.add("95");
        allavgoil.add("11.31km/L");
        alladdoil.add("41.48L");
        allrunKm.add("402Km");
        alltotalKm.add("144896公里");

        alldate.add("09/01");
        alloil.add("95");
        allavgoil.add("11.38km/L");
        alladdoil.add("44.48L");
        allrunKm.add("502Km");
        alltotalKm.add("138896公里");

        alldate.add("08/01");
        alloil.add("95");
        allavgoil.add("12.08km/L");
        alladdoil.add("44.48L");
        allrunKm.add("502Km");
        alltotalKm.add("135826公里");

        alldate.add("07/01");
        alloil.add("95");
        allavgoil.add("12.14km/L");
        alladdoil.add("44.48L");
        allrunKm.add("502Km");
        alltotalKm.add("138894公里");

        alldate.add("06/01");
        alloil.add("98");
        allavgoil.add("12.34km/L");
        alladdoil.add("40.40L");
        allrunKm.add("501Km");
        alltotalKm.add("138896公里");

        alldate.add("05/01");
        alloil.add("98");
        allavgoil.add("12.31km/L");
        alladdoil.add("40.41L");
        allrunKm.add("500Km");
        alltotalKm.add("122146公里");

        alldate.add("04/01");
        alloil.add("98");
        allavgoil.add("12.31km/L");
        alladdoil.add("43.41L");
        allrunKm.add("550Km");
        alltotalKm.add("122146公里");

        alldate.add("03/01");
        alloil.add("98");
        allavgoil.add("12.31km/L");
        alladdoil.add("40.41L");
        allrunKm.add("500Km");
        alltotalKm.add("122146公里");

        alldate.add("02/01");
        alloil.add("98");
        allavgoil.add("11.89km/L");
        alladdoil.add("44.41L");
        allrunKm.add("412Km");
        alltotalKm.add("122146公里");

        date = new String[alldate.size()];
        oil = new String[alloil.size()];
        avgoil = new String[allavgoil.size()];
        addoil = new String[alladdoil.size()];
        runKm = new String[allrunKm.size()];
        totalKm = new String[alltotalKm.size()];

        date = alldate.toArray(date);
        oil = alloil.toArray(oil);
        avgoil = allavgoil.toArray(avgoil);
        addoil = alladdoil.toArray(addoil);
        runKm = allrunKm.toArray(runKm);
        totalKm = alltotalKm.toArray(totalKm);

        for (int i = 0; i < alldate.size(); i++) {
            Log.e(TAG,date[i] + " .. . .. ");
            myListData.add(new GasTotalItem(date[i],oil[i],avgoil[i],addoil[i],runKm[i],totalKm[i]));

        }
        myAdapter = new GasTotalListAdapter(getApplicationContext(), myListData);
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
