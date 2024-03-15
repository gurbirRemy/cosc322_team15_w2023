package ubc.cosc322;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.ArrayUtils;
//
//import boardState.Action;
//import boardState.ActionValidator;
//import sfs2x.client.entities.Room;
//import ygraph.ai.smartfox.games.amazons.*;
//import ygraph.ai.smartfox.games.BaseGameGUI;
//import ygraph.ai.smartfox.games.GameClient;
//import ygraph.ai.smartfox.games.GameMessage;
//import ygraph.ai.smartfox.games.GamePlayer;
//
///**
// * An example illustrating how to implement a GamePlayer
// * 
// * @author Yong Gao (yong.gao@ubc.ca) Jan 5, 2021
// *
// */
//public class Main extends GamePlayer {
//
//	private GameClient gameClient = null;
//	private BaseGameGUI gamegui = null;
//	private String[] xaxis = { "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
//	public ArrayList<Integer> boardState = null;
//	private String userName = null;
//	private String passwd = null;
//	private String playercolor = null;
//	private String playerblack = null;
//	private String playerwhite = null;
//	private ArrayList<Integer> gameState = null;
//	private static HashMap<ArrayList<Integer>, Integer> board = new HashMap<>();
//
//	/**
//	 * The main method
//	 * 
//	 * @param args for name and passwd (current, any string would work)
//	 */
//	public static void main(String[] args) {
//		GamePlayer player;
//		// Create HumanPlayer
//		 player = new HumanPlayer();
//
//		// Create Bot
//		//player = new Main(args[0], args[1]);
//		createBoard();
//		
//		// creating a public validator 
////		ActionValidator validator ;
////		validator  = new ActionValidator(board);
//
//		if (player.getGameGUI() == null) {
//			player.Go();
//		} else {
//			BaseGameGUI.sys_setup();
//			java.awt.EventQueue.invokeLater(new Runnable() {
//				public void run() {
//					player.Go();
//
//				}
//
//			});
//
//		}
//
//	}
//
//	/**
//	 * Any name and passwd
//	 * 
//	 * @param userName
//	 * @param passwd
//	 */
//	public Main(String userName, String passwd) {
//		this.userName = userName;
//		this.passwd = passwd;
//
//		// To make a GUI-based player, create an instance of BaseGameGUI
//		// and implement the method getGameGUI() accordingly
//		this.gamegui = new BaseGameGUI(this);
//
//	}
//
//	@Override
//	public void onLogin() {
//		userName = gameClient.getUserName();
//		// System.out.println(gameClient.getRoomList());
//		if (gamegui != null) {
//			gamegui.setRoomInformation(gameClient.getRoomList());
//		}
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
//		if (messageType.equals(GameMessage.GAME_STATE_BOARD)) {
//			System.out.println((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
//			gameState = ((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
//			boardState = gameState;
//			gamegui.setGameState(gameState);
//
//		}
//
//		else if (messageType.equals(GameMessage.GAME_ACTION_START)) {
//			// Figure out who is black and white
//			playerblack = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
//			System.out.println(playerblack + " has the Black pieces");
//			playerwhite = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
//			System.out.println(playerwhite + " has the White pieces");
//
//			// Provide the positions as int[] and not as array list!
//
//			/*
//			 * STEPS - Array list is only used for starting moves. converts the Table into a
//			 * hashmap<int[],Integer> use the key as int[] to validate the move.
//			 */
//			ArrayList<Integer> currentPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
//			ArrayList<Integer> NextPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
//			ArrayList<Integer> ArrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
//
//
//			boolean isValidMove = false;
//		
//				ActionValidator validator = new ActionValidator(board);
//			 isValidMove = validator.isValidMove(currentPos, NextPos, ArrowPos);
//			
//			
//			
//			System.out.println("Is the move valid? " + isValidMove);
//
//			if (getGameClient().getUserName().equals(msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))) {
//				playercolor = "black";
//				Move();
//			} else {
//				playercolor = "white";
//				System.out.println("Waiting for opponent's move.");
//			}
//
//			
//		}
//
//		else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
//			gamegui.updateGameState(msgDetails);
//
//			System.out.println(msgDetails);
//
//			ArrayList<Integer> currentPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
//			ArrayList<Integer> NextPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
//			ArrayList<Integer> ArrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
//			
//			boolean isValidMove = false;
//			
//			ActionValidator validator = new ActionValidator(board);
//		    isValidMove = validator.isValidMove(currentPos, NextPos, ArrowPos);
//		
//		
//		
//		System.out.println("Is the move valid? " + isValidMove);
//
//			
//			String oppMove = (xaxis[currentPos.get(1)] + (currentPos.get(0)) + "->" + xaxis[NextPos.get(1)]
//					+ (NextPos.get(0)) + "(Arrow: " + xaxis[ArrowPos.get(1)] + (ArrowPos.get(0)) + ")");
//			if (playercolor.equals("white")) {
//				System.out.println("Move by " + playerblack + " : " + oppMove);
//			} else {
//				System.out.println("Move by " + playerwhite + " : " + oppMove);
//			}
//			System.out.println("hi" + (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
//			// Move();
//		}
//		return true;
//	}
//
//	@Override
//	public String userName() {
//		return userName;
//	}
//
//	@Override
//	public GameClient getGameClient() {
//		// TODO Auto-generated method stub
//		return this.gameClient;
//	}
//
//	@Override
//	public BaseGameGUI getGameGUI() {
//		// TODO Auto-generated method stub
//		return this.gamegui;
//	}
//
//	@Override
//	public void connect() {
//		// TODO Auto-generated method stub
//		gameClient = new GameClient(userName, passwd, this);
//	}
//
//	private void Move() {
//
//		Action move = new Action(4, 10, 7, 7, 7, 2);// x ,y, x, y, x, y
//		System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
//		getGameClient().sendMoveMessage(move.makeMap());
//		getGameGUI().updateGameState(move.makeMap());
//	}
//
//	private static void createBoard() {
//		for (int i = 1; i <= 10; i++) {
//			for (int j = 1; j <= 10; j++) {
//				ArrayList<Integer> coordinates = new ArrayList<>();
//				coordinates.add(i);
//				coordinates.add(j);
//				board.put(coordinates, 0);
//			}
//		}
//	}
//
//	private void updateBoard(ArrayList<Integer> boardstate, ArrayList<Integer> currentPos, ArrayList<Integer> nextPos,
//			ArrayList<Integer> arrowPos) {
//		/*
//		 * White Queen = 1, Black Queen = 2, Arrow = 3 Initial State [0, 0, 0, 0, 0, 0,
//		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,
//		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
//		 * 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//		 * 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0]
//		 */
//
//	}
//
//	private void makeRandomMove() {
//		// ArrayList<Action> actions = ActionGenerator.generateActions(state, color);
//		// Action selectedAction = actions.get((int) (Math.random() * actions.size()));
//		// state = new State(state, selectedAction);
//		// getGameClient().sendMoveMessage(selectedAction.makeMap());
//		// getGameGUI().updateGameState(selectedAction.makeMap());
//	}
//
//}// end of class


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;

