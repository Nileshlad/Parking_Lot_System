import com.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotSystemTest {

    Object vehicle = null;
    ParkingLotSystem parkingLotSystem = null;

    @Before
    public void setUp() throws Exception {
        vehicle = new Object();
        parkingLotSystem = new ParkingLotSystem();
    }

    //TEST CASE 1.1 AND USE CASE-1

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.park(new Object());
        Assert.assertTrue(isParked);
    }

    //TEST CASE 1.2 AND USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnFalse() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.park(new Object());
        boolean isUnParked = parkingLotSystem.UnParked(new Object());
        Assert.assertFalse(isUnParked);
    }

    //TEST CASE 1.3 EQUAL METHOD USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ReturnTrue() {
        parkingLotSystem.park(vehicle);
        boolean isUnParked = parkingLotSystem.UnParked(vehicle);
        Assert.assertEquals(true, isUnParked);
    }
}
