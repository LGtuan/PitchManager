package com.example.duan1_pro1121.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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

    @Query("SELECT * FROM ORDER_DETAILS WHERE orderId = :id")
    List<OrderDetails> getOrderDetailsWithId(int id);

    @Query("SELECT SERVICE.name,SUM(ORDER_DETAILS.tongTien) FROM SERVICE" +
            " INNER JOIN ORDER_DETAILS ON SERVICE.id = ORDER_DETAILS.serviceId" +
            " INNER JOIN ORDERS ON ORDER_DETAILS.orderId = ORDERS.id" +
            " WHERE ORDERS.dateCreate LIKE :month GROUP BY ORDER_DETAILS.serviceId")
    Cursor getInfoDoanhThuService(String month);

    @Query("SELECT SERVICE.name,COUNT(ORDER_DETAILS.serviceId) FROM SERVICE" +
            " INNER JOIN ORDER_DETAILS ON SERVICE.id = ORDER_DETAILS.serviceId" +
            " INNER JOIN ORDERS ON ORDER_DETAILS.orderId = ORDERS.id" +
            " WHERE ORDERS.dateCreate LIKE :month GROUP BY ORDER_DETAILS.serviceId")
    Cursor getInfoPopularService(String month);
}