import boardState.Action;
import boardState.ActionValidator;
import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.amazons.*;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * 
 * @author Yong Gao (yong.gao@ubc.ca) Jan 5, 2021
 *
 */
public class Main extends GamePlayer {

	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;
	private String[] xaxis = { "0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	public ArrayList<Integer> boardState = null;
	private String userName = null;
	private String passwd = null;
	private String playercolor = null;
	private String playerblack = null;
	private String playerwhite = null;
	private ArrayList<Integer> gameState = null;
	private static final int[] dx = { 1, 1, 1, 0, 0, -1, -1, -1 };
	private static final int[] dy = { 1, 0, -1, 1, -1, 1, 0, -1 };
	public static HashMap<ArrayList<Integer>, Integer> board = new HashMap<>();

	/**
	 * The main method
	 * 
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		GamePlayer player;
		//GamePlayer p2;
		// Create HumanPlayer
		//player = new HumanPlayer();

		// Create Bot
		player = new Main(args[0], args[1]);
		
		createBoard();

		if (player.getGameGUI() == null) {
			player.Go();
		} else {
			BaseGameGUI.sys_setup();
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					player.Go();

				}

			});

		}

	}

	/**
	 * Any name and passwd
	 * 
	 * @param userName
	 * @param passwd
	 */
	public Main(String userName, String passwd) {
		this.userName = userName;
		this.passwd = passwd;

		// To make a GUI-based player, create an instance of BaseGameGUI
		// and implement the method getGameGUI() accordingly
		this.gamegui = new BaseGameGUI(this);

	}

	@Override
	public void onLogin() {
		userName = gameClient.getUserName();
		// System.out.println(gameClient.getRoomList());
		if (gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}

	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		if (messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			System.out.println((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			gameState = ((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			boardState = gameState;
			// testing board 
			System.out.println("test1");
			printBoard(board);
			gamegui.setGameState(gameState);

		}

		else if (messageType.equals(GameMessage.GAME_ACTION_START)) {
			// Figure out who is black and white
			createBoard();
		
			playerblack = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
			System.out.println(playerblack + " has the Black pieces");
			playerwhite = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
			System.out.println(playerwhite + " has the White pieces");

			if (getGameClient().getUserName().equals(msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))) {
				playercolor = "black";
				makeRandomMove(2);
			} else {
				playercolor = "white";
				System.out.println("Waiting for opponent's move.");
			}

		} else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {

			gamegui.updateGameState(msgDetails);

			ArrayList<Integer> currentPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			ArrayList<Integer> NextPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
			ArrayList<Integer> ArrowPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);

			ArrayList<Integer> c = new ArrayList<Integer>();
			c.addAll(currentPos);
			ArrayList<Integer> n = new ArrayList<Integer>();
			n.addAll(NextPos);
			ArrayList<Integer> a = new ArrayList<Integer>();
			a.addAll(ArrowPos);

			String Move = (xaxis[currentPos.get(1)] + (currentPos.get(0)) + "->" + xaxis[NextPos.get(1)]
					+ (NextPos.get(0)) + "(Arrow: " + xaxis[ArrowPos.get(1)] + (ArrowPos.get(0)) + ")");
			if (playercolor.equals("white")) {
				System.out.println("Move by " + playerblack + " : " + Move);
				//updateBoard(c, n, a); // first we are updating only then passing ?
				// testing baord
				System.out.println("test2_white");
				printBoard(board);
				ActionValidator validator = new ActionValidator(board);
				System.out.println(validator.isValidMove(c, n, a));
				updateBoard(c, n, a);
				makeRandomMove(1);
			} else {
				System.out.println("Move by " + playerwhite + " : " + Move);
				
				System.out.println("test2_black");
				printBoard(board);
				ActionValidator validator = new ActionValidator(board);
				if(validator.isValidMove(c, n, a))
				 System.out.println("Correct move");
				else 
					System.out.println("Wrong move");
				updateBoard(c, n, a);
				makeRandomMove(2);
			}

		}
		return true;
	}

	@Override
	public String userName() {
		return userName;
	}

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
		gameClient = new GameClient(userName, passwd, this);
	}

	private void Move() {

		Action move = new Action(4, 1, 8, 5, 6, 7);// x ,y, x, y, x, y
		System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
		getGameClient().sendMoveMessage(move.makeMap());
		getGameGUI().updateGameState(move.makeMap());

		ArrayList<Integer> c1 = new ArrayList<Integer>();
		c1.add(1);// y
		c1.add(4);// x
		ArrayList<Integer> n1 = new ArrayList<Integer>();
		n1.add(5);
		n1.add(8);
		ArrayList<Integer> a1 = new ArrayList<Integer>();
		a1.add(7);
		a1.add(6);

		updateBoard(c1, n1, a1);

	}

	private static void createBoard() {
		for (int i = 1; i <= 10; i++) {
			for (int j = 1; j <= 10; j++) {
				ArrayList<Integer> coordinates = new ArrayList<>();
				coordinates.add(i);
				coordinates.add(j);
				if (((i == 1) & (j == 7)) | ((i == 4) & (j == 10)) || ((i == 7) & (j == 10))
						|| ((i == 10) & (j == 7))) {
					board.put(coordinates, 2);
				} else if (((i == 1) & (j == 4)) | ((i == 4) & (j == 1)) || ((i == 7) & (j == 1))
						|| ((i == 10) & (j == 4))) {
					board.put(coordinates, 1);
				} else
					board.put(coordinates, 0);
			}
		}

		/*
		 * for (Entry<ArrayList<Integer>, Integer> board : board.entrySet()) {
		 * System.out.println(board.getKey() + ": " + board.getValue()); }
		 */
	}

	private void updateBoard(ArrayList<Integer> current, ArrayList<Integer> next, ArrayList<Integer> arrow) {
		/*
		 * White Queen = 1, Black Queen = 2, Arrow = 3 Initial State [0, 0, 0, 0, 0, 0,
		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0,
		 * 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0,
		 * 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
		 * 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0]
		 */
		String color = null;
		// change (y,x) to (x,y)
		int a = current.get(0);
		current.set(0, current.get(1));
		current.set(1, a);

		a = next.get(0);
		next.set(0, next.get(1));
		next.set(1, a);

		a = arrow.get(0);
		arrow.set(0, arrow.get(1));
		arrow.set(1, a);

		// which piece
		if (board.get(current) == 1) {
			color = "white";
		} else if (board.get(current) == 2) {
			color = "black";
		} else {
			System.out.println("Invalid Move!");
			// System.exit(0);
		}
		// check move legality() param -> board, curr, next, arrow
		// if legal

		board.put(current, 0);
		if (color.equals("black")) {
			board.put(next, 2);
			board.put(arrow, 3);
		} else if (color.equals("white")) {
			board.put(next, 1);
			board.put(arrow, 3);
		}
//		 for (Entry<ArrayList<Integer>, Integer> board : board.entrySet()) {
//		 System.out.println(board.getKey() + ": " + board.getValue());
//		 }

	}

	private void makeRandomMove(int c) {
		// current positions
		List<ArrayList<Integer>> whitecurrentpositions = board.entrySet().stream()
				.filter(entry -> entry.getValue() == 1).map(entry -> entry.getKey()).collect(Collectors.toList());
		// System.out.println(whitecurrentpositions);

		List<ArrayList<Integer>> blackcurrentpositions = board.entrySet().stream()
				.filter(entry -> entry.getValue() == 2).map(entry -> entry.getKey()).collect(Collectors.toList());
		// System.out.println(blackcurrentpositions);

		// available squares
		List<ArrayList<Integer>> emptysquares = board.entrySet().stream().filter(entry -> entry.getValue() == 0)
				.map(entry -> entry.getKey()).collect(Collectors.toList());

		// possiblesquares for white
		ArrayList<ArrayList<Integer>> wq1 = new ArrayList<>();// .get0
		ArrayList<ArrayList<Integer>> wq2 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> wq3 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> wq4 = new ArrayList<>();// .get3

		for (int i = 0; i < whitecurrentpositions.size(); i++) {
			int x = whitecurrentpositions.get(i).get(0);
			int y = whitecurrentpositions.get(i).get(1);
			if (i == 0) {
				wq1 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 1) {
				wq2 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 2) {
				wq3 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 3) {
				wq4 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			}
		}

		// possiblesquares for black
		ArrayList<ArrayList<Integer>> bq1 = new ArrayList<>();// .get0
		ArrayList<ArrayList<Integer>> bq2 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> bq3 = new ArrayList<>();
		ArrayList<ArrayList<Integer>> bq4 = new ArrayList<>();// .get3

		for (int i = 0; i < blackcurrentpositions.size(); i++) {
			int x = blackcurrentpositions.get(i).get(0);
			int y = blackcurrentpositions.get(i).get(1);
			if (i == 0) {
				bq1 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 1) {
				bq2 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 2) {
				bq3 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			} else if (i == 3) {
				bq4 = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(x, y).clone();
			}
		}

		// select random queen
		Random rand = new Random();
		ArrayList<Integer> c2 = new ArrayList<Integer>();
		int a = rand.nextInt(4);
		if (c == 1) {
			c2 = whitecurrentpositions.get(a);
		} else if (c == 2) {
			c2 = blackcurrentpositions.get(a);
		}

		// select random next move
		ArrayList<Integer> n2 = new ArrayList<Integer>();
		if (c == 1) {
			if (a == 0) {
				n2 = wq1.get(rand.nextInt(wq1.size()));
			} else if (a == 1) {
				n2 = wq2.get(rand.nextInt(wq2.size()));
			} else if (a == 2) {
				n2 = wq3.get(rand.nextInt(wq3.size()));
			} else if (a == 3) {
				n2 = wq4.get(rand.nextInt(wq4.size()));
			}
		} else if (c == 2) {
			if (a == 0) {
				n2 = bq1.get(rand.nextInt(bq1.size()));
			} else if (a == 1) {
				n2 = bq2.get(rand.nextInt(bq2.size()));
			} else if (a == 2) {
				n2 = bq3.get(rand.nextInt(bq3.size()));
			} else if (a == 3) {
				n2 = bq4.get(rand.nextInt(bq4.size()));
			}
		}

		// select random arrow move
		ArrayList<ArrayList<Integer>> aq = new ArrayList<>();
		aq = (ArrayList<ArrayList<Integer>>) generatePossibleMoves(n2.get(0), n2.get(1)).clone();
		ArrayList<Integer> a2 = new ArrayList<Integer>();
		a2 = aq.get(rand.nextInt(aq.size()));

		//send move to server
		Action move = new Action(c2.get(0), c2.get(1), n2.get(0), n2.get(1), a2.get(0), a2.get(1));
		System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
		getGameClient().sendMoveMessage(move.makeMap());
		getGameGUI().updateGameState(move.makeMap());
		
		//update board
		ArrayList<Integer> c3 = new ArrayList<Integer>();
		c3.add(c2.get(1));// y
		c3.add(c2.get(0));// x
		ArrayList<Integer> n3 = new ArrayList<Integer>();
		n3.add(n2.get(1));
		n3.add(n2.get(0));
		ArrayList<Integer> a3 = new ArrayList<Integer>();
		a3.add(a2.get(1));
		a3.add(a2.get(0));

		updateBoard(c3, n3, a3);

	}

	private static ArrayList<ArrayList<Integer>> generatePossibleMoves(int x, int y) {
		ArrayList<ArrayList<Integer>> possibleMoves = new ArrayList<>();

		// Check all 8 directions
		for (int i = 0; i < 8; i++) {
			int cx = x;
			int cy = y;
			// Move in direction (dx[i], dy[i])
			while (true) {
				cx += dx[i];
				cy += dy[i];
				// Check if out of bounds
				if (cx < 1 || cx >= 11 || cy < 1 || cy >= 11) {
					break;
				}
				// Check if the cell is occupied
				ArrayList<Integer> c = new ArrayList<Integer>();
				c.add(cx);
				c.add(cy);
				if (board.get(c) != 0) {
					break;
				}
				// Add the move
				possibleMoves.add(new ArrayList<>(Arrays.asList(cx, cy)));
			}
		}
		System.out.println(possibleMoves);
		return possibleMoves;
	}
	
	private static void printBoard(HashMap<ArrayList<Integer>, Integer> board) {
	    int[][] matrix = new int[10][10];
	    // Fill the matrix with values from the board
	    for (Map.Entry<ArrayList<Integer>, Integer> entry : board.entrySet()) {
	        ArrayList<Integer> position = entry.getKey();
	        int value = entry.getValue();
	        int x = position.get(0) - 1; // Adjust for zero-based indexing
	        int y = position.get(1) - 1; // Adjust for zero-based indexing
	        matrix[y][x] = value;
	    }

	    // Print the matrix
	    for (int i = 0; i < 10; i++) {
	        for (int j = 0; j < 10; j++) {
	            System.out.print(matrix[i][j] + " ");
	            // Print coordinates (x, y)
	            System.out.print("(" + (i + 1) + "," + (j + 1) + "): ");
	        }
	        System.out.println();
	    }
	}


}// end of class
