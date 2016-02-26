package com.mayank.selfuploadform.selfupload.images.photopicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.images.tagging.TaggingFragment;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.repository.PhotoPickerRepository;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerFragment extends BaseSelfUploadFragment implements PhotoPickerView,
        ImageField.ImageFieldInteractionListener, View.OnClickListener {

    public static final int IMAGE_CAPTURE_REQUEST_CODE = 102;
    private Toolbar toolbar;
    private GridView gridView;
    private Button addPhotosButton;
    private PhotoPickerPresenter photoPickerPresenter;
    private PhotoPickerGridAdapter adapter;
    private TextView selectedText;
    private PhotoModel photoModel;
    private ArrayList<String> paths;

    public static PhotoPickerFragment newInstance(PhotoModel photoModel) {
        PhotoPickerFragment fragment = new PhotoPickerFragment();
        if (null != photoModel) {
            Bundle bundle = new Bundle();
            bundle.putString(GalleryRepository.PHOTO_MODEL, new Gson().toJson(photoModel));
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_photo_picker, container, false);
        getViews(view);
        initToolbar();
        getExtras();
        initPresenter();
        setListeners();
        return view;
    }

    private void getExtras() {
        paths = new ArrayList<>();
        String photoModelString = getArguments().getString(GalleryRepository.PHOTO_MODEL, null);
        if (null != photoModelString) {
            photoModel = new Gson().fromJson(photoModelString, PhotoModel.class);
            for (String tag : photoModel.getMap().keySet()) {
                for (PhotoModel.PhotoObject photoObject : photoModel.getMap().get(tag)) {
                    paths.add(photoObject.getPath());
                }
            }
        }
    }

    @Override
    protected int getStatusBarColor() {
        return ContextCompat.getColor(getContext(), R.color.black_20_percent_opacity);
    }

    private void setListeners() {
        addPhotosButton.setOnClickListener(this);
    }

    private void initPresenter() {
        PhotoPickerRepository photoPickerRepository = new PhotoPickerRepository(getContext());
        photoPickerPresenter = new PhotoPickerPresenter(this, photoPickerRepository);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.add_photos_fragment_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.purple));
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), R.drawable.close_purple));
        ViewCompat.setElevation(toolbar, getResources().getDimensionPixelSize(R.dimen.dimen_10dp));
        ViewCompat.setElevation(selectedText, getResources().getDimensionPixelSize(R.dimen.dimen_12dp));
        setToolbar(toolbar);
    }

    @Override
    public void updateFragment(int requestCode, Object... data) {
        super.updateFragment(requestCode, data);
        switch (requestCode) {
            case IMAGE_CAPTURE_REQUEST_CODE: {
                photoPickerPresenter.initDefaults(new PhotoPickerRepository(getContext()));
                break;
            }
        }
    }

    private void getViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        gridView = (GridView) view.findViewById(R.id.picker_grid_view);
        addPhotosButton = (Button) view.findViewById(R.id.add_photos);
        selectedText = (TextView) view.findViewById(R.id.selected_text);
    }

    @Override
    public void showImages(ArrayList<PhotoModel.PhotoObject> images) {
        adapter = new PhotoPickerGridAdapter(getContext(), images, paths, this, this);
        gridView.setAdapter(adapter);
    }

    @Override
    public void addSelection(PhotoModel.PhotoObject photoObject) {
        photoPickerPresenter.addSelection(photoObject);
        adapter.addSelection(photoObject);
    }

    @Override
    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        photoPickerPresenter.clearSelection(photoObject);
        adapter.clearSelection(photoObject);
    }

    @Override
    public void launchTagging(ArrayList<PhotoModel.PhotoObject> photoObjects) {
        if (null != photoModel) {
            openFragment(TaggingFragment.newInstance(photoModel, photoObjects));
        } else {
            openFragment(TaggingFragment.newInstance(photoObjects));
        }
    }

    @Override
    public void setSelectedText(Integer integer) {
        if (0 != integer) {
            selectedText.setText(getString(R.string.selected_text_format, integer));
            setViewVisibility(addPhotosButton, true);
        } else {
            selectedText.setText(null);
            setViewVisibility(addPhotosButton, false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_photos: {
                photoPickerPresenter.addPhotosClickEvent();
                break;
            }
            case R.id.container: {
                launchCamera();
                break;
            }
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        getActivity().startActivityForResult(intent, IMAGE_CAPTURE_REQUEST_CODE);
    }
}