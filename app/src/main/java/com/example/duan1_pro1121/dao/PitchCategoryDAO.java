package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.PithCategory;

import java.util.List;

@Dao
public interface PitchCategoryDAO {

    @Query("SELECT * FROM PITCH_CATEGORY")
    List<PithCategory> getAll();

    @Insert
    void insert(PithCategory category);

    @Delete
    void delete(PithCategory category);

    @Update
    void update(PithCategory category);

}
