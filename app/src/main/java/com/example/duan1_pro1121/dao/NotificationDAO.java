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

    @Query("SELECT * FROM MY_NOTIFICATION ORDER BY MY_NOTIFICATION.id DESC")
    List<MyNotification> getALl();

//    @Query("SELECT * FROM MY_NOTIFICATION WHERE customerId = :id")
//    List<MyNotification> getAllWithIdCus(int id);

    @Update
    void update(MyNotification notification);

    @Insert
    void insert(MyNotification notification);

    @Delete
    void delete(MyNotification notification);

    @Query("SELECT * FROM MY_NOTIFICATION " +
            "INNER JOIN NOTIFICATIONDETAILS " +
            "ON MY_NOTIFICATION.id = NOTIFICATIONDETAILS.notificationId " +
            "INNER JOIN CUSTOMER " +
            "ON NOTIFICATIONDETAILS.customerId = CUSTOMER.id " +
            "WHERE CUSTOMER.id = :id ORDER BY MY_NOTIFICATION.id DESC")
    List<MyNotification> getNotifiWithCusId(int id);

    @Query("SELECT MAX(id) FROM MY_NOTIFICATION")
    int getNewNotification();
}
