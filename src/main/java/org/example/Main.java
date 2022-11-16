package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import org.example.algorithm.Area;
import org.example.algorithm.bee.BeeColony;
import org.example.algorithm.bee.factory.BeeColonyFactory;
import org.example.algorithm.dfs.DepthFirstSearch;
import org.example.graph.ColorGraph;
import org.example.graph.exporter.GraphExporter;
import org.example.graph.exporter.factory.GraphExporterFactory;
import org.example.graph.factory.GraphFactory;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.example.constant.Constant.*;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Properties props = new Properties();
        props.load(new FileReader(APPLICATION_PROPERTIES));
        int numberOfAreas = Integer.parseInt(props.getProperty(NUMBER_OF_AREAS));
        int precision = Integer.parseInt(props.getProperty(PRECISION));

        GraphFactory graphFactory = GraphFactory.newInstance();
        ColorGraph colorGraph = graphFactory.newColourGraph();

        List<ColorGraph> colorGraphs = new ArrayList<>();
        for (int i = 0; i < numberOfAreas; i++) {
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
        yAxis.setLabel("Chromatic numbers");

        LineChart<Number, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Quality of result");

        XYChart.Series<Number, Number> series
                = new XYChart.Series<>();

        for (int i = 0; i < result.size(); i++)
            series.getData().add(new XYChart.Data<>(((i + 1) * precision), result.get(i)));

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }
}