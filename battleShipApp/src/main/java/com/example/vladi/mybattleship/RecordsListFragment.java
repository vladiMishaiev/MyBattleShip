package com.example.vladi.mybattleship;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vladi.mybattleship.DAL.RecordsDatabase;
import com.example.vladi.mybattleship.Logic.Record;

import java.util.ArrayList;
import java.util.List;

public class RecordsListFragment extends Fragment {
    private OnRecordSelectedFromListListener mListener;
    private List<Record> records;
    private ListView recordsView;
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
        View view = inflater.inflate(R.layout.fragment_records_list, container, false);
        /*records = ((RecordsActivity)getActivity()).getRecords();
        recordsView = (ListView)view.findViewById(R.id.recordsList);
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), R.layout.adapter_record_view_layout, records);
        recordsView.setAdapter(adapter);*/
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
       super.onActivityCreated(savedInstanceState);
        records = ((RecordsActivity)getActivity()).getRecords();
        recordsView = (ListView)getView().findViewById(R.id.recordsList);
        RecordListAdapter adapter = new RecordListAdapter(getActivity(), R.layout.adapter_record_view_layout, records);
        recordsView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnRecordSelectedFromListListener) context;
    }
}
