import com.parkinglotsystem.ParkingLotSystem;
import org.junit.Assert;
import org.junit.Test;

public class ParkingLotSystemTest {
    
    //TEST CASE 1.1 AND USE CASE-1

    @Test
    public void givenACar_WhenParked_ShouldReturnTrue() {
        ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
        boolean isParkied = parkingLotSystem.park(new Object());
        Assert.assertTrue(isParkied);
    }
}
