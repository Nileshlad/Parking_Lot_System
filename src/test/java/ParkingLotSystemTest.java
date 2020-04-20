import com.AirportSecurity;
import com.Owner;
import com.exception.ParkingLotException;
import com.model.Vehicle;
import com.service.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    ParkingLotSystem parkingLotSystem = null;
    Vehicle vehicle = null;
    Owner owner = null;
    AirportSecurity airportSecurity = null;

    @Before
    public void setUp() throws Exception {
        parkingLotSystem = new ParkingLotSystem();
        owner = new Owner();
        airportSecurity = new AirportSecurity();
    }

    //TEST CASE 1.1 AND USE CASE-1
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle);
        Assert.assertEquals(true, isParked);
    }

    //TEST CASE 1.2 AND USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnFalse() {
        vehicle = new Vehicle("1", "car");
        Vehicle vehicle1 = new Vehicle("2", "bus");
        parkingLotSystem.park(vehicle);
        boolean isParked = parkingLotSystem.isVehicleParked(vehicle1);
        Assert.assertEquals(false, isParked);
    }

    //TEST CASE 1.3 EQUAL METHOD USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() {
        vehicle = new Vehicle("1", "car");
        parkingLotSystem.park(vehicle);
        parkingLotSystem.unPark(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }

    //TEST CASE 3.1 USE CASE-3
    @Test
    public void givenANullVehicle_WhenUnParked_ShouldThrowException() throws ParkingLotException {
        try {
            vehicle = new Vehicle("1", "car");
            parkingLotSystem.park(vehicle);
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
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.isVehicleUnParked(vehicle);
        Assert.assertEquals(false, isUnParked);
    }

    //TEST CASE 3.3
    @Test
    public void givenAVehicles_WhenParkingLotIsFull_ShouldThrowException() throws ParkingLotException {
        try {
            vehicle = new Vehicle("1", "car");
            parkingLotSystem.park(vehicle);
            Vehicle vehicle1 = new Vehicle("2", "car1");
            parkingLotSystem.park(vehicle1);
            Vehicle vehicle2 = new Vehicle("3", "car2");
            parkingLotSystem.park(vehicle2);
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
        parkingLotSystem.park(vehicle);
        Vehicle vehicle1 = new Vehicle("2", "car1");
        parkingLotSystem.park(vehicle1);
        Assert.assertEquals("Full", airportSecurity.getParkingLotStatus());
        Assert.assertEquals("Full", owner.getParkingLotStatus());
    }

}
