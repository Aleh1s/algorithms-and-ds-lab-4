package org.example.graph.exporter.factory.impl;

import org.example.exception.GraphExporterFactoryImplException;
import org.example.graph.exporter.GraphExporter;
import org.example.graph.exporter.factory.GraphExporterFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static org.example.constant.Constant.APPLICATION_PROPERTIES;
import static org.example.constant.Constant.OUTPUT_FILE_NAME;

public class GraphExporterFactoryImpl extends GraphExporterFactory {

    private String fileName;

    public GraphExporterFactoryImpl() throws GraphExporterFactoryImplException {
        Properties props = new Properties();
        try {
            props.load(new FileReader(APPLICATION_PROPERTIES));
            this.fileName = props.getProperty(OUTPUT_FILE_NAME);
        } catch (IOException e) {
            throw new GraphExporterFactoryImplException(e);
        }
    }

    @Override
    public GraphExporter newGraphExporter() {
        return new GraphExporter(fileName);
    }
}
