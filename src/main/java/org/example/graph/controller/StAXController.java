package org.example.graph.controller;

import org.example.graph.exception.StAXControllerException;
import org.xml.sax.SAXException;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static org.example.graph.controller.XMLTag.*;

public class StAXController {

    private List<List<Integer>> adjacencyList;
    private final String xsdFileName;
    private final String xmlFileName;
    private final XMLInputFactory factory;
    public StAXController(String xsdFileName, String xmlFileName) {
        this.xsdFileName = Objects.requireNonNull(xsdFileName);
        this.xmlFileName = Objects.requireNonNull(xmlFileName);
        this.factory = XMLInputFactory.newInstance();
    }

    public void buildAdjacencyList() throws StAXControllerException {
        validate();

        int currId = 0;
        List<Integer> adjacentVertices = null;
        List<List<Integer>> adjacencyList = null;
        try {
            XMLEventReader reader =
                    factory.createXMLEventReader(new FileReader(xmlFileName));
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    String tagName = startElement.getName().getLocalPart();
                    XMLTag tag = XMLTag.from(tagName);
                    if (tag.equals(GRAPH)) {
                        adjacencyList = new ArrayList<>();
                    } else if (tag.equals(VERTEX)) {
                        adjacentVertices = new ArrayList<>();
                        Iterator<Attribute> attributes =
                                startElement.getAttributes();
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            String localPart = attribute.getName().getLocalPart();
                            if (localPart.equals("id")) {
                                currId = Integer.parseInt(attribute.getValue());
                            }
                        }
                    } else if (tag.equals(ADJACENT_VERTEX)) {
                        Iterator<Attribute> attributes
                                = startElement.getAttributes();
                        assert adjacentVertices != null;
                        while (attributes.hasNext()) {
                            Attribute attribute = attributes.next();
                            String localPart = attribute.getName().getLocalPart();
                            if (localPart.equals("id")) {
                                adjacentVertices.add(Integer.parseInt(attribute.getValue()));
                            }
                        }
                    }
                } else if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    String tagName = endElement.getName().getLocalPart();
                    XMLTag tag = from(tagName);
                    if (tag.equals(GRAPH)) {
                        this.adjacencyList = adjacencyList;
                    } else if (tag.equals(VERTEX)) {
                        assert adjacencyList != null;
                        adjacencyList.add(currId, adjacentVertices);
                    }
                }
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new StAXControllerException(e);
        }
    }

    private void validate() throws StAXControllerException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = schemaFactory.newSchema(new File(xsdFileName));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlFileName));
        } catch (SAXException | IOException e) {
            throw new StAXControllerException(e);
        }
    }


    public List<List<Integer>> getAdjacencyList() {
        return adjacencyList;
    }
}
