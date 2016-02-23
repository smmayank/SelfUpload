package com.mayank.selfuploadform.selfupload.photopicker;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public interface PhotoPickerView {

    void showImages(ArrayList<String> images);

    void setMenuText(Integer integer);

}
