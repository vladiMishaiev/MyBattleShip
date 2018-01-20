package com.example.vladi.mybattleship;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
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
import com.google.android.gms.maps.model.LatLng;

public class FinishGameActivity extends AppCompatActivity {
    private static final String TAG = "FinishGameActivity";
    private ImageView outcomeImg;
    private double score;
    private Button replayBtn;
    private Button homeBtn;
    private Button submitScore;
    private EditText nameInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_game);
        getScore();
        setImage();
        setReplayBtn();
        setHomeBtn();
        setSubmitBox();
    }
    private void setReplayBtn(){
        replayBtn = (Button)findViewById(R.id.replayBtn);
        replayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishGameActivity.this,BattleActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void setHomeBtn(){
        homeBtn = (Button)findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinishGameActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setSubmitBox(){
        //set submit box visability
        LinearLayout recordBox = (LinearLayout)findViewById(R.id.submitRecordBox);
        //add load lowest record from db and check
        if (score!=0)
            recordBox.setVisibility(LinearLayout.VISIBLE);
        else
            recordBox.setVisibility(LinearLayout.GONE);


        submitScore = (Button)findViewById(R.id.submitScoreBtn);
        nameInput = (EditText)findViewById(R.id.nameInput);
        //get box input
        submitScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RecordsDatabase db = RecordsDatabase.getInstance(getApplicationContext());
                    Location loc = getLastBestLocation();
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
    private Location getLastBestLocation() {
        LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS;
        Location locationNet;
        try
        {
            locationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        long GPSLocationTime = 0;
        if (null != locationGPS) { GPSLocationTime = locationGPS.getTime(); }

        long NetLocationTime = 0;

        if (null != locationNet) {
            NetLocationTime = locationNet.getTime();
        }

        if ( 0 < GPSLocationTime - NetLocationTime ) {
            return locationGPS;
        }
        else {
            return locationNet;
        }
        } catch(Exception e) {Log.d("GPS", e.getMessage());}

        return null;
    }
}
