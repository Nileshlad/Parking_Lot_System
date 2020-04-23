package parkinglotsystem.model;

import parkinglotsystem.enumclass.DriverType;
import parkinglotsystem.enumclass.VehicleSize;

import java.awt.*;
import java.sql.Driver;

public class Vehicle {


    private final Color color;
    private Driver driver;
    private String vehicleId;
    private String name;
    private VehicleSize vehicleSize;

    public Vehicle(String id, String name, Driver driver, VehicleSize vehicleSize, Color color) {
        this.vehicleId = id;
        this.name = name;
        this.driver = driver;
        this.vehicleSize = vehicleSize;
        this.color = color;
    }

    public Vehicle(String s, String thur, DriverType handicapDriver) {
    }

    public Driver getDriver() {
        return driver;
    }

    public Color getColor() {
        return color;
    }

    public VehicleSize getVehicleType() {
        return vehicleSize;
    }
}