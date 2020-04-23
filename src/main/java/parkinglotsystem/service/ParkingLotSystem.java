package parkinglotsystem.service;

import parkinglotsystem.enumclass.DriverType;
import parkinglotsystem.enumclass.VehicleSize;
import parkinglotsystem.observer.IObservable;
import parkinglotsystem.observer.Owner;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;

import java.util.*;

public class ParkingLotSystem {
    //constant
     int PARKING_LOT_CAPACITY = 2;
     int CHARGES_PER_HOUR = 10;

    //variable
    private String is_full;
    private int numberOfSlot = 1;



    LinkedHashMap<String, Vehicle> parkingLot = null;
    private List<IObservable> observableList = new ArrayList<>();
    Owner owner = null;
    Object attendant = null;

    //constructor
    public ParkingLotSystem(Owner owner, Attendant attendant, LinkedHashMap parkingLot) {
        this.owner = owner;
        this.attendant = attendant;
        this.parkingLot = parkingLot;
    }

    //overloading method as owner and attendant
    public ParkingLotSystem(Owner owner, Object attendant) {
        this.owner = owner;
        this.attendant = attendant;
   }



    //observe method use case-5
    public void addObserver(IObservable iObservable) {
        this.observableList.add(iObservable);
    }

    //set status
    public void setStatus(String is_full) {
        this.is_full = is_full;
        for (IObservable observable : this.observableList) {
            observable.update(this.is_full);
        }
    }

    //park method
    public void park(Vehicle vehicle, DriverType handicapDriver, VehicleSize smallVehicle) throws ParkingLotException {
        if (parkingLot.size() < PARKING_LOT_CAPACITY) {
            parkingLot.put(vehicle.getVehicleId(), vehicle);
            chargeVehicle(vehicle);
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

    //create parking lot
    public void createParkingLot() {
        int counter = 1, index = 0, slot = 1, length = 0, slotCapacity = 0;
        while (index != PARKING_LOT_CAPACITY) {
            slotCapacity = PARKING_LOT_CAPACITY / numberOfSlot;
            if (PARKING_LOT_CAPACITY % numberOfSlot != 0)
                slotCapacity += 1;
            if (counter == slotCapacity + 1) {
                counter = 1;
                slot++;
            }
            String number1 = Integer.toString(counter);
            length = number1.length();
            if (length == 1) {
                number1 = "0" + number1;
            }
            String key = "P" + Integer.toString(slot) + number1;
            parkingLot.put(key, null);
            index++;
            counter++;
        }
    }

    //get my car parking number method
    public int getMyCarParkingNumber(Vehicle vehicle) {
        Set<String> keys = parkingLot.keySet();
        List<String> listKeys = new ArrayList<String>(keys);
        Iterator<String> itr = parkingLot.keySet().iterator();
        while (itr.hasNext()) {
            String key = itr.next();
            if (parkingLot.get(key) == vehicle)
                return listKeys.indexOf(key);
        }
        return 0;
    }

    //METHOD FOR CHARGE PARKING VEHICLE
    public int chargeVehicle(Vehicle vehicle) {
        int totalCharges = vehicle.getHour() * CHARGES_PER_HOUR;
        return totalCharges;
    }

    private boolean isParkingSlotEmpty() {
        if (parkingLots.entrySet().stream()
                .filter(parkingSlot -> parkingSlot.getValue().containsValue(null))
                .count() > 0) {
            this.parkingStatus = false;
            parkingStatusObserver.notifyObservers(parkingStatus);
            return !parkingStatus;
        }
        this.parkingStatus = true;
        parkingStatusObserver.notifyObservers(parkingStatus);
        return !parkingStatus;
    }

    //park vehicle
    public boolean parkVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) throw new ParkingLotException("Vehicle already parked"
                , ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
        if (isParkingSlotEmpty()) {
            return vehicle.driverType.getVehicleParked(vehicle, this);
        }
        throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
    }

    //METHOD FOR FIND VEHICLE
    public boolean getVehicle(Vehicle vehicle) {
        if (list1.contains(vehicle))
            return true;
        else if (list2.contains(vehicle))
            return true;
        return false;
    }

}