package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.example.algorithm.Area;
import org.example.algorithm.bee.BeeColony;
import org.example.algorithm.bee.factory.BeeColonyFactory;
import org.example.algorithm.dfs.DepthFirstSearch;
import org.example.graph.ColorGraph;
import org.example.graph.exporter.GraphExporter;
import org.example.graph.exporter.factory.GraphExporterFactory;
import org.example.graph.factory.GraphFactory;

import java.util.*;

public class Main extends Application {
    @SneakyThrows
    public static void main(String[] args) {

        launch(args);
//        List<String> strings = List.of("antiquewhite4", "brown3", "maroon", "mediumpurple", "skyblue",
//                "springgreen", "yellowgreen", "tomato", "navy", "mistyrose", "olive", "darkorange", "chocolate",
//                "darkred", "khaki");
//        strings.forEach(s -> System.out.println(s.toUpperCase()));





//        int numberOfVertices = 200;
//        int minPower = 1;
//        int maxPower = 30;
//        int maxEdges = numberOfVertices * (maxPower / 2);
//
//        List<List<Integer>> adjacencyList = new ArrayList<>(numberOfVertices);
//
//        for (int i = 0; i < numberOfVertices; i++) {
//            adjacencyList.add(new ArrayList<>());
//        }
//        ThreadLocalRandom random = ThreadLocalRandom.current();
//        int edges = random.nextInt(maxEdges);
//        while (edges != 0) {
//            int v1 = random.nextInt(0, numberOfVertices);
//            int v2 = random.nextInt(0, numberOfVertices);
//
//            if ((v1 != v2) && !adjacencyList.get(v1).contains(v2)) {
//                if (adjacencyList.get(v1).size() < maxPower && adjacencyList.get(v2).size() < maxPower) {
//                    adjacencyList.get(v1).add(v2);
//                    adjacencyList.get(v2).add(v1);
//                    edges--;
//                }
//            }
//        }
//
//        for (int i = 0; i < adjacencyList.size(); i++) {
//            System.out.println(i + " ==> " + adjacencyList.get(i));
//        }
//
//        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//        DocumentBuilder documentBuilder = null;
//        try {
//            documentBuilder = dbf.newDocumentBuilder();
//        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
//        }
//        Document document = documentBuilder.newDocument();
//
//        Element root = document.createElement("graph");
//        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//        root.setAttribute("xsi:noNamespaceSchemaLocation", "graph.xsd");
//        document.appendChild(root);
//
//        for (int i = 0; i < adjacencyList.size(); i++) {
//            Element vertex = document.createElement("vertex");
//            vertex.setAttribute("id", String.valueOf(i));
//            root.appendChild(vertex);
//
//            List<Integer> adjacentVertices = adjacencyList.get(i);
//            for (int j = 0; j < adjacentVertices.size(); j++) {
//                Element adjacentVertex = document.createElement("adjacentVertex");
//                adjacentVertex.setAttribute("id", String.valueOf(adjacentVertices.get(j)));
//                vertex.appendChild(adjacentVertex);
//            }
//        }
//
//        try {
//            TransformerFactory factory = TransformerFactory.newInstance();
//            Transformer transformer = factory.newTransformer();
//            DOMSource domSource = new DOMSource(document);
//            StreamResult streamResult = new StreamResult(new FileWriter("graph.xml"));
//            transformer.transform(domSource, streamResult);
//        } catch (Exception e) {
//            System.out.println("error");
//        }
        //xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        //       xsi:noNamespaceSchemaLocation="graph.xsd"

        //        MutableGraph<Node> graph = GraphBuilder
//                .undirected()
//                .allowsSelfLoops(false)
//                .build();
////
////        Node n1 = new Node(1, RED);
////        Node n2 = new Node(2, BLUE);
////        Node n3 = new Node(3, BLACK);
////        Node n4 = new Node(4, GREEN);
//
//        graph.putEdge(n1, n2);
//        graph.putEdge(n1, n3);
//        graph.putEdge(n2, n3);
//        graph.putEdge(n4, n2);
//
//        graph.adja
//
////        Set<String> bread = graph.adjacentNodes("bread"); // суміжні
//        System.out.println(graph);
    }

    @Override
    public void start(Stage stage) throws Exception {

        GraphFactory graphFactory = GraphFactory.newInstance();
        ColorGraph colorGraph = graphFactory.newColourGraph();

        List<ColorGraph> colorGraphs = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            DepthFirstSearch dfs = new DepthFirstSearch(colorGraph);
            ColorGraph graph = dfs.search();
            colorGraphs.add(graph);
        }

        BeeColonyFactory colonyFactory = BeeColonyFactory.newInstance();
        BeeColony beeColony = colonyFactory.getBeeColony();
        beeColony.setColorGraphs(colorGraphs);
        List<Integer> result = beeColony.search();

        Area peek = beeColony.getAreasQueue().peek();
        if (peek != null) {
            ColorGraph graph = peek.getGraph();
            GraphExporterFactory factory = GraphExporterFactory.newInstance();
            GraphExporter graphExporter = factory.newGraphExporter();
            graphExporter.setColourGraph(graph);
            graphExporter.export();
        }

        stage.setTitle("Graphic");

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Iterations");
        yAxis.setLabel("Chromatic number");

        LineChart<Number, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Quality of result");

        XYChart.Series<Number, Number> series
                = new XYChart.Series<>();

        for (int i = 0; i < result.size(); i++)
            series.getData().add(new XYChart.Data<>(i, result.get(i)));

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}