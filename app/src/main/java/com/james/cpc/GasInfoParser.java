package com.james.cpc;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.james.cpc.dataBase.TinyDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 101716 on 2017/11/17.
 */

public class GasInfoParser extends ContextWrapper {
    String TAG = GasInfoParser.class.getSimpleName();
    String url = "http://new.cpc.com.tw/mobile/Home/";
    private HandlerThread mThread;
    private Handler mThreadHandler;
    ArrayList<String> oilPriceList = new ArrayList<String>();
    Context mContext;
    TinyDB tinydb;


    public GasInfoParser(Context base) {
        super(base);
        this.mContext = base.getApplicationContext();
        tinydb = new TinyDB(mContext);
    }

    public void start() {
        mThread = new HandlerThread("jsoup");
        mThread.start();
        mThreadHandler = new Handler(mThread.getLooper());
        mThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                getGasOilPrice();
            }
        });

    }
    public ArrayList<String> getGasOilPrice(){
        String[] temp;
        String date = "";
        try {
            Document doc = Jsoup.connect(url).get();
            //Log.e(TAG, doc.title());
            Element table = doc.select("dl#OilPrice2").first();
            Elements rows = table.select("strong");
            for (int i = 0; i < rows.size(); i++) {
                //Log.e(TAG, rows.get(i).text() + "");
                oilPriceList.add(rows.get(i).text());
            }
            Log.e(TAG,rows.get(0).text()+" - "+rows.get(1).text()
                    +" - "+rows.get(2).text()+" - "+rows.get(3).text()
                    +" - "+rows.get(4).text());
            writeDb(rows.get(0).text(),rows.get(1).text()
                    ,rows.get(2).text(),rows.get(3).text()
                    ,rows.get(4).text());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return oilPriceList;
    }

    public void writeDb(String a, String b,String c,String d, String e) {
        Log.e(TAG,mContext + "");
        SharedPreferences prefs = mContext.getSharedPreferences("Gas", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("92", a);
        editor.putString("95", b);
        editor.putString("98", c);
        editor.putString("disol", d);
        editor.putString("alloc", e);
        editor.commit();


        tinydb.putString("92", a);
        tinydb.putString("95", b);
        tinydb.putString("98", c);
        tinydb.putString("disol", d);
        tinydb.putString("alloc", e);
    }
}
