package parkinglotsystem.observer;

public class AirportSecurity implements IObservable {
    public static boolean securityStatus;

    public void updateStatus(boolean status) {
        securityStatus = status;
    }
}
