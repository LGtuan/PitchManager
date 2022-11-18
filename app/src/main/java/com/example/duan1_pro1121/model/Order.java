package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "ORDERS",foreignKeys = {@ForeignKey(entity = Customer.class,parentColumns = "id",childColumns = "customerId",onDelete = CASCADE),
        @ForeignKey(entity = Pitch.class,parentColumns = "id",childColumns = "pitchId",onDelete = CASCADE)})
public class Order implements Serializable {
    @PrimaryKey
    private int id;
    private int customerId;
    private int pitchId;
    private String dateCreate;
    private String datePlay;
    private int totalPitchMoney;
    private int total;
    private int status;
    private int soCa;
    private int soLanCapNhat = 1;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
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

    public String getDatePlay() {
        return datePlay;
    }

    public void setDatePlay(String datePlay) {
        this.datePlay = datePlay;
    }

    public int getSoCa() {
        return soCa;
    }

    public void setSoCa(int soCa) {
        this.soCa = soCa;
    }

    public int getSoLanCapNhat() {
        return soLanCapNhat;
    }

    public void setSoLanCapNhat(int soLanCapNhat) {
        this.soLanCapNhat = soLanCapNhat;
    }
}
