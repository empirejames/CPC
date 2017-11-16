package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by 101716 on 2017/11/15.
 */

public class LastActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    public String TAG = LastActivity.class.getSimpleName();
    TextView countryName, curStation, curtel;
    TextView curStates, curPM, curAQI, curPublishTime;
    TextView oil_supply_92,oil_supply_95,oil_supply_98,oil_supply_disol,oil_supply_Alloc,ecard,yoyocard,happycash, members, creditself, washCar, activityTime;
    private SwipeRefreshLayout laySwipe;
    LinearLayout bgElement ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(this);
        getData();
    }
    @Override
    public void onRefresh() {
        Intent i = new Intent(LastActivity.this, ViewPageController.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        laySwipe.setRefreshing(false);
    }

    public void getData() {
        Log.e(TAG,"getData 2 ....");
        String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
        String location,curgas92, curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf
                ,curWashcar,curyoyocard,curecard,curhappycash,curactivitytime;
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA2",Context.MODE_PRIVATE);
        location = prefs.getString("curlocation2", null);
        countryNameS = prefs.getString("countryName2", null);
        curStationS = prefs.getString("curStation2", null);
        curte1S = prefs.getString("curDistance2", null);
        curStatesS = prefs.getString("curStates2", null);
        curPMS = prefs.getString("curPM2", null);
        curAQIS = prefs.getString("curAQI2", null);
        curPublishTimeS = prefs.getString("curPublishTime2", null);
        curgas92 = prefs.getString("curgas922", null);
        curgas95= prefs.getString("curgas952", null);
        curgas98= prefs.getString("curgas982", null);
        curgasAlcool= prefs.getString("curgasAlcool2", null);
        curdisol= prefs.getString("curdisol2", null);
        curmember= prefs.getString("curmember2", null);
        curcreditshelf= prefs.getString("curcreditshelf2", null);
        curWashcar= prefs.getString("curWashcar2", null);
        curyoyocard= prefs.getString("curyoyocard2", null);
        curecard= prefs.getString("curecard2", null);
        curhappycash= prefs.getString("curhappycash2", null);
        curactivitytime= prefs.getString("curactivitytime2", null);
        setupView(location,countryNameS,curStationS,curte1S,curStatesS,curPMS,curAQIS,curPublishTimeS,curgas92
                ,curgas95,curgas98,curgasAlcool,curdisol,curmember,curcreditshelf,curWashcar,curyoyocard,curecard
                ,curhappycash,curactivitytime);
    }
    public void setupView(String location, String a, String b, String c, String d, String e, String f, String g, String h
            , String i, String j, String k, String l, String m, String n, String o, String p, String q, String r, String s) {
        countryName = (TextView) findViewById(R.id.countryName);
        curStation = (TextView) findViewById(R.id.curStation);
        curtel = (TextView) findViewById(R.id.curtel);
        curStates = (TextView) findViewById(R.id.curStates);
        curPM = (TextView) findViewById(R.id.curPM);
        curAQI = (TextView) findViewById(R.id.curAQI);
        curPublishTime = (TextView) findViewById(R.id.curPublishTime);
        oil_supply_92 = (TextView) findViewById(R.id.oil_supply_92);
        oil_supply_95 = (TextView) findViewById(R.id.oil_supply_95);
        oil_supply_98 = (TextView) findViewById(R.id.oil_supply_98);
        oil_supply_disol = (TextView) findViewById(R.id.oil_supply_disol);
        oil_supply_Alloc = (TextView) findViewById(R.id.oil_supply_Alloc);
        members = (TextView) findViewById(R.id.members);
        creditself = (TextView) findViewById(R.id.creditself);
        ecard = (TextView) findViewById(R.id.ecard);
        yoyocard = (TextView) findViewById(R.id.yoyocard);
        happycash = (TextView) findViewById(R.id.happycash);
        washCar = (TextView) findViewById(R.id.washCar);
        activityTime = (TextView) findViewById(R.id.activityTime);
        Log.e(TAG, h + " i " + i + " j " + j + " k " + k + " l " + l + " m " + m + " n " + n + " o " + o + " p " + p + " q " + q + " r " + r + " s " + s);
        if (h.equals("1")) {
            oil_supply_92.setVisibility(View.VISIBLE);
        }
        if (i.equals("1")) {
            oil_supply_95.setVisibility(View.VISIBLE);
        }
        if (j.equals("1")) {
            oil_supply_98.setVisibility(View.VISIBLE);
        }
        if (k.equals("1")) {
            oil_supply_Alloc.setVisibility(View.VISIBLE);
        }
        if (l.equals("1")) {
            oil_supply_disol.setVisibility(View.VISIBLE);
        }
        members.setVisibility(View.VISIBLE);
        if (m.equals("0")) {
            members.setText("無");
        }
        creditself.setVisibility(View.VISIBLE);
        if (n.equals("0")) {
            creditself.setText("無");
        }
        washCar.setVisibility(View.VISIBLE);
        if (o.length() == 0) {
            washCar.setText("無");
        } else {
            washCar.setText(o);
        }
        yoyocard.setVisibility(View.VISIBLE);
        if (p.equals("0")) {
            yoyocard.setText("無");
        }
        ecard.setVisibility(View.VISIBLE);
        if (q.equals("0")) {
            ecard.setText("");
        }
        happycash.setVisibility(View.VISIBLE);
        if (r.equals("0")) {
            happycash.setText("");
        }
        activityTime.setVisibility(View.VISIBLE);
        activityTime.setText(s);


        countryName.setText(a);
        curStation.setText(location);
        curtel.setText(c);
        curStates.setText(d);
        curPM.setText(e);
        curAQI.setText(f);
        curPublishTime.setText(g);
        if (d.equals("良好")) {
            bgElement.setBackgroundResource(R.drawable.bgd_great);
        } else if (d.equals("普通")) {
            bgElement.setBackgroundResource(R.drawable.bgd);
        } else {
            bgElement.setBackgroundResource(R.drawable.bgd_danger);
        }
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
