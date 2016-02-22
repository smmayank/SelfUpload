package com.mayank.selfuploadform.selfupload.search.building;

import com.mayank.selfuploadform.base.Logger;

public class SelfUploadBuildingSearchPresenter {

  private final SelfUploadBuildingSearchView view;

  public SelfUploadBuildingSearchPresenter(SelfUploadBuildingSearchView view) {
    this.view = view;
    initDefaults();
  }

  private void initDefaults() {
    view.setEmptySearchText(null);
  }

  public void onLocalitySelected(int position) {
    Logger.logD(this, "onLocalitySelected %d", position);
  }

  public void searchBuilding(CharSequence text) {
    Logger.logD(this, "searchLocality %s", text);
    view.setEmptySearchText(text);
  }

  public void onDestroy() {
  }

  public void onEmptyViewClicked(CharSequence text) {
    Logger.logD(this, "onEmptyViewClicked %s", text);
    view.openLocalitySearchView(text);
  }
}
