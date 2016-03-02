package com.mayank.selfuploadform.selfupload.images.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.images.photopicker.PhotoPickerFragment;
import com.mayank.selfuploadform.selfupload.images.tagging.TaggingFragment;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryFragment extends BaseSelfUploadFragment implements GalleryView, View.OnClickListener,
        ImageField.ImageFieldInteractionListener {

    private Toolbar toolbar;
    private RecyclerView galleryRecyclerView;
    private Button saveProceedButton;
    private GalleryPresenter galleryPresenter;
    private GalleryAdapter galleryAdapter;
    private PhotoModel photoModel;
    private ImageView editImageView;
    private ImageView tagImageView;
    private ImageView deleteImageView;
    private boolean editModeSelected = false;

    public static GalleryFragment newInstance(PhotoModel photoModel) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GalleryRepository.GALLERY_MODEL, new Gson().toJson(photoModel));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_gallery, container, false);
        getViews(view);
        getExtras();
        initToolbar();
        initPresenter();
        setListeners();
        return view;
    }

    private void setListeners() {
        editImageView.setOnClickListener(this);
        deleteImageView.setOnClickListener(this);
        tagImageView.setOnClickListener(this);
        saveProceedButton.setOnClickListener(this);
    }

    private void getExtras() {
        photoModel = new Gson().fromJson(getArguments().getString(GalleryRepository.GALLERY_MODEL), PhotoModel.class);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.gallery_fragment_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), R.drawable.back_white));
        setToolbar(toolbar);
    }

    private void initPresenter() {
        galleryPresenter = new GalleryPresenter(this, photoModel);
    }

    private void getViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        galleryRecyclerView = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        saveProceedButton = (Button) view.findViewById(R.id.save_proceed);
        editImageView = (ImageView) view.findViewById(R.id.edit);
        deleteImageView = (ImageView) view.findViewById(R.id.delete);
        tagImageView = (ImageView) view.findViewById(R.id.tag);
        ViewCompat.setElevation(saveProceedButton, getResources().getDimensionPixelSize(R.dimen.dimen_10dp));
    }

    @Override
    public boolean onBackPressedHandled() {
        if (editModeSelected) {
            galleryPresenter.setDefaultState();
            galleryPresenter.clearSelectedImages();
            return !super.onBackPressedHandled();
        }
        return super.onBackPressedHandled();
    }

    @Override
    public void setDefaultMenuItems() {
        setViewVisibility(editImageView, true);
        setViewVisibility(deleteImageView, false);
        setViewVisibility(tagImageView, false);
    }

    @Override
    public void setHighlightedMenuItem() {
        setViewVisibility(editImageView, false);
        setViewVisibility(deleteImageView, true);
        setViewVisibility(tagImageView, true);
    }

    @Override
    public void disableEditMode() {
        editModeSelected = false;
    }

    @Override
    public void setDefaultState() {
        galleryAdapter.setState(ImageField.State.DEFAULT);
    }

    @Override
    public void setImagesToView(PhotoModel photoModel) {
        galleryAdapter = new GalleryAdapter(getContext(), photoModel, this, this);
        galleryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryRecyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void setHighlightedState() {
        editModeSelected = true;
        galleryAdapter.setState(ImageField.State.HIGHLIGHTED);
    }

    @Override
    public void clearSelectedImages() {
        galleryAdapter.clearSelectedImages();
    }

    @Override
    public void addSelection(PhotoModel.PhotoObject photoObject) {
        galleryPresenter.addSelection(photoObject);
    }

    @Override
    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        galleryPresenter.clearSelection(photoObject);
    }

    @Override
    public void addPhotoObject(PhotoModel.PhotoObject photoObject) {
        galleryAdapter.addPhotoObject(photoObject);
    }

    @Override
    public void clearPhotoObject(PhotoModel.PhotoObject photoObject) {
        galleryAdapter.clearPhotoObject(photoObject);
    }

    @Override
    public void launchTagging(PhotoModel photoModel, ArrayList<PhotoModel.PhotoObject> photoObjects) {
        clearBackStack();
        openFragment(TaggingFragment.newInstance(photoModel, photoObjects));
    }

    @Override
    public void launchPicker(PhotoModel photoModel) {
        openFragment(PhotoPickerFragment.newInstance(photoModel));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_proceed: {
                GalleryRepository.setPhotoModel(getContext(), getPropertyModel().getId(), photoModel);
                break;
            }
            case R.id.edit: {
                galleryPresenter.editOptionClicked();
                break;
            }
            case R.id.delete: {
                galleryPresenter.deleteOptionClicked();
                break;
            }
            case R.id.tag: {
                galleryPresenter.tagOptionClicked();
                break;
            }
            case R.id.add_photos: {
                galleryPresenter.addPhotosClicked();
            }
        }
    }
}
