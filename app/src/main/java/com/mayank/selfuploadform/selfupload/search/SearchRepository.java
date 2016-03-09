package com.mayank.selfuploadform.selfupload.search;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.network.IApiMethods;
import com.mayank.selfuploadform.selfupload.search.building.models.BuildingDetailsResult;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.building.models.LocalitiesModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vikas-pc on 01/03/16
 */
public class SearchRepository {

    private static final String TAG = "SearchRepository";
    private RestAdapter mRestAdapter;
    public static final String SUPER_TYPE_BUILDING = "building";
    public static final String SUPER_TYPE_LOCALITY = "locality";
    private static final String SERVICE_TYPE = "buy";
    private static final String SOURCE = "mobile";
    private SearchResultCallback.OnSearchDataReceived mOnSearchDataReceived;

    public void getSearchResult(String endPoint, String input, SearchResultCallback.OnSearchDataReceived
            onSearchDataReceived, String superType) {
        mRestAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        this.mOnSearchDataReceived = onSearchDataReceived;
        doNetworkCall(input,superType);

    }
    private void doNetworkCall(String input,String superType) {
        IApiMethods methods = mRestAdapter.create(IApiMethods.class);
        methods.getBuildingSearchResult(input,SERVICE_TYPE,superType,SOURCE, new SearchResultCallback(
                mOnSearchDataReceived));
    }

    public void getBuildingDetails (String endPoint, int id, BuildingDetailsCallback.OnBuildingDetailsReceived
            onBuildingDetailsReceived) {
        mRestAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        IApiMethods methods = mRestAdapter.create(IApiMethods.class);
        methods.getBuildingDetails(id, new BuildingDetailsCallback (onBuildingDetailsReceived));
    }

    public void getLocality(String endPoint, Double lat, Double lng, LocalityFetchCallback.OnLocalityDataReceived
            onlocalityDataReceived) {
        mRestAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        IApiMethods methods = mRestAdapter.create(IApiMethods.class);
        methods.getLocality(lat, lng, new LocalityFetchCallback(onlocalityDataReceived));


    }

    public static class SearchResultCallback implements Callback<ArrayList<SearchResultModel>> {

        private OnSearchDataReceived mOnSearchDataReceived;

        public SearchResultCallback(OnSearchDataReceived onSearchDataReceived) {
            this.mOnSearchDataReceived = onSearchDataReceived;
        }

        @Override
        public void success(ArrayList<SearchResultModel> buildingSearchResults, Response response) {
            mOnSearchDataReceived.onSearchDataReceived(buildingSearchResults);

        }

        @Override
        public void failure(RetrofitError error) {
            Logger.logD(SearchResultCallback.class, error.getUrl(), error.getMessage());

        }

        public interface OnSearchDataReceived {
            void onSearchDataReceived(ArrayList<SearchResultModel> buildingSearchResults);
        }
    }

    public static class BuildingDetailsCallback implements Callback<BuildingDetailsResult.BuildingDetailsWrapper> {

        public OnBuildingDetailsReceived mOnBuildingDetailsReceived;
        public BuildingDetailsCallback (OnBuildingDetailsReceived onBuildingDetailsReceived) {
            this.mOnBuildingDetailsReceived = onBuildingDetailsReceived;
        }

        @Override
        public void success(BuildingDetailsResult.BuildingDetailsWrapper buildingDetailsWrapper, Response response) {
            mOnBuildingDetailsReceived.onBuildingDetailsReceived(buildingDetailsWrapper.getResult());

        }

        @Override
        public void failure(RetrofitError error) {
            Logger.logD(SearchResultCallback.class, error.getUrl(), error.getMessage());

        }

        public interface OnBuildingDetailsReceived {
            void onBuildingDetailsReceived(ArrayList<BuildingDetailsResult> buildingDetailsResults);
        }
    }

    public static class LocalityFetchCallback implements Callback<LocalitiesModel.LocalitiesWrapper> {

        private OnLocalityDataReceived mOnLocalityDataReceived;
        public LocalityFetchCallback (OnLocalityDataReceived onLocalityDataReceived) {
            this.mOnLocalityDataReceived = onLocalityDataReceived;
        }
        @Override
        public void success(LocalitiesModel.LocalitiesWrapper localitiesWrapper, Response response) {
            mOnLocalityDataReceived.onLocalityDataReceived(localitiesWrapper.getLocality());

        }

        @Override
        public void failure(RetrofitError error) {
            Logger.logD(SearchResultCallback.class, error.getUrl(), error.getMessage());

        }

        public interface OnLocalityDataReceived {
            void onLocalityDataReceived(LocalitiesModel locality);
        }
    }
}
