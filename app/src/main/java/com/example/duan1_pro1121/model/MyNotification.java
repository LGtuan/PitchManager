package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "MY_NOTIFICATION",foreignKeys =
@ForeignKey(entity = Customer.class,parentColumns = "id",childColumns = "customerId", onDelete = CASCADE))
public class MyNotification {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customerId;
    private String title;
    private String content;
    private String date;
    private String toDate;
    byte[] img;

    public MyNotification(){

    }

    public MyNotification(int id, int customerId, String title, String content, String date, String toDate, byte[] img) {
        this.id = id;
        this.customerId = customerId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.toDate = toDate;
        this.img = img;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
