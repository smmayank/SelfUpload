package com.mayank.selfuploadform.selfupload.search.building;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.search.SearchRepository;
import com.mayank.selfuploadform.selfupload.search.building.adapter.SearchAdapter;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.locality.SelfUploadLocalitySearchFragment;

import java.util.ArrayList;

public class SelfUploadBuildingSearchFragment extends BaseSelfUploadFragment
        implements SelfUploadBuildingSearchView, AdapterView.OnItemClickListener,
        View.OnClickListener, TextWatcher {

    private SelfUploadSearchSearchPresenter presenter;
    private Toolbar toolbar;
    private TextView searchResultEmpty;
    private EditText buildingSearchEditor;
    private RecyclerView mLocalitiesList;
    private SearchAdapter mSearchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
        SearchRepository buildingSearchRepository = new SearchRepository();
        presenter = new SelfUploadSearchSearchPresenter(this, buildingSearchRepository);
    }

    private void initViews(View inflate) {
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);
        searchResultEmpty =
                (TextView) inflate.findViewById(R.id.self_upload_building_search_result_empty);
        searchResultEmpty.setOnClickListener(this);

        mLocalitiesList =
                (RecyclerView) inflate.findViewById(R.id.self_upload_buidling_search_results);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLocalitiesList.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchAdapter(getContext(), new ArrayList<SearchResultModel>(), this);
        mLocalitiesList.setAdapter(mSearchAdapter);

        buildingSearchEditor =
                (EditText) inflate.findViewById(R.id.self_building_upload_building_search_editor);
        buildingSearchEditor.addTextChangedListener(this);
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
            case R.id.self_upload_building_search_result_empty: {
                presenter.onEmptyViewClicked(buildingSearchEditor.getText());
                break;
            }
            case R.id.building_name: {
                int id = Integer.parseInt(v.getTag(R.id.building_id).toString());
                presenter.getBuildingDetails(id);
                break;
            }
        }
    }

    @Override
    public void setEmptySearchText(CharSequence text) {
        setViewVisibility(searchResultEmpty, !TextUtils.isEmpty(text));
        searchResultEmpty.setText(getString(R.string.self_upload_search_result_not_found, text));
    }

    @Override
    public void openLocalitySearchView(CharSequence buildingName) {
        Fragment frag = SelfUploadLocalitySearchFragment.newInstance(buildingName);
        openFragment(frag);
    }

    @Override
    public void refreshAdapter() {
        mSearchAdapter.notifyDataSetChanged();

    }

    @Override
    public void setAdapterData(ArrayList<SearchResultModel> buildingSearchResults) {
        mSearchAdapter.updateAdapter(buildingSearchResults);

    }

    @Override
    public void setLocality(String name) {
        Toast.makeText(getContext(), name, Toast.LENGTH_LONG).show();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        presenter.searchBuilding(s);
    }
}
