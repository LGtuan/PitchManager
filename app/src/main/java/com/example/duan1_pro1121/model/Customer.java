package com.example.duan1_pro1121.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CUSTOMER")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String phone;
    private String name;
    private String address;
    private int coin;
    private String password;
    private String cmnd;
    private byte[] img;

    public Customer() {
    }

    public Customer(int id, String name, String phone, String address, int coin, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.coin = coin;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }
}
