package org.example.graph.factory.impl;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.example.graph.ColourGraph;
import org.example.graph.controller.StAXController;
import org.example.graph.exception.GraphFactoryException;
import org.example.graph.exception.StAXControllerException;
import org.example.graph.factory.GraphFactory;
import org.example.graph.node.Node;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public final class GraphFactoryImpl extends GraphFactory {

    private final String DEFAULT_XML_FILE_NAME;
    private final String DEFAULT_XSD_FILE_NAME;

    public GraphFactoryImpl() throws GraphFactoryException {
        Properties props = new Properties();
        try {
            props.load(new FileReader("src/main/resources/application.properties"));
            DEFAULT_XML_FILE_NAME = props.getProperty("xml.file.name");
            DEFAULT_XSD_FILE_NAME = props.getProperty("xsd.file.name");
        } catch (IOException e) {
            throw new GraphFactoryException(e);
        }
    }

    public ColourGraph newColourGraph() throws GraphFactoryException {
        StAXController stAXController = new StAXController(DEFAULT_XSD_FILE_NAME, DEFAULT_XML_FILE_NAME);

        try {
            stAXController.buildAdjacencyList();
        } catch (StAXControllerException e) {
            throw new GraphFactoryException(e);
        }

        return buildGraph(stAXController.getAdjacencyList());
    }

    private static ColourGraph buildGraph(List<List<Integer>> adjacencyList) {
        MutableGraph<Node> graph = GraphBuilder.undirected()
                .allowsSelfLoops(false).build();

        for (int i = 0; i < adjacencyList.size(); i++) {
            List<Integer> adjacentVertices = adjacencyList.get(i);
            for (Integer adjacentVertex : adjacentVertices) {
                graph.putEdge(new Node(i), new Node(adjacentVertex));
            }
        }
        return new ColourGraph(graph);
    }
}
