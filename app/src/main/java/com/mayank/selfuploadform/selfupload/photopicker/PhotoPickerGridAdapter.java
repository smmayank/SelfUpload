package com.mayank.selfuploadform.selfupload.photopicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> images;
    private ImageField.ImageFieldInteractionListener imageFieldInteractionListener;

    public PhotoPickerGridAdapter(Context context, ArrayList<String> images, ImageField.ImageFieldInteractionListener
            imageFieldInteractionListener) {
        this.context = context;
        this.images = images;
        this.imageFieldInteractionListener = imageFieldInteractionListener;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = new ImageField(context);
        }
        ImageField imageField = (ImageField) convertView;
        imageField.setState(ImageField.State.HIGHLIGHTED);
        imageField.setImageFieldInteractionListener(imageFieldInteractionListener);
        PhotoModel.PhotoObject photoObject = new PhotoModel.PhotoObject();
        photoObject.setPath(images.get(position));
        imageField.setPhoto(photoObject);
        return convertView;
    }
}
