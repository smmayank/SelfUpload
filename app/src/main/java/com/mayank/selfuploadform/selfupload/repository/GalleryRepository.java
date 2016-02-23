package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mayank.selfuploadform.models.PhotoModel;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryRepository {

    private static final String PHOTO_MODEL = "photo_model";

    private Context context;
    private Integer propertyID;

    public GalleryRepository(Context context, Integer propertyID) {
        this.context = context;
        this.propertyID = propertyID;
    }

    public PhotoModel getPhotoModel() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(PHOTO_MODEL, null);
        if (null != value) {
            return new Gson().fromJson(value, PhotoModel.class);
        } else {
            return new PhotoModel();
        }
    }

}
