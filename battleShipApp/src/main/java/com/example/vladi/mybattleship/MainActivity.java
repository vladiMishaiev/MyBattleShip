package com.example.vladi.mybattleship;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.vladi.mybattleship.Logic.Record;

public class MainActivity extends AppCompatActivity {
    private final static String DIFFICULTY = "difficulty";
    private final static String FILE = "appInfo";
    private static final String TAG = "MainActivity";
    private Spinner mSpinner;
    private Button btnNewGame;
    private Button recordsBtn;
    private String mDifficulty;
    private ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");
        setSpinner();
        setNewGameBtn();
        setRecordsBtn();
    }

    private void setSpinner(){
        mSpinner = (Spinner) findViewById(R.id.dificultySpinner);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    private void setNewGameBtn()
    {
        btnNewGame =(Button)findViewById(R.id.startBtn);
        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences(FILE, Context.MODE_PRIVATE);
                mDifficulty = mSpinner.getSelectedItem().toString();
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(DIFFICULTY,mDifficulty);
                editor.apply();
                Intent intent = new Intent(MainActivity.this,BattleActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setRecordsBtn(){
        recordsBtn =(Button)findViewById(R.id.recordsBtn);
        recordsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordsActivity.class);
                startActivity(intent);
            }
        });
    }
}
