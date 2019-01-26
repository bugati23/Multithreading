package service.parser;

import entity.Train;
import service.reader.TrainStAxBuilder;
import service.reader.validate.ValidatorException;
import service.reader.validate.ValidatorSaxXsd;

import java.util.Set;

public class TrainParser implements Parser {
    private ValidatorSaxXsd validator = new ValidatorSaxXsd();
    @Override
    public Set<Train> parse(String fileName, String schemaXsd)throws XmlParserException{
        try {
            validator.validate(fileName, schemaXsd);
            TrainStAxBuilder builder = new TrainStAxBuilder();
            builder.buildSetEntities(fileName);
            return builder.getEntities();
        }
        catch (ValidatorException e){
            throw new XmlParserException(e);
        }
    }
}
