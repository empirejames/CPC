package com.james.cpc.item;

/**
 * Created by 101716 on 2017/11/20.
 */

public class TourismItem {

    String Location, UpdateTime, Name , OpenTime, Longitudemm, Latitude, telPhone, scribe;

    public TourismItem(String Location, String UpdateTime, String Name, String OpenTime,String Longitudemm, String Latitude
    ,String telPhone, String scribe){
        this.Location = Location; //經度
        this.UpdateTime = UpdateTime; //緯度
        this.Name = Name; //緯度
        this.OpenTime = OpenTime; //經度
        this.Longitudemm = Longitudemm; //經度
        this.Latitude = Latitude; //緯度
        this.telPhone = telPhone; //經度
        this.scribe = scribe; //緯度
}
    public String getLocation() {
        return Location;
    }
    public String getUpdateTime() {
        return UpdateTime;
    }
    public String getName() {
        return Name;
    }
    public String getOpenTime() {
        return OpenTime;
    }
    public String getLongitudemm() {
        return Longitudemm;
    }
    public String getLatitude() {
        return Latitude;
    }
    public String getTelPhone() {
        return telPhone;
    }
    public String getScribe() {
        return scribe;
    }

}
