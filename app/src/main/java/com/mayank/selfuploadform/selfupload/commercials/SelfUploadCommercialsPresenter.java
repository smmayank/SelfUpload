package com.mayank.selfuploadform.selfupload.commercials;

import com.mayank.selfuploadform.models.PropertyModel;
import com.mayank.selfuploadform.selfupload.repository.ProgressRepository;

public class SelfUploadCommercialsPresenter {

    private PropertyModel propertyModel;
    private final SelfUploadCommercialsView view;

    public SelfUploadCommercialsPresenter(SelfUploadCommercialsView view, PropertyModel propertyModel) {
        this.propertyModel = propertyModel;
        this.view = view;
        setDefaults();
    }

    private void setDefaults() {
        view.setAvailableFrom(propertyModel.getAvailableFrom());
        view.setBrokerage(propertyModel.getBrokerage(), propertyModel.getBrokerageFactor());
        view.setBuiltUpArea(propertyModel.getBuiltUpArea(), propertyModel.getBuiltUpAreaFactor());
        view.setCarpetArea(propertyModel.getCarpetArea(), propertyModel.getCarpetAreaFactor());
        view.setPrice(propertyModel.getPrice(), propertyModel.getPriceFactor());
        view.setPriceNegotiable(propertyModel.getIsPriceNegotiable());
        view.setSocietyCharges(propertyModel.getSocietyCharges(), propertyModel.getSocietyChargesFactor());
        setProgress();
    }

    public void priceNegotiable(boolean negotiable) {
        propertyModel.setIsPriceNegotiable(negotiable);
        setProgress();
    }

    public void setAvailableFrom(String availableFrom) {
        propertyModel.setAvailableFrom(availableFrom);
        view.setAvailableFrom(propertyModel.getAvailableFrom());
        setProgress();
    }

    public void setPrice(Double price, Double factor) {
        propertyModel.setPrice(price);
        propertyModel.setPriceFactor(factor);
        setProgress();
    }

    public void setBrokerage(Double brokerage, Double factor) {
        propertyModel.setBrokerage(brokerage);
        propertyModel.setBrokerageFactor(factor);
        setProgress();
    }

    public void setBuiltUpArea(Double area, Double factor) {
        propertyModel.setBuiltUpArea(area);
        propertyModel.setBuiltUpAreaFactor(factor);
        setProgress();
    }

    public void setCarpetArea(Double area, Double factor) {
        propertyModel.setCarpetArea(area);
        propertyModel.setCarpetAreaFactor(factor);
        setProgress();
    }

    public void setSocietyCharges(Double societyCharges, Double factor) {
        propertyModel.setSocietyCharges(societyCharges);
        propertyModel.setSocietyChargesFactor(factor);
        setProgress();
    }

    private void setProgress() {
        view.setProgress(ProgressRepository.getCommercialProgress(propertyModel));
    }
}