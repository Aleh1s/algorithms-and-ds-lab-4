package org.example.graph.exporter.factory;

import org.example.graph.exception.GraphExporterFactoryException;
import org.example.graph.exporter.GraphExporter;
import org.example.graph.exporter.factory.impl.GraphExporterFactoryImpl;

public abstract class GraphExporterFactory {

    public static GraphExporterFactory newInstance() throws GraphExporterFactoryException {
        return new GraphExporterFactoryImpl();
    }

    public abstract GraphExporter newGraphExporter();

}
