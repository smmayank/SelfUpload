package com.mayank.selfuploadform.selfupload.images.photopicker;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.repository.PhotoPickerRepository;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerPresenter implements PhotoPickerRepository.FetchImagesCallback {

    private PhotoPickerView photoPickerView;
    private ArrayList<PhotoModel.PhotoObject> photoObjects;

    public PhotoPickerPresenter(PhotoPickerView photoPickerView, PhotoPickerRepository photoPickerRepository) {
        this.photoPickerView = photoPickerView;
        this.photoObjects = new ArrayList<>();
        initDefaults(photoPickerRepository);
    }

    public void initDefaults(PhotoPickerRepository photoPickerRepository) {
        photoPickerRepository.fetchImages(this);
    }

    @Override
    public void onImagesFetch(ArrayList<PhotoModel.PhotoObject> images) {
        photoPickerView.showImages(images);
    }

    public void addSelection(PhotoModel.PhotoObject photoObject) {
        this.photoObjects.add(photoObject);
        setSelectedMenuText();
    }

    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        this.photoObjects.remove(photoObject);
        setSelectedMenuText();
    }

    private void setSelectedMenuText() {
        photoPickerView.setSelectedText(photoObjects.size());
    }

    public void addPhotosClickEvent() {
        photoPickerView.launchTagging(photoObjects);
    }
}
