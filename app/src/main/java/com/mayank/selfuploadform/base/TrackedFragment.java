package com.mayank.selfuploadform.base;

import android.support.v4.app.Fragment;

public abstract class TrackedFragment extends Fragment {

  @Override
  public void onResume() {
    super.onResume();
    setTrackingScreenName(getTrackingScreenName());
  }

  abstract public String getTrackingScreenName();

  public void setTrackingScreenName(String screenName) {
    TrackingManager.sendTrackingScreenName(screenName);
  }

  public void setTrackingScreenTabName(String tabSelection) {
    TrackingManager.sendTrackingScreenName(getTrackingScreenName() + "_" + tabSelection);
  }
}