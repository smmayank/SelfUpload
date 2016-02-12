package com.mayank.selfuploadform.selfupload.dashboard;

public interface SelfUploadDashboardView {

  int CARD_STATUS_NEW = 0;
  int CARD_STATUS_INCOMPLETE = 1;
  int CARD_STATUS_COMPLETE = 2;

  void setUsername(CharSequence name);

  void setProgress(int progress);

  void showProgressBar(boolean visible);

  void enableActionButton(boolean enabled);

  void setDetailsSubTitle(CharSequence data);

  void setDetailsStatus(int status);

  void setCommercialsSubTitle(CharSequence data);

  void setCommercialsStatus(int status);

  void showPhotos(CharSequence... images);

  void setPhotosStatus(int status);

  void openDetailsView();
}