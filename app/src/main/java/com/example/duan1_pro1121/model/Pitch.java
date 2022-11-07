package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "PITCH",foreignKeys = @ForeignKey(entity = PithCategory.class,parentColumns = "id",childColumns = "categoryId",onDelete = CASCADE))
public class Pitch {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int categoryId;
    private int status;

    public Pitch() {
    }

    public Pitch(int id, String name, int categoryId, int status) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.status = status;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
