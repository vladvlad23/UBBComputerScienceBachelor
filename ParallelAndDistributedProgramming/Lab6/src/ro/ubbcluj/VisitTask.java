package ro.ubbcluj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class VisitTask implements Runnable {

    private final Node root;
    private final Node startingNode;
    private final List<Node> path;
    private final List<Node> result;
    private final Lock lock;
    private final int graphSize;

    @Override
    public void run() {
        startVisiting(startingNode);
    }

    public void setResult() {
        lock.lock();
        result.addAll(path);
        lock.unlock();
    }

    private void startVisiting(Node node) {
        path.add(node);
        if (path.size() == graphSize && node.children.contains(root)) {
            path.add(root);
            setResult();
            return;
        }
        node.getChildren().stream().filter(nextNode -> !path.contains(nextNode)).forEach(this::startVisiting);
    }

    public VisitTask(Node root, Node startingNode, List<Node> result, Lock lock, int graphSize) {
        this.root = root;
        this.startingNode = startingNode;
        this.result = result;
        this.lock = lock;
        this.graphSize = graphSize;
        path = new ArrayList<>();
        path.add(root);
    }

}
