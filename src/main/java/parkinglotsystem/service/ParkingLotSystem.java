package parkinglotsystem.service;

import parkinglotsystem.enumclass.DriverType;
import parkinglotsystem.enumclass.VehicleDetails;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;
import parkinglotsystem.observer.AirportSecurity;
import parkinglotsystem.observer.Owner;
import parkinglotsystem.observer.ParkingStatusObserver;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

public class ParkingLotSystem {

    //constant
    private final int PARKING_LOT_CAPACITY;
    private final int NUMBER_OF_LOTS;
    private final int PARKING_ROW_CAPACITY;
    private final static String parkingAttendant = "xyz";


    //object of observe class
    Owner parkingLotOwner = new Owner();
    AirportSecurity airportSecurity = new AirportSecurity();
    ParkingStatusObserver parkingStatusObserver = new ParkingStatusObserver();

    private Map<Integer, TreeMap<Integer, Vehicle>> parkingLots = new TreeMap<>();
    private boolean parkingStatus;

    //number of lots and capacity
    public ParkingLotSystem(int capacity, int numberOfLots) {
        PARKING_LOT_CAPACITY = capacity;
        NUMBER_OF_LOTS = numberOfLots;
        PARKING_ROW_CAPACITY = PARKING_LOT_CAPACITY / NUMBER_OF_LOTS;
        IntStream.range(1, NUMBER_OF_LOTS + 1)
                .forEach(i -> parkingLots.put(i, getNullTreeMap()));

        parkingStatusObserver.registerObserver(parkingLotOwner);
        parkingStatusObserver.registerObserver(airportSecurity);
    }

    private TreeMap<Integer, Vehicle> getNullTreeMap() {
        TreeMap<Integer, Vehicle> parkingSpots = new TreeMap<>();
        IntStream.range(1, PARKING_ROW_CAPACITY + 1).forEach(i -> parkingSpots.put(i, null));
        return parkingSpots;
    }

