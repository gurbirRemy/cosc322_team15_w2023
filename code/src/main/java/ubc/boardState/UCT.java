package ubc.boardState;

import java.util.List;

public class UCT {
    public static final double EXPLORATION_CONSTANT = Math.sqrt(2);

    public static Node findBestNodeWithUCT(Node node) {
        int parentVisit = node.getVisitCount();
        return node.getChildArray().stream()
                .max((node1, node2) -> Double.compare(uctValue(parentVisit, node1.getWinScore(), node1.getVisitCount()),
                                                     uctValue(parentVisit, node2.getWinScore(), node2.getVisitCount())))
                .orElseThrow(() -> new IllegalStateException("No best node found"));
    }

    private static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Double.MAX_VALUE;
        }
        return ((double) nodeWinScore / (double) nodeVisit) + EXPLORATION_CONSTANT * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
    }
}
