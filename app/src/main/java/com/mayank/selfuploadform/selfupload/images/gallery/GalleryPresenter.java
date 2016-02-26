package com.mayank.selfuploadform.selfupload.images.gallery;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryPresenter {

    private GalleryView galleryView;
    private PhotoModel photoModel;
    private ArrayList<PhotoModel.PhotoObject> selectedPhotoObjects;

    public GalleryPresenter(GalleryView galleryView, PhotoModel photoModel) {
        this.galleryView = galleryView;
        this.photoModel = photoModel;
        initDefaults();
    }

    private void initDefaults() {
        this.selectedPhotoObjects = new ArrayList<>();
        galleryView.setDefaultMenuItems();
        galleryView.setImagesToView(photoModel);
    }

    public void editOptionClicked() {
        galleryView.setHighlightedMenuItem();
        galleryView.setHighlightedState();
    }

    public void setDefaultState() {
        galleryView.setDefaultMenuItems();
        galleryView.disableEditMode();
        galleryView.setDefaultState();
    }

    public void addSelection(PhotoModel.PhotoObject photoObject) {
        if (!this.selectedPhotoObjects.contains(photoObject)) {
            this.selectedPhotoObjects.add(photoObject);
            galleryView.addPhotoObject(photoObject);
        }
    }

    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        if (selectedPhotoObjects.contains(photoObject)) {
            this.selectedPhotoObjects.remove(photoObject);
            galleryView.clearPhotoObject(photoObject);
        }
    }

    public void clearSelectedImages() {
        this.selectedPhotoObjects.clear();
        galleryView.clearSelectedImages();
    }

    public void tagOptionClicked() {
        galleryView.launchTagging(photoModel, selectedPhotoObjects);
    }

    public void deleteOptionClicked() {
        ArrayList<String> tagsToBeRemoved = new ArrayList<>();
        for (String tag : photoModel.getMap().keySet()) {
            ArrayList<PhotoModel.PhotoObject> photoObjects = photoModel.getMap().get(tag);
            ArrayList<PhotoModel.PhotoObject> objectsToBeRemoved = new ArrayList<>();
            for (PhotoModel.PhotoObject photoObject : photoObjects) {
                if (selectedPhotoObjects.contains(photoObject)) {
                    objectsToBeRemoved.add(photoObject);
                }
            }
            if (photoObjects.size() == objectsToBeRemoved.size()) {
                tagsToBeRemoved.add(tag);
            } else {
                for (PhotoModel.PhotoObject photoObject : objectsToBeRemoved) {
                    photoObjects.remove(photoObject);
                }
            }
            photoModel.getMap().put(tag, photoObjects);
        }
        for (String tag : tagsToBeRemoved) {
            photoModel.getMap().remove(tag);
        }
        selectedPhotoObjects.clear();
        galleryView.disableEditMode();
        galleryView.setDefaultMenuItems();
        galleryView.setImagesToView(photoModel);
    }

    public void addPhotosClicked() {
        galleryView.launchPicker(photoModel);
    }
}
