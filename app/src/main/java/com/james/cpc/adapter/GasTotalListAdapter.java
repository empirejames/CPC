package com.james.cpc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.james.cpc.R;
import com.james.cpc.item.GasTotalItem;
import com.james.cpc.item.InfoItem;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/6/27.
 */

public class GasTotalListAdapter extends BaseAdapter implements Filterable {
    private ArrayList<GasTotalItem> mListItems;
    private LayoutInflater inflater;
    Context context;
    String TAG = GasTotalListAdapter.class.getSimpleName();
    public GasTotalListAdapter(Context context) {
        this.context = context;
    }

    public GasTotalListAdapter(Context context, ArrayList<GasTotalItem> itemList) {
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
        final View row = inflater.inflate(R.layout.gas_total_item, parent, false);
        final GasTotalItem item = mListItems.get(position);
        final TextView txt_date = (TextView) row.findViewById(R.id.txt_date);
        final TextView txt_oil = (TextView) row.findViewById(R.id.txt_oil);
        final TextView txt_avgoil = (TextView) row.findViewById(R.id.txt_avgoil);
        final TextView txt_addoil = (TextView) row.findViewById(R.id.txt_addoil);
        final TextView txt_runkm = (TextView) row.findViewById(R.id.txt_runkm);
        final TextView txt_total_run = (TextView) row.findViewById(R.id.txt_total_run);
        View view = (View)row.findViewById(R.id.oil_background);

        txt_date.setText(item.getDate());
        if(item.getOil().equals("92")){
            view.setBackgroundResource(R.drawable.rule_background_92);
        }else if(item.getOil().equals("95")){
            view.setBackgroundResource(R.drawable.rule_background_95);
        }else if(item.getOil().equals("98")) {
            view.setBackgroundResource(R.drawable.rule_background_98);
        }
        txt_oil.setText(item.getOil());
        txt_avgoil.setText(item.getAvgcost());
        txt_addoil.setText(item.getAddoil());
        txt_runkm.setText(item.getRunkm());
        txt_total_run.setText(item.getTotoalKm());
        return row;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
