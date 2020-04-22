package com.model;

public class Vehicle {
    private int hour;
    private String vehicleId;
    private String name;

    public Vehicle(String id, String name) {
        this.vehicleId = id;
        this.name = name;

    }

    public Vehicle(String id, String name,int hour) {
        this.vehicleId = id;
        this.name = name;
        this.hour = hour;
    }
    public int getHour() {
        return hour;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getName() {
        return name;
    }
}
