package com.mayank.selfuploadform.selfupload.search.locality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;

public class SelfUploadLocalitySearchFragment extends BaseSelfUploadFragment
        implements SelfUploadLocalitySearchView, AdapterView.OnItemClickListener, TextWatcher {

  private SelfUploadLocalitySearchPresenter presenter;
  private Toolbar toolbar;
  private EditText localitySearchEditor;

  public static Fragment newInstance(CharSequence buildingName) {
    Fragment frag = new SelfUploadLocalitySearchFragment();
    frag.setArguments(SelfUploadLocalitySearchPresenter.generateArgs(buildingName));
    return frag;
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    View inflate =
            inflater.inflate(R.layout.self_upload_building_search_fragment, container, false);
    initValues();
    initViews(inflate);
    initToolbar();
    initPresenter();
    return inflate;
  }

  private void initValues() {
  }

  private void initPresenter() {
    presenter = new SelfUploadLocalitySearchPresenter(this);
    presenter.onCreate(getArguments());
  }

  private void initViews(View inflate) {
    toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);

    RecyclerView localitiesList =
            (RecyclerView) inflate.findViewById(R.id.self_upload_buidling_search_results);

    localitySearchEditor =
            (EditText) inflate.findViewById(R.id.self_building_upload_building_search_editor);
    localitySearchEditor.addTextChangedListener(this);
  }

  @Override
  protected int getStatusBarColor() {
    return ContextCompat.getColor(getContext(), R.color.black_40_percent_opacity);
  }

  private void initToolbar() {
    setToolbar(toolbar);
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    presenter.onLocalitySelected(position);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    presenter.onDestroy();
  }

  @Override
  public void setBuildingName(CharSequence buildingName) {
    toolbar.setTitle(buildingName);
    setToolbar(toolbar);
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {
  }

  @Override
  public void afterTextChanged(Editable s) {
    presenter.searchLocality(s);
  }
}
