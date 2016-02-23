package com.mayank.selfuploadform.selfupload.photopicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.repository.PhotoPickerRepository;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerFragment extends BaseSelfUploadFragment implements PhotoPickerView,
        ImageField.ImageFieldInteractionListener, View.OnClickListener {

    private Toolbar toolbar;
    private GridView gridView;
    private Button addPhotosButton;
    private MenuItem textMenuItem;
    private PhotoPickerPresenter photoPickerPresenter;
    private PhotoPickerRepository photoPickerRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_photo_picker, container, false);
        getViews(view);
        initToolbar();
        initPresenter();
        setListeners();
        return view;
    }

    private void setListeners() {
        addPhotosButton.setOnClickListener(this);
    }

    private void initPresenter() {
        photoPickerRepository = new PhotoPickerRepository(getContext());
        photoPickerPresenter = new PhotoPickerPresenter(this, photoPickerRepository);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.add_photos_fragment_title));
        toolbar.setTitleTextColor(ContextCompat.getColor(getContext(), R.color.purple));
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.white));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getContext(), R.drawable.close_purple));
        setToolbar(toolbar);
    }

    private void getViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        gridView = (GridView) view.findViewById(R.id.picker_grid_view);
        addPhotosButton = (Button) view.findViewById(R.id.add_photos);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (null != menu) {
            menu.clear();
        }
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.self_upload_photo_picker_menu, menu);
        if (null != menu) {
            textMenuItem = menu.findItem(R.id.text_item);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void showImages(ArrayList<String> images) {
        PhotoPickerGridAdapter adapter = new PhotoPickerGridAdapter(getContext(), images, this);
        gridView.setAdapter(adapter);
    }

    @Override
    public void addSelection(PhotoModel.PhotoObject photoObject) {
        photoPickerPresenter.addSelection(photoObject);
    }

    @Override
    public void clearSelection(PhotoModel.PhotoObject photoObject) {
        photoPickerPresenter.clearSelection(photoObject);
    }

    @Override
    public void setMenuText(Integer integer) {
        if (null != textMenuItem && 0 != integer) {
            textMenuItem.setTitle(getString(R.string.selected_text_format, integer));
            refreshMenu();
            setViewVisibility(addPhotosButton, true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_photos: {
                photoPickerPresenter.addPhotosClickEvent();
                break;
            }
        }
    }
}
