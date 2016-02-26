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
    private PhotoModel previousPhotoModel;

    public TaggingPresenter(TaggingView taggingView, PhotoModel previousPhotoModel, int count) {
        this.taggingView = taggingView;
        this.count = count;
        this.doneCount = 0;
        this.previousPhotoModel = previousPhotoModel;
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
        if (null != previousPhotoModel) {
            HashMap<String, String> pathTagMap = new HashMap<>();
            HashMap<String, PhotoModel.PhotoObject> pathObjectMap = new HashMap<>();
            for (String tag : previousPhotoModel.getMap().keySet()) {
                for (PhotoModel.PhotoObject photoObject : previousPhotoModel.getMap().get(tag)) {
                    pathTagMap.put(photoObject.getPath(), tag);
                    pathObjectMap.put(photoObject.getPath(), photoObject);
                }
            }
            for (String tag : photoModel.getMap().keySet()) {
                for (PhotoModel.PhotoObject photoObject : photoModel.getMap().get(tag)) {
                    pathTagMap.put(photoObject.getPath(), tag);
                    pathObjectMap.put(photoObject.getPath(), photoObject);
                }
            }
            photoModel.getMap().clear();
            for (String path : pathTagMap.keySet()) {
                String tag = pathTagMap.get(path);
                PhotoModel.PhotoObject photoObject = pathObjectMap.get(path);
                ArrayList<PhotoModel.PhotoObject> photoObjects;
                if (photoModel.getMap().containsKey(tag)) {
                    photoObjects = photoModel.getMap().get(tag);
                } else {
                    photoObjects = new ArrayList<>();
                }
                photoObjects.add(photoObject);
                photoModel.getMap().put(tag, photoObjects);
            }
        }
        taggingView.launchGallery(photoModel);
    }
}
