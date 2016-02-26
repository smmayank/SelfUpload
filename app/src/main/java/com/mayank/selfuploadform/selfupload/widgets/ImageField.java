package com.mayank.selfuploadform.selfupload.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by rahulchandnani on 22/02/16
 */
@SuppressWarnings("SuspiciousNameCombination")
public class ImageField extends RelativeLayout implements View.OnClickListener {

    private static final int FRACTION = 3;
    private static final long ANIMATION_DURATION = 300;
    private ImageView image;
    private ImageView check;
    private int currentState;
    private PhotoModel.PhotoObject photoObject;
    private ImageFieldInteractionListener imageFieldInteractionListener;
    private int width;
    private ImageView overlay;

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
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (int) ((double) size.x / FRACTION);
        image = new ImageView(getContext());
        this.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gallery_element_background));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.width = width;
        params.height = width;
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
        overlay = new ImageView(getContext());
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params2.width = width;
        params2.height = width;
        overlay.setLayoutParams(params2);
        overlay.setImageDrawable(
                new ColorDrawable(ContextCompat.getColor(getContext(), R.color.gallery_element_background_40_percent)));
        ViewCompat.setElevation(overlay, getResources().getDimensionPixelSize(R.dimen.dimen_16dp));
        this.addView(overlay);
        overlay.setVisibility(GONE);
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
        File file = new File(photoObject.getPath());
        Picasso.with(getContext()).load(file).resize(width, width).centerCrop().into(image);
    }

    public void setState(int state) {
        this.currentState = state;
        switch (currentState) {
            case State.HIGHLIGHTED: {
                setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_0dp));
                check.setVisibility(View.VISIBLE);
                check.setOnClickListener(this);
                image.setOnClickListener(this);
                overlay.setVisibility(View.GONE);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                this.setEnabled(true);
                break;
            }
            case State.SELECTED: {
                setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_12dp));
                check.setVisibility(View.VISIBLE);
                check.setOnClickListener(this);
                overlay.setVisibility(View.GONE);
                image.setOnClickListener(this);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_selected));
                this.setEnabled(true);
                break;
            }
            case State.USED: {
                setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_12dp));
                check.setVisibility(View.GONE);
                check.setOnClickListener(null);
                image.setOnClickListener(null);
                overlay.setVisibility(View.VISIBLE);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                this.setEnabled(false);
                break;
            }
            default:
            case State.DEFAULT: {
                setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.dimen_0dp));
                check.setVisibility(View.GONE);
                check.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.photo_unselected));
                check.setOnClickListener(null);
                overlay.setVisibility(View.GONE);
                image.setOnClickListener(null);
                this.setEnabled(true);
                break;
            }
        }
    }

    private void setPadding(final int margin) {
        ValueAnimator animator = ValueAnimator.ofInt(image.getPaddingRight(), margin);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Integer value = (Integer) valueAnimator.getAnimatedValue();
                image.setPadding(value, value, value, value);
            }
        });
        animator.setDuration(ANIMATION_DURATION);
        animator.start();
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