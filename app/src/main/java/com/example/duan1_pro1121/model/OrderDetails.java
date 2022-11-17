package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ORDER_DETAILS",
        foreignKeys = {@ForeignKey(entity = Order.class,parentColumns = "id",childColumns = "orderId",onDelete = CASCADE),
                        @ForeignKey(entity = ServiceBall.class,parentColumns = "id",childColumns = "serviceId",onDelete = CASCADE)})
public class OrderDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int orderId;
    private int serviceId;
    private int soLuong;
    private int tongTien;

    public OrderDetails() {
    }

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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }
}
