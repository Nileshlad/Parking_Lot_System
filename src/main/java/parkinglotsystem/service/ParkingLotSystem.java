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
    private int numberOfSlot = 4;



    LinkedHashMap<String, Vehicle> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;

    private List<IObservable> observableList = new ArrayList<>();
    Owner owner = null;
    Object attendant = null;

    //constructor
    public ParkingLotSystem(Owner owner, Attendant attendant, LinkedHashMap parkingLot, LinkedHashMap availableLot) {
        this.owner = owner;
        this.attendant = attendant;
        this.parkingLot = parkingLot;
        this.availableLot = availableLot ;
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
    public void park(Vehicle vehicle) throws ParkingLotException {
        owner.isAvailable(parkingLot, PARKING_LOT_CAPACITY);
        attendant.park(parkingLot, vehicle, availableLot);
        if (!parkingLot.containsValue(null))
            setStatus("Full");
    }

    //unparked method
    public void unPark(Vehicle vehicle) throws ParkingLotException {
        owner.isPresent(parkingLot, vehicle);
        attendant.UnPark(parkingLot, vehicle, availableLot);
        if (parkingLot.containsValue(null)) {
            setStatus("Have Space");
        }
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
        while (index != parkingLotCapacity) {
            slotCapacity = parkingLotCapacity / numberOfSlot;
                if (parkingLotCapacity % numberOfSlot != 0) {
                    slotCapacity += 1;
                }
            if (counter == slotCapacity + 1) {
                availableLot.put("P" + Integer.toString(slot), counter - 1);
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
            if (slot == numberOfSlot)
                availableLot.put("P" + Integer.toString(slot), counter - 1);
        }
    }

    //get my car parking number method
    public int getMyCarParkingNumber(Vehicle vehicle) {
        public String getMyCarParkingNumber(Vehicle vehicle) {
            Iterator<String> itr = parkingLot.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                if (parkingLot.get(key) == vehicle)
                return key;
            }
            return null;
        }

    }

    //METHOD FOR CHARGE PARKING VEHICLE
    public int chargeVehicle(Vehicle vehicle) {
        int totalCharges = vehicle.getHour() * CHARGES_PER_HOUR;
        return totalCharges;
    }


    //large vehicle parking spot method
    public boolean getSpotForLargeVehicle(Vehicle vehicle) {
        int parkingLot = this.getParkingLot();
        int spotNumber = parkingLots.get(parkingLot)
                .entrySet()
                .stream()
                .filter(spotNumberForLargeVehicle -> spotNumberForLargeVehicle.getValue() == null)
                .filter(integerParkedVehicleEntry -> getFilterLargeEmptySpace(parkingLot, integerParkedVehicleEntry))
                .findFirst()
                .get()
                .getKey();
        vehicle.lotNo = parkingLot;
        if (spotNumber != PARKING_LOT_CAPACITY)
            spotNumber += 1;
        vehicle.spotNo = spotNumber;
        this.getSpotForNormalDriver(vehicle);
        return true;
    }


    public boolean getSpotForNormalDriver(Vehicle vehicle) {
    }
}