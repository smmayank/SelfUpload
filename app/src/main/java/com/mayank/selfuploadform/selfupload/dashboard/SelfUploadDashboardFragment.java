package com.mayank.selfuploadform.selfupload.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;

public class SelfUploadDashboardFragment extends BaseSelfUploadFragment
        implements SelfUploadDashboardView {

  private SelfUploadDashboardPresenter presenter;
  private int versionCode;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    View inflate = inflater.inflate(R.layout.self_upload_dashboard_fragment, container, false);
    presenter = new SelfUploadDashboardPresenter(getActivity(), this, versionCode);
    Toolbar toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
    setToolbar(toolbar);
    return inflate;
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

  }

  @Override
  public void setProgress(int progress) {

  }

  @Override
  public void changeSubmitButtonStatus(boolean enabled) {

  }

  @Override
  public void showCards(SelfUploadDashboardCard... cards) {

  }

  @Override
  public void updateCard(int index) {

  }
}
