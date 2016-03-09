package com.mayank.selfuploadform.selfupload.search.building.models;

/**
 * Created by vikas-pc on 09/03/16
 */
public class LocalitiesModel {

    private static final String TAG = "LocalitiesModel";
    private String name;
    private String uuid;
    private String feature_type;

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFeature_type() {
        return feature_type;
    }

    public class LocalitiesWrapper {
        private LocalitiesModel locality;

        public LocalitiesModel getLocality() {
            return locality;
        }
    }
}
