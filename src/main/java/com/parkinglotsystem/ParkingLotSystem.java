package com.parkinglotsystem;

import com.exception.ParkingLotException;

public class ParkingLotSystem {


    private Object vehicle = null;

    //Defoult constructor
    public ParkingLotSystem() {
    }

    //park method
    public boolean park(Object vehicle) throws ParkingLotException {
        if (this.vehicle != null)
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        this.vehicle = vehicle;

        return false;
    }

    //unparked method
    public boolean unPark(Object vehicle) {
        if (vehicle == null)
            throw new ParkingLotException("No such a vehicle", ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE);
        if (this.vehicle.equals(vehicle))
            this.vehicle = null;
        return false;
    }


   //vehicle parked method
    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicle.equals(vehicle))
            return true;
        return false;
    }


    //vehicle unparked method
    public boolean isVehicleUnParked(Object vehicle) {
        if (this.vehicle != vehicle)
            return true;
        return false;
    }

    public static void main(String[] args) {

    }
}