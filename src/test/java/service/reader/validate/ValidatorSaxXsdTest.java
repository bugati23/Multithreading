package service.reader.validate;

import org.junit.Test;

public class ValidatorSaxXsdTest {
    @Test
    public void validate() throws ValidatorException {
        String fileName = "src/test/resources/Trains.xml";
        String schemaName = "src/test/resources/Train.xsd";
        ValidatorSaxXsd validator = new ValidatorSaxXsd();
        validator.validate(fileName,schemaName);
    }
}
