package com.mayank.selfuploadform.selfupload;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.base.Logger;
import com.mayank.selfuploadform.models.PropertyModel;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment.BaseSelfUploadFragmentCallback;
import com.mayank.selfuploadform.selfupload.dashboard.SelfUploadDashboardFragment;
import com.mayank.selfuploadform.selfupload.repository.RealmRepository;


public class SelfUploadActivity extends AppCompatActivity
        implements BaseSelfUploadFragmentCallback {

    private static final int FRAGMENT_CONTAINER = R.id.fragment_container;
    private RealmRepository mRealmRepository;
    private PropertyModel mPropertyModel;
    private Integer mPropertyId;
    public static final String PROPERTY_ID = "property_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initDashboardFragment();
        mRealmRepository = new RealmRepository(this);
        if (getIntent().getExtras() != null) {
            mPropertyId = getIntent().getExtras().getInt(PROPERTY_ID);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRealmRepository.beginTransaction();
        if (mPropertyId == null) {
            mPropertyModel = mRealmRepository.getRealmObject(PropertyModel.class, -1);
        } else {
            mPropertyModel = mRealmRepository.getRealmObject(PropertyModel.class, mPropertyId);
        }

    }

    private void initDashboardFragment() {
        SelfUploadDashboardFragment frag = new SelfUploadDashboardFragment();
        replaceFragment(frag);
    }

    public void replaceFragment(Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(FRAGMENT_CONTAINER, frag);
        if (!(frag instanceof SelfUploadDashboardFragment)) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public PropertyModel getPropertyModel() {
        return mPropertyModel;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Fragment frag = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
                if (frag instanceof BaseSelfUploadFragment &&
                        ((BaseSelfUploadFragment) frag).onBackPressedHandled()) {
                    return true;
                }
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (null != supportActionBar) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRealmRepository.commitTransaction();
        Logger.logD(this, "values saved");
    }
}
