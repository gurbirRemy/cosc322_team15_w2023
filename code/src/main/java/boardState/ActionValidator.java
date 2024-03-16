package boardState;


import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ActionValidator {
	// WHATEVER BOARD IS CREATED, chnage with his code
	private HashMap<ArrayList<Integer>, Integer> board;
	
	public ActionValidator(HashMap<ArrayList<Integer>, Integer> board) {
        this.board = board;
    }

    public boolean isValidMove(ArrayList<Integer> queenPrev, ArrayList<Integer> queenNext, ArrayList<Integer> arrow) {
        // cases- queen's own position, next move, arrows position, allowed direction
        // if queen's previous position is valid and contains a queen of the current
        // player
        if (!isValidPosition(queenPrev) || !isCurrentPlayerQueen(queenPrev)) {
        	System.out.println(isValidPosition(queenPrev));
        	System.out.println(isCurrentPlayerQueen(queenPrev));
        	System.out.println(1);
        	return false;
        }

        // if queen's next position is valid and empty
        if (!isValidPosition(queenNext) || !isEmptyPosition(queenNext)) {
        	System.out.println(2);
            return false;
        }

        // if arrow position is valid and empty
        if (!isValidPosition(arrow) || !isEmptyPosition(arrow)) {
        	System.out.println(3);
            return false;
        }

        // if the move follows queen's movement pattern (horizontal, vertical, diagonal)
        if (!isValidQueenMove(queenPrev, queenNext)) {
        	System.out.println(4);
            return false;
        }

        // Check if there are any obstacles (other queens) in the path
        if (!isPathClear(queenPrev, queenNext)) {
        	System.out.println(5);
        	
            return false;
        }

        // Check if the arrow is shot in a valid direction (same as queen's movement)
        if (!isValidArrowDirection(queenNext, arrow)) {
        	System.out.println(6);
            return false;
        }
        
        if (!isarrowPathClear(queenNext, arrow)) {
            return false;
        }

        return true;
    }

    // Check if the position lies in the domain
    private boolean isValidPosition(ArrayList<Integer> position) {
        int x = position.get(0);//x 
        int y = position.get(1);//y 
        System.out.println(board.get(position)+"{ "+ position.get(0)+" "+ position.get(1)+"}");
        
        return x >= 0 && x <= 10 && y >= 0 && y <= 10;
    }

    // check if current positions belongs to a queen
    private boolean isCurrentPlayerQueen(ArrayList<Integer> position) {
        int player = board.get(position);
        System.out.println(board.get(position)+"{ "+ position.get(0)+" "+ position.get(1)+"}");
        return player == 1 || player == 2; // Assuming 1 represents white player's queen, and 2 black's
    }

    // check if position is empty, positions we move to
    private boolean isEmptyPosition(ArrayList<Integer> position) {
        return board.get(position) == 0;
    }

    // Now check if move is horizontal, vertical or diagonal only
    private boolean isValidQueenMove(ArrayList<Integer> queenPrev, ArrayList<Integer> queenNext) {
        // calculate the difference between the x and y
        int deltaX = Math.abs(queenNext.get(0) - queenPrev.get(0));
        int deltaY = Math.abs(queenNext.get(1) - queenPrev.get(1));

        // Check if the queen is not moving at all
        if (deltaX == 0 && deltaY == 0) {
            return false;
        }
        // return true if one of x and y is non-zero, or x=y (diagonal)
        return deltaX == 0 || deltaY == 0 || deltaX == deltaY;
    }

    // The way is clear to reach it
    private boolean isPathClear(ArrayList<Integer> queenPrev, ArrayList<Integer> queenNext) {
        // check direction of the movement
        int deltaX = Integer.compare(queenNext.get(0), queenPrev.get(0));
        int deltaY = Integer.compare(queenNext.get(1), queenPrev.get(1));
        // next box to move
        int x = queenPrev.get(0) + deltaX;
        int y = queenPrev.get(1) + deltaY;
        // discontinue while loop when x,y = queenNext
        while (!((deltaX != 0 && x == queenNext.get(0)) || (deltaY != 0 && y == queenNext.get(1)))) {
            if (board.get(new ArrayList<>(Arrays.asList(x, y))) != 0) {
                return false;
            }
            x += deltaX;
            y += deltaY;
        }
      
        return true;
    }

    // arrow direction
    private boolean isValidArrowDirection(ArrayList<Integer> queenPrev, ArrayList<Integer> arrow) {
        int deltaX = Math.abs(arrow.get(0) - queenPrev.get(0));
        int deltaY = Math.abs(arrow.get(1) - queenPrev.get(1));

        // Check if the arrow is not moving at all
        if (deltaX == 0 && deltaY == 0) {
            return false;
        }
        return deltaX == 0 || deltaY == 0 || deltaX == deltaY;
    }
    
    
    private boolean isarrowPathClear(ArrayList<Integer> newpos, ArrayList<Integer> sa) {
    	// sa is shoot arrow
        // check direction of the movement
        int deltaX = Integer.compare(sa.get(0), newpos.get(0));
        int deltaY = Integer.compare(sa.get(1), newpos.get(1));
        // next box to move
        int x = newpos.get(0) + deltaX;
        int y = newpos.get(1) + deltaY;
        // discontinue while loop when x,y = queenNext
        while (!((deltaX != 0 && x == sa.get(0)) || (deltaY != 0 && y == sa.get(1)))) {
            if (board.get(new ArrayList<>(Arrays.asList(x, y))) != 0) {
                return false;
            }
            x += deltaX;
            y += deltaY;
        }
        return true;
    }


    // test case
//    public static void main(String[] args) {
//        // Example usage:
//        HashMap<ArrayList<Integer>, Integer> board = new HashMap<>();
//        // Populate the board with appropriate values...
//        // Create an instance of ActionValidator with the board
//        ActionValidator validator = new ActionValidator(board);
//        // Define queen's previous position, queen's next position, and arrow position
//        ArrayList<Integer> queenPrev = new ArrayList<>(List.of(3, 0));
//        ArrayList<Integer> queenNext = new ArrayList<>(List.of(6, 3));
//        ArrayList<Integer> arrow = new ArrayList<>(List.of(9, 6));
//        // Check if the move is valid
//        boolean isValid = validator.isValidMove(queenPrev, queenNext, arrow);
//        System.out.println("Is the move valid? " + isValid);
//    }
}