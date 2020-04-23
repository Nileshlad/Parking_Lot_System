package parkinglotsystem.enumclass;

import parkinglotsystem.model.Vehicle;
import parkinglotsystem.observer.DriverFactory;
import parkinglotsystem.service.ParkingLotSystem;

public enum DriverType {
    NORMAL_DRIVER {
        @Override
        public boolean getVehicleParked(Vehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return DriverFactory.getVehicleParkBySize(vehicle, parkingLotSystem);
        }
    }, HANDICAP_DRIVER {
        @Override
        public boolean getVehicleParked(Vehicle vehicle, ParkingLotSystem parkingLotSystem) {
            return parkingLotSystem.getSpotForHandicapDriver(vehicle);
        }
    };

    public abstract boolean getVehicleParked(Vehicle vehicle, ParkingLotSystem parkingLotSystem);
}

