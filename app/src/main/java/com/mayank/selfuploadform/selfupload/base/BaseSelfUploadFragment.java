package com.mayank.selfuploadform.selfupload.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.base.TrackedFragment;
import com.mayank.selfuploadform.models.PropertyModel;

public abstract class BaseSelfUploadFragment extends TrackedFragment {

    private BaseSelfUploadFragmentCallback listener;
    private int defaultColor;

    public boolean onBackPressedHandled() {
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseSelfUploadFragmentCallback) {
            listener = (BaseSelfUploadFragmentCallback) context;
        } else {
            throw new RuntimeException("Activity must implement BaseSelfUploadFragmentCallback");
        }
        defaultColor = ContextCompat.getColor(getContext(), R.color.primary_dark);
    }

    @Override
    public void onResume() {
        super.onResume();
        int statusBarColor = getStatusBarColor();
        listener.setStatusBarColor(statusBarColor);
    }

    public void updateFragment(int requestCode, Object... data) {

    }

    protected void callBackPress(){
        listener.callBackPress();
    }

    protected int getStatusBarColor() {
        return defaultColor;
    }

    protected final void setToolbar(Toolbar toolbar) {
        listener.setToolbar(toolbar);
    }

    protected void openFragment(Fragment frag) {
        listener.replaceFragment(frag);
    }

    protected void requestForPermission(String[] permissions, int requestID) {
        listener.requestForPermission(permissions, requestID);
    }

    protected PropertyModel getPropertyModel() {
        return listener.getPropertyModel();
    }

    protected void clearBackStack() {
        listener.clearBackStack();
    }

    protected void callUpdateAndReplace (int requestCode, String fragmentTag, Object... data) {
        listener.updateAndReplaceFragment(requestCode,fragmentTag,data);
    }

    public void onPermissionResult(int requestID, boolean granted) {
    }

    @Override
    public String getTrackingScreenName() {
        return getClass().getSimpleName();
    }

    protected final void setViewVisibility(View buildingName, boolean visibility) {
        buildingName.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public interface BaseSelfUploadFragmentCallback {
        void setToolbar(Toolbar toolbar);

        void setStatusBarColor(int color);

        void replaceFragment(Fragment frag);

        void requestForPermission(String[] permissions, int requestID);

        void clearBackStack();

        PropertyModel getPropertyModel();

        void updateAndReplaceFragment(int requestCode,String fragmentTag, Object... data);

        void callBackPress();
    }

}
