package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadEntry;

import java.util.ArrayList;
import java.util.List;

public class SelfUploadFormRepository {

    private static final java.lang.String SEPARATOR = ",";
    private final Context context;

    public SelfUploadFormRepository(Context context) {
        this.context = context;
    }

    public List<BaseSelfUploadEntry<Integer>> getPropertyType() {
        return getIntegerData(R.array.property_types);
    }

    private List<BaseSelfUploadEntry<Integer>> getIntegerData(int resId) {
        ArrayList<BaseSelfUploadEntry<Integer>> propertyType = new ArrayList<>();
        String[] stringArray = context.getResources().getStringArray(resId);
        for (String data : stringArray) {
            String[] split = data.split(SEPARATOR);
            propertyType.add(new BaseSelfUploadEntry<Integer>(split[0], Integer.parseInt(split[1])));
        }
        return propertyType;
    }

    public List<BaseSelfUploadEntry<Integer>> getFlatConfigurationType() {
        return getIntegerData(R.array.flat_configurations);
    }

    public List<BaseSelfUploadEntry<String>> getEntranceEntries() {
        return getStringData(R.array.entrance_facing_types, true);
    }

    private List<BaseSelfUploadEntry<String>> getStringData(int resId, boolean replicate) {
        ArrayList<BaseSelfUploadEntry<String>> propertyType = new ArrayList<>();
        String[] stringArray = context.getResources().getStringArray(resId);
        for (String data : stringArray) {
            if (replicate) {
                propertyType.add(new BaseSelfUploadEntry<String>(data, data));
            } else {
                String[] split = data.split(SEPARATOR);
                propertyType.add(new BaseSelfUploadEntry<String>(split[0], split[1]));
            }
        }
        return propertyType;
    }

    private List<BaseSelfUploadEntry<Boolean>> getBooleanData(int resId) {
        ArrayList<BaseSelfUploadEntry<Boolean>> propertyType = new ArrayList<>();
        String[] stringArray = context.getResources().getStringArray(resId);
        for (String data : stringArray) {
            String[] split = data.split(SEPARATOR);
            propertyType.add(new BaseSelfUploadEntry<Boolean>(split[0], Boolean.parseBoolean(split[1])));
        }
        return propertyType;
    }

    public List<BaseSelfUploadEntry<Boolean>> getAmenitiesEntries() {
        return getBooleanData(R.array.yes_no);
    }



}
