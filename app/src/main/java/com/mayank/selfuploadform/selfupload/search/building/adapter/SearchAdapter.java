package com.mayank.selfuploadform.selfupload.search.building.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.search.building.models.SearchResultModel;

import java.util.ArrayList;

/**
 * Created by vikas-pc on 01/03/16
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.DataObjectHolder>{

    private static final String TAG = "SearchAdapter";
    private Context mContext;
    private ArrayList<SearchResultModel> mBuildingSearchResult;
    private View.OnClickListener mOnclickListener;

    public SearchAdapter(Context context, ArrayList<SearchResultModel> buildingSearchResults, View.OnClickListener
     onClickListener) {
        this.mContext = context;
        this.mBuildingSearchResult = buildingSearchResults;
        this.mOnclickListener = onClickListener;

    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.building_search_result, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view, mOnclickListener);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        holder.buildingName.setText(mBuildingSearchResult.get(position).getName());
        holder.buildingName.setTag(R.id.building_id, mBuildingSearchResult.get(position).getUuid());


    }

    @Override
    public int getItemCount() {
        return mBuildingSearchResult.size();
    }

    public void updateAdapter(ArrayList<SearchResultModel> buildingSearchResults) {
        mBuildingSearchResult.clear();
        mBuildingSearchResult.addAll(buildingSearchResults);
        notifyDataSetChanged();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        TextView buildingName;

        public DataObjectHolder(View itemView, View.OnClickListener onClickListener) {
            super(itemView);
            buildingName = (TextView)itemView.findViewById(R.id.building_name);
            buildingName.setOnClickListener(onClickListener);
        }

    }

}
