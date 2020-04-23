package parkinglotsystem.observer;

import parkinglotsystem.exception.ParkingLotException;
import parkinglotsystem.model.Vehicle;

import java.util.Map;

public interface IObservable {
    public void update(Object o);

    void isAvailable(Map<String, Vehicle> parkingLot, int parkingLotCapacity) throws ParkingLotException;
}
