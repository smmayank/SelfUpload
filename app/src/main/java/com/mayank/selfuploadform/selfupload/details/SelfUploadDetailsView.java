package com.mayank.selfuploadform.selfupload.details;

import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadEntry;

import java.util.List;

public interface SelfUploadDetailsView {

    void enableActionButton(boolean enable);

    void setPropertyTypes(List<BaseSelfUploadEntry> propertyTypes);

    void setFlatConfiguration(List<BaseSelfUploadEntry> flatConfigurationType);

    void setEntranceFacingTypes(List<BaseSelfUploadEntry> entranceEntries);

    void setAmenitiesEntries(List<BaseSelfUploadEntry> amenitiesEntries);

    void showBuildingsDetails(boolean visibility);

    void openLocalitySearch();
}