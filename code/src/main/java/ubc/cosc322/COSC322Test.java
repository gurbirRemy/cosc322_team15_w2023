
package ubc.cosc322;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

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
public class COSC322Test extends GamePlayer {

	private GameClient gameClient = null;
	private BaseGameGUI gamegui = null;

	private String userName = null;
	private String passwd = null;

	/**
	 * The main method
	 * 
	 * @param args for name and passwd (current, any string would work)
	 */
	public static void main(String[] args) {
		//COSC322Test player = new COSC322Test(args[0], args[1]);
		
		HumanPlayer player = new HumanPlayer();
		
		player.setGameGui(player.gm);

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
	public COSC322Test(String userName, String passwd) {
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
		gameClient.joinRoom("Nicola Lake");
		gameClient.sendTextMessage("Good luck!");

	}

	@Override
	public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
		// This method will be called by the GameClient when it receives a game-related
		// message
		// from the server.

		// For a detailed description of the message types and format,
		// see the method GamePlayer.handleGameMessage() in the game-client-api
		// document.
		 System.out.println(messageType);
		// System.out.println(msgDetails);
		 if(messageType.equals(GameMessage.GAME_STATE_BOARD)) {
			 gamegui.setGameState((ArrayList<Integer>) msgDetails.get("game-state"));}
		// if(messageType.equals(GameMessage.GAME_ACTION_MOVE)){
			 //gamegui.updateGameState(msgDetails);}
		 
		return true;
		 ActionValidator validator = new ActionValidator(board);
	        // Define queen's previous position, queen's next position, and arrow position
	      
	        boolean isValid = validator.isValidMove(queenPrev, queenNext, arrow);
	        System.out.println("Is the move valid? " + isValid);
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
	
	public int[] getMove() {
		int[] currentPos = {1, 4};
		//get currentPos -> choose amazon that can move to max numPossibleMoves 
		int[] targetPos = {4, 7};
		//get targetPos -> choose position that has max numPossibleMoves
		int[] arrowPos = {9, 7};
		//get arrowPos -> select opponent amazon that can move to maxNumPossibleMoves, throw a
		
		int[] a = ArrayUtils.addAll(currentPos, targetPos);
		int[] b = ArrayUtils.addAll(a, arrowPos);
		//System.out.println(Arrays.toString(b));
		return b;
		
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
}// end of class
