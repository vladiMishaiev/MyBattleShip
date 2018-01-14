package com.example.vladi.mybattleship.DAL;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.vladi.mybattleship.Logic.Record;

/**
 * Created by Max on 13/01/2018.
 */

@Database(entities = {Record.class}, version = 1)
public abstract class RecordsDatabase extends RoomDatabase {
    public final static String DB_NAME = "mybattleship.db";
    public abstract RecordsDao recordsDao();
    private static RecordsDatabase instance;

    public static RecordsDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), RecordsDatabase.class, DB_NAME).build();
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
