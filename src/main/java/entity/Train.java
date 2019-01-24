package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TunnelManager;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Train extends Thread {
    private static final int TIME_IN_TUNNEL_IN_SECONDS = 3;
    private static final Logger LOGGER = LogManager.getLogger(Train.class);
    private int number;
    private boolean direction;
    private TunnelManager tunnelManager;

    public Train(){}

    public Train(TunnelManager tunnelManager, boolean direction, int number){
        this.tunnelManager = tunnelManager;
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

    public TunnelManager getTunnelManager() {
        return tunnelManager;
    }

    public void setTunnelManager(TunnelManager tunnelManager) {
        this.tunnelManager = tunnelManager;
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
                Objects.equals(getTunnelManager(), train.getTunnelManager());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getDirection(), getTunnelManager());
    }
}
