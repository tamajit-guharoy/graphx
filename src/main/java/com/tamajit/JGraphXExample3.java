package com.tamajit;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JGraphXExample3 {
  public static void main(String[] args) {
    // Create a new graph
    mxGraph graph = new mxGraph();

    // Set the nodes to be fixed (non-movable)
    graph.setCellsEditable(false);
    graph.setCellsMovable(false);

    // Obtain the default parent object of the graph
    Object parent = graph.getDefaultParent();

    // Create the left side nodes
    Object a1 = graph.insertVertex(parent, null, "A1", 100, 200, 50, 30);
    Object a2 = graph.insertVertex(parent, null, "A2", 100, 300, 50, 30);
    Object a3 = graph.insertVertex(parent, null, "A3", 100, 400, 50, 30);

    // Create the right side nodes
    Object b1 = graph.insertVertex(parent, null, "B1", 400, 200, 50, 30);
    Object b2 = graph.insertVertex(parent, null, "B2", 400, 300, 50, 30);
    Object b3 = graph.insertVertex(parent, null, "B3", 400, 400, 50, 30);

    // Create the edges
    Object e1 = graph.insertEdge(parent, null, null, a1, b1);
    Object e2 = graph.insertEdge(parent, null, null, a2, b2);
    Object e3 = graph.insertEdge(parent, null, null, a3, b3);

    // Customize the edge style
    graph.setCellStyles(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ELBOW, new Object[]{e1, e2, e3});
    graph.setCellStyles(mxConstants.STYLE_ROUNDED, "true", new Object[]{e1, e2, e3});

    // Create a mouse listener to handle edge changes
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        Object cell = graphComponent.getCellAt(e.getX(), e.getY());

        if (cell != null && graph.getModel().isVertex(cell)) {
          Object[] edges = graph.getEdges(cell);
          if (edges.length > 0) {
            for (Object edge : edges) {
              Object sourceVertex = graph.getModel().getTerminal(edge, false);
              if (sourceVertex == a1 || sourceVertex == a2 || sourceVertex == a3) {
                graph.getModel().setTerminal(edge, cell, true);
              }
            }
          }
        }
      }
    });

    // Create a JFrame to contain the graph component
    JFrame frame = new JFrame("JGraphX Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(graphComponent);
    frame.pack();
    frame.setVisible(true);
  }
}
