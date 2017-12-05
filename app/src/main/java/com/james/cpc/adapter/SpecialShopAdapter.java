package com.james.cpc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.james.cpc.R;
import com.james.cpc.item.ListItem;
import com.james.cpc.item.SpecialListItem;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/6/27.
 */

public class SpecialShopAdapter extends BaseAdapter implements Filterable {
    private ArrayList<SpecialListItem> mListItems;
    private LayoutInflater inflater;
    Context context;
    String TAG = SpecialShopAdapter.class.getSimpleName();
    public SpecialShopAdapter(Context context) {
        this.context = context;
    }

    public SpecialShopAdapter(Context context, ArrayList<SpecialListItem> itemList) {
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
        final View row = inflater.inflate(R.layout.specialstore_item, parent, false);
        final SpecialListItem item = mListItems.get(position);
        final TextView tv_storeName = (TextView) row.findViewById(R.id.tv_storeName);
        tv_storeName.setText(item.getStoreName());

        final TextView tv_StoreTel = (TextView) row.findViewById(R.id.tv_StoreTel);
        tv_StoreTel.setText(item.getStoreTel());

        final TextView tv_location = (TextView) row.findViewById(R.id.tv_location);
        tv_location.setText(item.getGooglePilot());

        TextView tv_detail = (TextView) row.findViewById(R.id.tv_detail);
        tv_detail.setText(item.getStoreDetail());
        return row;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
