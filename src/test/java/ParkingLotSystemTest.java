import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;
import parkinglotsystem.observer.AirportSecurity;
import parkinglotsystem.observer.Owner;
import parkinglotsystem.service.Attendant;
import parkinglotsystem.service.ParkingLotSystem;

import java.util.LinkedHashMap;


public class ParkingLotSystemTest {

    LinkedHashMap<String, Vehicle> parkingLot = null;
    LinkedHashMap<String, Integer> availableLot = null;
    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;
    Attendant attendant = null;
    LinkedHashMap<String, Integer> availableLot = null;


    @Before
    public void setUp() {
        owner = new Owner();
        airportSecurity = new AirportSecurity();
        attendant = new Attendant(parkingLotSystem);
        parkingLot = new LinkedHashMap();
    }

    //TEST CASE 1.1 AND USE CASE-1
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    //TEST CASE 1.2 AND USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnFalse() {
        vehicle = new Vehicle("1", "car");
        Vehicle vehicle1 = new Vehicle("2", "bus");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(false, isParked);
    }

    //TEST CASE 1.3 EQUAL METHOD USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() {
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    //TEST CASE 3.1 USE CASE-3
    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() throws ParkingLotException {
        try {
            vehicle = new Vehicle("1", "car");
            parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
            parkingLotSystem.unPark(null);
            boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
            Assert.assertEquals(true, isUnParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE, e.type);
        }
    }

    //TEST CASE 3.2
    @Test
    public void givenAVehicle_WhenAlreadyParkedAndCheckIfUnPark_ShouldReturnFalse() throws ParkingLotException {
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    //TEST CASE 3.3 and throw the exception
    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() throws ParkingLotException {
        try {
            vehicle = new Vehicle("1", "car");
            parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
            Vehicle vehicle1 = new Vehicle("2", "car1");
            parkingLotSystem.park(vehicle1, HANDICAP_DRIVER, SMALL_VEHICLE);
            Vehicle vehicle2 = new Vehicle("3", "car2");
            parkingLotSystem.park(vehicle2, HANDICAP_DRIVER, SMALL_VEHICLE);
            boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
            Assert.assertEquals(true, isParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL, e.type);
        }
    }

    //TEST CASE 4.1 AND USE CASE-4
    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToAirportSecurityAndOwner() throws ParkingLotException {
        parkingLotSystem.addObserver(owner);
        parkingLotSystem.addObserver(airportSecurity);
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        Vehicle vehicle1 = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle1, HANDICAP_DRIVER, SMALL_VEHICLE);
        Assert.assertEquals("Full", airportSecurity.getParkingLotStatus());
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    //TEST CASE 5.1 AND USE CASE-5
    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldInformToOwner() throws ParkingLotException {
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        Vehicle vehicle1 = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle1, HANDICAP_DRIVER, SMALL_VEHICLE);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

    //TEST CASE 5.2
    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotException {
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        Vehicle vehicle1 = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle1, HANDICAP_DRIVER, SMALL_VEHICLE);
        Assert.assertEquals("Full", owner.getParkingLotStatus());
        parkingLotSystem.unPark(vehicle1);
        Assert.assertEquals("Have Space", owner.getParkingLotStatus());
    }

    //TEST CASE 6.1 AND USE CASE-6
    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldParkTheCar(Object attendant) throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant);
        parkingLotSystem.addObserver(owner);
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        Vehicle vehicle = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        parkingLotSystem.unPark(vehicle);
        System.out.println(parkingLotSystem.isVehicleParked(vehicle));
    }

    //TEST CASE 7.1 AND USE CASE-7
    @Test
    public void givenVehicle_WhenWantToFindCar_ShouldNumberInParkingLot() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant, parkingLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int index = 0; index < numberOfCars; index++) {
            Vehicle vehicle = new Vehicle(Integer.toString(index), "BMW");
            parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        }
        Vehicle vehicle2 = new Vehicle("55", "Thur");
        parkingLotSystem.park(vehicle2, HANDICAP_DRIVER, SMALL_VEHICLE);
        int numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals(9, numberInParkingLot);
    }

    //TEST CASE 8.1 and ues case-8 parking charges
    @Test
    public void givenVehicle_WhenPark_ShouldReturnChargesTrue() throws ParkingLotException {
        parkingLotSystem.addObserver(owner);
        Vehicle vehicle = new Vehicle("1", "Car1", 4);
        parkingLotSystem.park(vehicle, HANDICAP_DRIVER, SMALL_VEHICLE);
        int parkingCharges = parkingLotSystem.chargeVehicle(vehicle);
        Assert.assertEquals(40, parkingCharges);
    }

    //TEST CASE 9.1 AND
    @Test
    public void givenVehicle_WhenOwnerWantAttendant_ShouldEvenlyDistributeCar() throws ParkingLotException {
        parkingLotSystem = new ParkingLotSystem(owner, attendant, parkingLot, availableLot);
        parkingLotSystem.createParkingLot();
        parkingLotSystem.addObserver(owner);
        int numberOfCars = 9;
        for (int index = 0; index < numberOfCars; index++) {
            Vehicle vehicle = new Vehicle(Integer.toString(index), "BMW");
            parkingLotSystem.park(vehicle);
        }
        Vehicle vehicle2 = new Vehicle("55", "Thur");
        parkingLotSystem.park(vehicle2);
        Vehicle vehicle3 = new Vehicle("75", "Thur");
        parkingLotSystem.park(vehicle3);
        parkingLotSystem.unPark(vehicle3);
        String numberInParkingLot = parkingLotSystem.getMyCarParkingNumber(vehicle2);
        Assert.assertEquals("P303", numberInParkingLot);
    }
}