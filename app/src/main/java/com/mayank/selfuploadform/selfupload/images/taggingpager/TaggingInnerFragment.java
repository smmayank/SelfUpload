package com.mayank.selfuploadform.selfupload.images.taggingpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingInnerFragment extends Fragment implements TaggingInnerView {

    private static final String PHOTO_OBJECT = "photo_object";
    private static final long ANIMATION_DURATION = 300;

    private ImageView imageView;
    private TextView tagTextView;
    private TaggingInnerPresenter taggingInnerPresenter;
    private PhotoModel.PhotoObject photoObject;

    public static TaggingInnerFragment newInstance(PhotoModel.PhotoObject photoObject) {
        TaggingInnerFragment fragment = new TaggingInnerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PHOTO_OBJECT, new Gson().toJson(photoObject));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_tag_selector_image, container, false);
        getViews(view);
        getExtras();
        initPresenter();
        return view;
    }

    private void getExtras() {
        this.photoObject = new Gson().fromJson(getArguments().getString(PHOTO_OBJECT), PhotoModel.PhotoObject.class);
    }

    private void initPresenter() {
        taggingInnerPresenter = new TaggingInnerPresenter(this, photoObject);
    }

    private void getViews(View view) {
        imageView = (ImageView) view.findViewById(R.id.image);
        tagTextView = (TextView) view.findViewById(R.id.tag);
    }

    public void updateTag(String tag) {
        taggingInnerPresenter.setTag(tag);
    }

    @Override
    public void setImage(String imagePath) {
        File file = new File(imagePath);
        Picasso.with(getContext()).load(file).into(imageView);
    }

    @Override
    public void setTag(String tag) {
        tagTextView.setText(tag);
        if (View.GONE == tagTextView.getVisibility()) {
            tagTextView.animate().alpha(1f).setDuration(ANIMATION_DURATION).start();
            tagTextView.setVisibility(View.VISIBLE);
        }
    }
}
