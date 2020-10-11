package com.example.soundhoard;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SoundboardDao {
    @Query("SELECT * FROM Soundboard")
    List<Soundboard> getAll();

    @Query("SELECT * FROM Soundboard WHERE soundboard_id = (:id) LIMIT 1")
    Soundboard loadById(int id);

    @Query("SELECT * FROM Soundboard WHERE soundboard_id IN (:soundboardIds)")
    List<Soundboard> loadAllByIds(int[] soundboardIds);

    @Query("SELECT * FROM Soundboard WHERE soundboard_name LIKE (:soundboardName) LIMIT 1")
    Soundboard findByName(String soundboardName);

    @Query("UPDATE Soundboard SET soundboard_name = (:newSoundboardName) WHERE soundboard_id = (:id)")
    void updateNameById(String newSoundboardName, int id);

    @Query("DELETE FROM Soundboard WHERE soundboard_id = (:id)")
    void deleteById(int id);

    @Insert
    void insertAll(Soundboard... soundboards);

    @Delete
    void delete(Soundboard soundboard);
}