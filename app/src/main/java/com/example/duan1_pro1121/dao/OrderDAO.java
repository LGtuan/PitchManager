package com.example.duan1_pro1121.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.ManagerCategory;
import com.example.duan1_pro1121.model.Order;
import com.example.duan1_pro1121.model.Pitch;

import java.util.ArrayList;
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

    @Query("SELECT SUM(total) FROM ORDERS WHERE dateCreate LIKE :date")
    int getDoanhThuWithDate(String date);

    @Query("SELECT PITCH.name,SUM(ORDERS.totalPitchMoney) FROM PITCH " +
            "INNER JOIN ORDERS ON PITCH.id = ORDERS.pitchId " +
            "WHERE ORDERS.dateCreate LIKE :date GROUP BY ORDERS.pitchId")
    Cursor getDoanhThuPitch(String date);

    @Query("SELECT PITCH.name,COUNT(ORDERS.pitchId) FROM PITCH " +
            "INNER JOIN ORDERS ON PITCH.id = ORDERS.pitchId " +
            "WHERE ORDERS.dateCreate LIKE :date GROUP BY ORDERS.pitchId")
    Cursor getPopularPitch(String date);
    
    @Query("SELECT * FROM ORDERS WHERE customerId == :customerId ORDER BY id DESC")
    List<Order> getOrderWithCustomerId(int customerId);

    @Query("SELECT * FROM ORDERS WHERE customerId == :id AND status =:status ORDER BY id DESC")
    List<Order> getOrderWithCustomerIdAndStatus(int id, int status);

    @Query("SELECT * FROM ORDERS WHERE managerId == :id AND dateCreate LIKE :date AND ORDERS.status != :status ORDER BY ORDERS.id DESC")
    List<Order> getOrderWithManagerId(int id,String date,int status);

    @Query("SELECT ORDERS.managerId,(SUM(ORDERS.total)/100)*3 FROM ORDERS " +
            "INNER JOIN MANAGER ON ORDERS.managerId = MANAGER.id " +
            "WHERE dateCreate LIKE :s AND MANAGER.phone != :account AND ORDERS.status != :status GROUP BY ORDERS.managerId ORDER BY ORDERS.id DESC")
    Cursor getDoanhThuStaff(String s,String account,int status);

    @Query("SELECT SUM(totalServiceMoney) FROM ORDERS WHERE dateCreate LIKE :s AND ORDERS.status != :status")
    Cursor getDoanhThuService(String s,int status);

    @Query("SELECT SUM(totalPitchMoney)+SUM(chiPhiKhac) FROM ORDERS WHERE dateCreate LIKE :s AND ORDERS.status != :status")
    Cursor getDoanhThuSanBong(String s,int status);

}
