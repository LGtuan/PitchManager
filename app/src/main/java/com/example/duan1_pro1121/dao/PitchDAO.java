package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Pitch;

import java.util.List;

@Dao
public interface PitchDAO {

    @Query("SELECT * FROM PITCH")
    List<Pitch> getAll();

    @Insert
    void insert(Pitch pitch);

    @Delete
    void delete(Pitch pitch);

    @Update
    void update(Pitch pitch);

}
