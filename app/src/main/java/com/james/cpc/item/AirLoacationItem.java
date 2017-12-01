package com.james.cpc.item;

import android.content.Context;

/**
 * Created by 101716 on 2017/6/27.
 */

public class AirLoacationItem {
    String TAG = AirLoacationItem.class.getSimpleName();
    private String AQI, CO, CO_8hr, County, NO, NO2, NOx
            , O3, O3_8hr, PM10, PM10_AVG, PM25, PM25_AVG, PublishTime, SiteName
            , SO2, Status, WindDirec, WindSpeed;
    Context context;
    private int _id;

    public AirLoacationItem(String AQI, String CO, String CO_8hr, String County
            , String NO, String NO2, String NOx, String O3, String O3_8hr, String PM10
            , String PM10_AVG, String PM25, String PM25_AVG, String PublishTime, String SiteName, String SO2
    , String Status, String WindDirec, String WindSpeed) {
        this.AQI = AQI;
        this.CO = CO;
        this.CO_8hr = CO_8hr;
        this.County = County;
        this.NO = NO;
        this.NO2 = NO2;
        this.NOx = NOx;
        this.O3 = O3;
        this.O3_8hr = O3_8hr;
        this.PM10 = PM10;
        this.PM10_AVG = PM10_AVG;
        this.PM25 = PM25;
        this.PM25_AVG = PM25_AVG;
        this.PublishTime = PublishTime;
        this.SiteName = SiteName;
        this.SO2 = SO2;
        this.Status = Status;
        this.WindDirec = WindDirec;
        this.WindSpeed = WindSpeed;
    }
    public String getAQI() {
        return AQI;
    }
    public String getCO() {
        return CO;
    }
    public String getCounty() {
        return County;
    }
    public String getCO_8hr() {
        return CO_8hr;
    }
    public String getNO() {
        return NO;
    }
    public String getNO2() {
        return NO2;
    }
    public String getNOx() {
        return NOx;
    }
    public String getO3() {
        return O3;
    }
    public String getO3_8hr() {
        return O3_8hr;
    }
    public String getPM10() {
        return PM10;
    }
    public String getPM10_AVG() {
        return PM10_AVG;
    }
    public String getPM25() {
        return PM25;
    }
    public String getPM25_AVG() {
        return PM25_AVG;
    }
    public String getPublishTime() {
        return PublishTime;
    }
    public String getSiteName() {
        return SiteName;
    }
    public String getSO2() {
        return SO2;
    }
    public String getStatus() {
        return Status;
    }
    public String getWindDirec() {
        return WindDirec;
    }
    public String getWindSpeed() {
        return WindSpeed;
    }





}
