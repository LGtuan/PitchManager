package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Order.class,parentColumns = "id",childColumns = "orderId",onDelete = CASCADE),
                        @ForeignKey(entity = MyTime.class,parentColumns = "id",childColumns = "timeId",onDelete = CASCADE)})
public class TimeOrderDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderId;
    private int timeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
}
