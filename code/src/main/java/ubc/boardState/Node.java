package ubc.boardState;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private Node parent;
    private List<Node> childArray;
    private double winScore;
    private int visitCount;
    private State state;
    private Action lastMove;

    // Constructor for root node
    public Node() {
        this.childArray = new ArrayList<>();
        this.winScore = 0.0;
        this.visitCount = 0;
    }

    // Constructor for node with an initial state and action
    public Node(State state, Action lastMove) {
        this();
        this.state = state;
        this.lastMove = lastMove;
    }

    // Copy constructor
    public Node(Node node) {
        this.childArray = new ArrayList<>(node.getChildArray()); // Performs a shallow copy; deep copy if necessary
        this.state = node.state.clone(); // Assumes a clone method in State class
        this.parent = node.parent; // Shallow copy is typically sufficient
        this.winScore = node.winScore;
        this.visitCount = node.visitCount;
        this.lastMove = node.lastMove; // Assuming Action is immutable or that shallow copy is sufficient
    }

    // Standard getters and setters
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildArray() {
        return childArray;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.childArray.add(child);
    }

    public double getWinScore() {
        return winScore;
    }

    public void setWinScore(double winScore) {
        this.winScore = winScore;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Action getLastMove() {
        return lastMove;
    }

    public void setLastMove(Action lastMove) {
        this.lastMove = lastMove;
    }

    public boolean isLeaf() {
        return childArray.isEmpty();
    }

    // Increment the visit count and add to the win score
    public void addScore(double score) {
        this.winScore += score;
        incrementVisit();
    }

    // Find the child with the highest win score per visit
    public Node getChildWithMaxScore() {
        return childArray.stream()
                .max((node1, node2) -> Double.compare(node1.getWinScore() / node1.getVisitCount(),
                                                      node2.getWinScore() / node2.getVisitCount()))
                .orElse(null);
    }

    // This method could be useful for debugging or Minimax algorithm implementations
    public void printTree(int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append(" ");
        }
        System.out.println(indent.toString() + "Node: Score=" + winScore + ", Visits=" + visitCount);
        for (Node child : childArray) {
            child.printTree(depth + 1);
        }
    }
}
