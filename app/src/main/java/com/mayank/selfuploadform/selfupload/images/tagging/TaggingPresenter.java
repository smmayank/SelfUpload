package com.mayank.selfuploadform.selfupload.images.tagging;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingPresenter {

    private TaggingView taggingView;
    private PhotoModel photoModel;
    private int count;
    private int doneCount;

    public TaggingPresenter(TaggingView taggingView, int count) {
        this.taggingView = taggingView;
        this.count = count;
        this.doneCount = 0;
        initDefaults();
    }

    private void initDefaults() {
        this.photoModel = new PhotoModel();
        photoModel.setMap(new HashMap<String, ArrayList<PhotoModel.PhotoObject>>());
    }

    public void addPhotoObject(String tag, PhotoModel.PhotoObject photoObject) {
        this.doneCount++;
        ArrayList<PhotoModel.PhotoObject> list;
        if (photoModel.getMap().containsKey(tag)) {
            list = photoModel.getMap().get(tag);
            list.add(photoObject);
        } else {
            list = new ArrayList<>();
            list.add(photoObject);
        }
        photoModel.getMap().put(tag, list);
        if (doneCount == count) {
            taggingView.disableTagSelector();
            taggingView.showDoneButton();
        } else {
            taggingView.setProgress((int) ((double) doneCount * 100 / count));
            taggingView.swipeFragment();
            taggingView.enableTagSelector();
            taggingView.setTitleProgress(doneCount + 1);
        }
    }

    public void removePhotoObject(String tag, PhotoModel.PhotoObject photoObject) {
        if (photoModel.getMap().containsKey(tag)) {
            ArrayList<PhotoModel.PhotoObject> list = photoModel.getMap().get(tag);
            list.remove(photoObject);
            photoModel.getMap().put(tag, list);
        }
    }

    public void doneButtonClicked() {
        taggingView.launchGallery(photoModel);
    }
}
