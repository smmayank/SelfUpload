package com.mayank.selfuploadform.selfupload.images.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class GalleryFragment extends BaseSelfUploadFragment implements GalleryView {

    private Toolbar toolbar;
    private RecyclerView galleryRecyclerView;
    private Button saveProceedButton;
    private MenuItem editMenuItem;
    private MenuItem tagMenuItem;
    private MenuItem deleteMenuItem;
    private GalleryPresenter galleryPresenter;
    private GalleryRepository galleryRepository;
    private GalleryAdapter galleryAdapter;

    public static GalleryFragment newInstance(PhotoModel photoModel) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GalleryRepository.PHOTO_MODEL, new Gson().toJson(photoModel));
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
        inflater.inflate(R.menu.self_upload_gallery_menu, menu);
        if (null != menu) {
            editMenuItem = menu.findItem(R.id.edit);
            tagMenuItem = menu.findItem(R.id.tag);
            deleteMenuItem = menu.findItem(R.id.delete);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_gallery, container, false);
        getViews(view);
        initToolbar();
        initPresenter();
        return view;
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.gallery_fragment_title));
        setToolbar(toolbar);
    }

    private void initPresenter() {
        galleryRepository = new GalleryRepository(getContext(), null);
        galleryPresenter = new GalleryPresenter(this, galleryRepository);
    }

    private void getViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        galleryRecyclerView = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        saveProceedButton = (Button) view.findViewById(R.id.save_proceed);
    }

    @Override
    public void setDefaultMenuItems() {
        editMenuItem.setVisible(true);
        deleteMenuItem.setVisible(false);
        tagMenuItem.setVisible(false);
    }

    @Override
    public void setHighlightedMenuItem() {
        editMenuItem.setVisible(false);
        deleteMenuItem.setVisible(true);
        tagMenuItem.setVisible(true);
    }

    @Override
    public void setImagesToView(PhotoModel photoModel,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener) {
        galleryAdapter = new GalleryAdapter(getContext(), photoModel, imageFieldInteractionListener);
        galleryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryRecyclerView.setAdapter(galleryAdapter);
    }
}
