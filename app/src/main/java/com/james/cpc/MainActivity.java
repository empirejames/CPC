package com.james.cpc;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = MainActivity.class.getSimpleName();
    TextView countryName, curStation, curtel, stationName;
    TextView curStates, curPM, curAQI, curPublishTime;
    TextView oil_supply_92, oil_supply_95, oil_supply_98, oil_supply_disol, oil_supply_Alloc, ecard, yoyocard, happycash, members, creditself, washCar, activityTime;
    LinearLayout bgElement;
    String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
    String location, curgas92, curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf, curWashcar, curyoyocard, curecard, curhappycash, curactivitytime;
    private SwipeRefreshLayout laySwipe;
    private ImageView img_right, img_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(this);
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
        Intent i = new Intent(MainActivity.this, ViewPageController.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        laySwipe.setRefreshing(false);
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
        Log.e(TAG, "getData 0 ....");
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA0", Context.MODE_PRIVATE);
        location = prefs.getString("curlocation0", null);
        countryNameS = prefs.getString("countryName0", null);
        curStationS = prefs.getString("curStation0", null);
        curte1S = prefs.getString("curDistance0", null);
        curStatesS = prefs.getString("curStates0", null);
        curPMS = prefs.getString("curPM0", null);
        curAQIS = prefs.getString("curAQI0", null);
        curPublishTimeS = prefs.getString("curPublishTime0", null);
        curgas92 = prefs.getString("curgas920", null);
        curgas95 = prefs.getString("curgas950", null);
        curgas98 = prefs.getString("curgas980", null);
        curgasAlcool = prefs.getString("curgasAlcool0", null);
        curdisol = prefs.getString("curdisol0", null);
        curmember = prefs.getString("curmember0", null);
        curcreditshelf = prefs.getString("curcreditshelf0", null);
        curWashcar = prefs.getString("curWashcar0", null);
        curyoyocard = prefs.getString("curyoyocard0", null);
        curecard = prefs.getString("curecard0", null);
        curhappycash = prefs.getString("curhappycash0", null);
        curactivitytime = prefs.getString("curactivitytime0", null);
        setupView(location,countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS, curgas92
                , curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf, curWashcar, curyoyocard, curecard
                , curhappycash, curactivitytime);
    }
    public void setupView(String location,String a, String b, String c, String d, String e, String f, String g, String h
            , String i, String j, String k, String l, String m, String n, String o, String p, String q, String r, String s) {
        stationName = (TextView)findViewById(R.id.stationName);
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
        stationName.setText(b);
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
}
