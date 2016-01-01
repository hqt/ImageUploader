package com.silicons.android.uploader.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.List;

/** Adapter for uploaded photos
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class UploadedPhotoAdapter extends RecyclerView.Adapter<UploadedPhotoAdapter.UploadedPhotoHolder> {

    List<PhotoItem> photoItems;

    @Override
    public UploadedPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image, parent, false);
        UploadedPhotoHolder holder = new UploadedPhotoHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(UploadedPhotoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return photoItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class UploadedPhotoHolder extends RecyclerView.ViewHolder {


        UploadedPhotoHolder(View itemView) {
            super(itemView);

        }
    }
}
