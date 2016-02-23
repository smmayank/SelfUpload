package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 23/02/16
 */
public class PhotoPickerRepository extends AsyncTask<Void, Void, ArrayList<String>> {

    private Context context;
    private FetchImagesCallback fetchImagesCallback;

    public PhotoPickerRepository(Context context) {
        this.context = context;
    }

    public void fetchImages(FetchImagesCallback fetchImagesCallback) {
        this.fetchImagesCallback = fetchImagesCallback;
        this.execute();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        if (null != fetchImagesCallback) {
            ArrayList<String> images = new ArrayList<>();
            final String[] projection = {MediaStore.Images.Media.DATA};
            final String selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
            final String sortOrder = MediaStore.Images.Media.DATE_MODIFIED + " DSC";
            final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, selection, null, sortOrder);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    do {
                        final String data = cursor.getString(dataColumn);
                        images.add(data);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            return images;
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> images) {
        if (null != images && null != fetchImagesCallback) {
            fetchImagesCallback.onImagesFetch(images);
        }
    }

    public interface FetchImagesCallback {

        void onImagesFetch(ArrayList<String> images);

    }

}