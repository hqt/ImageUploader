package com.silicons.android.uploader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.uploader.model.PhotoItem;
import com.silicons.android.uploader.utils.DateUtils;

import java.util.List;

import static com.silicons.android.uploader.utils.LogUtils.makeLogTag;

/**
 * Created by Huynh Quang Thao on 1/2/16.
 */
public class FailPhotoAdapter extends RecyclerView.Adapter<FailPhotoAdapter.QueuedPhotoHolder> {
    private static final String TAG = makeLogTag(QueuePhotoAdapter.class);

    private static Bitmap mPlaceHolderBitmap;

    private List<PhotoItem> mPhotoItems;

    private Context mContext;

    private IFailedPhotoItemListener mListener;

    public FailPhotoAdapter(IFailedPhotoItemListener listener,
                             Context context, List<PhotoItem> photos) {
        this.mListener = listener;
        this.mPhotoItems = photos;
        this.mContext = context;
        if (mPlaceHolderBitmap == null) {
            mPlaceHolderBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.fail);
        }
    }

    @Override
    public QueuedPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_failed_image, parent, false);
        QueuedPhotoHolder holder = new QueuedPhotoHolder(v, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(QueuedPhotoHolder holder, int position) {
        PhotoItem photo = mPhotoItems.get(position);

        holder.mSizeTextView.setText(photo.getSize()/1024 + " MB");
        holder.mDateCreatedTextView.setText(DateUtils.convertLongToDate(photo.getTimeCreated()));
        holder.mPathTextView.setText(photo.getPath());
        holder.mNameTextView.setText(photo.getFlickrTitle());

        // we accept has UI flunky here :)
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath(), options);
        if (bitmap == null) {
            holder.mImageView.setImageBitmap(mPlaceHolderBitmap);
        } else {
            holder.mImageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return mPhotoItems.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class QueuedPhotoHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mSizeTextView;
        private TextView mDateCreatedTextView;
        private TextView mPathTextView;
        private TextView mNameTextView;
        private IFailedPhotoItemListener mListener;

        QueuedPhotoHolder(View itemView, IFailedPhotoItemListener listener) {
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

    public static interface IFailedPhotoItemListener {
        public void onClick(int position);
    }
}
