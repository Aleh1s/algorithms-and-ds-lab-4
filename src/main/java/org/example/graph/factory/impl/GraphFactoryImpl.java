package org.example.graph.factory.impl;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.example.exception.GraphFactoryException;
import org.example.exception.StAXControllerException;
import org.example.graph.ColorGraph;
import org.example.graph.controller.StAXController;
import org.example.graph.factory.GraphFactory;
import org.example.graph.node.Vertex;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.example.constant.Constant.*;

public final class GraphFactoryImpl extends GraphFactory {

    private final String DEFAULT_XML_FILE_NAME;
    private final String DEFAULT_XSD_FILE_NAME;

    public GraphFactoryImpl() throws GraphFactoryException {
        Properties props = new Properties();
        try {
            props.load(new FileReader(APPLICATION_PROPERTIES));
            DEFAULT_XML_FILE_NAME = props.getProperty(XML_FILE_NAME);
            DEFAULT_XSD_FILE_NAME = props.getProperty(XSD_FILE_NAME);
        } catch (IOException e) {
            throw new GraphFactoryException(e);
        }
    }

    public ColorGraph newColourGraph() throws GraphFactoryException {
        StAXController stAXController = new StAXController(DEFAULT_XSD_FILE_NAME, DEFAULT_XML_FILE_NAME);

        try {
            stAXController.buildAdjacencyList();
        } catch (StAXControllerException e) {
            throw new GraphFactoryException(e);
        }

        return buildGraph(stAXController.getAdjacencyList());
    }

    private static ColorGraph buildGraph(List<List<Integer>> adjacencyList) {
        MutableGraph<Vertex> graph = GraphBuilder.undirected()
                .allowsSelfLoops(false).build();

        for (int i = 0; i < adjacencyList.size(); i++) {
            List<Integer> adjacentVertices = adjacencyList.get(i);
            for (Integer adjacentVertex : adjacentVertices) {
                graph.putEdge(new Vertex(i), new Vertex(adjacentVertex));
            }
        }
        return new ColorGraph(graph);
    }
}
