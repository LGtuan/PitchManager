package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;

import java.util.List;

@Dao
public interface ManagerDAO {

    @Query("SELECT * FROM MANAGER")
    List<Manager> getAll();

    @Insert
    void insert(Manager manager);

    @Delete
    void delete(Manager manager);

    @Update
    void update(Manager manager);
    @Query("SELECT * FROM MANAGER WHERE PHONE = :phone AND ID != :id")
    List<Manager> getManagerWithPhone(String phone,int id);

    @Query("SELECT * FROM MANAGER WHERE NAME LIKE :name")
    List<Manager> getManagerWithName(String name);

    @Query("SELECT * FROM MANAGER WHERE NAME LIKE :id")
    List<Manager> getManagerWithID(int id);
    @Query("DELETE FROM MANAGER")
    void deleteAll();
}
