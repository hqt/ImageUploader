package com.silicons.android.uploader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.cache.DiskLruImageCache;
import com.silicons.android.uploader.cache.MemoryImageCache;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.task.flickr.ImageDownloadTask;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.DateUtils;
import com.silicons.android.uploader.utils.ImageUtils;
import com.silicons.android.uploader.widgets.AsyncDrawable;

import java.util.List;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/** Adapter for uploaded photos
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class UploadedPhotoAdapter extends RecyclerView.Adapter<UploadedPhotoAdapter.UploadedPhotoHolder> {

    private static final String TAG = makeLogTag(UploadedPhotoAdapter.class);

    private static Bitmap mPlaceHolderBitmap;

    private List<PhotoItem> mPhotoItems;

    private Context mContext;

    private IUploadedPhotoItemListener mListener;

    public UploadedPhotoAdapter(IUploadedPhotoItemListener listener,
                                Context context, List<PhotoItem> photos) {
        this.mListener = listener;
        this.mPhotoItems = photos;
        this.mContext = context;
        if (mPlaceHolderBitmap == null) {
            mPlaceHolderBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loading);
        }
    }

    @Override
    public UploadedPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_uploaded_image, parent, false);
        UploadedPhotoHolder holder = new UploadedPhotoHolder(v, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(UploadedPhotoHolder holder, int position) {
        PhotoItem photo = mPhotoItems.get(position);

        holder.mSizeTextView.setText(photo.getSize()/1024 + " MB");
        holder.mDateCreatedTextView.setText(DateUtils.convertLongToDate(photo.getTimeCreated()));
        holder.mPathTextView.setText(photo.getPath());
        holder.mNameTextView.setText(photo.getFlickrTitle());

        loadImage(holder, photo);

    }

    private void loadImage(UploadedPhotoHolder holder, PhotoItem photo) {
        // check if new image should be load or not
        if (!ImageUtils.isNewImage(photo.getFlickrId(), holder.mImageView)) {
            return;
        }

        // check if on memory cache
        MemoryImageCache memoryImageCache = UploaderApplication.getImageMemoryCache();
        if (memoryImageCache.containsKey(photo.getFlickrId())) {
            Log.e(TAG, photo.getFlickrTitle() + " memory cached hit");
            Bitmap image = memoryImageCache.getBitmapFromMemCache(photo.getFlickrId());
            holder.mImageView.setImageBitmap(image);
            return;
        }

        // check if on file cache
        DiskLruImageCache imageDiskCache = UploaderApplication.getImageDiskCache();
        if (imageDiskCache.containsKey(photo.getFlickrId())) {
            Log.e(TAG, photo.getFlickrTitle() + " file cached hit");
            Bitmap image = imageDiskCache.getBitmap(photo.getFlickrId());
            holder.mImageView.setImageBitmap(image);
            return;
        }

        // else. try to download new image
        // by creating new task for this image view
        Log.e(TAG, photo.getFlickrTitle() + " need to download");
        ImageDownloadTask task = new ImageDownloadTask(mContext, holder.mImageView,
                photo.getFlickrId(), AppConstant.PhotoType.PHOTO_ID_MEDIUM, false);
        AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mPlaceHolderBitmap, task);
        holder.mImageView.setImageDrawable(asyncDrawable);
        task.execute();
    }

    @Override
    public int getItemCount() {
        return mPhotoItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



    public static class UploadedPhotoHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mSizeTextView;
        private TextView mDateCreatedTextView;
        private TextView mPathTextView;
        private TextView mNameTextView;
        private IUploadedPhotoItemListener mListener;

        UploadedPhotoHolder(View itemView, IUploadedPhotoItemListener listener) {
            super(itemView);

            this.mListener = listener;
            mImageView = (ImageView) itemView.findViewById(R.id.photo_image_view);
            mPathTextView = (TextView) itemView.findViewById(R.id.path_text_view);
            mDateCreatedTextView = (TextView) itemView.findViewById(R.id.date_created_text_view);
            mSizeTextView = (TextView) itemView.findViewById(R.id.photo_size_text_view);
            mNameTextView = (TextView) itemView.findViewById(R.id.photo_name_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }
    }

    public static interface IUploadedPhotoItemListener {
        public void onClick(int position);
    }
}
