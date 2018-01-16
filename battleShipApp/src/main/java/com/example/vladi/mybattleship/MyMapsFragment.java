package com.example.vladi.mybattleship;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vladi.mybattleship.Logic.Record;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;

public class MyMapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Record> records;
    private ArrayList<Marker> recordsMarkers;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeMap();
        //init arraylist
    }

    private void initializeMap() {
        if (mMap == null) {
            SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
            mapFrag.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //ArrayList<Record> records = ((RecordsActivity)getActivity()).getRecords();

    }

    public void setRecords (ArrayList<Record> records){
        this.records = records;

        Iterator<Record> itr = records.iterator();
        while(itr.hasNext()){
            Record temp = itr.next();
            recordsMarkers.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(temp.getLat(),temp.getLang()))
                    .title(temp.getName())));
        }

    }
}
