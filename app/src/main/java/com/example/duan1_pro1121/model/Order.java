package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "ORDERS",foreignKeys = {@ForeignKey(entity = Manager.class, parentColumns = "id", childColumns = "managerId", onDelete = CASCADE),
        @ForeignKey(entity = Customer.class,parentColumns = "id",childColumns = "customerId",onDelete = CASCADE),
        @ForeignKey(entity = Pitch.class,parentColumns = "id",childColumns = "pitchId",onDelete = CASCADE)})
public class Order {
    @PrimaryKey
    private int id;
    private int managerId;
    private int customerId;
    private int pitchId;
    private float startTime;
    private float endTime;
    private String date;
    private int totalPitchMoney;
    private int total;
    private int status;

    public Order() {
    }

    public Order(int id, int managerId, int customerId, int pitchId, int startTime, int endTime, String date,int totalPitchMoney, int total, int status) {
        this.id = id;
        this.managerId = managerId;
        this.customerId = customerId;
        this.pitchId = pitchId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.total = total;
        this.status = status;
        this.totalPitchMoney = totalPitchMoney;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPitchId() {
        return pitchId;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }

    public float getStartTime() {
        return startTime;
    }

    public void setStartTime(float startTime) {
        this.startTime = startTime;
    }

    public float getEndTime() {
        return endTime;
    }

    public void setEndTime(float endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalPitchMoney() {
        return totalPitchMoney;
    }

    public void setTotalPitchMoney(int totalPitchMoney) {
        this.totalPitchMoney = totalPitchMoney;
    }
}
