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
import android.widget.TabHost;
import android.widget.TextView;
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
        if (checkPermission() == true) {
            new AsyncHttpTask().execute();
            //getStation();
            //CSVReadAir();
        }
    }
    public boolean checkPermission() {
        boolean isEnable = false;
        Log.e(TAG, "checkPermission...");
        lms = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if (lms.isProviderEnabled(LocationManager.GPS_PROVIDER) || lms.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            locationServiceInitial();
            isEnable = true;
        } else {
            locationServiceInitial();
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));    //開啟設定頁面
            isEnable = false;
        }
        return isEnable;
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
                finish();
            } else {
                Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                ;
                if (location == null) {
                    location = lms.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                getLocation(location);
            }
        } else {
            Location location = lms.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            ;
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
        //Log.e(TAG, "Sort");
        Collections.sort(gasSation, new Comparator<gasStationItem>() {
            @Override
            public int compare(gasStationItem gasS1, gasStationItem gasS2) {
                double a = Double.parseDouble(gasS1.getDistance());
                double b = Double.parseDouble(gasS2.getDistance());
                //Log.e(TAG,a + " V.S " + b);
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
                        distanceFin = "610.12 公里";
                    }
                    String[] distanceKil = distanceFin.split("公");
                    myDataset.add(new gasStationItem(
                            line[1], line[2], line[3], line[6]
                            , line[10], line[11], line[12], line[13]
                            , line[15], line[16], line[17], line[19]
                            , line[20], line[21], line[22], line[23], line[24]
                            , line[25], line[26], line[27], line[28], distanceKil[0], distanceKil[1]));
                    Log.e(TAG, line[1] + " " + line[2] + " " + line[4] + " " + line[6]
                            + " " + line[10] + " " + line[11] + " " + line[12] + " " + line[13]
                            + " " + line[15] + " " + line[16] + " " + line[17] + " " + line[19]
                            + " " + line[20] + " " + line[21] + " " + line[22] + " " + line[23] + " " + line[24]
                            + " " + line[25] + " " + line[26] + " " + line[27] + " " + line[28] + " " + distanceKil[0] + " " + distanceKil[1]);
                }
            }
            DistanceSort(myDataset);
            //Log.e(TAG, myDataset.get(0).getDistance() + myDataset.get(0).getDistanceM() + " " + myDataset.get(0).getStationName() + myDataset.get(0).getCountryName());
            setDataToMain(myDataset.get(0).getCountryName()
                    ,myDataset.get(0).getSelfStation() +" " +myDataset.get(0).getStationName()
                    ,myDataset.get(0).getDistance() + "公" + myDataset.get(0).getDistanceM()
                    ,mGridData.get(0).getStatus()
                    ,"PM2.5指數 : "+mGridData.get(0).getPM25_AVG()
                    ,"空氣AQI指數 : "+mGridData.get(0).getAQI()
                    ,"更新時間 : "+mGridData.get(0).getPublishTime());
            setDataToMiddle(myDataset.get(1).getCountryName()
                    ,myDataset.get(1).getSelfStation() +" " +myDataset.get(1).getStationName()
                    ,myDataset.get(1).getDistance() + "公" + myDataset.get(1).getDistanceM()
                    ,mGridData.get(1).getStatus()
                    ,"PM2.5指數 : "+mGridData.get(1).getPM25_AVG()
                    ,"空氣AQI指數 : "+mGridData.get(1).getAQI()
                    ,"更新時間 : "+mGridData.get(1).getPublishTime());
            setDataToLast(myDataset.get(2).getCountryName()
                    ,myDataset.get(2).getSelfStation() +" " +myDataset.get(2).getStationName()
                    ,myDataset.get(2).getDistance() + "公" + myDataset.get(2).getDistanceM()
                    ,mGridData.get(2).getStatus()
                    ,"PM2.5指數 : "+mGridData.get(1).getPM25_AVG()
                    ,"空氣AQI指數 : "+mGridData.get(1).getAQI()
                    ,"更新時間 : "+mGridData.get(1).getPublishTime());
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
    public void setDataToMain(String a, String b, String c, String d, String e,String f,String g ){

        SharedPreferences prefs = getApplication().getSharedPreferences("DATA",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("countryName", a);
        editor.putString("curStation", b);
        editor.putString("curDistance", c);
        editor.putString("curStates", d);
        editor.putString("curPM", e);
        editor.putString("curAQI", f);
        editor.putString("curPublishTime", g);
        editor.commit();

    }
    public void setDataToMiddle(String a, String b, String c, String d, String e,String f,String g ){
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA1",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("countryName1", a);
        editor.putString("curStation1", b);
        editor.putString("curDistance1", c);
        editor.putString("curStates1", d);
        editor.putString("curPM1", e);
        editor.putString("curAQI1", f);
        editor.putString("curPublishTime1", g);
        editor.commit();
    }
    public void setDataToLast(String a, String b, String c, String d, String e,String f,String g ){
        SharedPreferences prefs = getApplication().getSharedPreferences("DATA2",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putString("countryName2", a);
        editor.putString("curStation2", b);
        editor.putString("curDistance2", c);
        editor.putString("curStates2", d);
        editor.putString("curPM2", e);
        editor.putString("curAQI2", f);
        editor.putString("curPublishTime2", g);
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
