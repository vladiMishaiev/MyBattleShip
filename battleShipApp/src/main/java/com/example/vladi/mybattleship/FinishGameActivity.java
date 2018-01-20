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
import com.google.android.gms.tasks.OnSuccessListener;

public class FinishGameActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        setContentView(R.layout.activity_finish_game);
        getScore();
        setImage();
        setReplayBtn();
        setHomeBtn();
        setSubmitBox();
    }

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

    private void setSubmitBox() {
        //set submit box visability
        LinearLayout recordBox = (LinearLayout) findViewById(R.id.submitRecordBox);
        if (score != 0)
            recordBox.setVisibility(LinearLayout.VISIBLE);
        else
            recordBox.setVisibility(LinearLayout.GONE);

        submitScore = (Button) findViewById(R.id.submitScoreBtn);
        nameInput = (EditText) findViewById(R.id.nameInput);

        //get box input
        submitScore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {

                    if (ActivityCompat.checkSelfPermission(FinishGameActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(FinishGameActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        // Request permission
                        ActivityCompat.requestPermissions(FinishGameActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        Log.d("LOCATION", "Request location permission");
                    } else {
                        Log.d("LOCATION", "Permission exist");
                        getLocationAndSaveToDatabase();
                    }


                } catch(Exception e) {}
            }
        });
    }
    private void getScore(){
        Bundle params = getIntent().getExtras();
        score = params.getDouble("score");
        Log.d(TAG, "getScore: "+score);
    }

    private void setImage(){
        outcomeImg = (ImageView)findViewById(R.id.outcomeImg);
        if (score!=0)
            outcomeImg.setImageResource(R.drawable.winner);
        else
            outcomeImg.setImageResource(R.drawable.looser);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            Log.d("LOCATION", "Request Response");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndSaveToDatabase();
            }
        }
    }
    private void saveScoreToDatabase() {
        try {
            RecordsDatabase db = RecordsDatabase.getInstance(getApplicationContext());
            double lat = -33.868820;
            double lng = 151.209296;
            if(loc != null) {
                lat = loc.getLatitude();
                lng = loc.getLongitude();
            }
            db.recordsDao().createRecord(new Record(nameInput.getText().toString(), score, lat, lng));
            nameInput.setEnabled(false);
            submitScore.setEnabled(false);
            Toast toastSavedSuccess = Toast.makeText(getApplicationContext(), "Record Saved!", Toast.LENGTH_LONG);
            toastSavedSuccess.show();
        } catch(Exception e) {
            Log.d("DB", e.toString());
            Toast toastFailedSave = Toast.makeText(getApplicationContext(), "Save failed!", Toast.LENGTH_LONG);
            toastFailedSave.show();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocationAndSaveToDatabase() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(FinishGameActivity.this, (OnSuccessListener<Location>) location -> {
            if (location != null) {
                loc = location;
            }
            saveScoreToDatabase();
        });
    }


}
