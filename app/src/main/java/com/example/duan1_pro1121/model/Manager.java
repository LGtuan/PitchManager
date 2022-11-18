package com.example.duan1_pro1121.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "MANAGER",
        foreignKeys = @ForeignKey(entity = ManagerCategory.class,
                parentColumns = "id",
                childColumns = "category_id",onDelete = CASCADE))
public class Manager {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String phone;
    private String name;
    private int category_id;
    private String password;
    private int salary;
    private String bankNumber;
    private String bankName;
    private int status;

    public Manager() {
    }

    public Manager(int id, String name, int category_id, String password, int salary) {
        this.id = id;
        this.name = name;
        this.category_id = category_id;
        this.password = password;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return "position";
    }

    public void setPosition(int category_id) {
        this.category_id = category_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
