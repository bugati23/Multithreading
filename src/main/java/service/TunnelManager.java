package service;

import entity.Train;
import entity.Tunnel;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TunnelManager implements TrainAction{
    private static final int POOL_SIZE = 3;
    private static final String FIRST_TUNNEL_NAME = "FIRST";
    private static final String SECOND_TUNNEL_NAME = "SECOND";
    private Tunnel firstTunnel;
    private Tunnel secondTunnel;
    private Semaphore firstTunnelSemaphore;
    private Semaphore secondTunnelSemaphore;
    private static AtomicBoolean instanceCreated = new AtomicBoolean();
    private static TunnelManager instance;
    private static Lock lock = new ReentrantLock();

    private TunnelManager() {
        firstTunnel = new Tunnel(FIRST_TUNNEL_NAME);
        secondTunnel = new Tunnel(SECOND_TUNNEL_NAME);
        firstTunnelSemaphore = new Semaphore(POOL_SIZE, true);
        secondTunnelSemaphore = new Semaphore(POOL_SIZE, true);
    }

    public static TunnelManager getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new TunnelManager();
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
        return null;
    }

    @Override
    public void leaveTunnel(Tunnel tunnel) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TunnelManager that = (TunnelManager) o;
        return Objects.equals(firstTunnel, that.firstTunnel) &&
                Objects.equals(secondTunnel, that.secondTunnel) &&
                Objects.equals(firstTunnelSemaphore, that.firstTunnelSemaphore) &&
                Objects.equals(secondTunnelSemaphore, that.secondTunnelSemaphore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstTunnel, secondTunnel, firstTunnelSemaphore, secondTunnelSemaphore);
    }
}
