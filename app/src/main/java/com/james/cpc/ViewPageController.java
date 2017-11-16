package com.james.cpc;

import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by 101716 on 2017/11/15.
 */

public class ViewPageController extends AppCompatActivity {
    Context context = null;
    LocalActivityManager manager = null;
    ViewPager pager = null;
    private String[] line;
    private String air_url = "http://opendata2.epa.gov.tw/AQI.json";
    private String getCity, getSelfStation, getStationName, getDistance;
    private ArrayList<AirLoacationItem> mGridData;
    private LocationManager lms;
    final private int REQUEST_CODE_ASK_ALL = 122;
    Double longitude, latitude;
    private String distanceFin;
    ArrayList<gasStationItem> myDataset = new ArrayList<gasStationItem>();



    protected ProgressDialog dialogSMS;
    private String TAG = ViewPageController.class.getSimpleName();
    private int offset = 0;// 动画图片偏移量
    private int currIndex = 0;// 当前页卡编号
    private int bmpW;// 动画图片宽度
    private ImageView cursor;// 动画图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = ViewPageController.this;
        manager = new LocalActivityManager(this , true);
        manager.dispatchCreate(savedInstanceState);
        mGridData = new ArrayList<>();
        startDialog();
        checkPermission();
            //getStation();
            //CSVReadAir();
    }
    public void checkPermission() {
        lms = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (lms.isProviderEnabled(LocationManager.GPS_PROVIDER) || lms.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            locationServiceInitial();
        } else {
            locationServiceInitial();
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面

        }
    }
    private void locationServiceInitial() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ) {
                requestPermissions(new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                }, REQUEST_CODE_ASK_ALL);
                locationServiceInitial();
                //finish();
            } else {
                Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location == null) {
                    location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                getLocation(location);
            }
        } else {
            Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null) {
                location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            getLocation(location);
        }
    }
    private void getLocation(Location location) {    //將定位資訊顯示在畫面中
        if (location != null) {
            longitude = location.getLongitude();    //取得經度
            latitude = location.getLatitude();    //取得緯度
            new AsyncHttpTask().execute();
            //Log.e(TAG, longitude + " .. " + latitude);
        } else {
            Toast.makeText(this, "無法定位座標", Toast.LENGTH_LONG).show();
        }
    }
    private String DistanceText(double distance) {
        if (distance < 1000) {
            //Log.e(TAG, String.valueOf((int) distance) + "公尺");
            return String.valueOf((int) distance) + " 公尺";
        } else {
            //Log.e(TAG, new DecimalFormat("#.00").format(distance / 1000) + "公里");
            return new DecimalFormat("#.00").format(distance / 1000) + " 公里";
        }
    }
    private void DistanceSort(ArrayList<gasStationItem> gasSation) {
        Log.e(TAG, "Sort"   + gasSation);
        Collections.sort(gasSation, new Comparator<gasStationItem>() {
            @Override
            public int compare(gasStationItem gasS1, gasStationItem gasS2) {
                double a = Double.parseDouble(gasS1.getDistance());
                double b = Double.parseDouble(gasS2.getDistance());
                return a < b ? -1 : 1;
            }
        });
    }
    public double Distance(double longitude1, double latitude1, double longitude2, double latitude2) {
        //Log.e(TAG, longitude1 + "." + latitude1 + " V.S. " + longitude2 + "." + latitude2);
        double radLatitude1 = latitude1 * Math.PI / 180;
        double radLatitude2 = latitude2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;
        return distance;
    }
    public void CSVRead() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new BufferedReader(new InputStreamReader(getAssets().open("location.csv"), "UTF-8")));
            while ((line = reader.readNext()) != null) {
                if (!line[1].equals("類別")) {
                    if (!line[23].equals("NULL") || !line[24].equals("NULL")) {
                        distanceFin = DistanceText(Distance(Double.parseDouble(line[23]), Double.parseDouble(line[24]), longitude, latitude));
                    } else {
                        int num1= (int)(Math.random()*99)+1;
                        distanceFin = "610."+num1 +" 公里";
                    }
                    String[] distanceKil = distanceFin.split("公");
                    myDataset.add(new gasStationItem(
                            line[1], line[2], line[3], line[5]
                            , line[10], line[11], line[12], line[13]
                            , line[15], line[16], line[17], line[19]
                            , line[20], line[21], line[22], line[23], line[24]
                            , line[25], line[26], line[27], line[28], distanceKil[0], distanceKil[1]));
//                    Log.e(TAG,  " 1 :" +line[1] + " 2 : " + line[2] + " 4 : " + line[4] + " 6 : " + line[6]
//                            + " 10: " + line[10] + " 11: " + line[11] + " 12: " + line[12] + " 13: " + line[13]
//                            + " 15: " + line[15] + " 16: " + line[16] + " 17: " + line[17] + " 19: " + line[19]
//                            + " 20: " + line[20] + " 21: " + line[21] + " 22: " + line[22] + " 23: " + line[23] + " 24: " + line[24]
//                            + " 25: " + line[25] + " 26: " + line[26] + " 27: " + line[27] + " 28: " + line[28] + " " + distanceKil[0] + " " + distanceKil[1]);
                }
            }
            DistanceSort(myDataset);
