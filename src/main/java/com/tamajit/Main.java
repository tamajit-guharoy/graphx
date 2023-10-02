package com.tamajit;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    // Create a new graph
    mxGraph graph = new mxGraph();

    // Obtain the default parent object of the graph
    Object parent = graph.getDefaultParent();

    // Enable the ability to move cells in the graph
    graph.setCellsMovable(true);

    // Add some vertices to the graph
    Object v1 = graph.insertVertex(parent, null, "Vertex 1", 20, 20, 80, 30);
    Object v2 = graph.insertVertex(parent, null, "Vertex 2", 200, 150, 80, 30);
    Object v3 = graph.insertVertex(parent, null, "Vertex 3", 400, 20, 80, 30);

    // Add an edge between two vertices
    graph.insertEdge(parent, null, "Edge", v1, v2);

    // Apply a layout algorithm to the graph for automatic arrangement
    mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
    layout.execute(parent);

    // Create a Swing component to display the graph
    mxGraphComponent graphComponent = new mxGraphComponent(graph);

    // Create a JFrame to contain the graph component
    JFrame frame = new JFrame("JGraphX Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(graphComponent);
    frame.pack();
    frame.setVisible(true);
  }
}
