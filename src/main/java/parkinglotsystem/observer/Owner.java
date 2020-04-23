package parkinglotsystem.observer;

import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;

import java.util.LinkedHashMap;
import java.util.Map;

public class Owner implements IObservable {
    private String parkingLotStatus;

    @Override
    public void update(Object status) {
        this.setParkingLotStatus((String) status);
    }

    public String getParkingLotStatus() {
        return parkingLotStatus;
    }

    public void setParkingLotStatus(String isFull) {
        this.parkingLotStatus = isFull;
    }

    @Override
    public void isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException {
        if (!parkingLot.containsValue(null)) {
            throw new ParkingLotException("Parking lot is full", ParkingLotException.ExceptionType.PARKING_LOT_IS_FULL);
        }
    }

    public void isPresent(LinkedHashMap<String, Vehicle> parkingLot, Vehicle vehicle) throws ParkingLotException {
        if (vehicle == null) {
            throw new ParkingLotException("No such a vehicle", ParkingLotException.ExceptionType.NO_SUCH_A_VEHICLE);
        }
    }
}