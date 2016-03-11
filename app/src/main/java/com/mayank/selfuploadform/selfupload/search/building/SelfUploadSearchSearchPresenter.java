package com.mayank.selfuploadform.selfupload.search.building;

import android.text.TextUtils;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.models.PropertyModel;
import com.mayank.selfuploadform.selfupload.search.building.models.BuildingDetailsResult;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.building.models.LocalitiesModel;
import com.mayank.selfuploadform.selfupload.search.SearchRepository;

import java.util.ArrayList;


public class SelfUploadSearchSearchPresenter implements
        SearchRepository.SearchResultCallback.OnSearchDataReceived,
        SearchRepository.BuildingDetailsCallback.OnBuildingDetailsReceived,SearchRepository.LocalityFetchCallback.OnLocalityDataReceived {

    private final SelfUploadBuildingSearchView view;
    private SearchRepository mBuildingSearchRepo;
    private PropertyModel mPropertyModel;

    public SelfUploadSearchSearchPresenter(SelfUploadBuildingSearchView view, SearchRepository
            buildingSearchRepository, PropertyModel propertyModel) {
        this.view = view;
        this.mBuildingSearchRepo = buildingSearchRepository;
        this.mPropertyModel = propertyModel;
        initDefaults();
    }

    private void initDefaults() {
        view.setEmptySearchText(null);
    }

    public void onLocalitySelected(int position) {
        Logger.logD(this, "onLocalitySelected %d", position);
    }

    public void searchBuilding(CharSequence text) {
        view.setEmptySearchText(text);
        if (!TextUtils.isEmpty(text) && text.length()%2 == 0) {
            mBuildingSearchRepo.getSearchResult("https://regions.housing" +
                    ".com", text.toString(),this,SearchRepository.SUPER_TYPE_BUILDING);

        }
    }

    public void getBuildingDetails (int id) {
        mPropertyModel.setBuildingId(id);
        mBuildingSearchRepo.getBuildingDetails("http://harmans.housing.com:3001", id, this);
    }

    public void getLocality(Double lat, Double lng) {
        mBuildingSearchRepo.getLocality("http://harmans.housing.com:3001", lat,lng, this);
    }

    public void onDestroy() {
    }

    public void onEmptyViewClicked(CharSequence text) {
        view.openLocalitySearchView(text);
    }


    @Override
    public void onSearchDataReceived(ArrayList<SearchResultModel> searchResultModels) {
        view.setAdapterData(searchResultModels);
    }

    @Override
    public void onBuildingDetailsReceived(ArrayList<BuildingDetailsResult> buildingDetailsResults) {

        getLocality(buildingDetailsResults.get(0).getLat(), buildingDetailsResults.get(0).getLng());


    }

    @Override
    public void onLocalityDataReceived(LocalitiesModel locality) {
        mPropertyModel.setLocalityId(locality.getUuid());
        mPropertyModel.setLocalityName(locality.getName());
        view.setLocality(locality);
    }

    public void setBuildingName(String buildingName) {
        mPropertyModel.setBuildingName(buildingName);
    }

    public String getBuildingName() {
        return mPropertyModel.getBuildingName();
    }
}
