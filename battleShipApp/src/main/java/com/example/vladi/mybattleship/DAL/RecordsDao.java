package com.example.vladi.mybattleship.DAL;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.vladi.mybattleship.Logic.Record;

import java.util.List;

/**
 * Created by vladi on 1/5/2018.
 */

@Dao
public interface RecordsDao {

    @Insert
    public void createRecord (Record record);

    @Query("SELECT * FROM records WHERE _name LIKE :name AND _difficulty LIKE :difficulty")
    public Record retrieveRecord (String name, String difficulty);

    @Update
    public void updateRecord (Record record);

    @Delete
    public void deleteRecord (Record record);

    @Query("DELETE FROM records WHERE _score LIKE :score AND _difficulty LIKE :difficulty")
    public void deleteRecordByScore (double score,String difficulty);

    @Query("SELECT * FROM records WHERE _difficulty LIKE :difficulty")
    public List<Record> getAllRecords (String difficulty);

    @Query("SELECT * FROM records")
    public List<Record> getAllRecords ();

    @Query("SELECT MIN(_score) FROM records WHERE _difficulty LIKE :difficulty")
    public double getMinScoreByDifficulty(String difficulty);

    @Query("SELECT MAX(_score) FROM records WHERE _difficulty LIKE :difficulty")
    public double getMaxScoreByDifficulty(String difficulty);

}
