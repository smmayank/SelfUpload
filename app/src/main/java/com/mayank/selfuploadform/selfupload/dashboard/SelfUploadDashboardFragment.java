package com.mayank.selfuploadform.selfupload.dashboard;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.commercials.SelfUploadCommercialsFragment;
import com.mayank.selfuploadform.selfupload.images.gallery.GalleryFragment;
import com.mayank.selfuploadform.selfupload.images.photopicker.PhotoPickerFragment;
import com.mayank.selfuploadform.selfupload.repository.GalleryRepository;
import com.mayank.selfuploadform.selfupload.repository.ProgressRepository;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class SelfUploadDashboardFragment extends BaseSelfUploadFragment
        implements SelfUploadDashboardView, View.OnClickListener {

    public static final int ACCESS_STORAGE_PERMISSION = 101;
    private static final int MAX_PROGRESS = 100;

    private static final String PROGRESS_TEXT = "%s%% COMPLETED";

    private SelfUploadDashboardPresenter presenter;

    private Toolbar toolbar;

    private Button saveUploadButton;

    private ProgressBar progressBar;

    private View defaultPhotosView;
    private View capturedPhotoView;
    private View imageStack;

    private TextView welcomeText;
    private TextView detailsHelpText;
    private TextView commercialHelpText;

    private ImageView detailsImageView;
    private ImageView commercialImageView;
    private ImageView photosImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_dashboard, container, false);
        initViews(view);
        initToolbar();
        setListeners(view);
        return view;
    }

    private void setListeners(View view) {
        view.findViewById(R.id.commercial_card).setOnClickListener(this);
        view.findViewById(R.id.property_details_card).setOnClickListener(this);
        view.findViewById(R.id.photos_card).setOnClickListener(this);
    }

    @Override
    public void onPermissionResult(int requestID, boolean granted) {
        super.onPermissionResult(requestID, granted);
        switch (requestID) {
            case ACCESS_STORAGE_PERMISSION: {
                if (granted) {
                    launchGalleryModule();
                } else {
                    Toast.makeText(getContext(), getString(R.string.storage_permission_denied), Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            }
        }
    }

    private void initViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        saveUploadButton = (Button) view.findViewById(R.id.save_upload);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        welcomeText = (TextView) view.findViewById(R.id.welcome_text);
        detailsHelpText = (TextView) view.findViewById(R.id.details_help_text);
        commercialHelpText = (TextView) view.findViewById(R.id.commercial_help_text);
        defaultPhotosView = view.findViewById(R.id.default_photos_view);
        capturedPhotoView = view.findViewById(R.id.photos_second_view);
        imageStack = view.findViewById(R.id.image_stack);
        detailsImageView = (ImageView) view.findViewById(R.id.details_image_view);
        commercialImageView = (ImageView) view.findViewById(R.id.commercial_image_view);
        photosImageView = (ImageView) view.findViewById(R.id.photos_image_view);
    }

    @Override
    public void onResume() {
        super.onResume();
        initValues();
    }

    private void initValues() {
        presenter = new SelfUploadDashboardPresenter(this, new GalleryRepository(getContext()), getPropertyModel(),
                new ProgressRepository(getContext()), "Daddy");
    }

    @Override
    protected int getStatusBarColor() {
        return ContextCompat.getColor(getContext(), R.color.primary_dark);
    }

    private void initToolbar() {
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_white);
        setToolbar(toolbar);
    }

    private void setImageView(ImageView imgView, int status) {
        int imageResource;
        switch (status) {
            case INCOMPLETE: {
                imageResource = R.drawable.incomplete;
                break;
            }
            case COMPLETED: {
                imageResource = R.drawable.complete;
                break;
            }
            default:
            case DEFAULT: {
                imageResource = R.drawable.enter;
                break;
            }
        }
        imgView.setImageResource(imageResource);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.property_details_card: {
                presenter.detailsCardClicked();
                break;
            }
            case R.id.commercial_card: {
                presenter.commercialsCardClicked();
                break;
            }
            case R.id.photos_card: {
                requestForPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_STORAGE_PERMISSION);
                break;
            }
            case R.id.save_upload: {
                presenter.saveUploadButtonClicked();
                break;
            }
        }
    }

    private void launchGalleryModule() {
        presenter.photosCardClicked();
    }

    @Override
    public void setUsername(String name) {
        welcomeText.setText(getString(R.string.welcome_text, name));
    }

    @Override
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
        if (progress != MAX_PROGRESS) {
            saveUploadButton.setText(String.format(PROGRESS_TEXT, progress));
            saveUploadButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.warm_grey));
            saveUploadButton.setOnClickListener(null);
        } else {
            saveUploadButton.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple));
            saveUploadButton.setText(getString(R.string.save_and_upload));
            saveUploadButton.setOnClickListener(this);
        }
    }

    @Override
    public void setDetailsSubTitle(String data) {
        if (!TextUtils.isEmpty(data)) {
            detailsHelpText.setText(data);
        } else {
            detailsHelpText.setText(getString(R.string.self_upload_dashboard_property_detail_sub_title));
        }
    }

    @Override
    public void setCommercialsSubTitle(String data) {
        if (!TextUtils.isEmpty(data)) {
            commercialHelpText.setText(data);
        } else {
            commercialHelpText.setText(getString(R.string.self_upload_dashboard_commercials_sub_title));
        }
    }

    @Override
    public void setCapturedImages(ArrayList<PhotoModel.PhotoObject> photoObjects) {
        handleImageStack(photoObjects);
    }

    @Override
    public void setDefaultPhotosView() {
        defaultPhotosView.setVisibility(View.VISIBLE);
        capturedPhotoView.setVisibility(View.GONE);
    }

    @Override
    public void setCapturedPhotosView() {
        defaultPhotosView.setVisibility(View.GONE);
        capturedPhotoView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDetailsImageView(int status) {
        setImageView(detailsImageView, status);
    }

    @Override
    public void setCommercialImageView(int status) {
        setImageView(commercialImageView, status);
    }

    @Override
    public void setPhotosImageView(int status) {
        setImageView(photosImageView, status);
    }

    @Override
    public void openDetailsView() {
        //openFragment(new SelfUploadDetailsFragment());
    }

    @Override
    public void openCommercialsView() {
        openFragment(new SelfUploadCommercialsFragment());
    }

    @Override
    public void openPickerView() {
        openFragment(new PhotoPickerFragment());
    }

    @Override
    public void openGalleryView(PhotoModel photoModel) {
        openFragment(GalleryFragment.newInstance(photoModel));
    }

    private void handleImageStack(ArrayList<PhotoModel.PhotoObject> photoObjects) {
        if (null != photoObjects && 0 < photoObjects.size()) {
            ArrayList<ImageView> imageViews = new ArrayList<>();
            imageViews.add((ImageView) imageStack.findViewById(R.id.image1));
            ImageView imageView2 = (ImageView) imageStack.findViewById(R.id.image2);
            ViewCompat.setElevation(imageView2, getResources().getDimension(R.dimen.dimen_5dp));
            imageViews.add(imageView2);
            ImageView imageView3 = (ImageView) imageStack.findViewById(R.id.image3);
            ViewCompat.setElevation(imageView3, getResources().getDimension(R.dimen.dimen_10dp));
            imageViews.add(imageView3);
            ImageView imageView4 = (ImageView) imageStack.findViewById(R.id.image4);
            ViewCompat.setElevation(imageView4, getResources().getDimension(R.dimen.dimen_15dp));
            imageViews.add(imageView4);
            TextView textView = (TextView) imageStack.findViewById(R.id.last_text);
            int maxCount;
            boolean showText;
            if (imageViews.size() == photoObjects.size()) {
                maxCount = imageViews.size();
                showText = false;
            } else if (imageViews.size() > photoObjects.size()) {
                maxCount = photoObjects.size();
                showText = false;
            } else {
                maxCount = imageViews.size() - 1;
                showText = true;
            }
            for (int i = 0; i < maxCount; i++) {
                imageViews.get(i).setVisibility(View.VISIBLE);
                File file = new File(photoObjects.get(i).getPath());
                Picasso.with(getContext()).load(file).into(imageViews.get(i));
            }
            if (showText) {
                imageViews.get(imageViews.size() - 1).setVisibility(View.VISIBLE);
                int count = photoObjects.size() - (imageViews.size() - 1);
                textView.setText(getString(R.string.more_images_format, count));
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            setDefaultPhotosView();
        }
    }

}