package com.james.cpc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import au.com.bytecode.opencsv.CSVReader;

public class MainActivity extends AppCompatActivity {
    private String[] line;
    private String TAG = MainActivity.class.getSimpleName();
    private String distanceFin;
    private LocationManager lms;
    private String getCity, getSelfStation, getStationName, getDistance;
    private String data_url = "http://vipmember.tmtd.cpc.com.tw/CPCSTN/STNWebService.asmx/QueryStation";
    private String air_url = "http://opendata2.epa.gov.tw/AQI.json";
    private ArrayList<AirLoacationItem> mGridData;

    final private int REQUEST_CODE_ASK_ALL = 122;
    protected ProgressDialog dialogSMS;
    RequestQueue mQueue;
    Double longitude, latitude;

    ArrayList<gasStationItem> myDataset = new ArrayList<gasStationItem>();
    TextView countryName, curStation, curtel;
    TextView curStates, curPM, curAQI, curPublishTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail);
        Context mContext = getApplicationContext();
        mQueue = Volley.newRequestQueue(mContext);
        mGridData = new ArrayList<>();
        if (checkPermission() == true) {
            startDialog();
            new AsyncHttpTask().execute();
            CSVRead();
            //getStation();
            //CSVReadAir();
        }
    }

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... strings) {
            getData(air_url);
            return null;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            for (int i = 0; i < mGridData.size(); i++) {
                if (mGridData.get(i).getCounty().equals(getCity)) {
                    if(getStationName.contains(mGridData.get(i).getSiteName())){
                        setupView(getCity, getSelfStation +  getStationName, getDistance
                                ,"距離 : " +mGridData.get(i).getStatus()
                                ,"PM 2.5 : " + mGridData.get(i).getPM25_AVG()
                                ,"空氣品質 AQI : " +mGridData.get(i).getAQI()
                                ,"更新時間 : " +mGridData.get(i).getPublishTime());
                    }else{
                        setupView(getCity, getSelfStation +  getStationName
                                ,"距離 : " + getDistance
                                ,mGridData.get(i).getStatus()
                                ,"PM 2.5 : " + mGridData.get(i).getPM25_AVG()
                                ,"空氣品質 AQI : " +mGridData.get(i).getAQI()
                                ,"更新時間 : " +mGridData.get(i).getPublishTime());
                    }

                }
            }

            dialogSMS.dismiss();
        }
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

    public void getStation() {
        StringRequest postRequest = new StringRequest(Request.Method.POST, data_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, response.length() + "");
                        try {
                            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                            dbf.setNamespaceAware(true);
                            DocumentBuilder db = dbf.newDocumentBuilder();
                            InputSource is = new InputSource();
                            is.setCharacterStream(new StringReader(response));
                            Document doc = db.parse(is);
                            Element root = doc.getDocumentElement();
                            NodeList books = root.getChildNodes();
                            Log.e(TAG, books.getLength() + "");
                        } catch (Exception e) {
                            Log.e(TAG, "Exception : " + e);
                        }

                        // startDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error String: " + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("City", "新北市");
                params.put("Village", "");
                params.put("Types", "");
                params.put("open24", "");
                params.put("queryitems", "");
                return params;
            }
        };
        mQueue.add(postRequest);
    }

    public void setupView(String a, String b, String c, String d, String e,String f,String g) {
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

    public void CSVReadAir() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new BufferedReader(new InputStreamReader(getAssets().open("airdata.csv"), "UTF-8")));
            while ((line = reader.readNext()) != null) {
                Log.e(TAG, line[0] + " - " + line[1] + " - " + line[3]);
            }
            //dialogSMS.dismiss();
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

    public void CSVRead() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(new BufferedReader(new InputStreamReader(getAssets().open("location.csv"), "UTF-8")));
            while ((line = reader.readNext()) != null) {
                if (!line[1].equals("類別")) {
                    Log.e(TAG, line[23] + " - " + line[24]);
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
            Log.e(TAG, myDataset.get(0).getDistance() + myDataset.get(0).getDistanceM() + " " + myDataset.get(0).getStationName() + myDataset.get(0).getCountryName());
            getCity = myDataset.get(0).getCountryName();
            getSelfStation = myDataset.get(0).getSelfStation() + " ";
            getStationName = myDataset.get(0).getStationName();
            getDistance = myDataset.get(0).getDistance() + "公" + myDataset.get(0).getDistanceM();
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
}
