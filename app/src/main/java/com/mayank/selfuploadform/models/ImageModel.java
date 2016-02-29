package com.mayank.selfuploadform.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vikas-pc on 23/02/16
 */
public class ImageModel extends RealmObject {

    private static final String TAG = "ImageModel";
    @PrimaryKey
    private int id;
    private String imageName;
    private String imageHash;
    private String imagePath;
    private int tagId;
    private String tagName;
    private PropertyModel associatedProperty;

    public PropertyModel getAssociatedProperty() {
        return associatedProperty;
    }

    public void setAssociatedProperty(PropertyModel associatedProperty) {
        this.associatedProperty = associatedProperty;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageHash() {
        return imageHash;
    }

    public void setImageHash(String imageHash) {
        this.imageHash = imageHash;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
