package com.silicons.android.uploader.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.adapter.FailPhotoAdapter;
import com.silicons.android.uploader.adapter.QueuePhotoAdapter;
import com.silicons.android.uploader.adapter.UploadedPhotoAdapter;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.model.FailedPhotoNotify;
import com.silicons.android.uploader.model.UploadedPhotoNotify;
import com.silicons.android.uploader.uploader.model.PhotoItem;

import java.util.List;

import de.greenrobot.event.EventBus;

public class FailUploadFragment extends Fragment implements FailPhotoAdapter.IFailedPhotoItemListener {
    private OnFragmentInteractionListener mListener;
    private ImageListActivity mActivity;

    private RecyclerView mPhotoRecycleView;

    private List<PhotoItem> mPhotos;

    private EventBus mBus = EventBus.getDefault();

    public FailUploadFragment() {
        // Required empty public constructor
    }

    public static FailUploadFragment newInstance() {
        FailUploadFragment fragment = new FailUploadFragment();
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
        View view = inflater.inflate(R.layout.fragment_fail_upload, container, false);

        mPhotoRecycleView = (RecyclerView) view.findViewById(R.id.failed_recycle_view);
        mPhotoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPhotos = PhotoItemDAL.getAllFailedPhotos();
        FailPhotoAdapter adapter = new FailPhotoAdapter(this, mActivity, mPhotos);
        mPhotoRecycleView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void onEventMainThread(FailedPhotoNotify photo) {
        mPhotos = PhotoItemDAL.getAllFailedPhotos();
        FailPhotoAdapter adapter = new FailPhotoAdapter(this, mActivity, mPhotos);
        mPhotoRecycleView.setAdapter(adapter);
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
        Toast.makeText(mActivity, "Retry function is not implement.", Toast.LENGTH_SHORT).show();
    }


    public interface OnFragmentInteractionListener {
    }
}
