package com.tamajit;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JGraphXExample5 {
  private static Object previousClickedVertex;
  private static Object currentEdge;

  public static void main(String[] args) {
    // Create a new graph
    mxGraph graph = new mxGraph();

    // Set the nodes to be fixed (non-movable) and non-resizable
    graph.setCellsEditable(false);
    graph.setCellsMovable(false);
    graph.setCellsResizable(false);

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

    // Create a mouse listener to handle double-click events
    mxGraphComponent graphComponent = new mxGraphComponent(graph);
    graphComponent.setConnectable(false);
    graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          Object cell = graphComponent.getCellAt(e.getX(), e.getY());

          if (cell != null && graph.getModel().isVertex(cell)) {
            if (previousClickedVertex == null) {
              // Store the first clicked vertex
              previousClickedVertex = cell;
            } else if (previousClickedVertex != cell) {
              // Create an edge between the two clicked vertices
              currentEdge = graph.insertEdge(parent, null, null, previousClickedVertex, cell);
              previousClickedVertex = null;
            }
          } else if (cell != null && graph.getModel().isEdge(cell)) {
            // Delete the double-clicked edge
            graph.getModel().remove(cell);
          }
        }
      }
    });

    // Customize the edge style
    graph.setCellStyles(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL, new Object[]{});
    graph.setCellStyles(mxConstants.STYLE_ROUNDED, "true", new Object[]{});

    // Disable edge labels from being movable
    graph.setEdgeLabelsMovable(false);

    // Create a JFrame to contain the graph component
    JFrame frame = new JFrame("JGraphX Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(graphComponent);
    frame.pack();
    frame.setVisible(true);
  }
}
