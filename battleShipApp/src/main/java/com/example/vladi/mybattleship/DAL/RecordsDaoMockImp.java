package com.example.vladi.mybattleship.DAL;

import com.example.vladi.mybattleship.Logic.Record;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by vladi on 1/5/2018.
 */

public class RecordsDaoMockImp implements RecordsDao {
    private static ArrayList<Record> records = new ArrayList<>();

    @Override
    public boolean createRecord(Record record) {
        records.add(record);
        return false;
    }

    @Override
    public Record retrieveRecord(String name,String difficulty) {
        Iterator<Record> itr = records.iterator();
        while (itr.hasNext()){
            Record temp = itr.next();
            if (temp.getName()==name)
                return temp;
        }
        return null;
    }

    @Override
    public boolean updateRecord(Record record) {
        return false;
    }

    @Override
    public boolean deleteRecord(String name,String difficulty) {
        return false;
    }

    @Override
    public double getMinScoreByDifficulty(String difficulty) {
        return 0;
    }

    @Override
    public double getMaxScoreByDifficulty(String difficulty) {
        return 0;
    }
}
