package com.service;

import com.Attendant;
import com.IObservable;
import com.Owner;
import com.exception.ParkingLotException;
import com.model.Vehicle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotSystem {
    //constant
    private String IS_Full;
    private int PARKING_LOT_CAPACITY = 2;

    Owner owner = null;
    Attendant attendant = null;

    HashMap<String, Object> parkingLot = new HashMap();
    private List<IObservable> observableList = new ArrayList<>();

    public ParkingLotSystem(Owner owner, Attendant attendant) {
        this.owner = owner;
        this.attendant = attendant;
    }
    //observe method use case-5
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
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        }
        if (parkingLot.size() == PARKING_LOT_CAPACITY)
            setStatus("Full");
    }

    //unparked method
    public void unPark(Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null)
            throw new ParkingLotException("No such a vehicle", ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE);
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