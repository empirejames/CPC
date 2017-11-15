package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 101716 on 2017/11/15.
 */

public class MiddleActivity extends Activity {
    public String TAG = MiddleActivity.class.getSimpleName();
    TextView countryName, curStation, curtel;
    TextView curStates, curPM, curAQI, curPublishTime;
    LinearLayout bgElement ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        getData();
    }
    public void getData() {
        Log.e(TAG,"getData 1....");
        String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA1", Context.MODE_PRIVATE);
        countryNameS = prefs.getString("countryName1", null);
        curStationS = prefs.getString("curStation1", null);
        curte1S = prefs.getString("curDistance1", null);
        curStatesS = prefs.getString("curStates1", null);
        curPMS = prefs.getString("curPM1", null);
        curAQIS = prefs.getString("curAQI1", null);
        curPublishTimeS = prefs.getString("curPublishTime1", null);
        Log.e(TAG,"Middle curte1S "  + curte1S);
        setupView(countryNameS,curStationS,curte1S,curStatesS,curPMS,curAQIS,curPublishTimeS);
    }
    public void setupView(String a, String b, String c, String d, String e, String f, String g) {
        countryName =  findViewById(R.id.countryName);
        curStation =  findViewById(R.id.curStation);
        curtel =  findViewById(R.id.curtel);
        curStates =  findViewById(R.id.curStates);
        curPM = findViewById(R.id.curPM);
        curAQI =  findViewById(R.id.curAQI);
        curPublishTime = findViewById(R.id.curPublishTime);

        countryName.setText(a);
        curStation.setText(b);
        curtel.setText(c);
        curStates.setText(d);
        curPM.setText(e);
        curAQI.setText(f);
        curPublishTime.setText(g);
        if(d.equals("良好")){
            bgElement.setBackgroundResource(R.drawable.bgd_great);
        }else if(d.equals("普通")){
            bgElement.setBackgroundResource(R.drawable.bgd);
        }else{
            bgElement.setBackgroundResource(R.drawable.bgd_danger);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e(TAG,"MiddleActivity onResume");

    }
    @Override
    public void onStop(){
        super.onStop();
        Log.e(TAG,"MiddleActivity onStop");

    }
}
