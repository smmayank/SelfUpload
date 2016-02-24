package com.mayank.selfuploadform.selfupload.images.photopicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerGridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PhotoModel.PhotoObject> images;
    private ImageField.ImageFieldInteractionListener imageFieldInteractionListener;
    private ArrayList<PhotoModel.PhotoObject> selectedPhotoObjects;
    private View.OnClickListener onClickListener;

    public PhotoPickerGridAdapter(Context context, ArrayList<PhotoModel.PhotoObject> images,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener,
            View.OnClickListener onClickListener) {
        this.context = context;
        this.images = images;
        this.imageFieldInteractionListener = imageFieldInteractionListener;
        this.selectedPhotoObjects = new ArrayList<>();
        this.onClickListener = onClickListener;
    }

    public void addSelection(PhotoModel.PhotoObject photoObject) {
        this.selectedPhotoObjects.add(photoObject);
    }

    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        this.selectedPhotoObjects.remove(photoObject);
    }

    @Override
    public int getCount() {
        return images.size() + 1;
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
        if (position == 0) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.self_upload_photo_picker_add_new, parent, false);
            convertView.findViewById(R.id.container).setOnClickListener(onClickListener);
        } else {
            ImageField imageField;
            if (convertView instanceof ImageField) {
                imageField = (ImageField) convertView;
            } else {
                imageField = new ImageField(context);
                convertView = imageField;
            }
            PhotoModel.PhotoObject object = images.get(position - 1);
            imageField.setImageFieldInteractionListener(imageFieldInteractionListener);
            imageField.setPhoto(object);
            if (selectedPhotoObjects.contains(object)) {
                imageField.setState(ImageField.State.SELECTED);
            } else {
                imageField.setState(ImageField.State.HIGHLIGHTED);
            }
        }
        return convertView;
    }
}