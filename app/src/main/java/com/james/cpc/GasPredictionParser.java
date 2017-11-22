package com.james.cpc;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
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

public class GasPredictionParser extends ContextWrapper {
    String TAG = GasPredictionParser.class.getSimpleName();
    String url = "http://www.taiwanoil.org/";
    private HandlerThread mThread;
    private Handler mThreadHandler;
    ArrayList<String> oilPriceList = new ArrayList<String>();
    Context mContext;
    TinyDB tinydb;


    public GasPredictionParser(Context base) {
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
            Element table = doc.select("table[class=topmenu2]").first();
            Elements rows = table.select("font[size=4]");
            temp = rows.get(0).text().split(" ");
            writeDb(temp[0],temp[1].substring(0,temp[1].length()-1));
            //Log.e(TAG,"temp : " + temp[0] + temp[1].substring(0,temp[1].length()-1));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return oilPriceList;
    }

    public void writeDb(String a, String b) {
        Log.e(TAG,"a: " + a + " b: " +b );
        tinydb.putString("upOrDown", a);
        tinydb.putString("persentage", b);
    }
}
