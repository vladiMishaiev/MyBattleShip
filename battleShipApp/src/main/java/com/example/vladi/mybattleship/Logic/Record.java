package com.example.vladi.mybattleship.Logic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by vladi on 1/5/2018.
 */

@Entity(tableName = "records")
public class Record {
    @PrimaryKey
    @ColumnInfo(name= "_name")
    @NonNull
    private String name;

    @ColumnInfo(name = "_score")
    private Double score;

    @ColumnInfo(name = "_location")
    private String location;

    @ColumnInfo(name = "_difficulty")
    private String difficulty;

    public Record(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return name + "|" + score + "|" + location + "|" + difficulty;
    }
}
