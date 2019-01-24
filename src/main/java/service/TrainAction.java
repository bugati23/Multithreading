package service;

import entity.Train;
import entity.Tunnel;

public interface TrainAction {
    Tunnel enterInTunnel(Train train);
    void leaveTunnel(Tunnel tunnel);
}
