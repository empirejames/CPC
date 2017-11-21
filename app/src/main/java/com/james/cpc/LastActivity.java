package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.james.cpc.dataBase.TinyDB;

/**
 * Created by 101716 on 2017/11/15.
 */

public class LastActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener{
    public String TAG = LastActivity.class.getSimpleName();
    TextView countryName, curStation, curtel, stationName;
    TextView curStates, curPM, curAQI, curPublishTime;
    TextView oil_supply_92,oil_supply_95,oil_supply_98,oil_supply_disol,oil_supply_Alloc,ecard,yoyocard,happycash, members, creditself, washCar, activityTime;
    TextView oil_supply_92_price, oil_supply_95_price, oil_supply_98_price, oil_supply_disol_price, oil_supply_Alloc_price;
    String gas92, gas95, gas98, disol, alocal;
    private SwipeRefreshLayout laySwipe;
    LinearLayout bgElement ;
    String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
    String location, curgas92, curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf
            ,curWashcar,curyoyocard,curecard,curhappycash,curactivitytime;
    private ImageView img_right, img_left;
    TinyDB tinydb;
    ProgressBar psBarAQI, psBarPM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(this);
        tinydb = new TinyDB(this);
        getData();
        LinearLayout dot_map = (LinearLayout )findViewById(R.id.locationLayout);
        dot_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap(countryNameS+location);
            }
        });
        LinearLayout mear_map = (LinearLayout )findViewById(R.id.locationpath);
        mear_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap(countryNameS+location);
            }
        });
    }
    public void goToMap(String getGpsLocation){
        String vDirectionUrl = "https://maps.google.com/maps?q=" + getGpsLocation;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(vDirectionUrl));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }
    @Override
    public void onRefresh() {
        Intent i = new Intent(LastActivity.this, ViewPageController.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        laySwipe.setRefreshing(false);
        finish();
    }

    public void getData() {
        Log.e(TAG,"getData 2 ....");
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
        gas92 = tinydb.getString("92");
        gas95 = tinydb.getString("95");
        gas98 = tinydb.getString("98");
        disol = tinydb.getString("disol");
        alocal = tinydb.getString("alloc");
        setupView(location,countryNameS,curStationS,curte1S,curStatesS,curPMS,curAQIS,curPublishTimeS,curgas92
                ,curgas95,curgas98,curgasAlcool,curdisol,curmember,curcreditshelf,curWashcar,curyoyocard,curecard
                ,curhappycash,curactivitytime,gas92,gas95,gas98,alocal,disol);
    }
    public void setupView(String location, String a, String b, String c, String d, String e, String f, String g, String h
            , String i, String j, String k, String l, String m, String n, String o, String p, String q, String r, String s
            ,String t,String u,String v, String w, String x) {
        String []pmData = e.split(":");
        String []AQIData = f.split(":");
        String AQINumber = AQIData[1].trim();
        String PMNumber = pmData[1].trim();
        countryName = (TextView) findViewById(R.id.countryName);
        curStation = (TextView) findViewById(R.id.curStation);
        stationName = (TextView)findViewById(R.id.stationName);
        curtel = (TextView) findViewById(R.id.curtel);
        curStates = (TextView) findViewById(R.id.curStates);
        curPM = (TextView) findViewById(R.id.curPM);
        curAQI = (TextView) findViewById(R.id.curAQI);
        psBarAQI = (ProgressBar)findViewById(R.id.progressBarAQI);
        psBarAQI.setMax(400);
        psBarPM = (ProgressBar)findViewById(R.id.progressBarPM);
        psBarPM.setMax(100);
        if(AQINumber.equals("")|| PMNumber.equals("")){
            AQINumber = "0";
            PMNumber = "0";
        }
        if(Integer.parseInt(AQINumber)<50){
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        }else if(Integer.parseInt(AQINumber)>50 && Integer.parseInt(AQINumber)<150){
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_blue));
        }else if(Integer.parseInt(AQINumber)>150 ){
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_red));
        }
        if(Integer.parseInt(PMNumber)>60){
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_red));
        }else if(Integer.parseInt(PMNumber)<60 &Integer.parseInt(PMNumber)>35){
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_blue));
        }else if(Integer.parseInt(PMNumber)<35){
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        }
        psBarAQI.setProgress(Integer.parseInt(AQINumber));
        psBarPM.setProgress(Integer.parseInt(PMNumber));
        curPublishTime = (TextView) findViewById(R.id.curPublishTime);
        oil_supply_92 = (TextView) findViewById(R.id.oil_supply_92);
        oil_supply_95 = (TextView) findViewById(R.id.oil_supply_95);
        oil_supply_98 = (TextView) findViewById(R.id.oil_supply_98);
        oil_supply_disol = (TextView) findViewById(R.id.oil_supply_disol);
        oil_supply_Alloc = (TextView) findViewById(R.id.oil_supply_Alloc);
        oil_supply_92_price = (TextView) findViewById(R.id.oil_supply_92_price);
        oil_supply_95_price = (TextView) findViewById(R.id.oil_supply_95_price);
        oil_supply_98_price = (TextView) findViewById(R.id.oil_supply_98_price);
        oil_supply_disol_price = (TextView) findViewById(R.id.oil_supply_disol_price);
        oil_supply_Alloc_price = (TextView) findViewById(R.id.oil_supply_Alloc_price);
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
            oil_supply_92_price.setVisibility(View.VISIBLE);
            oil_supply_92_price.setText(t);
        }
        if (i.equals("1")) {
            oil_supply_95.setVisibility(View.VISIBLE);
            oil_supply_95_price.setVisibility(View.VISIBLE);
            oil_supply_95_price.setText(u);
        }
        if (j.equals("1")) {
            oil_supply_98.setVisibility(View.VISIBLE);
            oil_supply_98_price.setVisibility(View.VISIBLE);
            oil_supply_98_price.setText(v);
        }
        if (k.equals("1")) {
            oil_supply_Alloc.setVisibility(View.VISIBLE);
            oil_supply_Alloc_price.setVisibility(View.VISIBLE);
            oil_supply_Alloc_price.setText(w);
        }
        if (l.equals("1")) {
            oil_supply_disol.setVisibility(View.VISIBLE);
            oil_supply_disol_price.setVisibility(View.VISIBLE);
            oil_supply_disol_price.setText(x);
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

        countryName.setText("導航至中油" +b);
        stationName.setText(b);
        curStation.setText(a+location);
        curtel.setText("距離約 " +c);
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
