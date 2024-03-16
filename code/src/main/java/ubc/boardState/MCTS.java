package ubc.boardState;

import java.util.List;

public class MCTS {
    public static final int WIN_SCORE = 10;
    private int opponent;

    public Node findNextMove(State initialState, int playerNo) {
        long end = System.currentTimeMillis() + 1000;
        Node rootNode = new Node(new State(initialState.getBoard(), playerNo));
        Tree tree = new Tree(rootNode);

        while (System.currentTimeMillis() < end) {
            Node promisingNode = selectPromisingNode(rootNode);
            if (!promisingNode.getState().isEndState()) {
                expandNode(promisingNode);
            }
            Node nodeToExplore = promisingNode;
            if (!promisingNode.getChildArray().isEmpty()) {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            backPropagation(nodeToExplore, playoutResult);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode;
    }

    private Node selectPromisingNode(Node rootNode) {
        Node node = rootNode;
        while (!node.isLeaf()) {
            node = UCT.findBestNodeWithUCT(node);
        }
        return node;
    }

    private void expandNode(Node node) {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParent(node);
            node.getChildArray().add(newNode);
        });
    }

    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoardStatusa();
        if (boardStatus == opponent) {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        while (boardStatus == State.IN_PROGRESS) {
            tempState.togglePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoardStatus();
        }
        return boardStatus;
    }

    private void backPropagation(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayerNo() == playerNo) {
                tempNode.getState().addScore(WIN_SCORE);
            }
            tempNode = tempNode.getParent();
        }
    }
}
