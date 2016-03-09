package com.mayank.selfuploadform.selfupload.search.locality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import com.mayank.selfuploadform.selfupload.search.building.adapter.SearchAdapter;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;
import com.mayank.selfuploadform.selfupload.search.SearchRepository;

import java.util.ArrayList;

public class SelfUploadLocalitySearchFragment extends BaseSelfUploadFragment
        implements SelfUploadLocalitySearchView, AdapterView.OnItemClickListener, TextWatcher,View.OnClickListener {

    private SelfUploadLocalitySearchPresenter presenter;
    private Toolbar toolbar;
    private EditText localitySearchEditor;
    private RecyclerView mLocalitiesList;
    private SearchAdapter mSearchAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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
                inflater.inflate(R.layout.self_upload_locality_search_fragment, container, false);
        initValues();
        initViews(inflate);
        initToolbar();
        initPresenter();
        return inflate;
    }

    private void initValues() {
    }

    private void initPresenter() {
        presenter = new SelfUploadLocalitySearchPresenter(this, new SearchRepository());
        presenter.onCreate(getArguments());
    }

    private void initViews(View inflate) {
        toolbar = (Toolbar) inflate.findViewById(R.id.toolbar);

        mLocalitiesList =
                (RecyclerView) inflate.findViewById(R.id.self_upload_locality_search_results);
        mLayoutManager = new LinearLayoutManager(getContext());
        mLocalitiesList.setLayoutManager(mLayoutManager);
        mSearchAdapter = new SearchAdapter(getContext(), new ArrayList<SearchResultModel>(), this);
        mLocalitiesList.setAdapter(mSearchAdapter);

        localitySearchEditor =
                (EditText) inflate.findViewById(R.id.self_building_upload_locality_search_editor);
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
    public void setAdapterData(ArrayList<SearchResultModel> searchResultModels) {
        mSearchAdapter.updateAdapter(searchResultModels);

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

    @Override
    public void onClick(View v) {

    }
}