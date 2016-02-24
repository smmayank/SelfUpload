package com.mayank.selfuploadform.selfupload.images.photopicker;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public interface PhotoPickerView {

    void showImages(ArrayList<PhotoModel.PhotoObject> images);

    void setSelectedText(Integer integer);

    void launchTagging(ArrayList<PhotoModel.PhotoObject> photoObjects);
}
