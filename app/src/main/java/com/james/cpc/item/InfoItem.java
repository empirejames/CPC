package com.james.cpc.item;

/**
 * Created by 101716 on 2017/6/27.
 */

public class InfoItem {
    String TAG = InfoItem.class.getSimpleName();
    private String title, text, detail;
    private Integer img;
    public InfoItem(Integer img ,String title, String text, String detail) {
        this.img = img;
        this.title = title;
        this.text = text;
        this.detail = detail;
    }
    public Integer getImg() {
        return img;
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
