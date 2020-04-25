import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import parkinglotsystem.enumclass.DriverType;
import parkinglotsystem.enumclass.VehicleDetails;
import parkinglotsystem.enumclass.VehicleSize;
import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;
import parkinglotsystem.observer.AirportSecurity;
import parkinglotsystem.observer.Owner;
import parkinglotsystem.service.ParkingLotSystem;

import java.time.LocalDateTime;
import java.util.List;


public class ParkingLotSystemTest {

    Vehicle Vehicle = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        Vehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL);
        parkingLotSystem = new ParkingLotSystem(2, 1);
    }

    //TEST CASE 1.1 AND USE CASE-1
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        Assert.assertTrue(parkingLotSystem.parkVehicle(Vehicle));
    }

    //TEST CASE 1.2 AND USE CASE-1
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnFalse() {
        Assert.assertFalse(parkingLotSystem.isVehicleParked(Vehicle));

    }

    //TEST CASE 2.1 AND USE CASE-2
    @Test
    public void givenVehicle_whenParkingLotIsEmpty_shouldInformOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertFalse(Owner.parkingStatus);
    }

    //TEST CASE 3.1 USE CASE-3 EXCEPTION THROW
    @Test
    public void givenVehicle_whenDriverUnparkingWrongVehicle_shouldReturnException() throws ParkingLotException {
        try {
            parkingLotSystem.unparkCar(Vehicle);
        } catch (ParkingLotException p) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, p.type);
        }
    }

    //TEST CASE 3.2
    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertTrue(Owner.parkingStatus);
    }

    //TEST CASE 4.1 AND USE CASE-4
    @Test
    public void givenVehicle_whenParkingLotIsFull_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertTrue(AirportSecurity.securityStatus);
    }

    //TEST CASE 5.1 AND USE CASE-5
    @Test
    public void givenVehicle_whenVehicleIsAlreadyParked_shouldThrowException() throws ParkingLotException {
        Vehicle parkedVehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL);
        try {
            parkingLotSystem.parkVehicle(parkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_PARKED, e.type);
        }
    }


    //TEST CASE 5.2
    @Test
    public void givenVehicle_whenParkingLotIsEmpty_shouldInformAirportSecurity() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        Assert.assertFalse(AirportSecurity.securityStatus);
    }

    //TEST CASE 6.1 AND USE CASE-6
    @Test
    public void givenVehicle_whenCarIsUnparked_shouldChangeTheStatusOfParkingLotToEmpty() {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(Vehicle);
        Assert.assertTrue(Owner.parkingStatus);
        parkingLotSystem.unparkCar(Vehicle);
        Assert.assertFalse(Owner.parkingStatus);
    }

    //TEST CASE 7.1 AND USE CASE-7
    @Test
    public void givenAVehicles_WhenParkingLotHasSpaceAgain_ShouldInformToParkingLotOwner() throws ParkingLotException {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        try {
            parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.LOT_FULL, e.type);
        }
    }


    //TEST CASE 8.1 and ues case-8 parking time and date
    @Test
    public void givenVehicle_whenDriverWantToUnpark_ShouldReturnParkedDateAndTimeToOwner() {
        parkingLotSystem.parkVehicle(Vehicle);
        int minute = LocalDateTime.now().getMinute();
        parkingLotSystem.unparkCar(Vehicle);
        Assert.assertEquals(minute, Owner.parkedDuration.getMinute());
    }

    //TEST CASE 9.1 and USE CASE-9
    @Test
    public void givenVehicles_whenLotIsEmpty_shouldAbleToParkEvenly() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(20, 5);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(Vehicle);
        Assert.assertEquals(2, Vehicle.spotNo);
    }

    //TEST CASE 10.1 AND USE CASE-10
    @Test
    public void givenVehicle_whenDriverIsHandicap_shouldParkedAtNearestSpot() throws ParkingLotException {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(4, 2);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(Vehicle);
        Vehicle parkedVehicle1 = new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        Assert.assertEquals(1, parkedVehicle1.spotNo);
    }

    //TEST CASE 11.1 AND USE CASE-11
    @Test
    public void givenVehicle_whenItisLarge_shouldAbleToPark() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(10, 1);
        Vehicle parkedVehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.LARGE);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        Vehicle parkedVehicle1 = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.unparkCar(parkedVehicle1);
        Assert.assertTrue(parkingLotSystem.parkVehicle(parkedVehicle));
    }

    //TEST CASE 12.1 AND USE CASE-12 //BIG VEHICLE
    @Test
    public void givenVehicle_whenWhiteVehiclesAreParked_shouldReturnThatVehicles() {
        Vehicle parkedVehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL, "White");
        Vehicle parkedVehicle1 = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL, "Green");
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<Vehicle> vehicleList = parkingLotSystem.getCarByDetails(VehicleDetails.WHITE);
        Assert.assertEquals(vehicleList.get(0), parkedVehicle);
    }

    //TEST CASE 13.1 AND USE CASE-13
    @Test
    public void givenVehicle_whenItIsBlueToyota_shouldReturnThatVehicles() {
        Vehicle parkedVehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL, "Blue");
        parkedVehicle.carManufacturer = "Maruti";
        Vehicle parkedVehicle1 = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL, "Blue");
        parkedVehicle1.carManufacturer = "Toyoto";
        parkingLotSystem.parkVehicle(parkedVehicle);
        parkingLotSystem.parkVehicle(parkedVehicle1);
        List<Vehicle> vehicleList = parkingLotSystem.getCarByDetails(VehicleDetails.TOYOTO, VehicleDetails.BLUE);
        Assert.assertEquals(vehicleList.get(0), parkedVehicle1);
    }

    //TEST CASE 14.1 AND USE CASE-14
    @Test
    public void givenVehicles_whenItIsNotBMW_shouldThrowException() {
        Vehicle parkedVehicle = new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL, "Blue");
        parkedVehicle.carManufacturer = "Toyoto";
        try {
            parkingLotSystem.getCarByDetails(VehicleDetails.BMW);
        } catch (ParkingLotException p) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE, p.type);
        }
    }

    //TEST CASE 15.1 AND USE CASE-15
    @Test
    public void givenVehicles_whenItIsParkedWithin30Min_shouldReturnThatVehicle() {
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        List<Vehicle> vehicleList = parkingLotSystem.getVehicleByTime(30);
        Assert.assertEquals(2, vehicleList.size());
    }

    //TEST CASE 16.1 AND USE CASE-16
    @Test
    public void givenVehicles_when_B_and_D_LotHasHandicapVehicle_shouldReturnIt() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(8, 4);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        List<Vehicle> sortedVehicle = parkingLotSystem.getHandicapCarInLot(2);
        Assert.assertEquals(3, sortedVehicle.size());
    }

    //  TEST CASE 17.1 AND USE CASE-17
    @Test
    public void givenVehicles_whenThatAreParked_shouldReturnIt() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem(8, 4);
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL, "blue"));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL, "blue1"));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL, "blue2"));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.HANDICAP_DRIVER, VehicleSize.SMALL, "blue3"));
        parkingLotSystem.parkVehicle(new Vehicle(DriverType.NORMAL_DRIVER, VehicleSize.SMALL));
        List<Vehicle> sortedVehicle = parkingLotSystem.getAllParkedVehicle();
        Assert.assertEquals(8, sortedVehicle.size());
    }
}
