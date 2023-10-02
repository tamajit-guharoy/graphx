package com.tamajit;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxICell;
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

public class JGraphXExample6 {
  private static Object previousClickedVertex;
  private static Object currentEdge;
  private static Map<Object, Object> edgeToSourceMap = new HashMap<>();

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
        if (e.getClickCount() == 1) {
          Object cell = graphComponent.getCellAt(e.getX(), e.getY());

          if (cell != null && graph.getModel().isVertex(cell)) {
            if (previousClickedVertex == null) {
              // Store the first clicked vertex
              previousClickedVertex = cell;
            } else if (previousClickedVertex != cell) {
              // Create an edge between the two clicked vertices
              System.out.println(cell.getClass().getName()+"ppppppppppppppppppppppppppppppppp");

             if(graph.getEdges(((mxCell) cell)).length>0){
                mxICell edge = ((mxCell) cell).getEdgeAt(0);
                graph.getModel().remove(edge);
                edgeToSourceMap.remove(edge);
              }

                if(((mxCell) previousClickedVertex).getValue().toString().startsWith("A") &&
                  ((mxCell) cell).getValue().toString().startsWith("B")){
                  currentEdge = graph.insertEdge(parent, null, null, previousClickedVertex, cell);
                  edgeToSourceMap.put(((mxCell) currentEdge), previousClickedVertex);

                }

              previousClickedVertex = null;
            }
          } else if (cell != null && graph.getModel().isEdge(cell)) {
            // Delete the double-clicked edge
            graph.getModel().remove(cell);
            edgeToSourceMap.remove(((mxCell) cell));
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

    // Create a button to print the current edge-node connections
    JButton printConnectionsButton = new JButton("Print Connections");
    printConnectionsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Current Edge-Node Connections:");
        for (Map.Entry<Object, Object> entry : edgeToSourceMap.entrySet()) {
          Object edge = entry.getKey();
          Object sourceNode = entry.getValue();
          Object targetNode = graph.getModel().getTerminal(edge, false);
          System.out.println("Edge: " + edge + ", Source Node: " + sourceNode + ", Target Node: " + targetNode);
        }
      }
    });
    frame.getContentPane().add(printConnectionsButton, "South");

    frame.pack();
    frame.setVisible(true);
  }
}
