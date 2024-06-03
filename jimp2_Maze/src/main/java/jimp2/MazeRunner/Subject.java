package jimp2.MazeRunner;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private final List<ObserverInterface> observers = new ArrayList<>();
    private static Subject instance;

    private Subject() {
    }

    public static Subject getInstance() {                  //singleton
        if (instance == null) {
            instance = new Subject();
        }
        return instance;
    }

    public void addObserver(ObserverInterface observer) {
        observers.add(observer);
    }

    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }

    public void notifyAllObservers(String message) {
        for (ObserverInterface observer : observers) {
            observer.update(message);
        }
    }
}
