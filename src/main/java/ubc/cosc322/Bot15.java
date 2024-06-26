package ubc.cosc322;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import ubc.cosc322.AmazonsGameManager.Square;
import ygraph.ai.smartfox.games.*;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class Bot15 extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gameGui = null;
	private final AmazonsGameManager RosieManager;
    private String userName = null;
    private String passwd = null;
    private final Logger logger;
 
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	Bot15 agent = new Bot15(args[0], args[1]);
    	
    	if(agent.getGameGUI() == null) {
    		agent.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	agent.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
      * @param passwd
     */
    
  public Bot15(String userName, String passwd) {
	this.userName = userName;
	this.passwd = passwd;

	logger = Logger.getLogger(AmazonsGameManager.class.toString());
	this.gameGui = new BaseGameGUI(this);
	this.RosieManager = new AmazonsGameManager();
}

    @Override
    public void onLogin() {
    	logger.info("Logged in.");
		userName = getGameClient().getUserName();
		if(gameGui != null){
			gameGui.setRoomInformation(gameClient.getRoomList());
		}
    }
    /**
    This method is used to handle the game messages received by the client.
    It takes in the messageType and msgDetails as input parameters and processes
    them accordingly. 
    @param messageType The type of game message received by the client.
    @param msgDetails The details of the game message received by the client.
    @return true if the game message was handled successfully, false otherwise.
    */
    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	long start = System.currentTimeMillis(); // start timer
    	//If the messageType is GAME_STATE_BOARD, the gameGui's gameState is updated.
    	if (messageType.equals(GameMessage.GAME_STATE_BOARD)) {
    		gameGui.setGameState((ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.GAME_STATE));
    	}
    	// If it is GAME_ACTION_MOVE, the gameGui is updated, RosieManager's opponentPlayerMove is called
        //and the client's move is made using findOurBestMove().
    	else if (messageType.equals(GameMessage.GAME_ACTION_MOVE)) {
    		gameGui.updateGameState(msgDetails);
    		RosieManager.opponentPlayerMove(msgDetails);
			Map<String, Object> actions = RosieManager.findOurBestMove(start);

			if(actions.isEmpty()){
				logger.severe("We have lost.");
			}else {
				gameGui.updateGameState(actions);
				gameClient.sendMoveMessage(actions);
				System.out.println("Move Made. Waiting for opponent.");
				
				
			}
    	}
    	//If the messageType is GAME_ACTION_START, the first player is determined
    	//and if the current user is playing as black pieces,their move is made using findOurBestMove().
    	else if (messageType.equals(GameMessage.GAME_ACTION_START)) {
    		String firstPlayer = (String) msgDetails.get(AmazonsGameMessage.PLAYER_BLACK);
    		
			if(!firstPlayer.equalsIgnoreCase(userName)){

				//RosieManager sets the players to white pieces.
				System.out.println("We control the White Queens.");
				RosieManager.setPlayers(Square.WHITE);

			} else {
				//Otherwise, RosieManager sets the players to black pieces.
				System.out.println("We control the Black Queens.");
				RosieManager.setPlayers(Square.BLACK);

				Map<String, Object> actions = RosieManager.findOurBestMove(start);

				if(actions.isEmpty()){
					System.out.println("We have lost.");
					
				}else {
					gameGui.updateGameState(actions);
					gameClient.sendMoveMessage(actions);
					System.out.println("Move Made. Waiting for opponent.");
					
				}
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
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		return gameGui;
	}

	@Override
	public void connect() {
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}

