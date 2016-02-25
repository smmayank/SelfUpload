package com.mayank.selfuploadform.selfupload.images.tagging;

import com.mayank.selfuploadform.models.PhotoModel;

/**
 * Created by rahulchandnani on 25/02/16
 */
public interface TaggingView {

    void swipeFragment();

    void showDoneButton();

    void setProgress(int fraction);

    void setTitleProgress(int count);

    void launchGallery(PhotoModel photoModel);

    void disableTagSelector();

    void enableTagSelector();
}
