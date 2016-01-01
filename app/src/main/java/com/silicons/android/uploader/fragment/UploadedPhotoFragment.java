package com.silicons.android.uploader.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silicons.android.uploader.R;

public class UploadedPhotoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private RecyclerView mPhotoRecycleView;

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

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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


    public interface OnFragmentInteractionListener {
    }
}
