package service;

import entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.parser.TrainParser;
import service.parser.XmlParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TunnelSupervisorTest {
    private static final Logger LOGGER = LogManager.getLogger(TunnelSupervisorTest.class);
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
    public void workTunnelSupervisor() throws XmlParserException {
        TrainParser parser = new TrainParser();
        Set<Train> actualSet = parser.parse("src/test/resources/Trains.xml","src/test/resources/Train.xsd");
        List<Train> trains = new ArrayList<>(actualSet);
        final int SLEEP_IN_SECONDS = 1;
        try {
            for (int i = 0; i < trains.size(); i++) {
                TimeUnit.SECONDS.sleep(SLEEP_IN_SECONDS);
                Train train = trains.get(i);
                LOGGER.info(train + " arrived");
                train.start();
            }
        }
        catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        }
    }
}
