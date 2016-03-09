package com.mayank.selfuploadform.selfupload.search.locality;

import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;

import java.util.ArrayList;

public interface SelfUploadLocalitySearchView {
  void setBuildingName(CharSequence buildingName);

  void setAdapterData(ArrayList<SearchResultModel> searchResultModels);
}
