package com.james.cpc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.james.cpc.adapter.CPCListAdapter;
import com.james.cpc.adapter.NoticeListAdapter;
import com.james.cpc.item.InfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 101716 on 2017/11/22.
 */

public class CPCProduceActivity extends AppCompatActivity {
    private String TAG = CPCProduceActivity.class.getSimpleName();
    private String[] eventArray, detailArray, packageArray;
    private Integer[] picsource;
    final List<Integer> allpics = new ArrayList<Integer>();
    final List<String> allTitles = new ArrayList<String>();
    final List<String> allDetails = new ArrayList<String>();
    final List<String> allPackageInfos = new ArrayList<String>();
    ArrayList<InfoItem> myListData = new ArrayList<InfoItem>();
    private CPCListAdapter myAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        addData();
    }

    public void addData() {
        allpics.add(R.drawable.gascpc);
        allTitles.add("國光牌 APEX 汽油清淨劑(2入裝)");
        allDetails.add("會員價 : $385");
        allPackageInfos.add("○恢復馬力 \n" +
                "○多效合一 \n" +
                "○高除淨力 \n" +
                "○高品質、多效、環保");

        allpics.add(R.drawable.gasdesao);
        allTitles.add("國光牌 特優級SJ/CD 車用機油 15W40 -4公升");
        allDetails.add("會員價 : $500");
        allPackageInfos.add("◆符合美國石油學會API SJ/CD性能規範 \n" +
                "◆本油品黏度為SAE 15W/40 \n" +
                "◆春夏秋冬四季均可使用 \n" +
                "◆以精煉之石蠟基油料，摻配高效能的抗氧化 \n" +
                "◆清淨分散、抗磨耗、防銹、防腐蝕、消泡等添加劑而成 \n" +
                "◆其有優異的抗氧化性、清淨分散及抗磨耗性能 \n" +
                "◆提供車輛引擎運轉所需的各項保護性能");

        picsource = new Integer[allpics.size()];
        eventArray = new String[allTitles.size()];
        detailArray = new String[allDetails.size()];
        packageArray = new String[allPackageInfos.size()];

        picsource = allpics.toArray(picsource);
        eventArray = allTitles.toArray(eventArray);
        detailArray = allDetails.toArray(detailArray);
        packageArray = allPackageInfos.toArray(packageArray);

        for (int i = 0; i < allTitles.size(); i++) {
            myListData.add(new InfoItem(picsource[i], eventArray[i], detailArray[i], packageArray[i]));
        }
        myAdapter = new CPCListAdapter(getApplicationContext(), myListData);
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
