package com.mayank.selfuploadform.selfupload.dashboard;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
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

public class SelfUploadDashboardFragment extends BaseSelfUploadFragment
        implements SelfUploadDashboardView, View.OnClickListener {

    private static final int MAX_PROGRESS = 100;
    public static final int ACCESS_STORAGE_PERMISSION = 101;
    private SelfUploadDashboardPresenter presenter;
    private Toolbar toolbar;
    private float cardsElevation;
    private Button actionButton;
    private ProgressBar progressBar;
    private TextView detailsSubtitleView;
    private TextView commercialsSubtitleView;
    private TextView photosSubtitleView;
    private ImageView detailsStatusView;
    private ImageView commercialsStatusView;
    private ImageView photosStatusView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.self_upload_dashboard_fragment, container, false);
        initValues();
        initViews(view);
        initToolbar();
        initPresenter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void setUsername(CharSequence name) {
        toolbar.setTitle(getString(R.string.self_upload_dashboard_title, name));
        updateToolbar();
    }

    private void updateToolbar() {
        setToolbar(toolbar);
    }

    @Override
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void showProgressBar(boolean visible) {
        setViewVisibility(progressBar, visible);
    }

    @Override
    public void enableActionButton(boolean enabled) {
        actionButton.setEnabled(enabled);
    }

    @Override
    public void setDetailsSubTitle(CharSequence data) {
        detailsSubtitleView.setText(data);
    }

    @Override
    public void setDetailsStatus(int status) {
        setStatus(detailsStatusView, status);
    }

    @Override
    public void setCommercialsSubTitle(CharSequence data) {
        commercialsSubtitleView.setText(data);
    }

    @Override
    public void setCommercialsStatus(int status) {
        setStatus(commercialsStatusView, status);
    }

    @Override
    public void showPhotos(CharSequence... images) {
        setViewVisibility(photosSubtitleView, null == images || 0 == images.length);
    }

    @Override
    public void setPhotosStatus(int status) {
        setStatus(photosStatusView, status);
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

    private void initValues() {
        cardsElevation = getResources().getDimension(R.dimen.dimen_5dp);
    }

    private void initViews(View inflate) {
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);

        View cardsList = inflate.findViewById(R.id.dashboard_cards_list);
        ViewCompat.setElevation(cardsList, cardsElevation);

        View detailsCard = inflate.findViewById(R.id.self_upload_dashboard_details_card);
        detailsCard.setOnClickListener(this);
        detailsSubtitleView =
                (TextView) detailsCard.findViewById(R.id.self_upload_dashboard_details_card_sub_title);
        detailsStatusView = (ImageView) detailsCard
                .findViewById(R.id.self_upload_dashboard_details_card_status_image);

        View commercialsCard = inflate.findViewById(R.id.self_upload_dashboard_commercials_card);
        commercialsCard.setOnClickListener(this);
        commercialsSubtitleView =
                (TextView) commercialsCard
                        .findViewById(R.id.self_upload_dashboard_commercials_card_sub_title);
        commercialsStatusView =
                (ImageView) commercialsCard
                        .findViewById(R.id.self_upload_dashboard_commercials_card_status_image);

        View photosCard = inflate.findViewById(R.id.self_upload_dashboard_photos_card);
        photosCard.setOnClickListener(this);
        photosSubtitleView =
                (TextView) photosCard.findViewById(R.id.self_upload_dashboard_photos_card_sub_title);
        photosStatusView = (ImageView) photosCard
                .findViewById(R.id.self_upload_dashboard_photos_card_status_image);

        actionButton = (Button) inflate.findViewById(R.id.dashboard_action_button);

        progressBar = (ProgressBar) inflate.findViewById(R.id.dashboard_progress_bar);
        progressBar.setMax(MAX_PROGRESS);
    }

    private void initPresenter() {
        presenter = new SelfUploadDashboardPresenter(this);
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
        updateToolbar();
    }

    private void setStatus(ImageView imgView, int status) {
        int imgRes;
        switch (status) {
            default:
            case CARD_STATUS_NEW: {
                imgRes = R.drawable.enter;
                break;
            }
            case CARD_STATUS_INCOMPLETE: {
                imgRes = R.drawable.incomplete;
                break;
            }
            case CARD_STATUS_COMPLETE: {
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
        presenter.photosCardClicked(new GalleryRepository(getContext(), null).getPhotoModel());
    }

}