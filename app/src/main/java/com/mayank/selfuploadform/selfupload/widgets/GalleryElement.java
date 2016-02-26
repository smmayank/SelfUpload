package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 22/02/16
 */
public class GalleryElement extends LinearLayout {

    private static final int NUM_OF_COLUMNS = 3;

    private TextView titleTextView;
    private int numOfColumns;
    private ImageField.ImageFieldInteractionListener imageFieldInteractionListener;
    private ArrayList<ImageField> imageFields;
    private ArrayList<PhotoModel.PhotoObject> photoObjects;
    private ArrayList<PhotoModel.PhotoObject> selectedPhotoObjects;

    public GalleryElement(Context context, ImageField.ImageFieldInteractionListener imageFieldInteractionListener) {
        this(context, null, 0);
        this.imageFieldInteractionListener = imageFieldInteractionListener;
    }

    public GalleryElement(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryElement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.imageFields = new ArrayList<>();
        this.setOrientation(LinearLayout.VERTICAL);
        this.setDividerDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.white)));
        this.setDividerPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_2dp));
        this.titleTextView = new TextView(getContext());
        this.addView(titleTextView);
        this.numOfColumns = NUM_OF_COLUMNS;
        this.selectedPhotoObjects = new ArrayList<>();
    }

    public void setState(int state) {
        for (ImageField field : imageFields) {
            field.setState(state);
        }
    }

    public void setNumberOfColumns(int columns) {
        this.numOfColumns = columns;
        for (int i = 1; i < getChildCount(); i++) {
            removeViewAt(i);
        }
        setImages(photoObjects, selectedPhotoObjects, ImageField.State.DEFAULT);
    }

    public void setTitle(String title) {
        this.titleTextView.setText(title);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) titleTextView.getLayoutParams();
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_16dp);
        params.setMargins(margin, margin, margin, margin);
        this.invalidate();
    }

    public void setImages(ArrayList<PhotoModel.PhotoObject> list,
            ArrayList<PhotoModel.PhotoObject> selectedPhotoObjects, int state) {
        if (null != list && 0 != list.size()) {
            photoObjects = list;
            this.selectedPhotoObjects = selectedPhotoObjects;
            imageFields.clear();
            int numOfRows =
                    (list.size() % numOfColumns == 0) ? list.size() / numOfColumns : (int) Math.ceil((double) list.size
                            () / numOfColumns);
            int currentRows = getChildCount() - 1;
            if (currentRows > numOfRows) {
                for (int i = numOfRows; i < currentRows; i++) {
                    removeViewAt(i);
                }
            } else {
                for (int i = 0; i < numOfRows - currentRows; i++) {
                    addView(createRow());
                }
            }
            for (int i = 0; i < numOfRows; i++) {
                LinearLayout layout = (LinearLayout) getChildAt(i + 1);
                for (int j = 0; j < numOfColumns; j++) {
                    int index = i * numOfColumns + j;
                    if (index < list.size()) {
                        ImageField field = (ImageField) layout.getChildAt(j);
                        field.setPhoto(list.get(index));
                        if (selectedPhotoObjects.contains(list.get(index))) {
                            field.setState(ImageField.State.SELECTED);
                        } else {
                            field.setState(state);
                        }
                        field.setVisibility(View.VISIBLE);
                        imageFields.add(field);
                    } else {
                        layout.getChildAt(j).setVisibility(View.INVISIBLE);
                    }
                }
            }
        }
        invalidate();
    }

    private LinearLayout createRow() {
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(HORIZONTAL);
        layout.setDividerPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_2dp));
        layout.setDividerDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.white)));
        for (int i = 0; i < numOfColumns; i++) {
            ImageField field = new ImageField(getContext());
            field.setImageFieldInteractionListener(imageFieldInteractionListener);
            layout.addView(field);
            LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) field.getLayoutParams();
            params1.width = 0;
            params1.weight = 1;
            field.setLayoutParams(params1);
        }
        return layout;
    }
}
