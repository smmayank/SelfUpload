package com.mayank.selfuploadform.network;

import com.mayank.selfuploadform.selfupload.search.building.models.BuildingDetailsResult;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.building.models.LocalitiesModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by vikas-pc on 01/03/16
 */
public interface IApiMethods {

    @GET("/api/v1/polygon/suggest")
    void getBuildingSearchResult (
            @Query("input") String input,
            @Query("service_type") String serviceType,
            @Query("super_type") String superType,
            @Query("source") String source,
            Callback<ArrayList<SearchResultModel>> cb
    );

    @GET("/api/v1/buildings/{building_id}")
    void getBuildingDetails (@Path("building_id") int buildingId,Callback<BuildingDetailsResult
            .BuildingDetailsWrapper> cb);

    @GET("/api/v1/polygon/reverse_geocode")
    void getLocality (@Query("lat") Double lat, @Query("lng") Double lng, Callback<LocalitiesModel.LocalitiesWrapper>
            localitiesWrapperCallback);
}
