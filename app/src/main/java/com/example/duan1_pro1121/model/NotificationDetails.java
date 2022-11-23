package com.example.duan1_pro1121.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = MyNotification.class,parentColumns = "id",childColumns = "notificationId")
                    ,@ForeignKey(entity = Customer.class,parentColumns = "id",childColumns = "customerId")})
public class NotificationDetails {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int customerId;
    private int notificationId;

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

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
}
