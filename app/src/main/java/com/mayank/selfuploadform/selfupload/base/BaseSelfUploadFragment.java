package com.mayank.selfuploadform.selfupload.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.mayank.selfuploadform.base.TrackedFragment;

public abstract class BaseSelfUploadFragment extends TrackedFragment {

  private BaseSelfUploadFragmentCallback listener;

  public boolean onBackPressedHandled() {
    return false;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof BaseSelfUploadFragmentCallback) {
      listener = (BaseSelfUploadFragmentCallback) context;
    } else {
      throw new RuntimeException("Activity must implement BaseSelfUploadFragmentCallback");
    }
  }

  protected final void setToolbar(Toolbar toolbar) {
    listener.setToolbar(toolbar);
  }

  protected void setStatusBarColor(int color) {
    listener.setStatusBarColor(color);
  }

  @Override
  public String getTrackingScreenName() {
    return getClass().getSimpleName();
  }

  public interface BaseSelfUploadFragmentCallback {
    void setToolbar(Toolbar toolbar);

    void setStatusBarColor(int color);
  }

}
