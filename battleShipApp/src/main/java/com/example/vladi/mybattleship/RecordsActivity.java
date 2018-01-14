package com.example.vladi.mybattleship;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vladi.mybattleship.Logic.Record;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity implements RecordsListFragment.OnRecordSelectedFromListListener{
    private static final String TAG = "RecordsActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private ArrayList<Record> records;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        initRecords();
        if(isServicesOK()){
           enableMapFragmentDisplay();
        }else{
           disableMapFragmentDisplay();
        }
    }

    public ArrayList<Record> getRecords() {
        //initRecords();
        return records;
    }

    private void initRecords(){
        records = new ArrayList<>();
        records.add(new Record("vladi",250.0));
        records.add(new Record("vadim",230.0));
        records.add(new Record("katya",220.0));
        records.add(new Record("vlai",250.0));
        records.add(new Record("vadm",230.0));
        records.add(new Record("ktya",220.0));
        records.add(new Record("vadi",250.0));
        records.add(new Record("vdim",230.0));
        records.add(new Record("kya",220.0));
        records.add(new Record("katya",220.0));
    }
    private void enableMapFragmentDisplay(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment mapFrag = fm.findFragmentById(R.id.map);
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .show(mapFrag)
                .commit();
    }
    private void disableMapFragmentDisplay(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment mapFrag = fm.findFragmentById(R.id.map);
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .hide(mapFrag)
                .commit();
    }
    public boolean isServicesOK(){
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

    @Override
    public void onFragmentInteraction(Record record) {

    }
}
