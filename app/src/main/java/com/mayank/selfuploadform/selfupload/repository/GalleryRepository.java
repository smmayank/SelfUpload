package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.mayank.selfuploadform.models.PhotoModel;

import java.util.HashMap;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryRepository {

    public static final String GALLERY_MODEL = "gallery_model";
    public static final int MIN_COUNT_GALLERY = 5;

    private Context context;

    public GalleryRepository(Context context) {
        this.context = context;
    }

    public PhotoModel getPhotoModel(Integer propertyID) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(GALLERY_MODEL, null);
        if (null != value) {
            GalleryModel galleryModel = new Gson().fromJson(value, GalleryModel.class);
            return galleryModel.getMap().get(propertyID);
        } else {
            return null;
        }
    }

    public static class GalleryModel {

        private HashMap<Integer, PhotoModel> map;

        public HashMap<Integer, PhotoModel> getMap() {
            return map;
        }

        public void setMap(HashMap<Integer, PhotoModel> map) {
            this.map = map;
        }
    }

    public static void setPhotoModel(Context context, Integer propertyID, PhotoModel photoModel) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String value = preferences.getString(GALLERY_MODEL, null);
        GalleryModel galleryModel;
        HashMap<Integer, PhotoModel> map;
        if (null != value) {
            galleryModel = new Gson().fromJson(value, GalleryModel.class);
            map = galleryModel.getMap();
        } else {
            galleryModel = new GalleryModel();
            map = new HashMap<>();
        }
        map.put(propertyID, photoModel);
        galleryModel.setMap(map);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(GALLERY_MODEL, new Gson().toJson(galleryModel));
        editor.apply();
    }

}
