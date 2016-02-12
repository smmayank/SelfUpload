package com.mayank.selfuploadform.selfupload.dashboard;

import android.content.Context;

public class SelfUploadDashboardPresenter {

  private final Context context;
  private final SelfUploadDashboardView view;
  private final int version;

  public SelfUploadDashboardPresenter(Context context, SelfUploadDashboardView view, int version) {
    this.context = context;
    this.view = view;
    this.version = version;
  }

  public void onResume() {

  }

  public void onPause() {

  }
}
