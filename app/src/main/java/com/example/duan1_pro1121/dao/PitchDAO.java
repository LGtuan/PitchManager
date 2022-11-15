package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Pitch;
import com.example.duan1_pro1121.model.PithCategory;

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

    @Query("SELECT * FROM PITCH WHERE name = :name")
    List<Pitch> getPitchWithName(String name);

    @Query("SELECT * FROM PITCH WHERE categoryId = :categoryId")
    List<Pitch> getPitchWithCategoryId(int categoryId);
    @Query("SELECT * FROM PITCH WHERE id = :id")
    List<Pitch> getPitchId(int id);
}
