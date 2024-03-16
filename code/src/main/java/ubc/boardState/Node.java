package ubc.boardState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Node {
    private Node parent;
    private List<Node> childArray;
    private double winScore;
    private int visitCount;
    private State state;

    public Node() {
        this.childArray = new ArrayList<>();
    }

    public Node(State state) {
        this.state = state;
        this.childArray = new ArrayList<>();
    }

    public Node(Node node) {
        this.childArray = new ArrayList<>();
        this.state = new State(node.getState());
        this.parent = node.getParent();
        this.winScore = node.getWinScore();
        this.visitCount = node.getVisitCount();
        for (Node child : node.getChildArray()) {
            this.childArray.add(new Node(child));
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildArray() {
        return childArray;
    }

    public void setChildArray(List<Node> childArray) {
        this.childArray = childArray;
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

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void incrementVisit() {
        this.visitCount++;
    }

    public void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE) {
            this.winScore += score;
        }
    }

    public boolean isLeaf() {
        return this.childArray.isEmpty();
    }

    public Node getRandomChildNode() {
        Random random = new Random();
        return this.childArray.get(random.nextInt(this.childArray.size()));
    }

    public Node getChildWithMaxScore() {
        return this.childArray.stream()
                .max((a, b) -> {
                    double scoreA = a.getWinScore() / a.getVisitCount();
                    double scoreB = b.getWinScore() / b.getVisitCount();
                    return Double.compare(scoreA, scoreB);
                })
                .orElse(null);
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.childArray.add(child);
    }
}
