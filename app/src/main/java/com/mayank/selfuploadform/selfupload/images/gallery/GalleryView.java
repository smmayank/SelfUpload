package com.mayank.selfuploadform.selfupload.images.gallery;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 22/02/16
 */
public interface GalleryView {

    void setDefaultMenuItems();

    void setHighlightedMenuItem();

    void setImagesToView(PhotoModel photoModel);

    void setHighlightedState();

    void setDefaultState();

    void addPhotoObject(PhotoModel.PhotoObject photoObject);

    void clearPhotoObject(PhotoModel.PhotoObject photoObject);

    void clearSelectedImages();

    void disableEditMode();

    void launchTagging(PhotoModel photoModel, ArrayList<PhotoModel.PhotoObject> photoObjects);

    void launchPicker(PhotoModel photoModel);

    void notifyUserToDelete(int maxAllowed, int currentTotal);

    void saveImages();
}