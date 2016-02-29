package com.mayank.selfuploadform.selfupload.commercials;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;

public class SelfUploadCommercialsFragment extends BaseSelfUploadFragment
        implements SelfUploadCommercialsView {

  private Toolbar toolbar;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    View inflate = inflater.inflate(R.layout.self_upload_commercials_fragment, container, false);
    initViews(inflate);
    initToolbar();
    return inflate;
  }

  private void initViews(View inflate) {
    toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
  }

  private void initToolbar() {
    toolbar.setTitle(getString(R.string.title_commercial));
    setToolbar(toolbar);
  }
}
