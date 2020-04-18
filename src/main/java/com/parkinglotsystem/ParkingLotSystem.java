package com.parkinglotsystem;

public class ParkingLotSystem {

    private Object vehicle;

    public ParkingLotSystem() {

    }

    //parking car park method
    public boolean park(Object vehicle) {
        this.vehicle = vehicle;
        return true;
    }

    //unparked method
    public boolean UnParked(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }

    public boolean isVehicleUnParked(Object vehicle) {
               return true;
    }
    public static void main(String[] args) {

    }
}
