package com.mayank.selfuploadform.selfupload;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment.BaseSelfUploadFragmentCallback;
import com.mayank.selfuploadform.selfupload.dashboard.SelfUploadDashboardFragment;


public class SelfUploadActivity extends AppCompatActivity
        implements BaseSelfUploadFragmentCallback {

  private static final int FRAGMENT_CONTAINER = R.id.fragment_container;
  private int defaultColor;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dashboard);
    defaultColor = ContextCompat.getColor(this, R.color.purple_primary_dark);
    initDashboardFragment();
  }

  private void initDashboardFragment() {
    SelfUploadDashboardFragment frag = new SelfUploadDashboardFragment();
    replaceFragment(frag);
  }

  private void replaceFragment(Fragment frag) {
    setStatusBarColor(defaultColor);
    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    ft.replace(FRAGMENT_CONTAINER, frag);
    ft.commit();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home: {
        Fragment frag = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
        if (frag instanceof BaseSelfUploadFragment &&
                ((BaseSelfUploadFragment) frag).onBackPressedHandled()) {
          return true;
        }
        onBackPressed();
        return true;
      }
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void setToolbar(Toolbar toolbar) {
    setSupportActionBar(toolbar);
    ActionBar supportActionBar = getSupportActionBar();
    if (null != supportActionBar) {
      supportActionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public void setStatusBarColor(int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Window window = getWindow();
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      window.setStatusBarColor(color);
    }
  }
}
