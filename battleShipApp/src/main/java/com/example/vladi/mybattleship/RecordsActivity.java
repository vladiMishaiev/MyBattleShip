package com.example.vladi.mybattleship;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.vladi.mybattleship.DAL.RecordsDatabase;
import com.example.vladi.mybattleship.Logic.Record;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.List;

public class RecordsActivity extends AppCompatActivity implements RecordsListFragment.OnRecordSelectedFromListListener{
    private static final String TAG = "RecordsActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    private List<Record> records;
    private RecordsDatabase recordsDB;
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

    public List<Record> getRecords() {
        try {
            records = (List<Record>) recordsDB.recordsDao().getAllRecords();
        } catch(Exception e){}
        return records;
    }

    private void initRecords(){
        records = new ArrayList<>();
        recordsDB = RecordsDatabase.getInstance(getApplicationContext());
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
