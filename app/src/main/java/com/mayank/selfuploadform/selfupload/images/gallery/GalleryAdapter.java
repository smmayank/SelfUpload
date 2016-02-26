package com.mayank.selfuploadform.selfupload.images.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.R;
import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.widgets.GalleryElement;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rahulchandnani on 22/02/16
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context context;
    private PhotoModel photoModel;
    private ArrayList<String> tagsList;
    private ImageField.ImageFieldInteractionListener imageFieldInteractionListener;
    private int state;
    private ArrayList<PhotoModel.PhotoObject> selectedPhotoObjects;
    private View.OnClickListener onClickListener;

    public GalleryAdapter(Context context, PhotoModel photoModel,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener,
            View.OnClickListener onClickListener) {
        this(context, photoModel, imageFieldInteractionListener, onClickListener, ImageField.State.DEFAULT);
    }

    public GalleryAdapter(Context context, PhotoModel photoModel,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener,
            View.OnClickListener onClickListener, int
            state) {
        this.context = context;
        this.photoModel = photoModel;
        if (null == photoModel) {
            throw new RuntimeException();
        }
        this.onClickListener = onClickListener;
        this.tagsList = new ArrayList<>();
        this.tagsList.addAll(photoModel.getMap().keySet());
        Collections.sort(tagsList);
        this.imageFieldInteractionListener = imageFieldInteractionListener;
        this.selectedPhotoObjects = new ArrayList<>();
        this.state = state;
    }

    public void clearSelectedImages() {
        this.selectedPhotoObjects.clear();
    }

    public void addPhotoObject(PhotoModel.PhotoObject photoObject) {
        if (!selectedPhotoObjects.contains(photoObject)) {
            this.selectedPhotoObjects.add(photoObject);
        }
    }

    public void clearPhotoObject(PhotoModel.PhotoObject photoObject) {
        if (this.selectedPhotoObjects.contains(photoObject)) {
            this.selectedPhotoObjects.remove(photoObject);
        }
    }

    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    @SuppressLint("InflateParams")
    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (ItemType.GALLERY_ELEMENT == viewType) {
            return new GalleryElementHolder(new GalleryElement(context, imageFieldInteractionListener));
        } else {
            return new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout
                    .self_upload_gallery_add_more_photos, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        if (holder instanceof GalleryElementHolder) {
            GalleryElementHolder galleryElementHolder = (GalleryElementHolder) holder;
            galleryElementHolder.getGalleryElement().setTitle(tagsList.get(position));
            galleryElementHolder.getGalleryElement()
                    .setImages(photoModel.getMap().get(tagsList.get(position)), selectedPhotoObjects,
                            state);
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.getView().setOnClickListener(onClickListener);
            ViewCompat.setElevation(footerViewHolder.getView(), context.getResources().getDimensionPixelSize(R.dimen
                    .dimen_10dp));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == tagsList.size()) {
            return ItemType.FOOTER;
        } else {
            return ItemType.GALLERY_ELEMENT;
        }
    }

    @Override
    public int getItemCount() {
        return tagsList.size() + 1;
    }

    private static class ItemType {

        public static final int GALLERY_ELEMENT = 1;
        public static final int FOOTER = 2;
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        public GalleryViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterViewHolder extends GalleryViewHolder {

        private View view;

        public FooterViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public View getView() {
            return view;
        }
    }

    public static class GalleryElementHolder extends GalleryViewHolder {

        private GalleryElement galleryElement;

        public GalleryElementHolder(View itemView) {
            super(itemView);
            if (itemView instanceof GalleryElement) {
                this.galleryElement = (GalleryElement) itemView;
            }
        }

        public GalleryElement getGalleryElement() {
            return galleryElement;
        }

    }

}
