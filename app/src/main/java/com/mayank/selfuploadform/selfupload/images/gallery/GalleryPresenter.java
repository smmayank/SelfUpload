package com.mayank.selfuploadform.selfupload.images.gallery;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryPresenter implements ImageField.ImageFieldInteractionListener {

    private GalleryView galleryView;
    private GalleryRepository galleryRepository;

    public GalleryPresenter(GalleryView galleryView, GalleryRepository galleryRepository) {
        this.galleryView = galleryView;
        this.galleryRepository = galleryRepository;
        initDefaults();
    }

    private void initDefaults() {
        galleryView.setDefaultMenuItems();
        galleryView.setImagesToView(galleryRepository.getPhotoModel(), this);
    }

    @Override
    public void addSelection(PhotoModel.PhotoObject photoObject) {

    }

    @Override
    public void clearSelection(PhotoModel.PhotoObject photoObject) {

    }
}
