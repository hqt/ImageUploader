package com.silicons.android.uploader.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silicons.android.uploader.R;
import com.silicons.android.uploader.activity.ImageListActivity;
import com.silicons.android.uploader.adapter.QueuePhotoAdapter;
import com.silicons.android.uploader.dal.PhotoItemDAL;
import com.silicons.android.uploader.model.PhotoItem;

import java.util.List;

import de.greenrobot.event.EventBus;

public class QueuePhotoFragment extends Fragment implements QueuePhotoAdapter.IQueuePhotoItemListener {
    private OnFragmentInteractionListener mListener;
    private ImageListActivity mActivity;

    private RecyclerView mPhotoRecycleView;

    private List<PhotoItem> mPhotos;


    private EventBus mBus = EventBus.getDefault();

    public QueuePhotoFragment() {
        // Required empty public constructor
    }

    public static QueuePhotoFragment newInstance() {
        QueuePhotoFragment fragment = new QueuePhotoFragment();
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

        View view =  inflater.inflate(R.layout.fragment_queue_photo, container, false);

        mPhotoRecycleView = (RecyclerView) view.findViewById(R.id.queued_recycle_view);
        mPhotoRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPhotos = PhotoItemDAL.getAllQueuedPhotos();
        QueuePhotoAdapter adapter = new QueuePhotoAdapter(this, mActivity, mPhotos);
        mPhotoRecycleView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    }

    public interface OnFragmentInteractionListener {

    }
}
