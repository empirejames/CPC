package com.james.cpc.item;

/**
 * Created by 101716 on 2017/6/27.
 */

public class FastListItem {
    String TAG = FastListItem.class.getSimpleName();
    private String storeName, storeDetail, storeTel, googlePilot;
    public FastListItem(String storeName, String storeDetail, String storeTel, String googlePilot) {
        this.storeName = storeName;
        this.storeDetail = storeDetail;
        this.storeTel = storeTel;
        this.googlePilot = googlePilot;
    }
    public String getStoreName() {
        return storeName;
    }
    public String getStoreDetail() {
        return storeDetail;
    }
    public String getStoreTel() {
        return storeTel;
    }
    public String getGooglePilot() {
        return googlePilot;
    }
}
