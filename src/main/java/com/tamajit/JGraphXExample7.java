package com.tamajit;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class JGraphXExample7 {
  private static Object previousClickedVertex;
  private static Map<Object, Object> nodeToEdgeMap = new HashMap<>();

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
              Object targetNode = getRightNode(cell);

              if (targetNode != null) {
                // Delete previous edge connected to the target node, if any
                Object previousEdge = nodeToEdgeMap.get(targetNode);
                if (previousEdge != null) {
                  graph.getModel().remove(previousEdge);
                  nodeToEdgeMap.remove(targetNode);
                }

                // Create an edge between the two clicked vertices
                Object edge = graph.insertEdge(parent, null, null, previousClickedVertex, targetNode);
                nodeToEdgeMap.put(targetNode, edge);
                previousClickedVertex = null;
              }
            }
          } else if (cell != null && graph.getModel().isEdge(cell)) {
            // Delete the double-clicked edge
            graph.getModel().remove(cell);
            removeEdgeFromNodeMap(cell);
          }
        }
      }
    });

    // Customize the edge style
    graph.setCellStyles(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_ORTHOGONAL, new Object[]{});
    graph.setCellStyles(mxConstants.STYLE_ROUNDED, "true", new Object[]{});

    // Disable edge labels from being movable
    graph.setEdgeLabelsMovable(false);

    // Create a button to print the edge connections
    JButton printButton = new JButton("Print Edge Connections");
    printButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Edge Connections:");
        for (Map.Entry<Object, Object> entry : nodeToEdgeMap.entrySet()) {
          Object rightNode = entry.getKey();
          Object edge = entry.getValue();
          Object leftNode = graph.getModel().getTerminal(edge, false);
          System.out.println(leftNode + " -> " + rightNode);
        }
      }
    });

    // Create a JPanel to contain the graph component and button
    JPanel panel = new JPanel();
    panel.add(graphComponent);
    panel.add(printButton);

    // Create a JFrame to contain the panel
    JFrame frame = new JFrame("JGraphX Example");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(panel);

    frame.pack();
    frame.setVisible(true);
  }

  private static Object getRightNode(Object cell) {
    if (cell.equals("A1")) {
      return "B1";
    } else if (cell.equals("A2")) {
      return "B2";
    } else if (cell.equals("A3")) {
      return "B3";
    }
    return null;
  }

  private static void removeEdgeFromNodeMap(Object edge) {
    for (Map.Entry<Object, Object> entry : nodeToEdgeMap.entrySet()) {
      if (entry.getValue() == edge) {
        nodeToEdgeMap.remove(entry.getKey());
        return;
      }
    }
  }
}
