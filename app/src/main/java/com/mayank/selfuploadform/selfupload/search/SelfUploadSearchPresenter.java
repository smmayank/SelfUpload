package com.mayank.selfuploadform.selfupload.search;

import com.mayank.selfuploadform.base.Logger;

public class SelfUploadSearchPresenter {

  private final SelfUploadSearchView view;

  public SelfUploadSearchPresenter(SelfUploadSearchView view) {
    this.view = view;
    initDefaults();
  }

  private void initDefaults() {
    view.showLocalitySearch(false);
    view.setEmptySearchText(null);
  }

  public void onLocalitySelected(int position) {
    Logger.logD(this, "onLocalitySelected %d", position);
  }

  public void searchBuilding(CharSequence text) {
    Logger.logD(this, "searchBuilding %s", text);
    view.setEmptySearchText(text);
    view.showLocalitySearch(false);
  }

  public void searchLocality(CharSequence text) {
    Logger.logD(this, "searchLocality %s", text);
  }

  public void onDestroy() {
  }

  public void onEmptyViewClicked(CharSequence text) {
    Logger.logD(this, "onEmptyViewClicked %s", text);
    view.setEmptySearchText(null);
    view.showLocalitySearch(true);
  }
}
