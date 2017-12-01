package com.james.cpc.item;

/**
 * Created by 101716 on 2017/6/27.
 */

public class GasTotalItem {
    String TAG = GasTotalItem.class.getSimpleName();
    private String date, oil, avgcost, addoil, runkm, totoalKm ;
    public GasTotalItem(String date ,String oil,String avgcost,String addoil, String runkm, String totoalKm) {
        this.date = date;
        this.oil = oil;
        this.avgcost = avgcost;
        this.addoil = addoil;
        this.runkm = runkm;
        this.totoalKm = totoalKm;
    }
    public String getDate() {
        return date;
    }
    public String getOil() {
        return oil;
    }

    public String getAvgcost() {
        return avgcost;
    }
    public String getAddoil() {
        return addoil;
    }
    public String getRunkm() {
        return runkm;
    }
    public String getTotoalKm() {
        return totoalKm;
    }
}
