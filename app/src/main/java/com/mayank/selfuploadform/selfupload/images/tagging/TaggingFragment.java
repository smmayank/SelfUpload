package com.mayank.selfuploadform.selfupload.images.tagging;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.widgets.TagSelectorField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingFragment extends BaseSelfUploadFragment implements TaggingView {

    private static final String PHOTO_OBJECT_HOLDER = "photo_object_holder";

    private static final String TITLE = "Tag photos (%s of %s)";

    private TagSelectorField tagSelectorField;
    private Button doneButton;
    private ProgressBar progressBar;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TaggingPresenter taggingPresenter;
    private ArrayList<PhotoModel.PhotoObject> photoObjects;

    public static TaggingFragment newInstance(ArrayList<PhotoModel.PhotoObject> photoObjects) {
        TaggingFragment fragment = new TaggingFragment();
        Bundle bundle = new Bundle();
        PhotoObjectHolder holder = new PhotoObjectHolder();
        holder.setPhotoObjects(photoObjects);
        bundle.putString(PHOTO_OBJECT_HOLDER, new Gson().toJson(holder));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (null != menu) {
            menu.clear();
        }
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.self_upload_tagging_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.delete == item.getItemId()) {
            // TODO: 25/02/16 Determine what is to be done when delete is clicked and do so accordingly.
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_tag_images, container, false);
        getViews(view);
        getExtras();
        initToolbar();
        return view;
    }

    private void initToolbar() {
        toolbar.setTitle(String.format(TITLE, 1, photoObjects.size()));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), R.drawable.close_white));
        setToolbar(toolbar);
    }

    @Override
    protected int getStatusBarColor() {
        return ContextCompat.getColor(getContext(), android.R.color.black);
    }

    private void getViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        doneButton = (Button) view.findViewById(R.id.done);
        progressBar = (ProgressBar) view.findViewById(R.id.tag_progress_bar);
        tagSelectorField = (TagSelectorField) view.findViewById(R.id.tag_selector);
    }

    public void getExtras() {
        PhotoObjectHolder holder = new Gson().fromJson(getArguments().getString(PHOTO_OBJECT_HOLDER),
                PhotoObjectHolder.class);
        photoObjects = holder.getPhotoObjects();
    }

    public static class PhotoObjectHolder {

        private ArrayList<PhotoModel.PhotoObject> photoObjects;

        public ArrayList<PhotoModel.PhotoObject> getPhotoObjects() {
            return photoObjects;
        }

        public void setPhotoObjects(ArrayList<PhotoModel.PhotoObject> photoObjects) {
            this.photoObjects = photoObjects;
        }
    }
}
