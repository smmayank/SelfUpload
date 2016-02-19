package com.mayank.selfuploadform.selfupload.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;

public class SelfUploadSearchFragment extends BaseSelfUploadFragment
        implements SelfUploadSearchView, AdapterView.OnItemClickListener, View.OnClickListener {

  private SelfUploadSearchPresenter presenter;
  private Toolbar toolbar;
  private TextView searchResultEmpty;
  private ForegroundColorSpan searchResultSpan;
  private EditText buildingSearchEditor;
  private EditText localitySearchEditor;

  private TextWatcher buildingSearchWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
      presenter.searchBuilding(s);
    }
  };

  private TextWatcher localitySearchWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
      presenter.searchLocality(s);
    }
  };

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState) {
    View inflate =
            inflater.inflate(R.layout.self_upload_search_fragment, container, false);
    initValues();
    initViews(inflate);
    initToolbar();
    initPresenter();
    return inflate;
  }

  private void initValues() {
    searchResultSpan =
            new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.black_87pc));
  }

  private void initPresenter() {
    presenter = new SelfUploadSearchPresenter(this);
  }

  private void initViews(View inflate) {
    toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
    searchResultEmpty =
            (TextView) inflate.findViewById(R.id.self_upload_search_result_empty);
    searchResultEmpty.setOnClickListener(this);

    RecyclerView localitiesList =
            (RecyclerView) inflate.findViewById(R.id.self_upload_search_results);

    buildingSearchEditor = (EditText) inflate.findViewById(R.id.self_building_upload_search_editor);
    buildingSearchEditor.addTextChangedListener(buildingSearchWatcher);

    localitySearchEditor = (EditText) inflate.findViewById(R.id.self_upload_locality_search_editor);
    localitySearchEditor.addTextChangedListener(localitySearchWatcher);
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
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.self_upload_search_result_empty: {
        CharSequence text = (CharSequence) v.getTag();
        presenter.onEmptyViewClicked(text);
        break;
      }
    }
  }

  @Override
  public void setEmptySearchText(CharSequence text) {
    boolean empty = TextUtils.isEmpty(text);
    setViewVisibility(searchResultEmpty, !empty);
    if (empty) {
      return;
    }
    String string = getString(R.string.self_upload_search_result_not_found, text);
    SpannableStringBuilder builder = new SpannableStringBuilder(string);
    builder.setSpan(searchResultSpan, string.indexOf(text.toString()), string.length(),
            Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    searchResultEmpty.setText(builder);
    searchResultEmpty.setTag(text);
  }

  @Override
  public void showLocalitySearch(boolean visible) {
    setViewVisibility(localitySearchEditor, visible);
  }

  @Override
  public void setBuildingSearchText(CharSequence text) {
    buildingSearchEditor.setEnabled(TextUtils.isEmpty(text));
    buildingSearchEditor.setText(text);
  }
}
