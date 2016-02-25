package com.mayank.selfuploadform.selfupload.images.taggingpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rahulchandnani on 25/02/16
 */
public class TaggingPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<PhotoModel.PhotoObject> photoObjects;
    private HashMap<Integer, TaggingInnerFragment> map;

    public TaggingPagerAdapter(FragmentManager fm, ArrayList<PhotoModel.PhotoObject> photoObjects) {
        super(fm);
        this.photoObjects = photoObjects;
        map = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        TaggingInnerFragment fragment = TaggingInnerFragment.newInstance(photoObjects.get(position));
        map.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return photoObjects.size();
    }

    public void setTag(String tag, int position) {
        map.get(position).updateTag(tag);
    }
}
