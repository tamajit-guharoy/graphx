package com.tamajit;
import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JGraphXExample {

  @SuppressWarnings(value = "unchecked" )
    @SuppressWarnings(value = "")
  Integer a;
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

    // Create a mouse listener to handle vertex click events
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
      Object sourceVertex;

      @Override
      public void mouseClicked(MouseEvent e) {
        // Get the cell that was clicked
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());

        if (cell != null) {
          if (sourceVertex == null) {
            // If no source vertex selected, set the clicked vertex as the source
            sourceVertex = cell;
          } else {
            // If a source vertex is already selected, create/delete an edge
            Object[] edges = graph.getEdgesBetween(sourceVertex, cell);
            if (edges != null && edges.length > 0) {
              // Delete the edge if it already exists
              graph.getModel().remove(edges[0]);
            } else {
              // Create a new edge
              graph.insertEdge(parent, null, "Edge", sourceVertex, cell);
            }
            // Reset the source vertex
            sourceVertex = null;
          }
        }
      }
    });

    // Apply a layout algorithm to the graph for automatic arrangement
    mxCompactTreeLayout layout = new mxCompactTreeLayout(graph);
    layout.execute(parent);

    // Create a JFrame to contain the graph component
    JFrame frame = new JFrame("JGraphX Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(graphComponent);
    frame.pack();
    frame.setVisible(true);
  }
}
