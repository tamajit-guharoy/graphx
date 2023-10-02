package com.tamajit;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;

public class JGraphXExample2 {
  public static void main(String[] args) {
    // Create a new graph
    mxGraph graph = new mxGraph();

    // Obtain the default parent object of the graph
    Object parent = graph.getDefaultParent();

    // Enable the ability to move cells in the graph
    graph.setCellsMovable(true);

    // Create left table vertices
    Object leftTable = graph.insertVertex(parent, null, "Left Table", 100, 100, 100, 30);
    Object leftRow1 = graph.insertVertex(parent, null, "Row 1", 100, 200, 80, 30);
    Object leftRow2 = graph.insertVertex(parent, null, "Row 2", 100, 300, 80, 30);
    Object leftRow3 = graph.insertVertex(parent, null, "Row 3", 100, 400, 80, 30);

    // Create right table vertices
    Object rightTable = graph.insertVertex(parent, null, "Right Table", 400, 100, 100, 30);
    Object rightRow1 = graph.insertVertex(parent, null, "Row 1", 400, 200, 80, 30);
    Object rightRow2 = graph.insertVertex(parent, null, "Row 2", 400, 300, 80, 30);
    Object rightRow3 = graph.insertVertex(parent, null, "Row 3", 400, 400, 80, 30);

    // Create edges between corresponding rows
    graph.insertEdge(parent, null, null, leftRow1, rightRow1);
    graph.insertEdge(parent, null, null, leftRow2, rightRow2);
    graph.insertEdge(parent, null, null, leftRow3, rightRow3);

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
