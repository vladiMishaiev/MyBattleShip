package com.example.vladi.mybattleship;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vladi.mybattleship.DAL.RecordsDatabase;
import com.example.vladi.mybattleship.Logic.Record;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecordsActivity extends AppCompatActivity implements RecordsListFragment.OnRecordSelectedFromListListener{
    private static final String TAG = "RecordsActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private final static String DIFFICULTY = "difficulty";
    private final static String FILE = "appInfo";
    //private Spinner mSpinner;
    //private ArrayAdapter<CharSequence> adapter;
    private String mDifficulty;
    private GoogleMap gMap;
    public static List<Record> records;
    private RecordsDatabase recordsDB;
    private List<Marker> markers;


    /* ==========================================================================================================================
    *   Activity Initialization.
    *   1. Initiates Records list.
    *   2. Initiates Map Fragment.
    *  ========================================================================================================================== */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        getDifficulty();
        initRecords();
        //setSpinner();
        if(isServicesOK()){
           initMap();
        }
    }

    /*private void setSpinner(){
        mSpinner = (Spinner) findViewById(R.id.recordsSpinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Fetch record list from DB
                try {
                    //records = recordsDB.recordsDao().getAllRecords();
                    records = recordsDB.recordsDao().getAllRecords(mSpinner.getSelectedItem().toString());
                } catch(Exception e) {}

                // Sort.
                Collections.sort(records);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Fetch record list from DB
                try {
                    //records = recordsDB.recordsDao().getAllRecords();
                    records = recordsDB.recordsDao().getAllRecords("Beginner");
                } catch(Exception e) {}

                // Sort.
                Collections.sort(records);
            }

        });
    }*/

    /* ==========================================================================================================================
    *   Record list getter.
    *  ========================================================================================================================== */
    public List<Record> getRecords() {
        return records;
    }

    /* ==========================================================================================================================
    *   Record list initialization.
    *   1. Get instance of DB.
    *   2. Get all records from DB instance.
    *   3. Sort descending.
    *  ========================================================================================================================== */
    private void initRecords(){
        records = new ArrayList<>();

        // Get instance of DB and input test players.
        try {
            recordsDB = RecordsDatabase.getInstance(getApplicationContext());
            recordsDB.recordsDao().createRecord(new Record("player1",100.0,31.94623000,34.81816292));
            recordsDB.recordsDao().createRecord(new Record("player2",150.0,31.96624111,34.81816292));
            recordsDB.recordsDao().createRecord(new Record("player3",90.0,31.98623222,34.81816292));
            recordsDB.recordsDao().createRecord(new Record("player4",300.0,31.92622333,34.81816292));

        } catch(Exception e) {}

        // Fetch record list from DB
        try {
            //records = recordsDB.recordsDao().getAllRecords();
            records = recordsDB.recordsDao().getAllRecords("Beginner");
        } catch(Exception e) {}

        // Sort.
        Collections.sort(records);
    }

    /* ==========================================================================================================================
    *   Map initialization
    *   1. Initiate MapFragment from XML.
    *   2. Initiate Markers of records.
    *   3. Set onMarkerClickListener to move and zoom camera on marker selection.
    *  ========================================================================================================================== */
    private void initMap(){
        android.app.FragmentManager fm = getFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map);
        mapFrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                initMarkers();
                gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 14f));
                        return false;
                    }
                });
            }
        });
    }

    /* ==========================================================================================================================
    *   Google Map Validation.
    *   Validates whether Google map service is available.
    *  ========================================================================================================================== */
    private boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(RecordsActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(RecordsActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /* ==========================================================================================================================
    *   Record Selection Event Handler.
    *   Handles selection of record from record list in record list fragment.
    *  ========================================================================================================================== */
    @Override
    public void onRecordSelectionTable(int recordPos) {
        Record selectedRecord = records.get(recordPos);
        LatLng loc = new LatLng(selectedRecord.getLat(), selectedRecord.getLang());
        for(Marker m : markers) {
            if(m.getPosition().equals(loc))
                m.showInfoWindow();
            else
                m.hideInfoWindow();
        }
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 14f));

    }

    /* ==========================================================================================================================
    *   Marker Initialization
    *   Adds markers to the map by iterating record list.
    *  ========================================================================================================================== */
    private void initMarkers() {
        markers = new ArrayList<>();
        for(Record r : records) {
            markers.add(gMap.addMarker(new MarkerOptions().position(new LatLng(r.getLat(), r.getLang())).visible(true).title("Name: " + r.getName()).snippet("Score: " + r.getScore())));
        }
    }

    private void getDifficulty() {
        SharedPreferences sp = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        mDifficulty = sp.getString(DIFFICULTY,"");
        Log.d(TAG, "getDifficulty: value is : " +mDifficulty);
    }
}
