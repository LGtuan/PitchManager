package com.example.duan1_pro1121.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duan1_pro1121.model.Customer;

import java.util.List;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM CUSTOMER")
    List<Customer> getAll();

    @Insert
    void insert(Customer customer);

    @Delete
    void delete(Customer customer);

    @Update
    void update(Customer customer);

    @Query("SELECT * FROM CUSTOMER WHERE PHONE = :phone AND ID != :id")
    List<Customer> getCustomerWithPhone(String phone,int id);

    @Query("SELECT * FROM CUSTOMER WHERE name LIKE :name")
    List<Customer> getCustomerWithName(String name);

    @Query("SELECT * FROM CUSTOMER WHERE id LIKE :id")
    List<Customer> getCustomerWithID(int id);

    @Query("SELECT id FROM CUSTOMER")
    Cursor getAllId();
}
