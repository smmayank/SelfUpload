package com.mayank.selfuploadform.selfupload.search.building.models;

import java.util.ArrayList;

/**
 * Created by vikas-pc on 09/03/16
 */
public class BuildingDetailsResult {

    private static final String TAG = "BuildingDetailsResult";
    Double lat, lng;

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public static class BuildingDetailsWrapper {
        ArrayList<BuildingDetailsResult> result;

        public ArrayList<BuildingDetailsResult> getResult() {
            return result;
        }
    }
}
