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
import com.mayank.selfuploadform.selfupload.search.building.SelfUploadBuildingSearchFragment;
import com.mayank.selfuploadform.selfupload.repository.SelfUploadFormRepository;
import com.mayank.selfuploadform.selfupload.widgets.InputField;
import com.mayank.selfuploadform.selfupload.widgets.NumberField;
import com.mayank.selfuploadform.selfupload.widgets.SelectionField;
import com.mayank.selfuploadform.selfupload.widgets.SpinnerField;

import java.util.List;

public class SelfUploadDetailsFragment extends BaseSelfUploadFragment
        implements SelfUploadDetailsView, View.OnClickListener,
        SelectionField.SelectionFieldInteractionListener<BaseSelfUploadEntry>,
        SpinnerField.SpinnerFieldInteractionListener<BaseSelfUploadEntry>,
        NumberField.NumberFieldInteractionListener, InputField.InputFieldInteractionListener {


    private Toolbar toolbar;
    private SelfUploadDetailsPresenter presenter;
    private View actionButton;

    private SelectionField<BaseSelfUploadEntry> propertyTypeSelector;
    private SpinnerField<BaseSelfUploadEntry> flatConfigSelector;
    private NumberField bathroomCount;
    private NumberField balconiesCount;
    private SpinnerField<BaseSelfUploadEntry> entryFacing;
    private InputField locality;
    private InputField buildingName;
    private InputField floorNumber;
    private InputField totalFloor;
    private InputField ageOfProperty;
    private View amenityHeader;
    private SelectionField<BaseSelfUploadEntry> parking;
    private SelectionField<BaseSelfUploadEntry> cupboards;
    private SelectionField<BaseSelfUploadEntry> pipeline;
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

    private void initToolbar() {
        toolbar.setTitle(R.string.property_details);
        setToolbar(toolbar);
    }

    private void initViews(View inflate) {
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        actionButton = inflate.findViewById(R.id.self_upload_details_action_button);
        actionButton.setOnClickListener(this);

        propertyTypeSelector = (SelectionField<BaseSelfUploadEntry>) inflate.findViewById(
                R.id.self_upload_details_property_type);
        propertyTypeSelector.setSelectionFieldInteractionListener(this);

        flatConfigSelector = (SpinnerField<BaseSelfUploadEntry>) inflate.findViewById(R.id
                .self_upload_details_flat_configuration);
        flatConfigSelector.setSpinnerFieldInteractionListener(this);

        bathroomCount = (NumberField) inflate.findViewById(R.id.self_upload_details_bathroom);
        bathroomCount.setOnNumberFieldInteractionListener(this);

        balconiesCount = (NumberField) inflate.findViewById(R.id.self_upload_details_balconies);
        balconiesCount.setOnNumberFieldInteractionListener(this);

        entryFacing = (SpinnerField<BaseSelfUploadEntry>) inflate
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

        parking = (SelectionField<BaseSelfUploadEntry>) inflate
                .findViewById(R.id.self_upload_details_amenity_parking);
        parking.setSelectionFieldInteractionListener(this);

        cupboards = (SelectionField<BaseSelfUploadEntry>) inflate
                .findViewById(R.id.self_upload_details_amenity_cupboards);
        cupboards.setSelectionFieldInteractionListener(this);

        pipeline = (SelectionField<BaseSelfUploadEntry>) inflate
                .findViewById(R.id.self_upload_details_amenity_pipeline);
        pipeline.setSelectionFieldInteractionListener(this);

        description = (InputField) inflate.findViewById(R.id.self_upload_details_description);
        description.setInputFieldInteractionListener(this);
    }

    private void initPresenter() {
        SelfUploadFormRepository repository =
                new SelfUploadFormRepository(getActivity());
        presenter = new SelfUploadDetailsPresenter(repository, this);
    }

    @Override
    public void enableActionButton(boolean enable) {
        actionButton.setEnabled(enable);
    }

    @Override
    public void setPropertyTypes(List<BaseSelfUploadEntry> propertyTypes) {
        propertyTypeSelector.addEntries(propertyTypes);
    }

    @Override
    public void setFlatConfiguration(List<BaseSelfUploadEntry> flatConfigurationType) {
        flatConfigSelector.addEntries(flatConfigurationType);
    }

    @Override
    public void setEntranceFacingTypes(List<BaseSelfUploadEntry> entranceEntries) {
        entryFacing.addEntries(entranceEntries);
    }

    @Override
    public void setAmenitiesEntries(List<BaseSelfUploadEntry> amenitiesEntries) {
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
    public void onSelectionFieldSelected(SelectionField field, int index, BaseSelfUploadEntry entry) {
        switch (field.getId()) {
            case R.id.self_upload_details_property_type: {
                presenter.propertyTypeSelected(index, entry);
                break;
            }
            case R.id.self_upload_details_amenity_parking: {
                presenter.parking(index, entry);
                break;
            }
            case R.id.self_upload_details_amenity_cupboards: {
                presenter.cupboards(index, entry);
                break;
            }
            case R.id.self_upload_details_amenity_pipeline: {
                presenter.pipeline(index, entry);
                break;
            }
        }
    }

    @Override
    public void onSpinnerFieldSelected(SpinnerField field, int index, BaseSelfUploadEntry entry) {
        switch (field.getId()) {
            case R.id.self_upload_details_flat_configuration: {
                presenter.flatConfigurationSelected(index, entry);
                break;
            }
            case R.id.self_upload_details_entrance: {
                presenter.entranceSelected(index, entry);
                break;
            }
            case R.id.self_upload_details_buildings: {
                presenter.buildingSelected(index, entry);
                break;
            }
        }
    }

    @Override
    public void onNumberSelected(NumberField field, int value) {
        switch (field.getId()) {
            case R.id.self_upload_details_bathroom: {
                presenter.onBathroomValueChanged(value);
                break;
            }
            case R.id.self_upload_details_balconies: {
                presenter.onBalconiesValueChanged(value);
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
}
