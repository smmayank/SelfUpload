package com.mayank.selfuploadform.selfupload.search.building;

import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;

import java.util.ArrayList;

public interface SelfUploadBuildingSearchView {
    void setEmptySearchText(CharSequence text);

    void openLocalitySearchView(CharSequence buildingName);

    void refreshAdapter();

    void setAdapterData(ArrayList<SearchResultModel> buildingSearchResults);

    void setLocality(String localityName);
}
