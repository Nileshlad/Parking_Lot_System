package com.exception;

public class ParkingLotException extends RuntimeException {

    public ExceptionType type;

    public enum ExceptionType {
        PARKING_LOT_IS_FULL, NO_SUCH_A_VEHICLE
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}