package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TunnelSupervisor;

import java.util.Objects;

public class Train extends Thread {
    private static final int TIME_IN_TUNNEL_IN_SECONDS = 3;
    private static final Logger LOGGER = LogManager.getLogger(Train.class);
    private int number;
    private boolean direction;
    private TunnelSupervisor tunnelSupervisor;

    public Train(){}

    public Train(TunnelSupervisor tunnelSupervisor, boolean direction, int number){
        this.tunnelSupervisor = tunnelSupervisor;
        this.direction = direction;
        this.number = number;
    }

    public int getNumber(){
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean getDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public TunnelSupervisor getTunnelSupervisor() {
        return tunnelSupervisor;
    }

    public void setTunnelSupervisor(TunnelSupervisor tunnelSupervisor) {
        this.tunnelSupervisor = tunnelSupervisor;
    }

    @Override
    public void run(){
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Train)) return false;
        Train train = (Train) o;
        return getNumber() == train.getNumber() &&
                getDirection() == train.getDirection() &&
                Objects.equals(getTunnelSupervisor(), train.getTunnelSupervisor());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDirection(), getTunnelSupervisor());
    }

}
