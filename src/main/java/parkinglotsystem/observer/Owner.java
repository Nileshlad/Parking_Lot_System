package parkinglotsystem.observer;

import java.time.LocalDateTime;

public class Owner implements IObservable {
    public static boolean parkingStatus;
    public static LocalDateTime parkedDuration;

    public void updateStatus(boolean status) {
        parkingStatus = status;
    }
}