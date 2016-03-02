package com.mayank.selfuploadform.selfupload.dashboard;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

public interface SelfUploadDashboardView {

    int DEFAULT = 0;
    int INCOMPLETE = 1;
    int COMPLETED = 2;

    void setUsername(String name);

    void setProgress(int progress);

    void setDetailsSubTitle(String data);

    void setCommercialsSubTitle(String data);

    void setCapturedImages(ArrayList<PhotoModel.PhotoObject> photoObjects);

    void setDefaultPhotosView();

    void setCapturedPhotosView();

    void setDetailsImageView(int status);

    void setCommercialImageView(int status);

    void setPhotosImageView(int status);

    void openDetailsView();

    void openCommercialsView();

    void openPickerView();

    void openGalleryView(PhotoModel photoModel);

}