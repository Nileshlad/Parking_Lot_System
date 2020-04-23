package parkinglotsystem.observer;

import parkinglotsystem.enumclass.VehicleSize;
import parkinglotsystem.model.Vehicle;
import parkinglotsystem.service.ParkingLotSystem;

public class DriverFactory {
    public static boolean getVehicleParkBySize(Vehicle vehicle, ParkingLotSystem parkingLotSystem) {
        if (vehicle.vehicleSize.equals(VehicleSize.LARGE)) {
            return parkingLotSystem.getSpotForLargeVehicle(vehicle);
        }
        return parkingLotSystem.getSpotForNormalDriver(vehicle);
    }
}