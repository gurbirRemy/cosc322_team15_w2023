package ubc.boardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class State {
    private HashMap<ArrayList<Integer>, Integer> board;
    private int playerNo;
    private Action lastMove;
    private static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public State(HashMap<ArrayList<Integer>, Integer> board, int playerNo, Action lastMove) {
        this.board = new HashMap<>(board);
        this.playerNo = playerNo;
        this.lastMove = lastMove;
    }

    public State(State state) {
        this.board = new HashMap<>(state.getBoard());
        this.playerNo = state.getPlayerNo();
        this.lastMove = state.lastMove != null ? new Action(state.lastMove) : null;
    }


    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        board.entrySet().stream()
            .filter(entry -> entry.getValue() == playerNo)
            .forEach(entry -> {
                ArrayList<ArrayList<Integer>> validMoves = generateValidMovesForPiece(entry.getKey());
                validMoves.forEach(move -> {
                    ArrayList<ArrayList<Integer>> validArrows = generateValidMovesForPiece(move);
                    validArrows.forEach(arrow -> {
                        State newState = new State(new HashMap<>(board), 3 - playerNo, lastMove);
                        newState.board.put(entry.getKey(), 0); 
                        newState.board.put(move, playerNo); 
                        newState.board.put(arrow, 3); 
                        possibleStates.add(newState);
                    });
                });
            });
        return possibleStates;
    }

    private ArrayList<ArrayList<Integer>> generateValidMovesForPiece(ArrayList<Integer> pos) {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<>();
        for (int direction = 0; direction < 8; direction++) {
            int x = pos.get(0);
            int y = pos.get(1);
            while (true) {
                x += dx[direction];
                y += dy[direction];
                ArrayList<Integer> newPos = new ArrayList<>(Arrays.asList(x, y));
                if (x < 1 || x > 10 || y < 1 || y > 10 || board.containsKey(newPos)) break;
                moves.add(new ArrayList<>(newPos));
            }
        }
        return moves;
    }

    public float calculateHeuristicScore() {
        float mobilityScore = calculateMobilityScore();
        float territoryScore = calculateTerritoryScore();

        float heuristicScore = mobilityScore * 0.5f + territoryScore * 0.5f;
        
        return heuristicScore;
    }

    private float calculateMobilityScore() {
        int mobility = 0;
        for (Map.Entry<ArrayList<Integer>, Integer> entry : board.entrySet()) {
            if (entry.getValue() == playerNo) {
                mobility += generateValidMovesForPiece(entry.getKey()).size();
            }
        }
        return mobility;
    }

    private float calculateTerritoryScore() {
        float territoryScore = 0;
        for (Map.Entry<ArrayList<Integer>, Integer> entry : board.entrySet()) {
            if (entry.getValue() == playerNo) {
                territoryScore += generateValidMovesForPiece(entry.getKey()).size();
            }
        }
        return territoryScore;
    }

    // Utility method: Clone the current state
    public State clone() {
        return new State(new HashMap<>(this.board), this.playerNo, lastMove);
    }

    // Getters and Setters
    public HashMap<ArrayList<Integer>, Integer> getBoard() {
        return new HashMap<>(this.board);
    }

    public int getPlayerNo() {
        return this.playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }
    public Action getLastMove() {
        return lastMove;
    }
    public void setLastMove(Action lastMove) {
        this.lastMove = lastMove;
    }
}

public void randomPlay() {
        List<State> possibleStates = getAllPossibleStates();
        if (!possibleStates.isEmpty()) {
            Random rand = new Random();
            int index = rand.nextInt(possibleStates.size());
            State chosenState = possibleStates.get(index);
            this.board = chosenState.board; // Update the board to the new state
            this.playerNo = 3 - this.playerNo; // Switch player
        }
    }
    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }
}
