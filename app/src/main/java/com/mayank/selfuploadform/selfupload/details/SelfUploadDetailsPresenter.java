package com.mayank.selfuploadform.selfupload.details;

import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadEntry;
import com.mayank.selfuploadform.selfupload.repository.SelfUploadFormRepository;

public class SelfUploadDetailsPresenter {
  private final SelfUploadDetailsView view;
  private final SelfUploadFormRepository repository;

  public SelfUploadDetailsPresenter(SelfUploadFormRepository repository,
          SelfUploadDetailsView view) {
    this.repository = repository;
    this.view = view;

    initDefaults();
  }

  private void initDefaults() {
    view.enableActionButton(false);
    view.setPropertyTypes(repository.getPropertyType());
    view.setFlatConfiguration(repository.getFlatConfigurationType());
    view.setEntranceFacingTypes(repository.getEntranceEntries());
    view.setAmenitiesEntries(repository.getAmenitiesEntries());
    view.showBuildingsDetails(false);
  }

  public void actionButtonClicked() {
    Logger.logD(this, "actionButtonClicked");
  }

  public void propertyTypeSelected(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "propertyTypeSelected : %d, entry %s", index, entry);
  }

  public void flatConfigurationSelected(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "flatConfigurationSelected : %d, entry %s", index, entry);
  }

  public void onBathroomValueChanged(int value) {
    Logger.logD(this, "onBathroomValueChanged : %d, ", value);
  }

  public void onBalconiesValueChanged(int value) {
    Logger.logD(this, "onBalconiesValueChanged : %d, ", value);
  }

  public void entranceSelected(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "entranceSelected : %d, entry %s", index, entry);
  }

  public void onBuildingClicked() {
    Logger.logD(this, "onBuildingClicked ");
    view.openLocalitySearch();
  }

  public void buildingSelected(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "buildingSelected : %d, entry %s", index, entry);
  }

  public void floorNumber(int value) {
    Logger.logD(this, "floorNumber : %d, ", value);
  }

  public void totalFloors(int value) {
    Logger.logD(this, "totalFloors : %d, ", value);
  }

  public void propertyAge(int value) {
    Logger.logD(this, "propertyAge : %d, ", value);
  }

  public void description(CharSequence text) {
    Logger.logD(this, "description : %d, ", text);
  }

  public void parking(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "parking : %d, entry %s", index, entry);
  }

  public void cupboards(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "cupboards : %d, entry %s", index, entry);
  }

  public void pipeline(int index, BaseSelfUploadEntry entry) {
    Logger.logD(this, "pipeline : %d, entry %s", index, entry);
  }
}