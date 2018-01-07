package com.example.vladi.mybattleship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        if (score!=0)
            recordBox.setVisibility(LinearLayout.VISIBLE);
        else
            recordBox.setVisibility(LinearLayout.GONE);


        submitScore = (Button)findViewById(R.id.submitScoreBtn);
        nameInput = (EditText)findViewById(R.id.nameInput);
        //get box input
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
}
