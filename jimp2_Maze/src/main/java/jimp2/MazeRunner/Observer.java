package jimp2.MazeRunner;

import java.util.ArrayList;
import java.util.List;

public class Observer {
    private List<ObserverInterface> observers = new ArrayList<>();
private static Observer instance;
private Observer() {}
public static Observer getInstance() { //singlethon
    if (instance == null) {
        instance = new Observer();
    }
    return instance;
}

    public void addObserver(ObserverInterface observer) {
        observers.add(observer);
    }

    public void removeObserver(ObserverInterface observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (ObserverInterface observer : observers) {
            observer.update(message);
        }
    }
}
