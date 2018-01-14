package com.example.vladi.mybattleship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vladi.mybattleship.Logic.Record;

import java.util.ArrayList;

public class RecordsListFragment extends Fragment {
    private OnRecordSelectedFromListListener mListener;
    private ArrayList<Record> records;

    public interface OnRecordSelectedFromListListener {
        void onFragmentInteraction(Record record);
        //maybe position
    }

    public RecordsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init arraylist
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        records = ((RecordsActivity)getActivity()).getRecords();
        return inflater.inflate(R.layout.fragment_records_list, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnRecordSelectedFromListListener) context;
    }
}
