package com.mayank.selfuploadform.selfupload.dashboard;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.mayank.selfuploadform.selfupload.details.SelfUploadDetailsFragment;
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
        return view;
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
        return ContextCompat.getColor(getContext(), R.color.purple_primary_dark);
    }

    private void initToolbar() {
        toolbar.setTitleTextAppearance(getContext(), R.style.SelfUploadToolbarTitleTextAppearance);
        toolbar.setSubtitleTextAppearance(getContext(),
                R.style.SelfUploadToolbarSubtitleTextAppearance);
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_primary));
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        layoutParams.height =
                getResources().getDimensionPixelSize(R.dimen.self_upload_dashboard_toolbar_height);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setSubtitle(getString(R.string.self_upload_dashboard_subtitle));
        setToolbar(toolbar);
    }

    private void setImageView(ImageView imgView, int status) {
        int imgRes;
        switch (status) {
            default:
            case DEFAULT: {
                imgRes = R.drawable.enter;
                break;
            }
            case INCOMPLETE: {
                imgRes = R.drawable.incomplete;
                break;
            }
            case COMPLETED: {
                imgRes = R.drawable.complete;
                break;
            }
        }
        imgView.setImageResource(imgRes);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_upload_dashboard_details_card: {
                presenter.detailsCardClicked();
                break;
            }
            case R.id.self_upload_dashboard_commercials_card: {
                presenter.commercialsCardClicked();
                break;
            }
            case R.id.self_upload_dashboard_photos_card: {
                requestForPermission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACCESS_STORAGE_PERMISSION);
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
            saveUploadButton.setText(getString(R.string.dashboard_status_text, progress));
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
        openFragment(new SelfUploadDetailsFragment());
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
            imageViews.add((ImageView) imageStack.findViewById(R.id.image2));
            imageViews.add((ImageView) imageStack.findViewById(R.id.image3));
            imageViews.add((ImageView) imageStack.findViewById(R.id.image4));
            TextView textView = (TextView) imageStack.findViewById(R.id.last_text);
            if (photoObjects.size() <= imageViews.size()) {
                for (int i = 0; i < photoObjects.size(); i++) {
                    imageViews.get(i).setVisibility(View.VISIBLE);
                    File file = new File(photoObjects.get(i).getPath());
                    Picasso.with(getContext()).load(file).into(imageViews.get(i));
                }
            } else {
                for (int i = 0; i < imageViews.size(); i++) {
                    imageViews.get(i).setVisibility(View.VISIBLE);
                    File file = new File(photoObjects.get(i).getPath());
                    Picasso.with(getContext()).load(file).into(imageViews.get(i));
                }
                int count = photoObjects.size() + 1 - imageViews.size();
                textView.setText(getString(R.string.more_images_format, count));
                textView.setVisibility(View.VISIBLE);
            }
        } else {
            setDefaultPhotosView();
        }
    }

}