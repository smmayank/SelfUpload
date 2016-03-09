package com.mayank.selfuploadform.selfupload.search.locality;

import android.os.Bundle;
import android.text.TextUtils;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.SearchRepository;

import java.util.ArrayList;

public class SelfUploadLocalitySearchPresenter implements SearchRepository.SearchResultCallback.OnSearchDataReceived {

  private static final String KEY_BUILDING_NAME = "locality_search_building_name";
  private final SelfUploadLocalitySearchView view;
    private SearchRepository mSearchRepo;

  public SelfUploadLocalitySearchPresenter(SelfUploadLocalitySearchView view, SearchRepository searchRepository) {
    this.view = view;
      this.mSearchRepo = searchRepository;
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
      if (!TextUtils.isEmpty(text) && text.length()%2 == 0) {
          mSearchRepo.getSearchResult("https://regions.housing" +
                  ".com", text.toString(),this,SearchRepository.SUPER_TYPE_LOCALITY);

      }

  }

  public void onDestroy() {
  }

  public void onCreate(Bundle savedInstanceState) {
    String buildingName = savedInstanceState.getString(KEY_BUILDING_NAME);
    view.setBuildingName(buildingName);
  }

    @Override
    public void onSearchDataReceived(ArrayList<SearchResultModel> searchResultModels) {
        view.setAdapterData(searchResultModels);
    }
}
