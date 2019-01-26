package service.reader.parser;

import entity.Train;
import org.junit.Before;
import org.junit.Test;
import service.TunnelSupervisor;
import service.parser.TrainParser;
import service.parser.XmlParserException;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class TrainParserTest {
    private Set<Train> expectedSet = new LinkedHashSet<>();
    @Before
    public void fillSet() {
        TunnelSupervisor tunnelSupervisor = TunnelSupervisor.getInstance();
        Train train1 = new Train(tunnelSupervisor,true,1);
        Train train2 = new Train(tunnelSupervisor,false,2);
        Train train3 = new Train(tunnelSupervisor,true,3);
        Train train4 = new Train(tunnelSupervisor,true,4);
        Train train5 = new Train(tunnelSupervisor,true,5);
        Train train6 = new Train(tunnelSupervisor,false,6);
        Train train7 = new Train(tunnelSupervisor,true,7);
        Train train8 = new Train(tunnelSupervisor,false,8);
        Train train9 = new Train(tunnelSupervisor,false,9);
        Train train10 = new Train(tunnelSupervisor,true,10);
        expectedSet.addAll(Arrays.asList(train1,train2,train3,train4,train5,train6,train7,train8,train9,train10));
    }
    @Test
    public void validParseStAX() throws XmlParserException {
        TrainParser parser = new TrainParser();
        Set<Train> actualSet = parser.parse("src/test/resources/Trains.xml","src/test/resources/Train.xsd");
        assertEquals(expectedSet,actualSet);
    }
}
