package jimp2.MazeRunner;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<Observer> observers = new ArrayList<>();
private static Subject instance;
private Subject() {}
public static Subject getInstance() { //singlethon
    if (instance == null) {
        instance = new Subject();
    }
    return instance;
}

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
