package service.parser;

import entity.Train;

import java.util.Set;

public interface Parser {
    Set<Train> parse(String fileName, String schemaXsd)throws XmlParserException;
}
