package com.example.duan1_pro1121.dao;

import android.database.Cursor;

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

    @Query("SELECT * FROM PITCH_CATEGORY WHERE id = :id")
    List<PithCategory> getCategoryPitchWithId(int id);

    @Query("SELECT PITCH_CATEGORY.NAME,COUNT(PITCH_CATEGORY.id) FROM PITCH_CATEGORY " +
            "INNER JOIN PITCH ON PITCH_CATEGORY.id = PITCH.categoryId " +
            "INNER JOIN ORDERS ON ORDERS.pitchId = PITCH.id " +
            "WHERE ORDERS.dateCreate LIKE :date GROUP BY PITCH_CATEGORY.id")
    Cursor getPitchCategoryWithTime(String date);
}
