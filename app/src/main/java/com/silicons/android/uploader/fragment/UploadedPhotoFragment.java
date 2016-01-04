package com.silicons.android.uploader.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.activity.LoginActivity;
import com.silicons.android.uploader.activity.PhotoViewerActivity;
import com.silicons.android.uploader.activity.UploaderActivity;
import com.silicons.android.uploader.adapter.FailPhotoAdapter;
import com.silicons.android.uploader.adapter.UploadedPhotoAdapter;
import com.silicons.android.uploader.config.AppConstant;
import com.silicons.android.uploader.config.PrefStore;
import com.silicons.android.uploader.config.UploaderApplication;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.model.UploadedPhotoNotify;
import com.silicons.android.uploader.uploader.authen.FlickrOath;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.List;

import de.greenrobot.event.EventBus;

public class UploadedPhotoFragment extends Fragment implements UploadedPhotoAdapter.IUploadedPhotoItemListener {

    private OnFragmentInteractionListener mListener;
    private ImageListActivity mActivity;

    private RecyclerView mPhotoRecycleView;

    private List<PhotoItem> mPhotos;


    private EventBus mBus = EventBus.getDefault();

    public UploadedPhotoFragment() {
        // Required empty public constructor
    }

    public static UploadedPhotoFragment newInstance() {
        UploadedPhotoFragment fragment = new UploadedPhotoFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_uploaded_photo, container, false);

        mPhotoRecycleView = (RecyclerView) view.findViewById(R.id.uploaded_recycle_view);
        mPhotoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPhotos = PhotoItemDAL.getAllUploadedPhotos();
        UploadedPhotoAdapter adapter = new UploadedPhotoAdapter(this, mActivity, mPhotos);
        mPhotoRecycleView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            mActivity = (ImageListActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        mBus.register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mBus.unregister(this);
    }

    public void onEventMainThread(UploadedPhotoNotify photo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Photo " + photo.getPhoto().getFlickrTitle() + " has uploaded successfully. Refresh view ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mPhotos = PhotoItemDAL.getAllUploadedPhotos();
                        UploadedPhotoAdapter adapter = new UploadedPhotoAdapter(
                                UploadedPhotoFragment.this, mActivity, mPhotos);
                        mPhotoRecycleView.setAdapter(adapter);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onClick(int position) {
        PhotoItem photo = mPhotos.get(position);
        Intent intent = new Intent(mActivity, PhotoViewerActivity.class);
        intent.putExtra("photo_id", photo.getFlickrId());
        startActivity(intent);
    }

    public interface OnFragmentInteractionListener {
    }
}
