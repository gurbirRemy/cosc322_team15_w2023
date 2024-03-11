


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
	private String[] xaxis = {"0", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
	public ArrayList<Integer> boardState = null;
	private String userName = null;
	private String passwd = null;
	private String playercolor = null;
	private String playerblack = null;
	private String playerwhite = null;
	private ArrayList<Integer> gameState = null;
	public static HashMap<ArrayList<Integer>, Integer> board = new HashMap<>();
	/**
	 * The main method
	 * 
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		GamePlayer player; 
		//Create HumanPlayer
		//player = new HumanPlayer();
		
		//Create Bot
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
		//System.out.println(gameClient.getRoomList());
		if (gamegui != null) {
			gamegui.setRoomInformation(gameClient.getRoomList());
		}

	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		 if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			 System.out.println((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			 gameState = ((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			 boardState = gameState;
			 gamegui.setGameState(gameState);
			
		 }
		
		 else if(messageType.equals(GameMessage.GAME_ACTION_START)){
			 //Figure out who is black and white
			createBoard();
			playerblack = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
			System.out.println(playerblack + " has the Black pieces");
			playerwhite = (String) msgDetails.get(AmazonsGameMessage.PLAYER_WHITE);
			System.out.println(playerwhite + " has the White pieces");
			
			if (getGameClient().getUserName().equals(msgDetails.get(AmazonsGameMessage.PLAYER_BLACK))){
				playercolor = "black";
				Move();
			}
			else {
				playercolor = "white";
				System.out.println("Waiting for opponent's move.");
			}
			
			 
		 }
		 else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
			 
			 gamegui.updateGameState(msgDetails);
			 
			 ArrayList<Integer> currentPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			 ArrayList<Integer> NextPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
			 ArrayList<Integer> ArrowPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS);
			 
			 ArrayList<Integer> c = new ArrayList<Integer>();
			 c.addAll(currentPos);
			 ArrayList<Integer> n = new ArrayList<Integer>();
			 n.addAll(NextPos);
			 ArrayList<Integer> a = new ArrayList<Integer>();
			 a.addAll(ArrowPos);
			 
			 
			 String Move = (xaxis[currentPos.get(1)] + (currentPos.get(0)) + "->" + xaxis[NextPos.get(1)] + (NextPos.get(0)) + "(Arrow: " + xaxis[ArrowPos.get(1)] + (ArrowPos.get(0)) + ")");
			 if (playercolor.equals("white")){
				System.out.println("Move by " + playerblack + " : " + Move); 
				updateBoard(c, n, a);
				ActionValidator validator = new ActionValidator(board);
				System.out.println(validator.isValidMove(c, n, a)); 
				makeRandomMove(1);
			 }
			 else {
				 System.out.println("Move by " + playerwhite + " : " + Move); 
				updateBoard(c, n, a);
				ActionValidator validator = new ActionValidator(board);
				System.out.println(validator.isValidMove(c, n, a)); 
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
		
		Action move = new Action(4, 1, 8, 5, 6, 7);//x ,y, x, y, x, y
		System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
		getGameClient().sendMoveMessage(move.makeMap());
		getGameGUI().updateGameState(move.makeMap());
		
		ArrayList<Integer> c1 = new ArrayList<Integer>();
		 c1.add(1);//y
		 c1.add(4);//x
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
                if (((i == 1) & (j == 7))|((i == 4) & (j == 10))||((i == 7) & (j == 10))||((i == 10) & (j == 7))){
                	board.put(coordinates, 2);
                }
                else if (((i == 1) & (j == 4))|((i == 4) & (j == 1))||((i == 7) & (j == 1))||((i == 10) & (j == 4))) {
                	board.put(coordinates, 1);
                }
                else board.put(coordinates, 0);
            }
        }
		
		/*for (Entry<ArrayList<Integer>, Integer> board : board.entrySet()) {
            System.out.println(board.getKey() + ": " + board.getValue());
        }*/
	}
	
	private void updateBoard(ArrayList<Integer> current, ArrayList<Integer> next, ArrayList<Integer> arrow) {
		/* White Queen = 1, Black Queen = 2, Arrow = 3
		 * Initial State
		 * [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
 			0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0]  */
		String color = null;
		//change (y,x) to (x,y)
		int a = current.get(0);
		current.set(0, current.get(1));
		current.set(1, a);
		
		a = next.get(0);
		next.set(0, next.get(1));
		next.set(1, a);
		
		a = arrow.get(0);
		arrow.set(0, arrow.get(1));
		arrow.set(1, a);
		
		//which piece
		if(board.get(current)== 1) {
			color = "white";
		}
		else if(board.get(current)== 2) {
			color = "black";
		}
		else {
			System.out.println("Invalid Move!");
			//System.exit(0);
		}
		//check move legality() param -> board, curr, next, arrow
		//if legal
		
		board.put(current, 0);
		if(color.equals("black")) {
			board.put(next, 2);
			board.put(arrow,3);
		}
		else if(color.equals("white")) {
			board.put(next, 1);
			board.put(arrow,3);
		}
		//for (Entry<ArrayList<Integer>, Integer> board : board.entrySet()) {
        //System.out.println(board.getKey() + ": " + board.getValue());
   // }
		
	}
	
    private void makeRandomMove(int c) {
    	//current positions
    	List<ArrayList<Integer>> whitecurrentpositions = board.entrySet().stream()
    			.filter(entry -> entry.getValue() == 1)
    			.map(entry -> entry.getKey())
    			.collect(Collectors.toList());
    	//System.out.println(whitecurrentpositions);
    	
    	List<ArrayList<Integer>> blackcurrentpositions = board.entrySet().stream()
    			.filter(entry -> entry.getValue() == 2)
    			.map(entry -> entry.getKey())
    			.collect(Collectors.toList());
    	//System.out.println(blackcurrentpositions);
    	
    	//available squares
    	List<ArrayList<Integer>> emptysquares = board.entrySet().stream()
    			.filter(entry -> entry.getValue() == 0)
    			.map(entry -> entry.getKey())
    			.collect(Collectors.toList());
    	Random rand = new Random();
    	ArrayList<Integer> c2 = new ArrayList<Integer>();
    	if (c == 1) {
    	c2 = whitecurrentpositions.get(rand.nextInt(4));}
    	else if (c == 2) {
    	c2 = blackcurrentpositions.get(rand.nextInt(4));}
    	ArrayList<Integer> n2 = new ArrayList<Integer>(); 
    	n2 = emptysquares.get(rand.nextInt(emptysquares.size()));
    	ArrayList<Integer> a2 = new ArrayList<Integer>(); 
    	a2 = emptysquares.get(rand.nextInt(emptysquares.size()));
    	
    	Action move = new Action(c2.get(0), c2.get(1), n2.get(0), n2.get(1), a2.get(0), a2.get(1));
    	System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
		getGameClient().sendMoveMessage(move.makeMap());
		getGameGUI().updateGameState(move.makeMap());
		
		ArrayList<Integer> c3 = new ArrayList<Integer>();
		 c3.add(c2.get(1));//y
		 c3.add(c2.get(0));//x
		 ArrayList<Integer> n3 = new ArrayList<Integer>();
		 n3.add(n2.get(1));
		 n3.add(n2.get(0));
		 ArrayList<Integer> a3 = new ArrayList<Integer>();
		 a3.add(a2.get(1));
		 a3.add(a2.get(0));
		 
		 updateBoard(c3, n3, a3);
    	  //ArrayList<Action> actions = ActionGenerator.generateActions(state, color);
      //  Action selectedAction = actions.get((int) (Math.random() * actions.size()));
      //  state = new State(state, selectedAction);
        //getGameClient().sendMoveMessage(selectedAction.makeMap());
       // getGameGUI().updateGameState(selectedAction.makeMap());
    }
	
}// end of class
