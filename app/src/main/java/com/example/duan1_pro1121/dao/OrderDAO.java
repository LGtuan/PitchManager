package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.ManagerCategory;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

import java.util.List;

@Dao
public interface OrderDAO {

    @Query("SELECT * FROM ORDERS ORDER BY id DESC")
    List<Order> getAll();

    @Insert
    void insert(Order order);

    @Delete
    void delete(Order order);

    @Update
    void update(Order order);

    @Query("SELECT * FROM ORDERS WHERE pitchId = :pitchId AND datePlay = :date")
    List<Order> getOrderWithPitchAndDate(int pitchId,String date);

//    @Query("SELECT * FROM ORDERS WHERE pitchId = :pitchId AND datePlay = :date AND startTime != :startTime")
//    List<Order> getOrderWithPitchAndDate(int pitchId,String date,int startTime);

    @Query("SELECT MAX(id) FROM ORDERS")
    int getIdMax();
    
    @Query("SELECT * FROM ORDERS WHERE ID = :id")
    List<Order> getOrderWithID(int id);

    @Query("DELETE FROM ORDERS")
    void deleteAll();
}