    //parked vehicle method
    public boolean parkVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) throw new ParkingLotException("Vehicle already parked"
                , ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
        if (isParkingSlotEmpty()) {
            return vehicle.driverType.getVehicleParked(vehicle, this);
        }
        throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.LOT_FULL);
    }

    //number of lot and spot
    public boolean getSpotForNormalDriver(Vehicle vehicle) {
        if (vehicle.lotNo == 0 || vehicle.spotNo == 0) {
            int parkingLot = this.getParkingLot();
            int spotNumber = this.getEmptySpot(parkingLot);
            vehicle.lotNo = parkingLot;
            vehicle.spotNo = spotNumber;
            vehicle.setParkedTime(LocalDateTime.now());
        }
        vehicle.attendantName = parkingAttendant;
        parkingLots.get(vehicle.lotNo).put(vehicle.spotNo, vehicle);
        isParkingSlotEmpty();
        return true;
    }

    //spot of handicap diver method
    public boolean getSpotForHandicapDriver(Vehicle vehicle) {
        vehicle.lotNo = this.getParkingLot();
        Map.Entry<Integer, Vehicle> spotForHandicap = parkingLots.get(vehicle.lotNo)
                .entrySet()
                .stream()
                .filter(parkingSpot -> parkingSpot.getValue() == null || parkingSpot.getValue().driverType != DriverType.HANDICAP_DRIVER)
                .findFirst().get();
        Vehicle parkedVehicle = spotForHandicap.getValue();
        vehicle.spotNo = spotForHandicap.getKey();
        vehicle.parkedTime = LocalDateTime.now();
        parkingLots.get(vehicle.lotNo).put(spotForHandicap.getKey(), vehicle);
        if (parkedVehicle != null) {
            parkedVehicle.spotNo = 0;
            parkedVehicle.lotNo = 0;
            this.parkVehicle(parkedVehicle);
        }
        return true;
    }

    //Big vehicle spot method
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

    //to filter and check the empty space
    private boolean getFilterLargeEmptySpace(int parkingLot, Map.Entry<Integer, Vehicle> integerParkedVehicleEntry) {
        Integer emptySpot = integerParkedVehicleEntry.getKey();
        if (parkingLots.get(parkingLot).get(emptySpot + 1) != null)
            return false;
        return true;
    }

    //return space spot
    private int getEmptySpot(int parkingLotNumber) {
        return parkingLots.get(parkingLotNumber)
                .entrySet().stream()
                .filter(spotNumber -> spotNumber.getValue() == null)
                .findFirst().get().getKey();
    }

    //return parking lot
    private int getParkingLot() {
        return parkingLots.entrySet()
                .stream()
                .sorted(Comparator.comparing(lots -> lots.getValue()
                        .entrySet().stream().filter(spot -> spot.getValue() == null).count()))
                .reduce((first, second) -> second)
                .orElse(null)
                .getKey();
    }

    //unparked method
    public Vehicle unparkCar(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) {
            int carParkedLotNumber = vehicle.lotNo;
            int carParkedSpotNumber = vehicle.spotNo;
            LocalDateTime parkedTime = parkingLots.get(carParkedLotNumber).get(carParkedSpotNumber).parkedTime;
            Owner.parkedDuration = parkedTime;
            Vehicle unparkedCar = parkingLots.get(carParkedLotNumber).get(carParkedSpotNumber);
            parkingLots.get(carParkedLotNumber).put(carParkedSpotNumber, null);
            isParkingSlotEmpty();
            return unparkedCar;
        }
        throw new ParkingLotException("Car is not parked", ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED);
    }

    //parking slot empty
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

    //to vehicle parked method
    public boolean isVehicleParked(Vehicle vehicle) {
        if (parkingLots.entrySet()
                .stream()
                .filter(parkingLot -> parkingLot.getValue().containsValue(vehicle))
                .count() > 0) {
            return true;
        }
        return false;
    }

    //list of parked vehicle
    public List<Vehicle> getAllParkedVehicle(int... lotNumbers) {
        List<Vehicle> allParkedVehicle = new ArrayList<>();
        parkingLots.entrySet()
                .stream().filter(parkingLot -> getFilterByLotNumber(parkingLot, lotNumbers))
                .forEach(integerTreeMapEntry -> integerTreeMapEntry.getValue().entrySet().stream()
                        .filter(slotNumber -> slotNumber.getValue() != null)
                        .forEach(sortByDetails ->
                                allParkedVehicle.add(sortByDetails.getValue())));
        return checkParkedVehicleList(allParkedVehicle);
    }

    //filter by slot number
    private boolean getFilterByLotNumber(Map.Entry<Integer, TreeMap<Integer, Vehicle>> parkingLot, int[] lotNumbers) {
        if (lotNumbers.length == 0) {
            return true;
        } else {
            if (parkingLot.getKey() % lotNumbers[0] == 0)
                return true;
        }
        return false;
    }

    //list of check parked vehicle list
    private List<Vehicle> checkParkedVehicleList(List<Vehicle> parkedVehicleList) {
        if (!parkedVehicleList.isEmpty())
            return parkedVehicleList;
        throw new ParkingLotException("No such vehicle in lot", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE);
    }

    //To police rule of car by detail
    public List<Vehicle> getCarByDetails(VehicleDetails... vehicleDetails) {
        List<Vehicle> sortedVehicleByDetails = new ArrayList<>();
        List<Vehicle> allParkedVehicle = getAllParkedVehicle();
        allParkedVehicle.stream()
                .filter(parkingSlot -> getFilteredByCarDetails(parkingSlot, vehicleDetails)).
                forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails));
        return checkParkedVehicleList(sortedVehicleByDetails);
    }

    //to police rule of car by detail of filter the data
    private boolean getFilteredByCarDetails(Vehicle parkedVehicle, VehicleDetails[] carDetails) {
        for (int index = 0; index < carDetails.length; index++)
            if (!parkedVehicle.toString().toLowerCase().contains(carDetails[index].toString().toLowerCase()))
                return false;
        return true;
    }

    //to
    public List<Vehicle> getVehicleByTime(int time) {
        List<Vehicle> sortedVehicleByTime = new ArrayList<>();
        List<Vehicle> allParkedVehicle = getAllParkedVehicle();
        allParkedVehicle.stream()
                .filter(parkedVehicle -> parkedVehicle.parkedTime.isAfter(LocalDateTime.now().minusMinutes(time)))
                .forEach(sortByDetails -> sortedVehicleByTime.add(sortByDetails));
        return checkParkedVehicleList(sortedVehicleByTime);
    }

    //to list get handicap in car lot and row B and D
    public List<Vehicle> getHandicapCarInLot(int... rowNo) {
        List<Vehicle> sortedVehicleByDetails = new ArrayList<>();
        List<Vehicle> allParkedVehicle = getAllParkedVehicle(rowNo[0]);
        allParkedVehicle.stream()
                .filter(parkingSlot -> parkingSlot.driverType.toString().contains("HANDICAP_DRIVER")).
                forEach(sortByDetails -> sortedVehicleByDetails.add(sortByDetails));
        return sortedVehicleByDetails;
    }


}
