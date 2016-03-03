package com.mayank.selfuploadform.selfupload.dashboard;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.models.PropertyModel;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.repository.ProgressRepository;

import java.util.ArrayList;

public class SelfUploadDashboardPresenter {

    private GalleryRepository galleryRepository;
    private ProgressRepository progressRepository;
    private String username;
    private SelfUploadDashboardView view;
    private PropertyModel model;

    public SelfUploadDashboardPresenter(SelfUploadDashboardView view, GalleryRepository galleryRepository,
            PropertyModel propertyModel, ProgressRepository progressRepository, String username) {
        this.view = view;
        this.model = propertyModel;
        this.username = username;
        this.progressRepository = progressRepository;
        this.galleryRepository = galleryRepository;
        initDefaults();
    }

    private void initDefaults() {
        view.setUsername(username);
        calculateProgress();
    }

    private void calculateProgress() {
        int detailsProgress = calculateDetailsProgress();
        int commercialProgress = calculateCommercialProgress();
        int photosProgress = calculatePhotosProgress();
        int totalProgress = (int) ((double) (commercialProgress + detailsProgress + photosProgress) / 3.0);
        view.setProgress(totalProgress);
    }

    private int calculateDetailsProgress() {
        int detailsProgress = ProgressRepository.getDetailsProgress(model);
        if (detailsProgress == 0) {
            view.setDetailsImageView(SelfUploadDashboardView.DEFAULT);
            view.setDetailsSubTitle(null);
        } else if (detailsProgress == 100) {
            view.setDetailsImageView(SelfUploadDashboardView.COMPLETED);
            view.setDetailsSubTitle(progressRepository.getDetailsText(model));
        } else {
            view.setDetailsImageView(SelfUploadDashboardView.INCOMPLETE);
            view.setDetailsSubTitle(progressRepository.getDetailsText(model));
        }
        return detailsProgress;
    }

    private int calculateCommercialProgress() {
        int commercialProgress = ProgressRepository.getCommercialProgress(model);
        if (commercialProgress == 0) {
            view.setCommercialImageView(SelfUploadDashboardView.DEFAULT);
            view.setCommercialsSubTitle(null);
        } else if (commercialProgress == 100) {
            view.setCommercialImageView(SelfUploadDashboardView.COMPLETED);
            view.setCommercialsSubTitle(progressRepository.getCommercialText(model));
        } else {
            view.setCommercialImageView(SelfUploadDashboardView.INCOMPLETE);
            view.setCommercialsSubTitle(progressRepository.getCommercialText(model));
        }
        return commercialProgress;
    }

    private int calculatePhotosProgress() {
        PhotoModel photoModel = galleryRepository.getPhotoModel(model.getId());
        ArrayList<PhotoModel.PhotoObject> photoObjects = new ArrayList<>();
        for (String tag : photoModel.getMap().keySet()) {
            photoObjects.addAll(photoModel.getMap().get(tag));
        }
        int totalObjects = photoObjects.size();
        int progress;
        if (0 == totalObjects) {
            progress = 0;
            view.setPhotosImageView(SelfUploadDashboardView.DEFAULT);
            view.setDefaultPhotosView();
        } else if (GalleryRepository.MIN_COUNT_GALLERY >= totalObjects) {
            progress = 100;
            view.setPhotosImageView(SelfUploadDashboardView.COMPLETED);
            view.setCapturedPhotosView();
            view.setCapturedImages(photoObjects);
        } else {
            progress = 50;
            view.setPhotosImageView(SelfUploadDashboardView.INCOMPLETE);
            view.setCapturedPhotosView();
            view.setCapturedImages(photoObjects);
        }
        return progress;
    }

    public void detailsCardClicked() {
        view.openDetailsView();
    }

    public void commercialsCardClicked() {
        view.openCommercialsView();
    }

    public void photosCardClicked() {
        PhotoModel photoModel = galleryRepository.getPhotoModel(model.getId());
        if (null == photoModel) {
            view.openPickerView();
        } else {
            view.openGalleryView(photoModel);
        }
    }

    public void saveUploadButtonClicked() {

    }
}
