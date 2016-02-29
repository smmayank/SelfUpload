package com.mayank.selfuploadform.selfupload.commercials;

import com.mayank.selfuploadform.models.PropertyModel;

public class SelfUploadCommercialsPresenter {

    private PropertyModel propertyModel;
    private final SelfUploadCommercialsView view;

    public SelfUploadCommercialsPresenter(SelfUploadCommercialsView view, PropertyModel propertyModel) {
        this.propertyModel = propertyModel;
        this.view = view;
        setDefaults();
    }

    private void setDefaults() {

    }
}
