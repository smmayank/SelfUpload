package com.mayank.selfuploadform.selfupload.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadEntry;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.repository.SelfUploadFormRepository;
import com.mayank.selfuploadform.selfupload.search.building.SelfUploadBuildingSearchFragment;
import com.mayank.selfuploadform.selfupload.widgets.InputField;
import com.mayank.selfuploadform.selfupload.widgets.NumberField;
import com.mayank.selfuploadform.selfupload.widgets.SelectionField;
import com.mayank.selfuploadform.selfupload.widgets.SpinnerField;

import java.util.List;

public class SelfUploadDetailsFragment extends BaseSelfUploadFragment implements SelfUploadDetailsView, View
        .OnClickListener, InputField.InputFieldInteractionListener, SelectionField.SelectionFieldInteractionListener,
        SpinnerField.SpinnerFieldInteractionListener {


    private Toolbar toolbar;
    private SelfUploadDetailsPresenter presenter;
    private View actionButton;

    private SelectionField<BaseSelfUploadEntry<Integer>> propertyTypeSelector;
    private SpinnerField<BaseSelfUploadEntry<Integer>> flatConfigSelector;
    private NumberField bathroomCount;
    private NumberField balconiesCount;
    private SpinnerField<BaseSelfUploadEntry<String>> entryFacing;
    private InputField locality;
    private InputField buildingName;
    private InputField floorNumber;
    private InputField totalFloor;
    private InputField ageOfProperty;
    private View amenityHeader;
    private SelectionField<BaseSelfUploadEntry<Boolean>> parking;
    private SelectionField<BaseSelfUploadEntry<Boolean>> cupboards;
    private SelectionField<BaseSelfUploadEntry<Boolean>> pipeline;
    private InputField description;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.self_upload_details_fragment, container, false);
        initValues();
        initViews(inflate);
        initToolbar();
        initPresenter();
        return inflate;
    }


    private void initValues() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private void initToolbar() {
        toolbar.setTitle(R.string.property_details);
        setToolbar(toolbar);
    }

    private void initViews(View inflate) {
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        actionButton = inflate.findViewById(R.id.self_upload_details_action_button);
        actionButton.setOnClickListener(this);

        propertyTypeSelector = (SelectionField<BaseSelfUploadEntry<Integer>>) inflate.findViewById(
                R.id.self_upload_details_property_type);
        propertyTypeSelector.setSelectionFieldInteractionListener(this);

        flatConfigSelector = (SpinnerField<BaseSelfUploadEntry<Integer>>) inflate.findViewById(R.id
                .self_upload_details_flat_configuration);
        flatConfigSelector.setSpinnerFieldInteractionListener(this);

        entryFacing = (SpinnerField<BaseSelfUploadEntry<String>>) inflate
                .findViewById(R.id.self_upload_details_entrance);
        entryFacing.setSpinnerFieldInteractionListener(this);

        buildingName = (InputField) inflate.findViewById(R.id.self_upload_details_buildings);
        buildingName.setInputFieldInteractionListener(this);

        locality = (InputField) inflate.findViewById(R.id.self_upload_details_locality);

        floorNumber = (InputField) inflate.findViewById(R.id.self_upload_details_floor_number);
        floorNumber.setInputFieldInteractionListener(this);

        totalFloor = (InputField) inflate.findViewById(R.id.self_upload_details_total_floor);
        totalFloor.setInputFieldInteractionListener(this);

        ageOfProperty = (InputField) inflate.findViewById(R.id.self_upload_details_property_age);
        ageOfProperty.setInputFieldInteractionListener(this);

        amenityHeader = inflate.findViewById(R.id.self_upload_details_amenity_header);

        parking = (SelectionField<BaseSelfUploadEntry<Boolean>>) inflate
                .findViewById(R.id.self_upload_details_amenity_parking);
        parking.setSelectionFieldInteractionListener(this);

        cupboards = (SelectionField<BaseSelfUploadEntry<Boolean>>) inflate
                .findViewById(R.id.self_upload_details_amenity_cupboards);
        cupboards.setSelectionFieldInteractionListener(this);

        pipeline = (SelectionField<BaseSelfUploadEntry<Boolean>>) inflate
                .findViewById(R.id.self_upload_details_amenity_pipeline);
        pipeline.setSelectionFieldInteractionListener(this);

        description = (InputField) inflate.findViewById(R.id.self_upload_details_description);
        description.setInputFieldInteractionListener(this);
    }

    private void initPresenter() {
        SelfUploadFormRepository repository =
                new SelfUploadFormRepository(getActivity());
        presenter = new SelfUploadDetailsPresenter(repository, this, getPropertyModel());
    }

    @Override
    public void enableActionButton(boolean enable) {
        actionButton.setEnabled(enable);
    }

    @Override
    public void setPropertyTypes(List<BaseSelfUploadEntry<Integer>> propertyTypes) {
        propertyTypeSelector.addEntries(propertyTypes);
    }

    @Override
    public void setFlatConfiguration(List<BaseSelfUploadEntry<Integer>> flatConfigurationType) {
        flatConfigSelector.addEntries(flatConfigurationType);
    }

    @Override
    public void setEntranceFacingTypes(List<BaseSelfUploadEntry<String>> entranceEntries) {
        entryFacing.addEntries(entranceEntries);
    }

    @Override
    public void setAmenitiesEntries(List<BaseSelfUploadEntry<Boolean>> amenitiesEntries) {
        parking.addEntries(amenitiesEntries);
        cupboards.addEntries(amenitiesEntries);
        pipeline.addEntries(amenitiesEntries);
    }

    @Override
    public void showBuildingsDetails(boolean visibility) {
        setViewVisibility(locality, visibility);
        setViewVisibility(floorNumber, visibility);
        setViewVisibility(totalFloor, visibility);
        setViewVisibility(ageOfProperty, visibility);
        setViewVisibility(amenityHeader, visibility);
        setViewVisibility(parking, visibility);
        setViewVisibility(cupboards, visibility);
        setViewVisibility(pipeline, visibility);
        setViewVisibility(description, visibility);
    }

    @Override
    public void openLocalitySearch() {
        openFragment(new SelfUploadBuildingSearchFragment());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.self_upload_details_action_button: {
                presenter.actionButtonClicked();
                break;
            }
        }
    }

    @Override
    public void onInputFieldClicked(InputField field) {
        switch (field.getId()) {
            case R.id.self_upload_details_buildings: {
                presenter.onBuildingClicked();
                break;
            }
        }
    }

    @Override
    public void onInputFieldChanged(InputField field, CharSequence text) {
        switch (field.getId()) {
            case R.id.self_upload_details_floor_number: {
                presenter.floorNumber(convertToInt(text));
                break;
            }
            case R.id.self_upload_details_total_floor: {
                presenter.totalFloors(convertToInt(text));
                break;
            }
            case R.id.self_upload_details_property_age: {
                presenter.propertyAge(convertToInt(text));
                break;
            }
            case R.id.self_upload_details_description: {
                presenter.description(text);
                break;
            }
        }
    }

    private int convertToInt(CharSequence text) {
        if (TextUtils.isEmpty(text) || !TextUtils.isDigitsOnly(text)) {
            return 0;
        }
        try {
            return Integer.parseInt(text.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void onSelectionFieldSelected(SelectionField field, int index, Object entry) {
        switch (field.getId()) {
            case R.id.self_upload_details_flat_configuration: {
                presenter.flatConfigurationSelected(index, (BaseSelfUploadEntry) entry);
                break;
            }
            case R.id.self_upload_details_entrance: {
                presenter.entranceSelected(index, (BaseSelfUploadEntry<String>) entry);
                break;
            }
            case R.id.self_upload_details_buildings: {
                presenter.buildingSelected(index, (BaseSelfUploadEntry<Integer>) entry);
                break;
            }
        }

    }

    @Override
    public void onSpinnerFieldSelected(SpinnerField field, int index, Object entry) {

        switch (field.getId()) {
            case R.id.self_upload_details_flat_configuration: {
                presenter.flatConfigurationSelected(index, (BaseSelfUploadEntry) entry);
                break;
            }
            case R.id.self_upload_details_entrance: {
                presenter.entranceSelected(index, (BaseSelfUploadEntry<String>) entry);
                break;
            }
            case R.id.self_upload_details_buildings: {
                presenter.buildingSelected(index, (BaseSelfUploadEntry<Integer>) entry);
                break;
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
