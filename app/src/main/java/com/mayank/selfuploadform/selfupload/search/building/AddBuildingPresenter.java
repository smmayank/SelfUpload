package com.mayank.selfuploadform.selfupload.search.building;

import com.mayank.selfuploadform.models.PropertyModel;

/**
 * Created by vikas-pc on 09/03/16
 */
public class AddBuildingPresenter  {

    private static final String TAG = "AddBuildingPresenter";
    private AddBuildingView mView;
    private PropertyModel mPropertyModel;

    public AddBuildingPresenter(AddBuildingView view, PropertyModel propertyModel) {
        this.mView = view;
        this.mPropertyModel = propertyModel;

    }

    public void setBuildingName(String buildingName) {
        mPropertyModel.setBuildingName(buildingName);

    }

    public void localitySearchCliecked() {
        mView.openLocalitySearch();
    }
}
