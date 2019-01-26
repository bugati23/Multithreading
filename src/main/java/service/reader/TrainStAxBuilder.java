package service.reader;

import entity.Train;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TunnelSupervisor;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TrainStAxBuilder {
    private static final Logger LOGGER = LogManager.getLogger(TrainStAxBuilder.class);
    private XMLInputFactory inputFactory;
    private Set<Train> entities;
    private TunnelSupervisor tunnelSupervisor;

    public TrainStAxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
        entities = new HashSet<>();
        tunnelSupervisor = TunnelSupervisor.getInstance();
    }

    public Set<Train> getEntities() {
        return entities;
    }

    public void buildSetEntities(String fileName) {
        FileInputStream inputStream = null;
        XMLStreamReader reader;
        String name;
        try {
            inputStream = new FileInputStream(new File(fileName));
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals("train")) {
                        Train train = buildTrain(reader);
                        entities.add(train);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException ex) {
            LOGGER.error(ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LOGGER.error(e);
            }
        }
    }

    private Train buildTrain(XMLStreamReader reader) throws XMLStreamException{
        Train train = new Train();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (name) {
                        case "number":
                            train.setNumber(Integer.valueOf(getXMLText(reader)));
                            break;
                        case "direction":
                            train.setDirection(Boolean.valueOf(getXMLText(reader)));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (name.equals("train")) {
                        train.setTunnelSupervisor(tunnelSupervisor);
                        return train;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Tariff");
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
