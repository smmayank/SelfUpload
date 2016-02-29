package com.mayank.selfuploadform.selfupload.commercials;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.widgets.ValueInputField;

public class SelfUploadCommercialsFragment extends BaseSelfUploadFragment
        implements SelfUploadCommercialsView, View.OnClickListener, ValueInputField.ValueChangedListener,
        CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private ProgressBar progressBar;
    private ValueInputField price;
    private ValueInputField brokerage;
    private ValueInputField societyMoveInCharges;
    private ValueInputField builtUpArea;
    private ValueInputField carpetArea;
    private TextView availableFrom;
    private CheckBox negotiableCheckbox;
    private Button saveProceedButton;
    private SelfUploadCommercialsPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_upload_commercials_fragment, container, false);
        initViews(view);
        setListeners();
        initToolbar();
        initPresenter();
        return view;
    }

    private void initPresenter() {
        presenter = new SelfUploadCommercialsPresenter(this, getPropertyModel());
    }

    private void setListeners() {
        price.addValueChangedListener(this);
        brokerage.addValueChangedListener(this);
        societyMoveInCharges.addValueChangedListener(this);
        builtUpArea.addValueChangedListener(this);
        carpetArea.addValueChangedListener(this);
        availableFrom.setOnClickListener(this);
        saveProceedButton.setOnClickListener(this);
        negotiableCheckbox.setOnCheckedChangeListener(this);
    }

    private void initViews(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        price = (ValueInputField) view.findViewById(R.id.price_field);
        brokerage = (ValueInputField) view.findViewById(R.id.brokerage);
        societyMoveInCharges = (ValueInputField) view.findViewById(R.id.society_move_in_charges);
        builtUpArea = (ValueInputField) view.findViewById(R.id.built_up_area);
        carpetArea = (ValueInputField) view.findViewById(R.id.carpet_area);
        availableFrom = (TextView) view.findViewById(R.id.available_from);
        negotiableCheckbox = (CheckBox) view.findViewById(R.id.price_negotiable);
        saveProceedButton = (Button) view.findViewById(R.id.save_proceed);
    }

    private void initToolbar() {
        toolbar.setTitle(getString(R.string.title_commercial));
        setToolbar(toolbar);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onValueChanged(ValueInputField papaInputField, Double value) {

    }
}
