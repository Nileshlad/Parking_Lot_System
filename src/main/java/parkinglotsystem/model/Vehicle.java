package parkinglotsystem.model;

import parkinglotsystem.enumclass.VehicleSize;

import java.awt.*;
import java.sql.Driver;

public class Vehicle {

    private final Color color;
    private Driver driver;
    private String vehicleId;
    private String name;
    private VehicleSize vehicleType;

    public Vehicle(String id, String name, Driver driver, VehicleSize vehicleType, Color color) {
        this.vehicleId = id;
        this.name = name;
        this.driver = driver;
        this.vehicleType = vehicleType;
        this.color = color;
    }

    public Driver getDriver() {
        return driver;
    }

    public Color getColor() {
        return color;
    }

    public VehicleSize getVehicleType() {
        return vehicleType;
    }
}