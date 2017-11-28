package com.james.cpc.Items;

import android.content.Context;

/**
 * Created by 101716 on 2017/6/27.
 */

public class InvoiceItem {
    String TAG = InvoiceItem.class.getSimpleName();
    private String stationCode, invoice;
    Context context;

    public InvoiceItem(String stationCode, String invoice) {
        this.stationCode = stationCode;
        this.invoice = invoice;

    }
    public String getStationCode() {
        return stationCode;
    }
    public String getInvoice() {
        return invoice;
    }

}
