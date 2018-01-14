package com.example.vladi.mybattleship.DAL;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.vladi.mybattleship.Logic.Record;

/**
 * Created by Max on 13/01/2018.
 */

@Database(entities = {Record.class}, version = 3)
public abstract class RecordsDatabase extends RoomDatabase {
    public final static String DB_NAME = "mybattleship.db";
    public abstract RecordsDao recordsDao();
    private static RecordsDatabase instance;
//    private static Migration migration1 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//
//        }
//    }

    public static RecordsDatabase getInstance(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), RecordsDatabase.class, DB_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        return instance;
    }


    public static void destroyInstance() {
        instance = null;
    }
}
