package service.reader.validate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class ValidatorSaxXsd {
    private static final Logger LOGGER = LogManager.getLogger();
    private String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
    public void validate(String fileName, String schemaName) throws ValidatorException{
        SchemaFactory factory = SchemaFactory.newInstance(language);
        File schemaLocation = new File(schemaName);
        try {
            Source source = new StreamSource(fileName);
            Schema schema = factory.newSchema(new StreamSource(schemaLocation));
            Validator validator = schema.newValidator();
            validator.validate(source);
        } catch (SAXException e) {
            LOGGER.error(e);
            throw new ValidatorException(e);
        } catch (IOException e) {
            LOGGER.error(e);
            throw new ValidatorException("Can't read the file", e);
        }
    }
}
