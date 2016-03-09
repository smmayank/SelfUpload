package com.mayank.selfuploadform.selfupload.search.building;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.widgets.InputField;

/**
 * Created by vikas-pc on 09/03/16
 */
public class AddBuildingFragment extends BaseSelfUploadFragment implements AddBuildingView,View.OnClickListener,InputField.InputFieldInteractionListener {

    private static final String TAG = "AddBuildingFragment";
    AddBuildingPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_building, container, false);
        initViews();
        initPresenter();
        return view;
    }

    private void initPresenter() {
        presenter = new AddBuildingPresenter(this);

    }

    private void initViews() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onInputFieldClicked(InputField field) {

    }

    @Override
    public void onInputFieldChanged(InputField field, CharSequence text) {

    }

    @Override
    public void setBuildingName(String name) {

    }

    @Override
    public void setLocalityName(String name) {

    }
}
