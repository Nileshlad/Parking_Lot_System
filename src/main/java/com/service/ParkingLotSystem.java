package com.service;

import com.IObservable;
import com.exception.ParkingLotException;
import com.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotSystem {
    //constant
    private String IS_Full;
    private int PARKING_LOT_CAPACITY = 2;

    HashMap<String, Object> parkingLot = new HashMap();
    private List<IObservable> observableList = new ArrayList<>();

    //Defoult constructor
    public ParkingLotSystem() {
    }

    public void addObserver(IObservable iObservable) {
        this.observableList.add(iObservable);
    }

    //set status
    public void setStatus(String isFull) {
        this.IS_Full = isFull;
        for (IObservable observable : this.observableList) {
            observable.update(this.IS_Full);
        }
    }


    //park method
    public void park(Vehicle vehicle) throws ParkingLotException {
        if (parkingLot.size() < PARKING_LOT_CAPACITY) {
            parkingLot.put(vehicle.getVehicleId(), vehicle);
        } else if (parkingLot.size() == PARKING_LOT_CAPACITY) {
            throw new ParkingLotException(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, "Parking lot is full");
        }
        if (parkingLot.size() == PARKING_LOT_CAPACITY)
            setStatus("Full");
    }

    //unparked method
    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, "No such a vehicle");
        else if (parkingLot.containsKey(vehicle.getVehicleId()))
            parkingLot.remove(vehicle.getVehicleId());
        if (parkingLot.size() < PARKING_LOT_CAPACITY)
            setStatus("Have Space");
    }

    //vehicle parked method
    public boolean isVehicleParked(Vehicle vehicle) {
        if (parkingLot.containsKey(vehicle.getVehicleId()))
            return true;
        return false;
    }

    //vehicle unparked method
    public boolean isVehicleUnParked(Vehicle vehicle) {
        if (!parkingLot.containsKey(vehicle.getVehicleId()))
            return true;
        return false;
    }
}