package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.ServiceBall;

import java.util.List;

@Dao
public interface ServiceDAO {

    @Query("SELECT * FROM SERVICE")
    List<ServiceBall> getAll();

    @Insert
    void insert(ServiceBall serviceBall);

    @Delete
    void delete(ServiceBall serviceBall);

    @Update
    void update(ServiceBall serviceBall);

}
