package com.example.vladi.mybattleship.DAL;

import com.example.vladi.mybattleship.Logic.Record;

/**
 * Created by vladi on 1/5/2018.
 */

public interface RecordsDao {

    public boolean createRecord (Record record);

    public Record retrieveRecord (String name,String difficulty);

    public boolean updateRecord (Record record);

    public boolean deleteRecord (String name,String difficulty);

    public double getMinScoreByDifficulty(String difficulty);

    public double getMaxScoreByDifficulty(String difficulty);

}
