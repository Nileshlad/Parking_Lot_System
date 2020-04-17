import com.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotSystemTest {

    //TEST CASE 1.1 AND USE CASE-1

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParked = parkingLotSystem.park(new Object());
        Assert.assertTrue(isParked);
    }

    //TEST CASE 1.2 AND USE CASE-2
    @Test
    public void givenAVehicle_WhenUnParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        parkingLotSystem.park(new Object());
        boolean isUnParked = parkingLotSystem.UnParked(new Object());
        Assert.assertTrue(isUnParked);
    }
}
