package ubc.boardState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class State {
    private HashMap<ArrayList<Integer>, Integer> board;
    private int playerNo;
    private static final int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private static final int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

    public State(HashMap<ArrayList<Integer>, Integer> board, int playerNo) {
        this.board = new HashMap<>(board);
        this.playerNo = playerNo;
    }


	public State(State state) {
		this.board = new HashMap<>(state.getBoard());
        this.playerNo = state.getPlayerNo();// TODO Auto-generated constructor stub
	}


	public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        board.entrySet().stream()
            .filter(entry -> entry.getValue() == playerNo)
            .forEach(entry -> {
                ArrayList<ArrayList<Integer>> validMoves = generateValidMovesForPiece(entry.getKey());
                validMoves.forEach(move -> {
                    // For each move, consider all possible arrow shots
                    ArrayList<ArrayList<Integer>> validArrows = generateValidMovesForPiece(move);
                    validArrows.forEach(arrow -> {
                        // Create a new state for each possible move and arrow shot combination
                        State newState = new State(new HashMap<>(board), 3 - playerNo); // Switch player
                        newState.board.put(entry.getKey(), 0); // Original position now empty
                        newState.board.put(move, playerNo); // Move the queen to the new position
                        newState.board.put(arrow, 3); // Place the arrow
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
            // Move in the direction until you hit an obstacle or the edge of the board
            while (true) {
                x += dx[direction];
                y += dy[direction];
                ArrayList<Integer> newPos = new ArrayList<>(Arrays.asList(x, y));
                if (x <= 0 || x > 10 || y <= 0 || y > 10 || board.containsKey(newPos)) break; // Stop at the edge or an obstacle
                moves.add(new ArrayList<>(newPos));
            }
        }
        return moves;
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

    // Utility method: Clone the current state
    public State clone() {
        return new State(new HashMap<>(this.board), this.playerNo);
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
}
