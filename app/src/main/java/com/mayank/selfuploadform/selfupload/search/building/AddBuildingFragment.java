package com.mayank.selfuploadform.selfupload.search.building;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.search.locality.SelfUploadLocalitySearchFragment;
import com.mayank.selfuploadform.selfupload.widgets.InputField;

/**
 * Created by vikas-pc on 09/03/16
 */
public class AddBuildingFragment extends BaseSelfUploadFragment implements AddBuildingView,View.OnClickListener,InputField.InputFieldInteractionListener {

    private static final String TAG = "AddBuildingFragment";
    AddBuildingPresenter presenter;
    InputField buildingName,localitySearch;
    private static final String LOCALITY_NAME = "locality_name";
    private Toolbar toolbar;


    private void initToolbar() {
        toolbar.setTitle(R.string.add_new_building);
        setToolbar(toolbar);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_building, container, false);
        initViews(view);
        initPresenter();
        initListeners();
        initToolbar();
        return view;
    }

    private void initListeners() {
        localitySearch.setInputFieldInteractionListener(this);
    }

    private void initPresenter() {
        presenter = new AddBuildingPresenter(this, getPropertyModel());

    }

    private void initViews(View view) {
        toolbar = (Toolbar)view.findViewById(R.id.toolbar);
        buildingName = (InputField)view.findViewById(R.id.building_name);
        localitySearch = (InputField)view.findViewById(R.id.locality_search);

    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void onInputFieldClicked(InputField field) {
        presenter.localitySearchCliecked();

    }

    @Override
    public void onInputFieldChanged(InputField field, CharSequence text) {
        presenter.setBuildingName(text.toString());

    }

    @Override
    public void setBuildingName(String name) {

    }

    @Override
    public void setLocalityName(String name) {

    }

    @Override
    public void openLocalitySearch() {
        openFragment(new SelfUploadLocalitySearchFragment());
    }

    @Override
    public void updateFragment(int requestCode, Object... data) {
        localitySearch.setText(data[0].toString());
        localitySearch.setEnabled(false);

    }
}
