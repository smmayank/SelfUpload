package com.mayank.selfuploadform.selfupload;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment;
import com.mayank.selfuploadform.selfupload.base.BaseSelfUploadFragment.BaseSelfUploadFragmentCallback;
import com.mayank.selfuploadform.selfupload.dashboard.SelfUploadDashboardFragment;
import com.mayank.selfuploadform.selfupload.images.photopicker.PhotoPickerFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SelfUploadActivity extends AppCompatActivity
        implements BaseSelfUploadFragmentCallback {

    private static final int FRAGMENT_CONTAINER = R.id.fragment_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initDashboardFragment();
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
        ft.commitAllowingStateLoss();
    }

    @Override
    public void requestForPermission(String[] permissions, int requestID) {
        if (null != permissions && 0 < permissions.length) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permissions[0])) {
                ActivityCompat.requestPermissions(this, permissions, requestID);
            } else {
                BaseSelfUploadFragment fragment = getAttachedFragment();
                if (null != fragment) {
                    fragment.onPermissionResult(requestID, true);
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BaseSelfUploadFragment fragment = getAttachedFragment();
        if (null != fragment) {
            switch (requestCode) {
                case SelfUploadDashboardFragment.ACCESS_STORAGE_PERMISSION: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager
                            .PERMISSION_GRANTED) {
                        fragment.onPermissionResult(requestCode, true);
                    } else {
                        fragment.onPermissionResult(requestCode, false);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                BaseSelfUploadFragment fragment = getAttachedFragment();
                if (null != fragment && fragment.onBackPressedHandled()) {
                    return true;
                }
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private BaseSelfUploadFragment getAttachedFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(FRAGMENT_CONTAINER);
        if (null != fragment && fragment instanceof BaseSelfUploadFragment) {
            return (BaseSelfUploadFragment) fragment;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PhotoPickerFragment.IMAGE_CAPTURE_REQUEST_CODE: {
                if (RESULT_OK == resultCode) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    BaseSelfUploadFragment fragment = getAttachedFragment();
                    if (null != fragment) {
                        new SaveImageTask(this, fragment, bitmap).execute();
                    }
                }
                break;
            }
        }
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
    public void onBackPressed() {
        BaseSelfUploadFragment fragment = getAttachedFragment();
        if (null == fragment || !fragment.onBackPressedHandled()) {
            super.onBackPressed();
        }
    }

    @Override
    public void clearBackStack() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 && !isFinishing()) {
            getSupportFragmentManager().popBackStackImmediate(
                    getSupportFragmentManager().getBackStackEntryAt(0).getId(),
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private static class SaveImageTask extends AsyncTask<Void, Void, String> {


        private static final String URI_FILE_PREFIX = "file://";
        private final BaseSelfUploadFragment fragment;
        private final Bitmap bitmap;
        private final Context context;


        public SaveImageTask(Context context, BaseSelfUploadFragment fragment, Bitmap bitmap) {
            this.context = context;
            this.fragment = fragment;
            this.bitmap = bitmap;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        @Override
        protected String doInBackground(Void... params) {
            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    context.getString(R.string.app_name));
            if (!folder.exists()) {
                folder.mkdirs();
            }
            try {
                File file = new File(folder, String.valueOf(System.currentTimeMillis()) + ".jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                String result = MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath
                        (), file.getName(), file.getName());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    mediaScanIntent.setData(Uri.fromFile(file));
                    context.sendBroadcast(mediaScanIntent);
                } else {
                    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                            Uri.parse(URI_FILE_PREFIX + Environment.getExternalStorageDirectory())));
                }
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (null != result) {
                fragment.updateFragment(PhotoPickerFragment.IMAGE_CAPTURE_REQUEST_CODE);
            }
        }
    }

}