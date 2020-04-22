package parkinglotsystem.model;

public class Vehicle {
    private int hour;
    private String vehicleId;
    private String name;

    public Vehicle(String id, String name) {
        this.vehicleId = id;
        this.name = name;

    }

    //Overloading vehicle method and give the vehicle charges
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
