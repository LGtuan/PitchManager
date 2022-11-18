package com.example.duan1_pro1121.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Manager;
import com.example.duan1_pro1121.model.OrderDetails;

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

}
