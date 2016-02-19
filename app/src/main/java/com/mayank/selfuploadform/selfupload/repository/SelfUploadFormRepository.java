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

  public List<BaseSelfUploadEntry> getPropertyType() {
    ArrayList<BaseSelfUploadEntry> propertyType = new ArrayList<>();
    String[] stringArray = context.getResources().getStringArray(R.array.property_types);
    for (String data : stringArray) {
      String[] split = data.split(SEPARATOR);
      propertyType.add(new BaseSelfUploadEntry(split[0], split[1]));
    }
    return propertyType;

  }

  public List<BaseSelfUploadEntry> getFlatConfigurationType() {
    ArrayList<BaseSelfUploadEntry> flatConfig = new ArrayList<>();
    String[] stringArray = context.getResources().getStringArray(R.array.flat_configurations);
    for (String data : stringArray) {
      String[] split = data.split(SEPARATOR);
      flatConfig.add(new BaseSelfUploadEntry(split[0], split[1]));
    }
    return flatConfig;
  }

  public List<BaseSelfUploadEntry> getEntranceEntries() {
    ArrayList<BaseSelfUploadEntry> flatConfig = new ArrayList<>();
    String[] stringArray = context.getResources().getStringArray(R.array.entrance_facing_types);
    for (String data : stringArray) {
      String[] split = data.split(SEPARATOR);
      flatConfig.add(new BaseSelfUploadEntry(split[0], split[1]));
    }
    return flatConfig;
  }

  public List<BaseSelfUploadEntry> getAmenitiesEntries() {
    ArrayList<BaseSelfUploadEntry> flatConfig = new ArrayList<>();
    String[] stringArray = context.getResources().getStringArray(R.array.yes_no);
    int index = 0;
    for (String data : stringArray) {
      flatConfig.add(new BaseSelfUploadEntry(data, String.valueOf(index)));
      index++;
    }
    return flatConfig;
  }
}
