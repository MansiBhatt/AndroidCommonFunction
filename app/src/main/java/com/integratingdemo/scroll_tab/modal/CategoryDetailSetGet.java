package com.integratingdemo.scroll_tab.modal;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mansi on 20-02-2017.
 */

public class CategoryDetailSetGet {

    @SerializedName("pk_categoryId")
    private
    String pk_categoryId;
    @SerializedName("category_name")
    private
    String category_name;
    @SerializedName("image")
    private
    String image;

    public String getPk_categoryId() {
        return pk_categoryId;
    }

    private String getCategory_name() {
        return category_name;
    }

    public String getImage() {
        return image;
    }

    @SerializedName("storeData")
    private
    ArrayList<StoreDataSetGet> storeDataSetGets = new ArrayList<>();

    public ArrayList<StoreDataSetGet> getStoreDataSetGets() {
        return storeDataSetGets;
    }

    public static String[] getCategoryNames(ArrayList<CategoryDetailSetGet> categories)
    {
        String[] categoryNames = new String[categories.size()];
        for(int i=0; i<categories.size(); i++)
        {
            categoryNames[i] = categories.get(i).getCategory_name();
        }
        return categoryNames;
    }

    public static String[] getCategoryImages(ArrayList<CategoryDetailSetGet> categories)
    {
        String[] categoryNames = new String[categories.size()];
        for(int i=0; i<categories.size(); i++)
        {
            categoryNames[i] = categories.get(i).getImage();
        }
        return categoryNames;
    }
}
