package com.mayank.selfuploadform.selfupload.images.taggingpager;

import com.mayank.selfuploadform.models.PhotoModel;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingInnerPresenter {

    private TaggingInnerView taggingInnerView;
    private PhotoModel.PhotoObject photoObject;

    public TaggingInnerPresenter(TaggingInnerView taggingInnerView, PhotoModel.PhotoObject photoObject) {
        this.taggingInnerView = taggingInnerView;
        this.photoObject = photoObject;
        initDefaults();
    }

    private void initDefaults() {
        taggingInnerView.setImage(photoObject.getPath());
    }

    public void setTag(String tag) {
        taggingInnerView.setTag(tag);
    }

}
