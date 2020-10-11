package com.example.soundhoard;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SoundboardHasSoundDao {
    @Query("SELECT * FROM SoundboardHasSound WHERE soundboard_id = (:soundboardId)")
    List<SoundboardHasSound> loadAllBySoundboardId(int soundboardId);

    @Query("SELECT * FROM SoundboardHasSound WHERE sound_id = (:soundId) LIMIT 1")
    SoundboardHasSound loadBySoundId(int soundId);

    @Query("DELETE FROM SoundboardHasSound WHERE soundboard_id = (:soundboardId)")
    void deleteBySoundboardId(int soundboardId);

    @Insert
    void insertAll(SoundboardHasSound... soundboardHasSounds);

    @Delete
    void delete(SoundboardHasSound soundboardHasSound);
}