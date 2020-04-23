package parkinglotsystem.observer;

import java.util.ArrayList;

public class ParkingStatusObserver {
    private ArrayList<IObservable> observers = new ArrayList<>();

    public void registerObserver(IObservable observer) {
        observers.add(observer);
    }

    public void notifyObservers(boolean parkingStatus) {
        for (IObservable ob : observers) {
            ob.updateStatus(parkingStatus);
        }
    }
}

