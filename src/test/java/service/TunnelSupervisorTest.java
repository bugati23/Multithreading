package service;

import entity.Train;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TunnelSupervisorTest {
    private TunnelSupervisor tunnelSupervisor;
    @Before
    public void initTunnelSupervisor(){
        tunnelSupervisor = TunnelSupervisor.getInstance();
    }
    @Test
    public void enterInTunnel1(){
        Train train1 = new Train(tunnelSupervisor,true,1);
        tunnelSupervisor.enterInTunnel(train1);
        Assert.assertEquals(train1.getDirection(),tunnelSupervisor.getFirstTunnel().getCurrentDirection().get());
    }
    @Test
    public void enterInTunnel2(){
        Train train1 = new Train(tunnelSupervisor,true,1);
        tunnelSupervisor.enterInTunnel(train1);
        Train train2 = new Train(tunnelSupervisor,false,2);
        tunnelSupervisor.enterInTunnel(train2);
        Assert.assertEquals(train2.getDirection(),tunnelSupervisor.getSecondTunnel().getCurrentDirection().get());
    }
    @Test
    public void leaveTunnel(){
        Train train1 = new Train(tunnelSupervisor,true,1);
        tunnelSupervisor.enterInTunnel(train1);
        tunnelSupervisor.leaveTunnel(tunnelSupervisor.getFirstTunnel());
        Assert.assertEquals(0,tunnelSupervisor.getFirstTunnel().getTrains().size());
    }
    @Test
    public void amountInTunnel(){
        Train train1 = new Train(tunnelSupervisor,true,1);
        tunnelSupervisor.enterInTunnel(train1);
        Train train2 = new Train(tunnelSupervisor,true,2);
        tunnelSupervisor.enterInTunnel(train2);
        Train train3 = new Train(tunnelSupervisor,true,3);
        tunnelSupervisor.enterInTunnel(train3);
        Train train4 = new Train(tunnelSupervisor,true,4);
        tunnelSupervisor.enterInTunnel(train4);
        Assert.assertEquals(3,tunnelSupervisor.getFirstTunnel().getTrains().size());
    }
    @Test
    public void changeDirectionOfTunnel(){
        Train train1 = new Train(tunnelSupervisor,true,1);
        tunnelSupervisor.enterInTunnel(train1);
        Train train2 = new Train(tunnelSupervisor,true,2);
        tunnelSupervisor.enterInTunnel(train2);
        Train train3 = new Train(tunnelSupervisor,true,3);
        tunnelSupervisor.enterInTunnel(train3);
        Train train4 = new Train(tunnelSupervisor,true,4);
        tunnelSupervisor.enterInTunnel(train4);
        Train train5 = new Train(tunnelSupervisor,true,5);
        tunnelSupervisor.enterInTunnel(train5);
        Train train6 = new Train(tunnelSupervisor,true,6);
        tunnelSupervisor.enterInTunnel(train6);
        Assert.assertFalse(tunnelSupervisor.getFirstTunnel().getCurrentDirection().get());
    }
}
