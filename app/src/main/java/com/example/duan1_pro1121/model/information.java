package com.example.duan1_pro1121.model;

public class information {
    private int id;
    private int managerId;
    private int customerId;
    private int pitchId;
    private String startTime;
    private String endTime;
    private String date;
    private int total;
    private int status;

    public information(int id, int managerId, int customerId, int pitchId, String startTime, String endTime, String date, int total, int status) {
        this.id = id;
        this.managerId = managerId;
        this.customerId = customerId;
        this.pitchId = pitchId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.total = total;
        this.status = status;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
