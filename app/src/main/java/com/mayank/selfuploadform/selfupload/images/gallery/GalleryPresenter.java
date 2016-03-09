package com.mayank.selfuploadform.selfupload.images.gallery;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;

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
        checkImages();
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
        galleryView.saveImages();
        checkImages();
    }

    public void saveImages() {
        if (GalleryRepository.MAX_COUNT_GALLERY < getCurrentTotalImages()) {
            galleryView.notifyUserToDelete(GalleryRepository.MAX_COUNT_GALLERY, getCurrentTotalImages());
        }
        galleryView.saveImages();

    }

    private void checkImages() {
        int currentTotal = getCurrentTotalImages();
        if (0 == currentTotal) {
            galleryView.launchPicker(photoModel);
        } else if (GalleryRepository.MAX_COUNT_GALLERY < currentTotal) {
            galleryView.setImagesToView(photoModel);
            galleryView.notifyUserToDelete(GalleryRepository.MAX_COUNT_GALLERY, currentTotal);
        } else {
            galleryView.setImagesToView(photoModel);
        }
    }

    private int getCurrentTotalImages() {
        if (null == photoModel) {
            return 0;
        } else {
            int total = 0;
            for (String key : photoModel.getMap().keySet()) {
                total += photoModel.getMap().get(key).size();
            }
            return total;
        }
    }

    public void addPhotosClicked() {
        galleryView.launchPicker(photoModel);
    }
}
