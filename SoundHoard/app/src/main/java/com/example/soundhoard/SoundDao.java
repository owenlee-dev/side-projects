package com.example.soundhoard;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SoundDao {
    @Query("SELECT * FROM Sound")
    List<Sound> getAll();

    @Query("SELECT * FROM Sound WHERE favorite_status = 1")
    List<Sound> getAllFavorites();

    @Query("SELECT * FROM Sound WHERE sound_id = (:id) LIMIT 1")
    Sound loadById(int id);

    @Query("SELECT * FROM Sound WHERE sound_id IN (:soundIds)")
    List<Sound> loadAllByIds(int[] soundIds);

    @Query("UPDATE Sound SET sound_name = (:newSoundName) WHERE sound_id = (:id)")
    void updateNameById(String newSoundName, int id);

    @Query("UPDATE Sound SET sound_uri = (:newSoundUri) WHERE sound_id = (:id)")
    void updateUriById(String newSoundUri, int id);

    @Query("UPDATE Sound SET favorite_status = (:value) WHERE sound_id = (:id)")
    void updateFavoriteStatusById(int value, int id);

    @Query("DELETE FROM Sound WHERE sound_id = (:id)")
    void deleteById(int id);

    @Insert
    long insert(Sound sound);

    @Delete
    void delete(Sound sound);
}