package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.ManagerCategory;

import java.util.List;

@Dao
public interface ManagerCategoryDAO {
    @Query("SELECT * FROM MANAGER_CATEGORY")
    List<ManagerCategory> getAll();

    @Query("SELECT * FROM MANAGER_CATEGORY WHERE NAME != 'admin'")
    List<ManagerCategory> getAllStaff();

    @Insert
    void insert(ManagerCategory managerCategory);

    @Delete
    void delete(ManagerCategory managerCategory);

    @Update
    void update(ManagerCategory managerCategory);

    @Query("SELECT * FROM MANAGER_CATEGORY WHERE ID = :id")
    List<ManagerCategory> getCategoryWithID(int id);
}
