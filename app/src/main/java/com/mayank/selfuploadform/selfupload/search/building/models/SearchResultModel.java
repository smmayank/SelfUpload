package com.mayank.selfuploadform.selfupload.search.building.models;

/**
 * Created by vikas-pc on 01/03/16
 */
public class SearchResultModel {

    private static final String TAG = "BuildingSearchResult";
    private int id,city_id;
    private String name,uuid;
    private boolean is_valid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean is_valid() {
        return is_valid;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

}
