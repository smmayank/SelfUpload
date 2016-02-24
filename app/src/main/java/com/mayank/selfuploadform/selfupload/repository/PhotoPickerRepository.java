package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.mayank.selfuploadform.models.PhotoModel;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerRepository extends AsyncTask<Void, Void, ArrayList<PhotoModel.PhotoObject>> {

    private static final String DESC = " DESC";
    private Context context;
    private FetchImagesCallback fetchImagesCallback;

    public PhotoPickerRepository(Context context) {
        this.context = context;
    }

    public void fetchImages(FetchImagesCallback fetchImagesCallback) {
        this.fetchImagesCallback = fetchImagesCallback;
        this.executeOnExecutor(THREAD_POOL_EXECUTOR);
    }

    @Override
    protected ArrayList<PhotoModel.PhotoObject> doInBackground(Void... params) {
        if (null != fetchImagesCallback) {
            ArrayList<PhotoModel.PhotoObject> images = new ArrayList<>();
            final String[] projection = {MediaStore.Images.Media.DATA};
            final String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + DESC;
            final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, sortOrder);
            if (null != cursor) {
                final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    do {
                        final String data = cursor.getString(dataColumn);
                        PhotoModel.PhotoObject object = new PhotoModel.PhotoObject();
                        object.setPath(data);
                        images.add(object);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            return images;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PhotoModel.PhotoObject> images) {
        if (null != images && null != fetchImagesCallback) {
            fetchImagesCallback.onImagesFetch(images);
        }
    }

    public interface FetchImagesCallback {

        void onImagesFetch(ArrayList<PhotoModel.PhotoObject> images);

    }

}