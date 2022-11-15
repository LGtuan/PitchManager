package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM ORDERS")
    List<Order> getAll();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM ORDERS WHERE pitchId = :pitchId AND date = :date")
    List<Order> getOrderWithPitchAndDate(int pitchId,String date);

    @Query("SELECT MAX(id) FROM ORDERS")
    int getIdMax();
}
