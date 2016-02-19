package com.mayank.selfuploadform.selfupload.dashboard;

import android.support.v4.app.FragmentActivity;

import com.mayank.selfuploadform.base.Logger;

public class SelfUploadDashboardPresenter {

  public static final int NEW_PROPERTY = -1;

  private static final int STATUS_COUNT = 3;

  private static final int DETAILS_INDEX = 0;
  private static final int COMMERCIALS_INDEX = 1;
  private static final int PHOTOS_INDEX = 2;

  private final FragmentActivity target;
  private final SelfUploadDashboardView view;
  private final int propertyId;

  private final int[] statuses;

  public SelfUploadDashboardPresenter(FragmentActivity context,
          SelfUploadDashboardView view) {
    this(context, view, NEW_PROPERTY);
  }

  public SelfUploadDashboardPresenter(FragmentActivity context, SelfUploadDashboardView view, int
          propertyId) {
    this.target = context;
    this.view = view;
    this.propertyId = propertyId;
    statuses = new int[STATUS_COUNT];
    if (NEW_PROPERTY != propertyId) {
      fetchProperty(propertyId);
    } else {
      createNewProperty();
    }
    initDefaults();
  }

  private void initDefaults() {
    view.enableActionButton(false);

    view.showProgressBar(true);
    view.setProgress(0);

    view.setUsername("John Doe");

    view.setDetailsSubTitle("2BHK Apartment, Nahar Amrit Shakti, No");
    view.setDetailsStatus(statuses[DETAILS_INDEX]);

    view.setCommercialsSubTitle("Price, Brokerage, Built-up-Area etc.");
    view.setCommercialsStatus(statuses[COMMERCIALS_INDEX]);

    view.showPhotos("Price, Brokerage, Built-up-Area etc.");
    view.setPhotosStatus(statuses[PHOTOS_INDEX]);
  }

  private void createNewProperty() {
  }

  private void fetchProperty(int propertyId) {

  }

  public void onResume() {
    Logger.logD(this, "onResume");
  }

  public void onPause() {
    Logger.logD(this, "onPause");
  }

  public void detailsCardClicked() {
    Logger.logD(this, "detailsCardClicked");
    view.openDetailsView();
  }

  public void commercialsCardClicked() {
    Logger.logD(this, "commercialsCardClicked");
    view.openCommercialsView();
  }

  public void photosCardClicked() {
    Logger.logD(this, "photosCardClicked");
  }
}
