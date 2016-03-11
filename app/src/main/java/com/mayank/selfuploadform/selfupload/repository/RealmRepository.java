package com.mayank.selfuploadform.selfupload.repository;

import android.content.Context;

import com.mayank.selfuploadform.models.ImageModel;
import com.mayank.selfuploadform.models.PropertyModel;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by vikas-pc on 23/02/16
 */
public class RealmRepository {

    private static final String TAG = "RealmRepository";
    private Realm mRealm;

    public RealmRepository(Context context) {
        mRealm = Realm.getInstance(context);
    }

    public boolean isInTransaction() {
        return mRealm.isInTransaction();
    }

    private <T extends RealmObject> int getNextId(Class<T> tClass) {
        Number currentMax = mRealm.where(tClass).max("id");
        if (null == currentMax) {
            return 1;
        }
        return currentMax.intValue() + 1;
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    public <T extends RealmObject> T getRealmObject(Class<T> tClass, int propertyId) {
        T obj;
        if (propertyId <= 0) {
            obj = mRealm.createObject(tClass);
            if (obj instanceof PropertyModel) {
                PropertyModel propertyModel = (PropertyModel) obj;
                propertyModel.setId(getNextId(PropertyModel.class));
            } else {
                ImageModel imageModel = (ImageModel) obj;
                imageModel.setId(getNextId(ImageModel.class));
            }
        } else {
            return mRealm.where(tClass).equalTo("id", propertyId).findFirst();
        }
        return obj;
    }

}
