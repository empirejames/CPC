package com.james.cpc.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.james.cpc.R;
import com.james.cpc.item.AirItem;
import com.james.cpc.item.GasTotalItem;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/6/27.
 */

public class AirListAdapter extends BaseAdapter implements Filterable {
    private ArrayList<AirItem> mListItems;
    private LayoutInflater inflater;
    Context context;
    String TAG = AirListAdapter.class.getSimpleName();
    public AirListAdapter(Context context) {
        this.context = context;
    }

    public AirListAdapter(Context context, ArrayList<AirItem> itemList) {
        this.context = context;
        mListItems = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mListItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        final View row = inflater.inflate(R.layout.air_total_item, parent, false);
        final AirItem item = mListItems.get(position);
        final ImageView img_status = (ImageView)row.findViewById(R.id.img_status);
        final TextView tv_aqi = (TextView) row.findViewById(R.id.tv_aqi);
        final TextView tv_co = (TextView) row.findViewById(R.id.tv_co);
        final TextView tv_no2 = (TextView) row.findViewById(R.id.tv_no2);
        final TextView tv_o3 = (TextView) row.findViewById(R.id.tv_o3);
        final TextView tv_so2 = (TextView) row.findViewById(R.id.tv_so2);
        final TextView tv_status = (TextView) row.findViewById(R.id.tv_status);
        final TextView tv_pm10 = (TextView) row.findViewById(R.id.tv_pm10);
        final TextView tv_PM25 = (TextView) row.findViewById(R.id.tv_pm25);
        final TextView UpdateTime = (TextView) row.findViewById(R.id.UpdateTime);

        final ProgressBar progressBarPM25 = (ProgressBar) row.findViewById(R.id.progressBarPM25);
        progressBarPM25.setMax(100);
        progressBarPM25.setProgress(Integer.parseInt(item.getPM25()));
        final ProgressBar progressBarSO2 = (ProgressBar) row.findViewById(R.id.progressBarSO2);
        progressBarSO2.setMax(500);
        float NOx = Float.parseFloat(item.getNOx())*100;
        String NOx_st = NOx+"";
        progressBarSO2.setProgress(Integer.parseInt(NOx_st.substring(0,2)));
        final ProgressBar progressBarNO2 = (ProgressBar) row.findViewById(R.id.progressBarNO2);
        progressBarNO2.setMax(1000);
        float NO = Float.parseFloat(item.getNO2())*100;
        String NO_st = NO+"";
        progressBarNO2.setProgress(Integer.parseInt(NO_st.substring(0,2)));
        final ProgressBar progressBarPM10 = (ProgressBar) row.findViewById(R.id.progressBarPM10);
        progressBarPM10.setMax(200);
        progressBarPM10.setProgress(Integer.parseInt(item.getPM10()));
        final ProgressBar progressBarO3 = (ProgressBar) row.findViewById(R.id.progressBarO3);
        progressBarO3.setMax(200);
        progressBarO3.setProgress(Integer.parseInt(item.getO3()));
        final ProgressBar progressBarCO = (ProgressBar) row.findViewById(R.id.progressBarCO);
        progressBarCO.setMax(1000);

        float co = Float.parseFloat(item.getCO())*100;
        String co_st = co+"";
        Log.e(TAG,"co_st " + co_st);
        progressBarCO.setProgress(Integer.parseInt(co_st.substring(0,2)));

        View view = (View)row.findViewById(R.id.oil_background);

        if(item.getStatus().equals("良好")){
            img_status.setImageResource(R.drawable.air_ok);
        }else if(item.getStatus().equals("普通")){
            img_status.setImageResource(R.drawable.air_sensitive);
        }else if(item.getStatus().equals("對敏感族群不健康")){
            img_status.setImageResource(R.drawable.air_unhealthy);
        }else if(item.getStatus().equals("危險")){
            img_status.setImageResource(R.drawable.air_died);
        }
        UpdateTime.setText("各項汙染物濃度更新 : " + item.getPublishTime());
        tv_aqi.setText(item.getAQI());
        tv_co.setText(item.getCO()+" ppm");
        tv_no2.setText(item.getNO2()+" ppb");
        tv_o3.setText(item.getO3()+" ppb");
        tv_so2.setText(item.getNOx()+" ppb");
        tv_status.setText(item.getStatus());
        tv_pm10.setText(item.getPM10()+" mg/m3");
        tv_PM25.setText(item.getPM25()+" mg/m2");


//        txt_date.setText(item.getDate());
//        if(item.getOil().equals("92")){
//            view.setBackgroundResource(R.drawable.rule_background_92);
//        }else if(item.getOil().equals("95")){
//            view.setBackgroundResource(R.drawable.rule_background_95);
//        }else if(item.getOil().equals("98")) {
//            view.setBackgroundResource(R.drawable.rule_background_98);
//        }
//        txt_oil.setText(item.getOil());
//        txt_avgoil.setText(item.getAvgcost());
//        txt_addoil.setText(item.getAddoil());
//        txt_runkm.setText(item.getRunkm());
//        txt_total_run.setText(item.getTotoalKm());
        return row;
    }


    @Override
    public Filter getFilter() {
        return null;
    }
}
