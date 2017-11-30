package com.james.cpc;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/6/27.
 */

public class InfoAdapter extends BaseAdapter implements Filterable {
    private ArrayList<InfoItem> mListItems;
    private LayoutInflater inflater;
    Context context;
    String TAG = InfoAdapter.class.getSimpleName();
    public InfoAdapter(Context context) {
        this.context = context;
    }

    public InfoAdapter(Context context, ArrayList<InfoItem> itemList) {
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
        final View row = inflater.inflate(R.layout.info_item, parent, false);
        final InfoItem item = mListItems.get(position);
        final ImageView img = (ImageView) row.findViewById(R.id.img_status);
        final TextView title = (TextView) row.findViewById(R.id.tv_title);
        final TextView text = (TextView) row.findViewById(R.id.tv_text);

        img.setImageResource(item.getImg());
        title.setText(item.getTitle());
        text.setText(item.getText());
        return row;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
