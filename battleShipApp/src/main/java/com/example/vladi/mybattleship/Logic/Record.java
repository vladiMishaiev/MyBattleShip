package com.example.vladi.mybattleship.Logic;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by vladi on 1/5/2018.
 */

@Entity(tableName = "records")
public class Record implements Comparable<Record>{
    @PrimaryKey
    @ColumnInfo(name= "_name")
    @NonNull
    private String name;

    @ColumnInfo(name = "_score")
    private Double score;

    //@ColumnInfo(name = "_location")
    //private String location;

    @ColumnInfo(name = "_lat")
    private double lat;

    @ColumnInfo(name = "_lang")
    private double lang;

    @ColumnInfo(name = "_difficulty")
    private String difficulty;

    @Ignore
    public Record(String name, Double score) {
        this.name = name;
        this.score = score;
    }

    public Record(String name, Double score,double lat,double lang) {
        this.name = name;
        this.score = score;
        this.lat = lat;
        this.lang = lang;
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

    //public String getLocation() {
    //    return location;
    //}

    //public void setLocation(String location) {
    //    this.location = location;
    //}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return name + "|" + score + "|" + difficulty;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.score == ((Record)obj).score)
            return true;
        return false;
    }

    @Override
    public int compareTo(@NonNull Record o) {
        if (this.score > o.score)
            return -1;
        if (this.score < o.score)
            return 1;
        return 0;
    }
}
