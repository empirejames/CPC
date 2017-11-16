package com.james.cpc;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    TextView countryName, curStation, curtel;
    TextView curStates, curPM, curAQI, curPublishTime;
    TextView oil_supply_92,oil_supply_95,oil_supply_98,oil_supply_disol,oil_supply_Alloc,ecard,yoyocard,happycash, members, creditself, washCar, activityTime;
    LinearLayout bgElement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "MainActivity onResume");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "MainActivity onStop");

    }

    public void getData() {
        Log.e(TAG,"getData 0 ....");
        String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
        String curgas92, curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf
                ,curWashcar,curyoyocard,curecard,curhappycash,curactivitytime;
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA",Context.MODE_PRIVATE);
        countryNameS = prefs.getString("countryName", null);
        curStationS = prefs.getString("curStation", null);
        curte1S = prefs.getString("curDistance", null);
        curStatesS = prefs.getString("curStates", null);
        curPMS = prefs.getString("curPM", null);
        curAQIS = prefs.getString("curAQI", null);
        curPublishTimeS = prefs.getString("curPublishTime", null);
        curgas92 = prefs.getString("curgas92", null);
        curgas95= prefs.getString("curgas95", null);
        curgas98= prefs.getString("curgas98", null);
        curgasAlcool= prefs.getString("curgasAlcool", null);
        curdisol= prefs.getString("curdisol", null);
        curmember= prefs.getString("curmember", null);
        curcreditshelf= prefs.getString("curcreditshelf", null);
        curWashcar= prefs.getString("curWashcar", null);
        curyoyocard= prefs.getString("curyoyocard", null);
        curecard= prefs.getString("curecard", null);
        curhappycash= prefs.getString("curhappycash", null);
        curactivitytime= prefs.getString("curactivitytime", null);
        setupView(countryNameS,curStationS,curte1S,curStatesS,curPMS,curAQIS,curPublishTimeS,curgas92
        ,curgas95,curgas98,curgasAlcool,curdisol,curmember,curcreditshelf,curWashcar,curyoyocard,curecard
        ,curhappycash,curactivitytime);
    }
    public void setupView(String a, String b, String c, String d, String e, String f, String g,String h
            ,String i,String j,String k,String l,String m,String n,String o,String p,String q,String r,String s) {
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
    Log.e(TAG,h+" i "+i+" j "+j+" k "+k+" l "+l+" m "+m+" n "+n+" o "+o+" p "+p+" q "+q+" r "+r+" s "+s);
        if(h.equals("1")){
            oil_supply_92.setVisibility(View.VISIBLE);
        }
        if(i.equals("1")){
            oil_supply_95.setVisibility(View.VISIBLE);
        }
        if(j.equals("1")){
            oil_supply_98.setVisibility(View.VISIBLE);
        }
        if(k.equals("1")){
            oil_supply_Alloc.setVisibility(View.VISIBLE);
        }
        if(l.equals("1")){
            oil_supply_disol.setVisibility(View.VISIBLE);
        }
        if(m.equals("1")){
            members.setVisibility(View.VISIBLE);
        }
        if(n.equals("1")){
            creditself.setVisibility(View.VISIBLE);
        }
        washCar.setVisibility(View.VISIBLE);
        if(o.length() ==0){
            washCar.setText("無洗車");
        }else{
            washCar.setText(o);
        }

        if(p.equals("1")){
            yoyocard.setVisibility(View.VISIBLE);
        }
        if(q.equals("1")){
            ecard.setVisibility(View.VISIBLE);
        }
        if(r.equals("1")){
            happycash.setVisibility(View.VISIBLE);
        }
        activityTime.setVisibility(View.VISIBLE);
        activityTime.setText(s);


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
}
