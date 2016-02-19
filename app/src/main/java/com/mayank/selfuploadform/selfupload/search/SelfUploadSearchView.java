package com.mayank.selfuploadform.selfupload.search;

public interface SelfUploadSearchView {
  void setEmptySearchText(CharSequence text);

  void showLocalitySearch(boolean visible);

  void setBuildingSearchText(CharSequence text);
}
