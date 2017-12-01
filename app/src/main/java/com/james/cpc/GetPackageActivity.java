package com.james.cpc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.item.ListItem;
import com.james.cpc.adapter.PackageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/11/22.
 */

public class GetPackageActivity extends AppCompatActivity {
    private String[] eventArray, detailArray, packageArray;
    final List<String> allTitles = new ArrayList<String>();
    final List<String> allDetails = new ArrayList<String>();
    final List<String> allPackageInfos = new ArrayList<String>();
    ArrayList<ListItem> myListData = new ArrayList<ListItem>();
    private PackageAdapter myAdapter;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_package);
        addData();
    }

    public void addData(){
        allTitles.add("奇怪拍賣 商品已到中油-翁子站");
        allDetails.add("請於 2017/12/18 號前取貨");
        allPackageInfos.add("A層倉庫 F2架 朱先生 電話，0988678910");

        allTitles.add("PCRoom 商品已到中油-東山路站");
        allDetails.add("請於 2017/12/19 號前取貨");
        allPackageInfos.add("B2倉庫 A2架上 林先生 電話，0978578123");

        eventArray = new String[allTitles.size()];
        detailArray = new String[allDetails.size()];
        packageArray = new String[allPackageInfos.size()];

        eventArray = allTitles.toArray(eventArray);
        detailArray = allDetails.toArray(detailArray);
        packageArray = allPackageInfos.toArray(packageArray);

        for (int i = 0; i < allTitles.size(); i++) {
            myListData.add(new ListItem(eventArray[i],detailArray[i],packageArray[i]));
        }
        myAdapter = new PackageAdapter(getApplicationContext(), myListData);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(onClickListView);

    }
    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //Toast.makeText(getBaseContext(), "click # " + (position + 1) + "\n", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("QRdata",myListData.get(position).getDetail());
            intent.setClass(GetPackageActivity.this, QRCodeGenActivity.class);
            startActivity(intent);

        }
    };
}
