package com.mayank.selfuploadform.selfupload.commercials;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.widgets.ValueInputField;

import java.util.Calendar;

public class SelfUploadCommercialsFragment extends BaseSelfUploadFragment
        implements SelfUploadCommercialsView, View.OnClickListener, ValueInputField.ValueChangedListener,
        CompoundButton.OnCheckedChangeListener, DatePickerDialog.OnDateSetListener {

    private static final String AVAILABLE_FROM_FORMAT = "%s / %s / %s";

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
        initToolbar();
        initPresenter();
        setListeners();
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
        presenter.priceNegotiable(isChecked);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save_proceed: {
                clearBackStack();
                break;
            }
            case R.id.available_from: {
                launchDatePicker();
                break;
            }
        }
    }

    private void launchDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        android.app.DatePickerDialog datePicker =
                new android.app.DatePickerDialog(getContext(), this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        presenter.setAvailableFrom(String.format(AVAILABLE_FROM_FORMAT, dayOfMonth, monthOfYear + 1, year));
    }

    @Override
    public void setPrice(Double price, Double factor) {
        this.price.setValue(price, factor);
    }

    @Override
    public void setBrokerage(Double brokerage, Double factor) {
        this.brokerage.setValue(brokerage, factor);
    }

    @Override
    public void setPriceNegotiable(Boolean priceNegotiable) {
        if (null != priceNegotiable) {
            negotiableCheckbox.setChecked(priceNegotiable);
        }
    }

    @Override
    public void setSocietyCharges(Double societyCharges, Double factor) {
        this.societyMoveInCharges.setValue(societyCharges, factor);
    }

    @Override
    public void setBuiltUpArea(Double builtUpArea, Double factor) {
        this.builtUpArea.setValue(builtUpArea, factor);
    }

    @Override
    public void setCarpetArea(Double carpetArea, Double factor) {
        this.carpetArea.setValue(carpetArea, factor);
    }

    @Override
    public void setProgress(int progress) {
        progressBar.setProgress(progress);
    }

    @Override
    public void onValueChanged(ValueInputField valueInputField, Double value, Double conversionFactor) {
        switch (valueInputField.getId()) {
            case R.id.price_field: {
                presenter.setPrice(value, conversionFactor);
                break;
            }
            case R.id.brokerage: {
                presenter.setBrokerage(value, conversionFactor);
                break;
            }
            case R.id.built_up_area: {
                presenter.setBuiltUpArea(value, conversionFactor);
                break;
            }
            case R.id.society_move_in_charges: {
                presenter.setSocietyCharges(value, conversionFactor);
                break;
            }
            case R.id.carpet_area: {
                presenter.setCarpetArea(value, conversionFactor);
                break;
            }
        }
    }

    @Override
    public void setAvailableFrom(String date) {
        if (null != date) {
            this.availableFrom.setText(date);
        }
    }

}