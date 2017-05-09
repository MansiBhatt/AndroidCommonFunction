package com.integratingdemo.scroll_tab.modal;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mansi on 20-02-2017.
 */

public class StoreDataSetGet {

    @SerializedName("pk_vendorId")
    private
    String pk_vendorId;
    @SerializedName("fk_categoryId")
    private
    String fk_categoryId;
    @SerializedName("vendor_storeName")
    private
    String vendor_storeName;
    @SerializedName("store_openingTime")
    private
    String store_openingTime;
    @SerializedName("store_closingTime")
    private
    String store_closingTime;

    public String getPk_vendorId() {
        return pk_vendorId;
    }

    public String getFk_categoryId() {
        return fk_categoryId;
    }

    public String getVendor_storeName() {
        return vendor_storeName;
    }

    public String getStore_openingTime() {
        return store_openingTime;
    }

    public String getStore_closingTime() {
        return store_closingTime;
    }
}
