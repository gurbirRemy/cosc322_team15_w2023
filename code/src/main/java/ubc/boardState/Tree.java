package ubc.boardState;

import java.util.LinkedList;
import java.util.Queue;

public class Tree {
    private Node root;

    public Tree() {
        root = new Node();
    }

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addChild(Node parent, Node child) {
        parent.getChildArray().add(child);
        child.setParent(parent);
    }

    public void removeChild(Node parent, Node child) {
        parent.getChildArray().remove(child);
        child.setParent(null);
    }

    // Perform a depth-first search traversal of the tree
    public void depthFirstSearch() {
        depthFirstSearch(root);
    }

    private void depthFirstSearch(Node node) {
        if (node != null) {
            // Process the current node
            System.out.println(node.getState()); // Example: print the state
            // Recursively traverse the child nodes
            for (Node child : node.getChildArray()) {
                depthFirstSearch(child);
            }
        }
    }

    // Perform a breadth-first search traversal of the tree
    public void breadthFirstSearch() {
        if (root == null) return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node node = queue.poll();
            // Process the current node
            System.out.println(node.getState()); // Example: print the state
            // Enqueue child nodes for further processing
            for (Node child : node.getChildArray()) {
                queue.add(child);
            }
        }
    }
}
