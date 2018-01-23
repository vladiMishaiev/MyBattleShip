package com.example.vladi.mybattleship;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vladi.mybattleship.DAL.RecordsDatabase;
import com.example.vladi.mybattleship.Logic.Record;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class FinishGameActivity extends AppCompatActivity {
    private final static String FILE = "appInfo";
    private final static String DIFFICULTY = "difficulty";
    private static final String TAG = "FinishGameActivity";
    private ImageView outcomeImg;
    private double score;
    private Button replayBtn;
    private Button homeBtn;
    private Button submitScore;
    private EditText nameInput;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location loc = null;
    private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 999;
    private String mDifficulty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_finish_game);
        getDifficulty();
        getScore();
        setImage();
        setReplayBtn();
        setHomeBtn();
        setSubmitBox();
    }

    /* ==========================================================================================================================
    *   Replay button settings.
    *  ========================================================================================================================== */
    private void setReplayBtn() {
        replayBtn = (Button) findViewById(R.id.replayBtn);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishGameActivity.this, BattleActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    /* ==========================================================================================================================
    *   Home button settings.
    *  ========================================================================================================================== */
    private void setHomeBtn() {
        homeBtn = (Button) findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishGameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /* ==========================================================================================================================
    *   Submit button settings.
    *  ========================================================================================================================== */
    private void setSubmitBox() {
        //set submit box visability
        LinearLayout recordBox = (LinearLayout)findViewById(R.id.submitRecordBox);
        RecordsDatabase db = RecordsDatabase.getInstance(getApplicationContext());
        // Save new record to database,
        double currentMinScore = db.recordsDao().getMinScoreByDifficulty(mDifficulty);
        int numOfRecords = db.recordsDao().getAllRecords(mDifficulty).size();
        if (score!=0 && (numOfRecords<10 || score>currentMinScore)) {
            recordBox.setVisibility(LinearLayout.VISIBLE);
            if (numOfRecords==10){
                db.recordsDao().deleteRecordByScore(currentMinScore,mDifficulty);
            }
        }
        else
            recordBox.setVisibility(LinearLayout.GONE);

        submitScore = (Button) findViewById(R.id.submitScoreBtn);
        nameInput = (EditText) findViewById(R.id.nameInput);

        //get box input
        submitScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /* 1. Check GPS access permission.
                *  2. If GPS permission is already given, get location and save to db.
                *  3. Else if GPS permission is not given, ask for permission.
                *  3.1 The callback function takes care of fetching location and saving to db. */
                try {
                    if (checkGpsPermission()) {
                        Log.d(TAG, "GPS permission is already given.");
                        // In case permission is granted, get last known location, and save to database.
                        getLocationAndSaveToDatabase();
                    } else {
                        // In case permission is not granted, ask for permission.
                        // The onRequestPermissionsResult callback, will fetch location and save to database.
                        // Request GPS access permission.
                        Log.d(TAG, "GPS permission wasn't found, asking for permission.");
                        ActivityCompat.requestPermissions(FinishGameActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                } catch(Exception e) {}
            }
        });
    }

    /* ==========================================================================================================================
    *   Get Score.
    *  ========================================================================================================================== */
    private void getScore(){
        Bundle params = getIntent().getExtras();
        score = params.getDouble("score");
        Log.d(TAG, "getScore: "+score);
    }

    /* ==========================================================================================================================
    *   Set Image.
    *  ========================================================================================================================== */
    private void setImage(){
        outcomeImg = (ImageView)findViewById(R.id.outcomeImg);
        if (score!=0)
            outcomeImg.setImageResource(R.drawable.winner);
        else
            outcomeImg.setImageResource(R.drawable.looser);
    }



    /* ==========================================================================================================================
    *   Save records to the database.
    *  ========================================================================================================================== */
    private void saveScoreToDatabase() {
        try {
            // Get database hook
            RecordsDatabase db = RecordsDatabase.getInstance(getApplicationContext());

            // Default location
            double lat = -33.868820;
            double lng = 151.209296;

            // If GPS location fetching succeeded.
            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }

            // Save new record to database,
            Record newRecord = new Record(nameInput.getText().toString(), score, lat, lng);
            newRecord.setDifficulty(mDifficulty);
            db.recordsDao().createRecord(newRecord);

            // Disable textfield and submit buttons.
            nameInput.setEnabled(false);
            submitScore.setEnabled(false);

            // Pop a success toast.
            Toast toastSavedSuccess = Toast.makeText(getApplicationContext(), "Record Saved!", Toast.LENGTH_LONG);
            toastSavedSuccess.show();
        } catch(Exception e) {

            Log.d("DB", e.toString());
            Toast toastFailedSave = Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG);
            toastFailedSave.show();
        }
    }

    /* ==========================================================================================================================
    *   Check for GPS access permission.
    *   True = GPS access granted.
    *   False = No GPS access permission.
    *  ========================================================================================================================== */
    private boolean checkGpsPermission() {
        return (!(ActivityCompat.checkSelfPermission(FinishGameActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(FinishGameActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
    }

    /* ==========================================================================================================================
    *   Get GPS location.
    *   Checks if permission is granted, and fetches last known location.
    *   Blocking task.
    *  ========================================================================================================================== */
    private void getLocationAndSaveToDatabase() {
        try {
            if(checkGpsPermission()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(FinishGameActivity.this, (OnCompleteListener<Location>) location -> {
                    if (location.getResult() != null) {
                        loc = location.getResult();
                    }
                    saveScoreToDatabase();
                });

            }
        } catch(Exception e) {}
    }

    /* ==========================================================================================================================
    *   Callback method for permission result events.
    *   If permission is granted, fetches location, and save to database.
    *   If permission is NOT granted, save to database without fetching location.
    *  ========================================================================================================================== */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
                Log.d("LOCATION", "Request Response");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationAndSaveToDatabase();
                }
                else
                    saveScoreToDatabase();
            }
        } catch (Exception e) {}
    }

    private void getDifficulty() {
        SharedPreferences sp = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        mDifficulty = sp.getString(DIFFICULTY,"");
        Log.d(TAG, "getDifficulty: value is : " +mDifficulty);
    }
}
