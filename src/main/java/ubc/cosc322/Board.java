package ubc.cosc322;

public class Board {
	private static final int THRESHOLD = 200000;	//Threshold for the number of nodes to be expanded by the minimax algorithm.
	private static final int DIM = 10;
	private static final int[][] BOARD_STATE_BEGINNING = {

			{0, 0, 0, 2, 0, 0, 2, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{2, 0, 0, 0, 0, 0, 0, 0, 0, 2},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
	};
	
	public static int[][] getBoardSetup(){
		return BOARD_STATE_BEGINNING;
	}
	
	public void printBoard() {
		for (int i = 0; i < BOARD_STATE_BEGINNING.length; i++) {
            for (int j = 0; j < BOARD_STATE_BEGINNING[i].length; j++) {
                System.out.print(BOARD_STATE_BEGINNING[i][j] + " ");
            }
            System.out.println();
        }
    }
	}
	

