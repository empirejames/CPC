package com.james.cpc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.item.InfoItem;
import com.james.cpc.adapter.NoticeListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/11/22.
 */

public class InfoTotalActivity extends AppCompatActivity {
    private String TAG = InfoTotalActivity.class.getSimpleName();
    private String[] eventArray, detailArray, packageArray;
    private Integer[] picsource;
    final List<Integer> allpics = new ArrayList<Integer>();
    final List<String> allTitles = new ArrayList<String>();
    final List<String> allDetails = new ArrayList<String>();
    final List<String> allPackageInfos = new ArrayList<String>();
    ArrayList<InfoItem> myListData = new ArrayList<InfoItem>();
    private NoticeListAdapter myAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        addData();
    }

    public void addData(){
        allpics.add(R.drawable.gun);
        allTitles.add("油耗提醒");
        allDetails.add("已開 450 公里請於最近的加油站補給");
        allPackageInfos.add("A層倉庫 F2架 朱先生 電話，0988678910");

        allpics.add(R.drawable.pollution);
        allTitles.add("空氣汙染提醒");
        allDetails.add("請減少戶外活動時間，並配戴或購買口罩");
        allPackageInfos.add("B2倉庫 A2架上 林先生 電話，0978578123");

        allpics.add(R.drawable.member);
        allTitles.add("會員提醒");
        allDetails.add("目前尚有157860點可兌換");
        allPackageInfos.add("B2倉庫 A2架上 林先生 電話，0978578123");

        allpics.add(R.drawable.cpc);
        allTitles.add("中油好康提醒");
        allDetails.add("近期油耗不佳，建議可添加中油油精，優惠價50元");
        allPackageInfos.add("B2倉庫 A2架上 林先生 電話，0978578123");
        picsource = new Integer[allpics.size()];
        eventArray = new String[allTitles.size()];
        detailArray = new String[allDetails.size()];
        packageArray = new String[allPackageInfos.size()];

        picsource = allpics.toArray(picsource);
        eventArray = allTitles.toArray(eventArray);
        detailArray = allDetails.toArray(detailArray);
        packageArray = allPackageInfos.toArray(packageArray);

        for (int i = 0; i < allTitles.size(); i++) {
            myListData.add(new InfoItem(picsource[i],eventArray[i],detailArray[i],packageArray[i]));
        }
        myAdapter = new NoticeListAdapter(getApplicationContext(), myListData);
        listView = (ListView) findViewById(R.id.listView_info);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(onClickListView);

    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position ==0){
            Intent intent = new Intent();
            intent.setClass(InfoTotalActivity.this, GasInfoActivity.class);
            startActivity(intent);

            }
            //Toast.makeText(getBaseContext(), "click # " + (position + 1) + "\n", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent();
//            intent.putExtra("QRdata",myListData.get(position).getDetail());
//            intent.setClass(InfoTotalActivity.this, QRCodeGenActivity.class);
//            startActivity(intent);

        }
    };
}
