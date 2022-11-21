package com.example.duan1_pro1121.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.OrderDetails;
import com.example.duan1_pro1121.model.statistical.ServicePopular;

import java.util.HashMap;
import java.util.List;

@Dao
public interface OrderDetailsDAO {

    @Query("SELECT * FROM ORDER_DETAILS")
    List<OrderDetails> getAll();

    @Insert
    void insert(OrderDetails details);

    @Delete
    void delete(OrderDetails details);

    @Update
    void update(OrderDetails details);

    @Query("DELETE FROM ORDER_DETAILS WHERE orderId = :orderId")
    void deleteWithOrderId(int orderId);

    @Query("SELECT * FROM ORDER_DETAILS WHERE orderId = :orderId")
    List<OrderDetails> getOrderDetailsByOrderId(int orderId);

    @Query("SELECT * FROM ORDER_DETAILS WHERE orderId = :id")
    List<OrderDetails> getOrderDetailsWithId(int id);

    @Query("SELECT * FROM ORDER_DETAILS INNER JOIN ORDERS ON ORDER_DETAILS.orderId = ORDERS.id WHERE ORDERS.dateCreate LIKE :month GROUP BY ORDER_DETAILS.serviceId")
    List<OrderDetails> getOrderDetailsWithTime(String month);

    @Query("SELECT ORDER_DETAILS.serviceId,COUNT(ORDER_DETAILS.serviceId) FROM ORDER_DETAILS INNER JOIN ORDERS ON ORDER_DETAILS.orderId = ORDERS.id WHERE ORDERS.dateCreate LIKE :month GROUP BY ORDER_DETAILS.serviceId")
    Cursor getInfoServiceWithDate(String month);
}
