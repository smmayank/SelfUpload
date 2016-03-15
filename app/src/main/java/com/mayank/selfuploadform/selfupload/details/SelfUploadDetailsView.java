package com.mayank.selfuploadform.selfupload.details;

import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadEntry;

import java.util.List;

public interface SelfUploadDetailsView {

    void enableActionButton(boolean enable);

    void setPropertyTypes(List<BaseSelfUploadEntry<Integer>> propertyTypes);

    void setFlatConfiguration(List<BaseSelfUploadEntry<Integer>> flatConfigurationType);

    void setEntranceFacingTypes(List<BaseSelfUploadEntry<String>> entranceEntries);

    void setAmenitiesEntries(List<BaseSelfUploadEntry<Boolean>> amenitiesEntries);

    void showBuildingsDetails(boolean visibility);

    void openBuildingSearch();

}