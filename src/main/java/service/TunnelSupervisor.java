package service;

import entity.Train;
import entity.Tunnel;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TunnelSupervisor implements TrainAction {
    private static final int POOL_SIZE = 3;
    private static final String FIRST_TUNNEL_NAME = "FIRST";
    private static final String SECOND_TUNNEL_NAME = "SECOND";
    private Tunnel firstTunnel;
    private Tunnel secondTunnel;
    private Semaphore firstTunnelSemaphore;
    private Semaphore secondTunnelSemaphore;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static TunnelSupervisor instance;
    private static Lock lock = new ReentrantLock();

    private TunnelSupervisor() {
        firstTunnel = new Tunnel(FIRST_TUNNEL_NAME);
        secondTunnel = new Tunnel(SECOND_TUNNEL_NAME);
        firstTunnelSemaphore = new Semaphore(POOL_SIZE, true);
        secondTunnelSemaphore = new Semaphore(POOL_SIZE, true);
    }

    public static TunnelSupervisor getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TunnelSupervisor();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    @Override
    public Tunnel enterInTunnel(Train train) {
        while(true) {
            if ((firstTunnel.getCurrentDirection() == null) || (firstTunnel.getCurrentDirection().get() == train.getDirection())) {
                if (firstTunnelSemaphore.tryAcquire()) {
                    startTrainInTunnel(firstTunnel, train);
                    return firstTunnel;
                }
            } else {
                if ((secondTunnel.getCurrentDirection() == null) || (secondTunnel.getCurrentDirection().get() == train.getDirection())) {
                    if (secondTunnelSemaphore.tryAcquire()) {
                        startTrainInTunnel(secondTunnel, train);
                        return secondTunnel;
                    }
                }
            }
        }
    }

    @Override
    public void leaveTunnel(Tunnel tunnel) {
        if (tunnel.getName().equals(firstTunnel.getName())) {
            firstTunnel.popTrain();
            if (firstTunnel.getTrains().isEmpty()) {
                firstTunnel.setCurrentDirection(null);
            }
            firstTunnelSemaphore.release();
        } else {
            secondTunnel.popTrain();
            if (secondTunnel.getTrains().isEmpty()) {
                secondTunnel.setCurrentDirection(null);
            }
            secondTunnelSemaphore.release();
        }
    }

    private void startTrainInTunnel(Tunnel tunnel, Train train) {
        if (tunnel.getTrains().isEmpty()) {
            tunnel.setCurrentDirection(new AtomicBoolean(train.getDirection()));
        }
        tunnel.pushTrain(train);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TunnelSupervisor that = (TunnelSupervisor) o;
        return Objects.equals(firstTunnel, that.firstTunnel) &&
                Objects.equals(secondTunnel, that.secondTunnel) &&
                Objects.equals(firstTunnelSemaphore, that.firstTunnelSemaphore) &&
                Objects.equals(secondTunnelSemaphore, that.secondTunnelSemaphore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTunnel, secondTunnel, firstTunnelSemaphore, secondTunnelSemaphore);
    }

    public Tunnel getFirstTunnel() {
        return firstTunnel;
    }

    public Tunnel getSecondTunnel() {
        return secondTunnel;
    }
}
