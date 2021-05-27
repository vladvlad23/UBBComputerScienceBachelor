package ro.ubbcluj;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    int information;
    List<Node> children;

    public Node(int information) {
        this.information = information;
        children = new ArrayList<>();
    }

    public int getInformation() {
        return information;
    }

    public void setInformation(int information) {
        this.information = information;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public void addChildren(List<Node> children) {
        this.children.addAll(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return information == node.information;
    }

    @Override
    public int hashCode() {
        return Objects.hash(information);
    }
}