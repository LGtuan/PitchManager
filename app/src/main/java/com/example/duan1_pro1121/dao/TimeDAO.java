package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.MyTime;

import java.util.List;

@Dao
public interface TimeDAO {

    @Query("SELECT * FROM MyTime")
    List<MyTime> getAll();

    @Insert
    void insert(MyTime time);

    @Update
    void update(MyTime time);

    @Delete
    void delete(MyTime time);

    @Query("SELECT * FROM MyTime WHERE id = :id")
    List<MyTime> getTimeWithId(int id);

    @Query("SELECT * FROM MyTime " +
            "INNER JOIN TIMEORDERDETAILS " +
            "ON MyTime.id = TIMEORDERDETAILS.timeId " +
            "INNER JOIN ORDERS " +
            "ON TIMEORDERDETAILS.orderId = ORDERS.id WHERE ORDERS.id = :id ORDER BY ORDERS.id ASC")
    List<MyTime> getTimeWithOrderId(int id);
}
