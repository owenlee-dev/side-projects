package com.example.soundhoard;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SoundboardHasSound {
    @PrimaryKey(autoGenerate = true)
    public int soundboard_has_sound_id;

    @ColumnInfo(name = "soundboard_id")
    public int soundboardId;

    @ColumnInfo(name = "sound_id")
    public int soundId;

    public SoundboardHasSound(int soundboardId, int soundId) {
        this.soundboardId = soundboardId;
        this.soundId = soundId;
    }
}