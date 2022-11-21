package com.example.duan1_pro1121.model.statistical;

public class ServicePopular {
    private int serviceId;
    private int count;

    public ServicePopular(int serviceId, int count) {
        this.serviceId = serviceId;
        this.count = count;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
