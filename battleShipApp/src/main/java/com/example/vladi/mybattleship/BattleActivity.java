package com.example.vladi.mybattleship;
/**
 * Created by vladi on 12/30/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vladi.mybattleship.Logic.Game;

public class BattleActivity extends AppCompatActivity implements SensorEventListener {
    private final static String FILE = "appInfo";
    private static final String TAG = "BattleActivity";
    private final static String DIFFICULTY = "difficulty";
    private GridView mGrid;
    private String mDifficulty;
    private Game mGame;
    private TileAdapter playerAdapter;
    private TileAdapter computerAdapter;
    private Button dfnBoardBtn;
    private Button atkBoardBtn;
    private TextView statusText;
    private Toast toast;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int tiltCounter = 0;
    private boolean tiltedRigth = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Log.d(TAG, "onCreate: started");

        getDifficulty();
        setGame();
        setToggleBtns();
        setGridEvent();
        initSensorService();
    }
    private void initSensorService(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    private void getDifficulty() {
        SharedPreferences sp = getSharedPreferences(FILE, Context.MODE_PRIVATE);
        mDifficulty = sp.getString(DIFFICULTY,"");
        Log.d(TAG, "getDifficulty: value is : " +mDifficulty);
    }
    private void setGame() {
        mGame = new Game(mDifficulty);
        statusText = (TextView)findViewById(R.id.statusLable);
        updateStatus();
        //mGame.getPlayerBoard().getTile(1,1).setEmptySlot(false);
        mGrid = (GridView) findViewById(R.id.boardGridView);
        playerAdapter = new TileAdapter(this,mGame.getPlayerBoard());
        computerAdapter = new TileAdapter(this,mGame.getComputerBoard());;
        mGrid.setAdapter(playerAdapter);
        mGrid.setNumColumns(mGame.getPlayerBoard().getCols());
    }

    public void setToggleBtns(){
        dfnBoardBtn = (Button)findViewById(R.id.dfnBoardBtn);
        atkBoardBtn = (Button)findViewById(R.id.atkBoardBtn);

        dfnBoardBtn.setOnClickListener((view)->{
            if (mGrid.getAdapter() != computerAdapter) {
                mGrid.setAdapter(computerAdapter);
                Log.d(TAG, "setToggleBtns: playerAdapter set");
            }
        });
        atkBoardBtn.setOnClickListener((view)->{
            if (mGrid.getAdapter() != playerAdapter) {
                mGrid.setAdapter(playerAdapter);
                Log.d(TAG, "setToggleBtns: computerAdapter set");
            }
        });
    }

    private void setGridEvent(){
        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (mGame.isPlayerTurn() && mGrid.getAdapter()==computerAdapter) {
                    int row = position / mGame.getComputerBoard().getCols();
                    int col = position % mGame.getComputerBoard().getCols();
                    if (mGame.playerPlay(row, col)) {
                        Log.d(TAG, "onItemClick: player played");
                        ((TileAdapter) mGrid.getAdapter()).notifyDataSetChanged();
                        //if ship destroyed show toast msg
                        if (mGame.getComputerBoard().getTile(row,col).getShip()!= null &&
                                mGame.getComputerBoard().getTile(row,col).getShip().isDestroyed()) {
                            setToast("Enemy ship destroyed");
                        }
                        //update status view
                        updateStatus();
                        //check for winner
                        if (mGame.gameStatus() != 0)
                            endGame();
                        Log.d(TAG, "onItemClick: entering computer play");
                        while (!mGame.isPlayerTurn()) {
                        //if (!mGame.isPlayerTurn()){
                            //computer play
                            mGame.computerPlay();
                            Log.e(TAG, "computer played");
                            //notify player adapter after computer play
                            playerAdapter.notifyDataSetChanged();
                            //if ship destroyed show toast msg
                            if (mGame.getPlayerBoard().getTile(row, col).getShip() != null &&
                                    mGame.getPlayerBoard().getTile(row, col).getShip().isDestroyed()) {
                                setToast("Player ship destroyed");
                            }
                            //update status view
                            updateStatus();
                            //check for winner
                            if (mGame.gameStatus() != 0)
                                endGame();
                        }
                    }
                }
            }
        });
    }
    private void endGame(){
        Intent intent = new Intent(BattleActivity.this,FinishGameActivity.class);
        Bundle params = new Bundle();
        if (mGame.gameStatus()==1)
            params.putDouble("score",mGame.getScore(true));
        else
            params.putDouble("score",0.0);
        intent.putExtras(params);
        startActivity(intent);
        finish();
    }
    private void setToast(String msg){
        toast = Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void updateStatus (){
        String statusMsg = "Player ships: "+ mGame.getPlayerShipsLeft() +
                " - Enemy ships: "+mGame.getComputerShipsLeft()+"    Moves: " +mGame.getMoves();
        statusText.setText(statusMsg);
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister Sensor listener
        sensorManager.unregisterListener(this);
    }

    private void moveShips(){
        Log.d(TAG, "moveShips: ships moved");
        playerAdapter.notifyDataSetChanged();
    }
    
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];


        if (x>4){
            if (!tiltedRigth) {
                tiltCounter = 0;
                tiltedRigth = true;
            }
            tiltCounter++;
        }else if (x<-4){
            if (tiltedRigth) {
                tiltCounter = 0;
                tiltedRigth = false;
            }
            tiltCounter++;
        }
        if (tiltCounter>=25){
            moveShips();
            tiltCounter=0;
        }
        /*float y = event.values[1];
        if (Math.abs(x) > Math.abs(y)) {
            if (x < 0) {
                Log.d(TAG, "onSensorChanged: You tilt the device right  x = " + x + " y= "+y);
            }
            if (x > 0) {
                Log.d(TAG, "onSensorChanged: You tilt the device left  x = " + x + " y= "+y);
            }
        } else {
            if (y < 0) {
                Log.d(TAG, "onSensorChanged: You tilt the device up  x = " + x + " y= "+y);
            }
            if (y > 0) {
                Log.d(TAG, "onSensorChanged: You tilt the device down  x = " + x + " y= "+y);

            }
        }
        if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
            Log.d(TAG, "onSensorChanged: Not tilt device  x = " + x + " y= "+y);
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
