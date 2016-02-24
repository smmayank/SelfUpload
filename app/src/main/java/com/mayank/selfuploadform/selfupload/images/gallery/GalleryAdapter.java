package com.mayank.selfuploadform.selfupload.images.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.mayank.selfuploadform.models.PhotoModel;
import com.mayank.selfuploadform.selfupload.widgets.GalleryElement;
import com.mayank.selfuploadform.selfupload.widgets.ImageField;

import java.util.ArrayList;

/**
 * Created by rahulchandnani on 22/02/16
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private Context context;
    private PhotoModel photoModel;
    private ArrayList<String> tagsList;
    private ImageField.ImageFieldInteractionListener imageFieldInteractionListener;
    private int state;

    public GalleryAdapter(Context context, PhotoModel photoModel,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener) {
        this(context, photoModel, imageFieldInteractionListener, ImageField.State.DEFAULT);
    }

    public GalleryAdapter(Context context, PhotoModel photoModel,
            ImageField.ImageFieldInteractionListener imageFieldInteractionListener, int state) {
        this.context = context;
        this.photoModel = photoModel;
        if (null == photoModel) {
            throw new RuntimeException();
        }
        this.tagsList = new ArrayList<>();
        this.tagsList.addAll(photoModel.getMap().keySet());
        this.imageFieldInteractionListener = imageFieldInteractionListener;
        this.state = state;
    }

    public void setState(int state) {
        this.state = state;
        notifyDataSetChanged();
    }

    @Override
    public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GalleryViewHolder(new GalleryElement(context, imageFieldInteractionListener));
    }

    @Override
    public void onBindViewHolder(GalleryViewHolder holder, int position) {
        holder.getGalleryElement().setTitle(tagsList.get(position));
        holder.getGalleryElement().setImages(photoModel.getMap().get(tagsList.get(position)));
        holder.getGalleryElement().setState(state);
    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private GalleryElement galleryElement;

        public GalleryViewHolder(View itemView) {
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
