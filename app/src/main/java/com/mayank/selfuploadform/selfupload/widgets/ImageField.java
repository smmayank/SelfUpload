package com.mayank.selfuploadform.selfupload.widgets;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;

/**
 * Created by rahulchandnani on 22/02/16
 */
public class ImageField extends RelativeLayout implements View.OnClickListener {

    private ImageView image;
    private ImageView check;
    private int currentState;
    private PhotoModel.PhotoObject photoObject;
    private ImageFieldInteractionListener imageFieldInteractionListener;

    public ImageField(Context context) {
        this(context, null, 0);
    }

    public ImageField(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        image = new ImageView(getContext());
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gallery_element_background));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        image.setLayoutParams(params);
        this.addView(image);
        check = new ImageView(getContext());
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        int margin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_5dp);
        params1.setMargins(0, margin, margin, 0);
        check.setLayoutParams(params1);
        ViewCompat.setElevation(check, getContext().getResources().getDimensionPixelSize(R.dimen.dimen_10dp));
        this.addView(check);
        setState(State.DEFAULT);
    }

    public void setImageFieldInteractionListener(ImageFieldInteractionListener imageFieldInteractionListener) {
        this.imageFieldInteractionListener = imageFieldInteractionListener;
    }

    /**
     * super.setOnClickListener() call removed to stop the use of OnClickListener with this view.
     */
    @Override
    public void setOnClickListener(OnClickListener l) {

    }

    @Override
    public void onClick(View v) {
        if (currentState == State.HIGHLIGHTED) {
            setState(State.SELECTED);
            if (null != imageFieldInteractionListener) {
                imageFieldInteractionListener.addSelection(photoObject);
            }
        } else {
            setState(State.HIGHLIGHTED);
            if (null != imageFieldInteractionListener) {
                imageFieldInteractionListener.clearSelection(photoObject);
            }
        }
    }

    public void setPhoto(PhotoModel.PhotoObject photoObject) {
        this.photoObject = photoObject;
        // TODO: 22/02/16 load images from photo object to image view.
    }

    public void setState(int state) {
        this.currentState = state;
        switch (currentState) {
            case State.HIGHLIGHTED: {
                setMargin(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_0dp));
                check.setVisibility(View.VISIBLE);
                check.setOnClickListener(this);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                this.setEnabled(true);
                break;
            }
            case State.SELECTED: {
                setMargin(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_12dp));
                check.setVisibility(View.VISIBLE);
                check.setOnClickListener(this);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_selected));
                this.setEnabled(true);
                break;
            }
            case State.USED: {
                setMargin(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_12dp));
                check.setVisibility(View.GONE);
                check.setOnClickListener(null);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                this.setEnabled(false);
                break;
            }
            default:
            case State.DEFAULT: {
                setMargin(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_0dp));
                check.setVisibility(View.GONE);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                check.setOnClickListener(null);
                this.setEnabled(true);
                break;
            }
        }
    }

    private void setMargin(int margin) {
        LayoutParams params = (RelativeLayout.LayoutParams) image.getLayoutParams();
        params.setMargins(margin, margin, margin, margin);
        image.setLayoutParams(params);
    }

    public static class State {

        public static final int DEFAULT = 997;
        public static final int HIGHLIGHTED = 998;
        public static final int SELECTED = 999;
        public static final int USED = 996;

    }

    public interface ImageFieldInteractionListener {

        void addSelection(PhotoModel.PhotoObject photoObject);

        void clearSelection(PhotoModel.PhotoObject photoObject);

    }

}