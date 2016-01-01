package com.silicons.android.uploader.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.fragment.FailUploadFragment;
import com.silicons.android.uploader.fragment.QueuePhotoFragment;
import com.silicons.android.uploader.fragment.UploadedPhotoFragment;

/** pager adapter for main screen
 * Created by Huynh Quang Thao on 1/1/16.
 */
public class PhotoPagerAdapter extends FragmentStatePagerAdapter {


    private Context mContext;
    private int mTabTitleResIds[] = {R.string.upload_photo_tab, R.string.queue_photo_tab, R.string.fail_upload_photo_tab};
    private int mImageResIds[] = {R.drawable.tab_upload, R.drawable.tab_queue, R.drawable.tab_fail};

    public PhotoPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new UploadedPhotoFragment();
                break;
            case 1:
                fragment = new QueuePhotoFragment();
                break;
            case 2:
                fragment = new FailUploadFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTabTitleResIds.length;
    }

    public View getTabView(int position){
        View view = LayoutInflater.from(mContext).inflate(R.layout.viewpager_item, null);
        TextView titleTextView= (TextView) view.findViewById(R.id.tab_title);
        titleTextView.setText(mContext.getResources().getString(mTabTitleResIds[position]));
        ImageView itemImage = (ImageView) view.findViewById(R.id.tab_icon);
        itemImage.setImageResource(mImageResIds[position]);
        return view;
    }
}