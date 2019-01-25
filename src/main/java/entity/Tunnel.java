package entity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tunnel {
    private String name;
    private Deque<Train> trains;
    private AtomicBoolean currentDirection;

    public Tunnel(){}

    public Tunnel(String name) {
        this.name = name;
        trains = new ArrayDeque<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicBoolean getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(AtomicBoolean currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Queue<Train> getTrains() {
        return trains;
    }

    public void setTrains(Deque<Train> trains) {
        this.trains = trains;
    }

    public void pushTrain(Train train) {
        trains.addLast(train);
    }

    public void popTrain() {
        trains.pop();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tunnel)) return false;
        Tunnel tunnel = (Tunnel) o;
        return Objects.equals(getName(), tunnel.getName()) &&
                Objects.equals(getTrains(), tunnel.getTrains()) &&
                Objects.equals(getCurrentDirection(), tunnel.getCurrentDirection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getTrains(), getCurrentDirection());
    }
}
