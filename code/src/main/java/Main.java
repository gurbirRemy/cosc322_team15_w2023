


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import boardState.Action;
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
	private static HashMap<ArrayList<Integer>, Integer> board = new HashMap<>();
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

	@SuppressWarnings("unchecked")
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
			
			ArrayList<Integer> currentPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			 ArrayList<Integer> NextPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
			 ArrayList<Integer> ArrowPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS);
			 
		 }
		 else if(messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
			 gamegui.updateGameState(msgDetails);
			 System.out.println(msgDetails);
			 ArrayList<Integer> currentPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
			 ArrayList<Integer> NextPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.QUEEN_POS_NEXT);
			 ArrayList<Integer> ArrowPos = (ArrayList<Integer>)msgDetails.get(AmazonsGameMessage.ARROW_POS);
			 String oppMove = (xaxis[currentPos.get(1)] + (currentPos.get(0)) + "->" + xaxis[NextPos.get(1)] + (NextPos.get(0)) + "(Arrow: " + xaxis[ArrowPos.get(1)] + (ArrowPos.get(0)) + ")");
			 if (playercolor.equals("white")){
				System.out.println("Move by " + playerblack + " : " + oppMove); 
			 }
			 else {
				 System.out.println("Move by " + playerwhite + " : " + oppMove); 
			 }
			 System.out.println("hi" + (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
			//Move();
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
		
		Action move = new Action(4, 10, 7, 7, 7, 2);//x ,y, x, y, x, y
		System.out.println("Move by " + getGameClient().getUserName() + " : " + move.toString());
		getGameClient().sendMoveMessage(move.makeMap());
		getGameGUI().updateGameState(move.makeMap());
	}
	
	private static void createBoard() {
		for (int i = 1; i <= 10; i++) {
            for (int j = 1; j <= 10; j++) {
                ArrayList<Integer> coordinates = new ArrayList<>();
                coordinates.add(i);
                coordinates.add(j);
                board.put(coordinates, 0);
            }
        }
	}
	
	private void updateBoard(ArrayList<Integer> boardstate, ArrayList<Integer> currentPos, ArrayList<Integer> nextPos, ArrayList<Integer> arrowPos) {
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
		
		
		
		
	}
	
    private void makeRandomMove() {
    	  //ArrayList<Action> actions = ActionGenerator.generateActions(state, color);
      //  Action selectedAction = actions.get((int) (Math.random() * actions.size()));
      //  state = new State(state, selectedAction);
        //getGameClient().sendMoveMessage(selectedAction.makeMap());
       // getGameGUI().updateGameState(selectedAction.makeMap());
    }
	
}// end of class
