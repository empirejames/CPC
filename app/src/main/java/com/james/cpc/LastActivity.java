package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by 101716 on 2017/11/15.
 */

public class LastActivity extends Activity {
    public String TAG = LastActivity.class.getSimpleName();
    TextView countryName, curStation, curtel;
    TextView curStates, curPM, curAQI, curPublishTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getData();
    }

    public void getData() {
        Log.e(TAG,"getData 2....");
        String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA2", Context.MODE_PRIVATE);
        countryNameS = prefs.getString("countryName2", null);
        curStationS = prefs.getString("curStation2", null);
        curte1S = prefs.getString("curDistance2", null);
        curStatesS = prefs.getString("curStates2", null);
        curPMS = prefs.getString("curPM2", null);
        curAQIS = prefs.getString("curAQI2", null);
        curPublishTimeS = prefs.getString("curPublishTime2", null);
        setupView(countryNameS,curStationS,curte1S,curStatesS,curPMS,curAQIS,curPublishTimeS);
    }
    public void setupView(String a, String b, String c, String d, String e, String f, String g) {
        countryName = (TextView) findViewById(R.id.countryName);
        curStation = (TextView) findViewById(R.id.curStation);
        curtel = (TextView) findViewById(R.id.curtel);
        curStates = (TextView) findViewById(R.id.curStates);
        curPM = (TextView) findViewById(R.id.curPM);
        curAQI = (TextView) findViewById(R.id.curAQI);
        curPublishTime = (TextView) findViewById(R.id.curPublishTime);

        countryName.setText(a);
        curStation.setText(b);
        curtel.setText(c);
        curStates.setText(d);
        curPM.setText(e);
        curAQI.setText(f);
        curPublishTime.setText(g);
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG,"LastActivity onResume");

    }
    @Override
    public void onStop(){
        super.onStop();
        Log.e(TAG,"LastActivity onStop");

    }
}
