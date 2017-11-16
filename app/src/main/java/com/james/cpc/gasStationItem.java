package com.james.cpc;

import android.content.Context;

/**
 * Created by 101716 on 2017/6/27.
 */

public class gasStationItem {
    String TAG = gasStationItem.class.getSimpleName();
    private String selfStation, stationName, countryName, location, gas92, gas95, gas98
            , alcohol, superDiesel, membership, makeSelf, Dieselself, invoice, yoyocard, eCard
            , happyCash, longitude, latitude, activeTime, washCar, etagTopup,maintain, distance, distanceM;
    Context context;
    private int _id;

    public gasStationItem(String selfStation, String stationName, String countryName, String location
            , String gas92, String gas95, String gas98, String alcohol
            , String superDiesel, String membership,String makeSelf,String Dieselself
            , String yoyocard, String eCard, String happyCash, String longitude,String latitude
            , String activeTime, String washCar, String etagTopup, String maintain,String distance,String distanceM) {
        this.selfStation = selfStation; //自營 直營
        this.stationName = stationName; //站名
        this.countryName = countryName; //鄉鎮名
        this.location = location; //地址
        this.gas92 = gas92; //92油
        this.gas95 = gas95; //95油
        this.gas98 = gas98; //98油
        this.alcohol = alcohol; //酒精氣油
        this.superDiesel = superDiesel; //超柴
        this.membership = membership; //會員
        this.makeSelf = makeSelf; //刷卡自助
        this.Dieselself = Dieselself; //自助柴油
        this.invoice = invoice; //電子發票
        this.yoyocard = yoyocard; //悠游卡
        this.eCard = eCard; //一卡通
        this.happyCash = happyCash; //HappyCash
        this.longitude = longitude; //經度
        this.latitude = latitude; //緯度
        this.activeTime = activeTime; //營業時間
        this.washCar = washCar; //洗車
        this.etagTopup = etagTopup; //eTag儲值
        this.distance = distance; //distance
        this.distanceM = distanceM;
    }
    public String getDistanceM() {
        return distanceM;
    }
    public String getDistance() {
        return distance;
    }
    public String getSelfStation() {
        return selfStation;
    }

    public String getStationName() {
        return stationName;
    }

    public String getCountryName() {
        return countryName;
    }
    public String getLocation() {
        return location;
    }
    public String getYoyocard() {
        return yoyocard;
    }

    public String getGas92() {
        return gas92;
    }

    public String getGas95() {
        return gas95;
    }

    public String getGas98() {
        return gas98;
    }
    public String getAlcohol() {
        return alcohol;
    }
    public String getSuperDiesel() {
        return superDiesel;
    }
    public String getMembership() {
        return membership;
    }
    public String getMakeSelf() {
        return makeSelf;
    }
    public String getDieselself() {
        return Dieselself;
    }
    public String getInvoice() {
        return invoice;
    }
    public String getHappyCash() {
        return happyCash;
    }
    public String getLongitude() {
        return longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public String getActiveTime() {
        return activeTime;
    }
    public String getWashCar() {
        return washCar;
    }
    public String getEtagTopup() {
        return etagTopup;
    }
    public String getECard() {
        return eCard;
    }





}
