package org.example.graph.factory;

import org.example.graph.ColourGraph;
import org.example.graph.exception.GraphFactoryException;
import org.example.graph.factory.impl.GraphFactoryImpl;

public abstract class GraphFactory {

    public static GraphFactory newInstance() throws GraphFactoryException {
        return new GraphFactoryImpl();
    }

    public abstract ColourGraph newColourGraph() throws GraphFactoryException;
}