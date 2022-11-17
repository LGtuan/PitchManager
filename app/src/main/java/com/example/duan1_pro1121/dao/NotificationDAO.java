package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.MyNotification;

import java.util.List;

@Dao
public interface NotificationDAO {

    @Query("SELECT * FROM MY_NOTIFICATION")
    List<MyNotification> getALl();

    @Query("SELECT * FROM MY_NOTIFICATION WHERE customerId = :id")
    List<MyNotification> getAllWithIdCus(int id);

    @Update
    void update(MyNotification notification);

    @Insert
    void insert(MyNotification notification);

    @Delete
    void delete(MyNotification notification);

}