//            Log.e(TAG, myDataset.get(0).getDistance() + myDataset.get(0).getDistanceM() + " " + myDataset.get(0).getStationName() + myDataset.get(0).getCountryName()
//                    +" :: "+ myDataset.get(0).getActiveTime());
//            Log.e(TAG, myDataset.get(1).getDistance() + myDataset.get(1).getDistanceM() + " " + myDataset.get(1).getStationName() + myDataset.get(1).getCountryName()
//                    +" :: "+ myDataset.get(1).getActiveTime());
//            Log.e(TAG, myDataset.get(2).getDistance() + myDataset.get(2).getDistanceM() + " " + myDataset.get(2).getStationName() + myDataset.get(2).getCountryName()
//                    +" :: "+ myDataset.get(2).getWashCar());

            for(int i=0;i<=2;i++){
                setDataToMain(i+""
                        ,myDataset.get(i).getLocation()
                        ,myDataset.get(i).getCountryName()
                        ,myDataset.get(i).getSelfStation() +" " +myDataset.get(i).getStationName()
                        ,myDataset.get(i).getDistance() + "公" + myDataset.get(i).getDistanceM()
                        ,mGridData.get(i).getStatus()
                        ,"PM2.5指數 : "+mGridData.get(i).getPM25_AVG()
                        ,"空氣AQI指數 : "+mGridData.get(i).getAQI()
                        ,"更新時間 : "+mGridData.get(i).getPublishTime()
                        ,myDataset.get(i).getGas92()
                        ,myDataset.get(i).getGas95()
                        ,myDataset.get(i).getGas98()
                        ,myDataset.get(i).getAlcohol()
                        ,myDataset.get(i).getSuperDiesel()
                        ,myDataset.get(i).getMembership()
                        ,myDataset.get(i).getMakeSelf()
                        ,myDataset.get(i).getWashCar()
                        ,myDataset.get(i).getYoyocard()
                        ,myDataset.get(i).getECard()
                        ,myDataset.get(i).getHappyCash()
                        ,myDataset.get(i).getActiveTime()
                );
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }
    }
    public void setDataToMain(String pageNm,String location, String a, String b, String c, String d, String e,String f,String g,String h
    ,String i,String j,String k,String l,String m,String n,String o,String p,String q,String r,String s) {

        SharedPreferences prefs = getApplication().getSharedPreferences("DATA" + pageNm, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("curlocation" + pageNm, location);
        editor.putString("countryName" + pageNm, a);
        editor.putString("curStation" + pageNm, b);
        editor.putString("curDistance" + pageNm, c);
        editor.putString("curStates" + pageNm, d);
        editor.putString("curPM" + pageNm, e);
        editor.putString("curAQI" + pageNm, f);
        editor.putString("curPublishTime" + pageNm, g);
        editor.putString("curgas92" + pageNm, h);
        editor.putString("curgas95" + pageNm, i);
        editor.putString("curgas98" + pageNm, j);
        editor.putString("curgasAlcool" + pageNm, k);
        editor.putString("curdisol" + pageNm, l);
        editor.putString("curmember" + pageNm, m);
        editor.putString("curcreditshelf" + pageNm, n);
        editor.putString("curWashcar" + pageNm, o);
        editor.putString("curyoyocard" + pageNm, p);
        editor.putString("curecard" + pageNm, q);
        editor.putString("curhappycash" + pageNm, r);
        editor.putString("curactivitytime" + pageNm, s);
        editor.commit();
    }
    public void getData(String url) {
        try {
            String json = Jsoup.connect(url).ignoreContentType(true).execute().body();
            if (json.indexOf("{") != -1) {
                String output = json.substring(json.indexOf("{"), json.lastIndexOf("}") + 1);
                JSONArray array = new JSONArray(json);

                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String AQI = jsonObject.getString("AQI");
                    String CO = jsonObject.getString("CO");
                    String CO_8hr = jsonObject.getString("CO_8hr");
                    String County = jsonObject.getString("County");
                    String NO = jsonObject.getString("NO");
                    String NO2 = jsonObject.getString("NO2");
                    String NOx = jsonObject.getString("NOx");
                    String O3 = jsonObject.getString("O3");
                    String O3_8hr = jsonObject.getString("O3_8hr");
                    String PM10 = jsonObject.getString("PM10");
                    String PM10_AVG = jsonObject.getString("PM10_AVG");
                    String PM25 = jsonObject.getString("PM2.5");
                    String PM25_AVG = jsonObject.getString("PM2.5_AVG");
                    String PublishTime = jsonObject.getString("PublishTime");
                    String SiteName = jsonObject.getString("SiteName");
                    String SO2 = jsonObject.getString("SO2");
                    String Status = jsonObject.getString("Status");
                    String WindDirec = jsonObject.getString("WindDirec");
                    String WindSpeed = jsonObject.getString("WindSpeed");
                    mGridData.add(new AirLoacationItem(AQI, CO, CO_8hr, County, NO, NO2
                            , NOx, O3, O3_8hr, PM10, PM10_AVG, PM25, PM25_AVG, PublishTime, SiteName
                            , SO2, Status, WindDirec, WindSpeed
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            getData(air_url);
            CSVRead();
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            initPagerViewer();
            dialogSMS.dismiss();
        }
    }

    public void startDialog() {
        dialogSMS = ProgressDialog.show(this, null, null, false, true);
        dialogSMS.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSMS.setContentView(R.layout.progressbar);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    Thread.sleep(30 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void initPagerViewer() {
        pager = (ViewPager) findViewById(R.id.viewpage);
        final ArrayList<View> list = new ArrayList<View>();
        Intent intent = new Intent(context, MainActivity.class);
        list.add(getView("MainActivity", intent));
        Intent intent2 = new Intent(context, MiddleActivity.class);
        list.add(getView("MiddleActivity", intent2));
        Intent intent3 = new Intent(context, LastActivity.class);
        list.add(getView("LastActivity", intent3));
        pager.setAdapter(new MyPagerAdapter(list));
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
    }
    private View getView(String id, Intent intent) {
        return manager.startActivity(id, intent).getDecorView();
    }
    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
        int two = one * 2;// 页卡1 -> 页卡3 偏移量

        @Override
        public void onPageSelected(int arg0) {
            Animation animation = null;
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        animation = new TranslateAnimation(one, 0, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, 0, 0, 0);
                    }
                    break;
                case 1:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, one, 0, 0);
                    } else if (currIndex == 2) {
                        animation = new TranslateAnimation(two, one, 0, 0);
                    }
                    break;
                case 2:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
                case 3:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
                case 4:
                    if (currIndex == 0) {
                        animation = new TranslateAnimation(offset, two, 0, 0);
                    } else if (currIndex == 1) {
                        animation = new TranslateAnimation(one, two, 0, 0);
                    }
                    break;
            }
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);
            //cursor.startAnimation(animation);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
    }
}
