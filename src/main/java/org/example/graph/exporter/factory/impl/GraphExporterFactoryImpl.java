package org.example.graph.exporter.factory.impl;

import org.example.exception.GraphExporterFactoryImplException;
import org.example.graph.exporter.GraphExporter;
import org.example.graph.exporter.factory.GraphExporterFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GraphExporterFactoryImpl extends GraphExporterFactory {

    private String fileName;

    public GraphExporterFactoryImpl() throws GraphExporterFactoryImplException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
            this.fileName = props.getProperty("output.file.name");
        } catch (IOException e) {
            throw new GraphExporterFactoryImplException(e);
        }
    }

    @Override
    public GraphExporter newGraphExporter() {
        return new GraphExporter(fileName);
    }
}
