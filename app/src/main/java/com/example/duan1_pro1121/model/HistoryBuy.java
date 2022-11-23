package com.example.duan1_pro1121.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HistoryBuy {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idCustomer;
    private int money;
    private int status;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
