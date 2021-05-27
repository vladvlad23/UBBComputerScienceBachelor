package ro.ubbcluj;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Graph {
    int graphSize;
    Node root;
    List<Node> nodeList;

    public Graph() {
        nodeList = new ArrayList<>();
        graphSize = 0;
    }

    public Graph(int graphSize) {
        nodeList = new ArrayList<>();
        this.setGraphSize(graphSize);
        this.root = new Node(1);
        nodeList.add(root);
        IntStream.range(2,graphSize+1).forEach(node -> nodeList.add(new Node(node)));
    }

    public void addNode(Node parent, Node child) {
        Node parentNode = nodeList.stream().filter(node -> node == parent).findFirst().orElse(null);
        if (parentNode != null) {
            parentNode.addChild(child);
            if (!nodeList.contains(child)) {
                nodeList.add(child);
                graphSize++;
            }
        }
    }

    public int getGraphSize() {
        return graphSize;
    }

    public void setGraphSize(int graphSize) {
        this.graphSize = graphSize;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        graphSize = 1;
        nodeList.add(root);
        this.root = root;
    }

    public List<Node> getAllNodes() {
        return nodeList;
    }
}
