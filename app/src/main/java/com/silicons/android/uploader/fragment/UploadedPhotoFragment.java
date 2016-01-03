package com.silicons.android.uploader.fragment;

import android.content.Context;
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
import com.silicons.android.uploader.activity.PhotoViewerActivity;
import com.silicons.android.uploader.activity.UploaderActivity;
import com.silicons.android.uploader.adapter.FailPhotoAdapter;
import com.silicons.android.uploader.adapter.UploadedPhotoAdapter;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.List;

public class UploadedPhotoFragment extends Fragment implements UploadedPhotoAdapter.IUploadedPhotoItemListener {

    private OnFragmentInteractionListener mListener;
    private ImageListActivity mActivity;

    private RecyclerView mPhotoRecycleView;

    private List<PhotoItem> mPhotos;

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
