package com.james.cpc;

/**
 * Created by 101716 on 2017/6/27.
 */

public class ListItem {
    String TAG = ListItem.class.getSimpleName();
    private String title, text, detail;
    public ListItem(String title, String text, String detail) {
        this.title = title;
        this.text = text;
        this.detail = detail;
    }
    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
    public String getDetail() {
        return detail;
    }
}
