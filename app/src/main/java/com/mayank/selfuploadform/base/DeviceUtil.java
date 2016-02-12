package com.mayank.selfuploadform.base;

import android.support.v4.app.FragmentActivity;

public class DeviceUtil {
  private int currentAppVersionCode;

  public DeviceUtil(FragmentActivity activity) {
  }

  public int getCurrentAppVersionCode() {
    return currentAppVersionCode;
  }
}