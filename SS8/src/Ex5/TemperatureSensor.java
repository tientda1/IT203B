package Ex5;

import java.util.ArrayList;
import java.util.List;

public class TemperatureSensor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private int temperature;

    @Override
    public void attach(Observer o) {
        if (!observers.contains(o)) observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(temperature);
        }
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
        System.out.println("Cảm biến: Nhiệt độ = " + temperature);
        notifyObservers();
    }
}
