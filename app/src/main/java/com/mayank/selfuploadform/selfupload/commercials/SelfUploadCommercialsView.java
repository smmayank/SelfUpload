package com.mayank.selfuploadform.selfupload.commercials;

public interface SelfUploadCommercialsView {

    void setPrice(Double price, Double factor);

    void setBrokerage(Double brokerage, Double factor);

    void setPriceNegotiable(Boolean priceNegotiable);

    void setSocietyCharges(Double societyCharges, Double factor);

    void setBuiltUpArea(Double builtUpArea, Double factor);

    void setCarpetArea(Double carpetArea, Double factor);

    void setAvailableFrom(String date);

    void setProgress(int progress);
}