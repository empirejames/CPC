package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ant.liao.GifView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.james.cpc.dataBase.TinyDB;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = MainActivity.class.getSimpleName();
    TextView countryName, curStation, curtel, stationName, invoiceFail;
    TextView curStates, curPM, curAQI, curPublishTime;
    TextView oil_supply_92, oil_supply_95, oil_supply_98, oil_supply_disol, oil_supply_Alloc, ecard, yoyocard, happycash, members, creditself, washCar, activityTime;
    TextView oil_supply_92_price, oil_supply_95_price, oil_supply_98_price, oil_supply_disol_price, oil_supply_Alloc_price, oilPrediction;
    LinearLayout bgElement;
    String countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS;
    String gas92, gas95, gas98, disol, alocal;
    String predictionUD, predictionValue,invoice, location, curgas92, curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf, curWashcar, curyoyocard, curecard, curhappycash, curactivitytime;
    private SwipeRefreshLayout laySwipe;
    TinyDB tinydb;
    ProgressBar psBarAQI, psBarPM;
    GifView gfup, gfdown;
    RatingBar ratingbarStart;
    ImageView img_airStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bgElement = (LinearLayout) findViewById(R.id.DefaultLinearLayout);
        laySwipe = (SwipeRefreshLayout) findViewById(R.id.laySwipe);
        laySwipe.setOnRefreshListener(this);
        ratingbarStart = (RatingBar) findViewById(R.id.ratingBarSelect);
        invoiceFail = (TextView) findViewById(R.id.invoiceFail);
        tinydb = new TinyDB(this);
        getData();
        LinearLayout dot_map = (LinearLayout) findViewById(R.id.locationLayout);
        dot_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap(countryNameS + location);
            }
        });
        LinearLayout mear_map = (LinearLayout) findViewById(R.id.locationpath);
        mear_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap(countryNameS + location);
            }
        });
        RelativeLayout air_relative =(RelativeLayout)findViewById(R.id.airRelative);
        air_relative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AirInfoActivity.class);
                startActivity(intent);
            }
        });
        setLineChartData();

    }

    public void setLineChartData() {
        LineChart lineChart = (LineChart) findViewById(R.id.chart_line);
        lineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, GasInfoActivity.class);
                startActivity(intent);
            }
        });
        String TAG = MainActivity.class.getSimpleName();
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = lineChart.getAxis(YAxis.AxisDependency.LEFT);
        leftAxis.setStartAtZero(false);
        leftAxis.setAxisMinValue(0);
        leftAxis.setAxisMaxValue(30);
        ArrayList<Entry> yAxData = new ArrayList<>();
        Description des = lineChart.getDescription();
        des.setEnabled(false);
        int numDataPoints = 12;
        for (int i = 0; i < numDataPoints; i++) {
            int cosFunction = (int) (Math.random() * 12) + 10;
            //Log.e(TAG,"cosFunction: " + cosFunction);
            int xEntry = i;
            yAxData.add(new Entry(xEntry, cosFunction));
        }
        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAxData, "油耗");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColors(Color.RED);
        lineDataSets.add(lineDataSet1);
        lineChart.setData(new LineData(lineDataSets));
        lineChart.setVisibleXRangeMaximum(65f);
        lineChart.invalidate();
    }

    public void goToMap(String getGpsLocation) {
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
        finish();
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
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA0", Context.MODE_PRIVATE);
        invoice = prefs.getString("curInvoice0", null);
        String str;
        if(invoice==null){
            str = "0";
            ratingbarStart.setVisibility(View.GONE);
            invoiceFail.setVisibility(View.VISIBLE);
        }else{
            double star = 3000 /Double.parseDouble(invoice) ;
            DecimalFormat df1 = new DecimalFormat("##");
            str = df1.format(star);
            ratingbarStart.setRating(Integer.parseInt(str));
        }
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
        gas92 = tinydb.getString("92");
        gas95 = tinydb.getString("95");
        gas98 = tinydb.getString("98");
        disol = tinydb.getString("disol");
        alocal = tinydb.getString("alloc");
        predictionUD = tinydb.getString("upOrDown");
        predictionValue = tinydb.getString("persentage");
        double price = Double.parseDouble(predictionValue)*0.01 * 20;
        DecimalFormat df = new DecimalFormat("##.00");
        price = Double.parseDouble(df.format(price));
        setupView(location, countryNameS, curStationS, curte1S, curStatesS, curPMS, curAQIS, curPublishTimeS, curgas92
                , curgas95, curgas98, curgasAlcool, curdisol, curmember, curcreditshelf, curWashcar, curyoyocard, curecard
                , curhappycash, curactivitytime, gas92, gas95, gas98, alocal, disol, predictionUD, price);
    }

    public void setupView(String location, String a, String b, String c, String d, String e, String f, String g, String h
            , String i, String j, String k, String l, String m, String n, String o, String p, String q, String r, String s
            , String t, String u, String v, String w, String x, String y, double z) {
        String[] pmData = e.split(":");
        String[] AQIData = f.split(":");
        String AQINumber = AQIData[1].trim();
        String PMNumber = pmData[1].trim();
        // Log.e(TAG,pmData[1].trim());
        // Log.e(TAG,AQIData[1].trim());
        stationName = (TextView) findViewById(R.id.stationName);
        countryName = (TextView) findViewById(R.id.countryName);
        curStation = (TextView) findViewById(R.id.curStation);
        curtel = (TextView) findViewById(R.id.curtel);
        curStates = (TextView) findViewById(R.id.curStates);
        curPM = (TextView) findViewById(R.id.curPM);
        curAQI = (TextView) findViewById(R.id.curAQI);
        psBarAQI = (ProgressBar) findViewById(R.id.progressBarAQI);
        psBarAQI.setMax(200);
        psBarPM = (ProgressBar) findViewById(R.id.progressBarPM);
        psBarPM.setMax(100);
        //Log.e(TAG,"AQI data : " + AQIData[1].trim());
        if (AQINumber.equals("") || PMNumber.equals("")) {
            AQINumber = "0";
            PMNumber = "0";
        }else if(PMNumber.equals("ND")){
            PMNumber = "0";
        }
        if (Integer.parseInt(AQINumber) < 50) {
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        } else if (Integer.parseInt(AQINumber) > 50 && Integer.parseInt(AQINumber) < 150) {
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_blue));
        } else if (Integer.parseInt(AQINumber) > 150) {
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_red));
        }else{
            psBarAQI.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        }
        if (Integer.parseInt(PMNumber) > 60) {
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_red));
        } else if (Integer.parseInt(PMNumber) < 60 & Integer.parseInt(PMNumber) > 35) {
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_blue));
        } else if (Integer.parseInt(PMNumber) < 35) {
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        }else{
            psBarPM.setProgressDrawable(getResources().getDrawable(R.drawable.color_progressbar_green));
        }
        psBarAQI.setProgress(Integer.parseInt(AQINumber));
        psBarPM.setProgress(Integer.parseInt(PMNumber));
        img_airStatus = (ImageView) findViewById(R.id.img_airStatus);
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
        oilPrediction = (TextView) findViewById(R.id.oilPrediction);
        oilPrediction.setText("油價預測 : " + y + " " + z +" 元");
        gfup = (GifView)findViewById(R.id.oilUp);
        gfdown = (GifView)findViewById(R.id.oilDown);
        if(y.equals("漲")){
            gfup.setVisibility(View.VISIBLE);
            oilPrediction.setTextColor(getResources().getColor(R.color.colorOrangeYellow));
        }else{
            gfdown.setVisibility(View.VISIBLE);
            oilPrediction.setTextColor(getResources().getColor(R.color.colorOrangeYellow));
        }
        members = (TextView) findViewById(R.id.members);
        creditself = (TextView) findViewById(R.id.creditself);
        ecard = (TextView) findViewById(R.id.ecard);
        yoyocard = (TextView) findViewById(R.id.yoyocard);
        happycash = (TextView) findViewById(R.id.happycash);
        washCar = (TextView) findViewById(R.id.washCar);
        activityTime = (TextView) findViewById(R.id.activityTime);

        //Log.e(TAG, h + " i " + i + " j " + j + " k " + k + " l " + l + " m " + m + " n " + n + " o " + o + " p " + p + " q " + q + " r " + r + " s " + s);
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


        countryName.setText("導航至中油" + b);
        stationName.setText(b);
        curStation.setText(a + location);
        curtel.setText("距離約 " + c);
        curPM.setText(e);
        curAQI.setText(f);
        curPublishTime.setText(g);

        if (d.equals("良好")) {
            curStates.setText("良好");
            bgElement.setBackgroundResource(R.drawable.bgd_great);
            img_airStatus.setImageResource(R.drawable.air_ok);
        } else if (d.equals("普通")) {
            curStates.setText("普通");
            bgElement.setBackgroundResource(R.drawable.bgd);
            img_airStatus.setImageResource(R.drawable.air_sensitive);
        }else if(d.equals("設備維護")){
            curStates.setText("設備維護");
            bgElement.setBackgroundResource(R.drawable.bgd_danger);
            img_airStatus.setImageResource(R.drawable.air_error);
        } else if(d.equals("對敏感族群不健康")){
            curStates.setText("不健康");
            bgElement.setBackgroundResource(R.drawable.bgd_danger);
            img_airStatus.setImageResource(R.drawable.air_unhealthy);
        }else{
            curStates.setText("危險");
            bgElement.setBackgroundResource(R.drawable.bgd_died);
            img_airStatus.setImageResource(R.drawable.air_died);
        }
    }
}
