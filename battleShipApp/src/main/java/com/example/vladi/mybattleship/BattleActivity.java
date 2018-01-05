package com.example.vladi.mybattleship;
/**
 * Created by vladi on 12/30/2017.
 */
import android.content.Context;
import android.content.SharedPreferences;
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

public class BattleActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);
        Log.d(TAG, "onCreate: started");

        getDifficulty();
        setGame();
        setToggleBtns();
        setGridEvent();
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
                if (mGrid.getAdapter()==computerAdapter) {
                    int row = position / mGame.getComputerBoard().getCols();
                    int col = position % mGame.getComputerBoard().getCols();
                    if (mGame.playerPlay(row, col)) {
                        ((TileAdapter) mGrid.getAdapter()).notifyDataSetChanged();
                        //if ship destroyed show toast msg
                        if (mGame.getComputerBoard().getTile(row,col).getShip()!= null &&
                                mGame.getComputerBoard().getTile(row,col).getShip().isDestroyed()) {
                            setToast("Enemy ship destroyed");
                        }
                        //update status view
                        updateStatus();
                        //check for winner
                        if (mGame.gameStatus() == 1)
                            endGame();
                        //computer play
                        mGame.computerPlay();
                        //notify player adapter after computer play
                        playerAdapter.notifyDataSetChanged();
                        //if ship destroyed show toast msg
                        if (mGame.getPlayerBoard().getTile(row,col).getShip()!=null &&
                                mGame.getPlayerBoard().getTile(row,col).getShip().isDestroyed()) {
                           setToast("Player ship destroyed");
                        }
                        //update status view
                        updateStatus();
                        //check for winner
                        if (mGame.gameStatus() == 1)
                            endGame();
                    }
                }
            }
        });
    }
    private void endGame(){
        //go to end screen
        //pass winner?
        //score
        //block back key
    }
    private void setToast(String msg){
        toast = Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void updateStatus (){
        String statusMsg = "Player ships: "+ mGame.getPlayerShipsLeft() +
                " - Enemy ships: "+mGame.getComputerShipsLeft()+"    Moves: " +mGame.getScore();
        statusText.setText(statusMsg);
    }
}
