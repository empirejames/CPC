package com.james.cpc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.james.cpc.item.ListItem;
import com.james.cpc.R;

import java.util.ArrayList;

/**
 * Created by 101716 on 2017/6/27.
 */

public class PackageAdapter extends BaseAdapter implements Filterable {
    private ArrayList<ListItem> mListItems;
    private LayoutInflater inflater;
    Context context;
    String TAG = PackageAdapter.class.getSimpleName();
    public PackageAdapter(Context context) {
        this.context = context;
    }

    public PackageAdapter(Context context, ArrayList<ListItem> itemList) {
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
        final View row = inflater.inflate(R.layout.list_item, parent, false);
        final ListItem item = mListItems.get(position);

        final TextView title = (TextView) row.findViewById(R.id.tv_title);
        title.setText(item.getTitle());

        TextView text = (TextView) row.findViewById(R.id.tv_text);
        text.setText(item.getText());
        return row;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
