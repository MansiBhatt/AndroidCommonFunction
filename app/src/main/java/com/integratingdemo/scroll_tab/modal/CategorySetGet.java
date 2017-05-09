package com.integratingdemo.scroll_tab.modal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mansi on 20-02-2017.
 */

public class CategorySetGet {

    @SerializedName("responseCode")
    private
    int sucsess;
    @SerializedName("responseMessage")
    private
    String msg;

    public int getSucsess() {
        return sucsess;
    }

    public String getMsg() {
        return msg;
    }

    @SerializedName("responseObject")
    private
    ResponseObject responseObject;

    public ResponseObject getResponseObject() {
        return responseObject;
    }

    public class ResponseObject{

        @SerializedName("categoryDetails")
        ArrayList<CategoryDetailSetGet> categoryDetailSetGets = new ArrayList<>();

        public ArrayList<CategoryDetailSetGet> getCategoryDetailSetGets() {
            return categoryDetailSetGets;
        }
    }

}
