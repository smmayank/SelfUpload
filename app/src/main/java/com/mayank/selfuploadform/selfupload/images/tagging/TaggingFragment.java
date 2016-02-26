package com.mayank.selfuploadform.selfupload.images.tagging;

import android.os.Bundle;
import android.os.Message;
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
import com.mayank.selfuploadform.selfupload.images.gallery.GalleryFragment;
import com.mayank.selfuploadform.selfupload.images.taggingpager.TaggingPagerAdapter;
import com.mayank.selfuploadform.selfupload.widgets.NonSwipeViewPager;
import com.mayank.selfuploadform.selfupload.widgets.TagSelectorField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingFragment extends BaseSelfUploadFragment implements TaggingView,
        TagSelectorField.OnTagSelectListener, View.OnClickListener {

    private static final String PHOTO_OBJECT_HOLDER = "photo_object_holder";
    private static final String PHOTO_MODEL = "photo_model";

    private static final String TITLE = "Tag photos (%s of %s)";
    private static final long ANIMATION_DURATION = 300;
    private static final long DELAY_TIME = 500;

    private TagSelectorField tagSelectorField;
    private Button doneButton;
    private ProgressBar progressBar;
    private NonSwipeViewPager viewPager;
    private Toolbar toolbar;
    private TaggingPresenter taggingPresenter;
    private ArrayList<PhotoModel.PhotoObject> photoObjects;
    private TaggingPagerAdapter adapter;
    private UpdateHandler updateHandler;
    private PhotoModel photoModel;

    public static TaggingFragment newInstance(ArrayList<PhotoModel.PhotoObject> photoObjects) {
        return newInstance(null, photoObjects);
    }

    public static TaggingFragment newInstance(PhotoModel photoModel, ArrayList<PhotoModel.PhotoObject> photoObjects) {
        TaggingFragment fragment = new TaggingFragment();
        Bundle bundle = new Bundle();
        PhotoObjectHolder holder = new PhotoObjectHolder();
        holder.setPhotoObjects(photoObjects);
        bundle.putString(PHOTO_OBJECT_HOLDER, new Gson().toJson(holder));
        if (null != photoModel) {
            bundle.putString(PHOTO_MODEL, new Gson().toJson(photoModel));
        }
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
        initPresenter();
        setupViewPager();
        setListeners();
        setupHandler();
        return view;
    }

    private void setupHandler() {
        updateHandler = new UpdateHandler(taggingPresenter, photoObjects, viewPager);
    }

    private void initPresenter() {
        taggingPresenter = new TaggingPresenter(this, photoModel, photoObjects.size());
    }

    private void setListeners() {
        tagSelectorField.setOnTagSelectListener(this);
        doneButton.setOnClickListener(this);
    }

    private void setupViewPager() {
        adapter = new TaggingPagerAdapter(getChildFragmentManager(), photoObjects);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setPageTransformer(true, new DepthPageTransformer());
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
        viewPager = (NonSwipeViewPager) view.findViewById(R.id.view_pager);
        doneButton = (Button) view.findViewById(R.id.done);
        progressBar = (ProgressBar) view.findViewById(R.id.tag_progress_bar);
        tagSelectorField = (TagSelectorField) view.findViewById(R.id.tag_selector);
    }

    public void getExtras() {
        PhotoObjectHolder holder = new Gson().fromJson(getArguments().getString(PHOTO_OBJECT_HOLDER),
                PhotoObjectHolder.class);
        photoObjects = holder.getPhotoObjects();
        String photoModelString = getArguments().getString(PHOTO_MODEL, null);
        if (null != photoModelString) {
            photoModel = new Gson().fromJson(photoModelString, PhotoModel.class);
        }
    }

    @Override
    public void enableTagSelector() {
        tagSelectorField.enable();
    }

    @Override
    public void onTagSelect(View view, String tag) {
        adapter.setTag(tag, viewPager.getCurrentItem());
        tagSelectorField.disable();
        Message message = updateHandler.obtainMessage(UpdateHandler.TAG, tag);
        updateHandler.sendMessageDelayed(message, DELAY_TIME);
    }

    @Override
    public void onTagRemove(View view, String tag) {
        taggingPresenter.removePhotoObject(tag, photoObjects.get(viewPager.getCurrentItem()));
    }

    @Override
    public void swipeFragment() {
        int index = viewPager.getCurrentItem();
        tagSelectorField.reset();
        viewPager.setCurrentItem(index + 1, true);
    }

    @Override
    public void disableTagSelector() {
        tagSelectorField.reset();
        tagSelectorField.disable();
    }

    @Override
    public void showDoneButton() {
        progressBar.animate().alpha(0f).setDuration(ANIMATION_DURATION).start();
        doneButton.animate().alpha(1f).setDuration(ANIMATION_DURATION).start();
        doneButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitleProgress(int count) {
        toolbar.setTitle(String.format(TITLE, count, photoObjects.size()));
    }

    @Override
    public void setProgress(int fraction) {
        progressBar.setProgress(fraction);
    }

    @Override
    public void launchGallery(PhotoModel photoModel) {
        clearBackStack();
        openFragment(GalleryFragment.newInstance(photoModel));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == doneButton.getId()) {
            taggingPresenter.doneButtonClicked();
        }
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

    private static class UpdateHandler extends android.os.Handler {

        public static final int TAG = 1;
        private TaggingPresenter taggingPresenter;
        private ArrayList<PhotoModel.PhotoObject> list;
        private NonSwipeViewPager viewPager;

        public UpdateHandler(TaggingPresenter taggingPresenter, ArrayList<PhotoModel.PhotoObject> list,
                NonSwipeViewPager viewPager) {
            this.taggingPresenter = taggingPresenter;
            this.list = list;
            this.viewPager = viewPager;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String tag = (String) msg.obj;
            taggingPresenter.addPhotoObject(tag, list.get(viewPager.getCurrentItem()));
        }
    }

    public static class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.50f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            if (position < -1) {
                view.setAlpha(0);
            } else if (position <= 0) {
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);
            } else if (position <= 1) {
                view.setAlpha(1 - position);
                view.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            } else {
                view.setAlpha(0);
            }
        }
    }

}