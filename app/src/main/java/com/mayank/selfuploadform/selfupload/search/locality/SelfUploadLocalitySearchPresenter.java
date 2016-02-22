package com.mayank.selfuploadform.selfupload.search.locality;

import android.os.Bundle;

import com.mayank.selfuploadform.base.Logger;

public class SelfUploadLocalitySearchPresenter {

  private static final String KEY_BUILDING_NAME = "locality_search_building_name";
  private final SelfUploadLocalitySearchView view;

  public SelfUploadLocalitySearchPresenter(SelfUploadLocalitySearchView view) {
    this.view = view;
  }

  public static Bundle generateArgs(CharSequence buildingName) {
    Bundle bundle = new Bundle();
    bundle.putString(KEY_BUILDING_NAME, buildingName.toString());
    return bundle;
  }

  public void onLocalitySelected(int position) {
    Logger.logD(this, "onLocalitySelected %d", position);
  }

  public void searchLocality(CharSequence text) {
    Logger.logD(this, "searchLocality %s", text);
  }

  public void onDestroy() {
  }

  public void onCreate(Bundle savedInstanceState) {
    String buildingName = savedInstanceState.getString(KEY_BUILDING_NAME);
    view.setBuildingName(buildingName);
  }
}
