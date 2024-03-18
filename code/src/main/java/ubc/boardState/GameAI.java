package ubc.boardState;

public class GameAI {
	public static double minimax(Node node, int depth, boolean isMaximizingPlayer) {
        if (depth == 0 || node.isLeaf()) {
            return node.getWinScore(); 
        }

        if (isMaximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Node child : node.getChildArray()) {
                double eval = minimax(child, depth - 1, false); // Switch to minimizing player
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Node child : node.getChildArray()) {
                double eval = minimax(child, depth - 1, true); // Switch to maximizing player
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }
	
	public static double minimaxWithAlphaBeta(Node node, int depth, double alpha, double beta, boolean isMaximizingPlayer) {
        if (depth == 0 || node.isLeaf()) {
            return node.getWinScore();
        }

        if (isMaximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Node child : node.getChildArray()) {
                double eval = minimaxWithAlphaBeta(child, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Node child : node.getChildArray()) {
                double eval = minimaxWithAlphaBeta(child, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return minEval;
        }
    }

}
