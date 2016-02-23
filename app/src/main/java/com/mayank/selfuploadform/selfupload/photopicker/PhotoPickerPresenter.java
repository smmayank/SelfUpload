package com.mayank.selfuploadform.selfupload.photopicker;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.repository.PhotoPickerRepository;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerPresenter implements PhotoPickerRepository.FetchImagesCallback {

    private PhotoPickerRepository photoPickerRepository;
    private PhotoPickerView photoPickerView;
    private ArrayList<PhotoModel.PhotoObject> photoObjects;

    public PhotoPickerPresenter(PhotoPickerView photoPickerView, PhotoPickerRepository photoPickerRepository) {
        this.photoPickerView = photoPickerView;
        this.photoPickerRepository = photoPickerRepository;
        this.photoObjects = new ArrayList<>();
        initDefaults();
    }

    private void initDefaults() {
        photoPickerRepository.fetchImages(this);
    }

    @Override
    public void onImagesFetch(ArrayList<String> images) {
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
        photoPickerView.setMenuText(photoObjects.size());
    }

    public void addPhotosClickEvent() {

    }
}
