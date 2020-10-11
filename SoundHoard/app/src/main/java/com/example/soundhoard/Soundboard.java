package com.example.soundhoard;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Soundboard implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int soundboard_id;

    @ColumnInfo(name = "soundboard_name")
    public String soundboardName;

    public Soundboard() {
        setName("");
    }

    public Soundboard(String name) {
        setName(name);
    }

    public int getId() {
        return this.soundboard_id;
    }

    public String getName() {
        return this.soundboardName;
    }

    public void setName(String name) {
        this.soundboardName = name;
    }
}