package com.mayank.selfuploadform.selfupload.images.gallery;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

/**
 * Created by rahulchandnani on 22/02/16
 */
public interface GalleryView {

    void setDefaultMenuItems();

    void setHighlightedMenuItem();

    void setImagesToView(PhotoModel photoModel, ImageField.ImageFieldInteractionListener imageFieldInteractionListener);

}
