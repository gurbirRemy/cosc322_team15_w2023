package ubc.cosc322.Graph;

import ubc.cosc322.AmazonsGameManager;

import java.util.*;

public class MovesGenerator {
		
    public record Move(int currentIndex, int nextIndex, int arrowIndex){
        
    }	  
	  /**
	  Returns a map of all possible moves a player can make on a given gameState, based on the current state of the game.
	  @param gameState the gameState representing the current state of the game
	  @param player the player whose moves are being generated
	  @return a map of all possible moves the player can make, with each move corresponding to a new cloned gameState
	  reflecting the state of the game after the move is made
	  **/
    public static Map<Move, Graph> possibleMoves(Graph gameState, AmazonsGameManager.Square activePlayer){
    	List<GraphNode> currentPlayer = new LinkedList<>();
    	for (GraphNode currentNode : gameState.getAllGraphNodes()) {
    	    if (currentNode.getNodeValue() == activePlayer) {
    	        currentPlayer.add(currentNode);
    	    }
    	}

     // Iterates over all the nodes in the location list and for each node, iterates over all its edges and
      // for each edge, iterates over all the edges it points to.
      // Generates a Move for each combination of nodes and adds it to the playerMoves map.
        /*Map<Move, Graph> playerMoves = new HashMap<>();
        Iterator<GraphNode> iterator = currentPlayer.iterator();
        GraphEdge.Direction[] path = GraphEdge.Direction.getAllDirections();
        while (iterator.hasNext()) {
            GraphNode node = iterator.next();
            for(GraphEdge.Direction next : path){
            	GraphEdge currentEdge = node.getExistingEdge(next);
                while(currentEdge!=null){
                    for(GraphEdge.Direction directionOfArrow : path){
                    	GraphEdge EdgeInDir = currentEdge.getTargetNode().getAvailableOrStartEdge( node,directionOfArrow);
                    	while(EdgeInDir!=null){
                    		 // Clones the current gameState
                            Graph clone = Graph.cloneGraph(gameState);
                             // creating a new move based on the edges available
                            Move currentMove = new Move(node.getNodeId(), currentEdge.getTargetNode().getNodeId(), EdgeInDir.getTargetNode().getNodeId());
                            clone.updateGraphWithNewMove(currentMove, activePlayer);
                            // adding the move and the cloned gameState to the Map
                            playerMoves.put(currentMove, clone);
                            EdgeInDir = EdgeInDir.getTargetNode().getAvailableOrStartEdge(node,directionOfArrow);
                        }
                    }
                    currentEdge = currentEdge.getTargetNode().getExistingEdge(next);
                }
            }
        }*/
    	
    	Map<Move, Graph> playerMoves = new HashMap<>();
    	GraphEdge.Direction[] path = GraphEdge.Direction.getAllDirections();

    	// Iterates over each player node
    	for (GraphNode node : currentPlayer) {
    	    generateMovesForNode(node, path, gameState, activePlayer, playerMoves);
    	}
        return playerMoves;
    }
    
    private static void generateMovesForNode(GraphNode node, GraphEdge.Direction[] path, Graph gameState, AmazonsGameManager.Square activePlayer, Map<Move, Graph> playerMoves) {
        for (GraphEdge.Direction nextDirection : path) {
            findAndProcessEdges(node, nextDirection, gameState, activePlayer, playerMoves, path);
        }
    }

    // Finds and processes edges for a given direction
    private static void findAndProcessEdges(GraphNode node, GraphEdge.Direction direction, Graph gameState, AmazonsGameManager.Square activePlayer, Map<Move, Graph> playerMoves, GraphEdge.Direction[] path) {
        GraphEdge currentEdge = node.getExistingEdge(direction);
        while (currentEdge != null) {
            exploreArrowDirections(node, currentEdge, gameState, activePlayer, playerMoves, path);
            currentEdge = currentEdge.getTargetNode().getExistingEdge(direction);
        }
    }

    // Explores all possible arrow directions for a move
    private static void exploreArrowDirections(GraphNode node, GraphEdge currentEdge, Graph gameState, AmazonsGameManager.Square activePlayer, Map<Move, Graph> playerMoves, GraphEdge.Direction[] path) {
        for (GraphEdge.Direction arrowDirection : path) {
            placeArrowAndGenerateMove(node, currentEdge, arrowDirection, gameState, activePlayer, playerMoves);
        }
    }

    // Places an arrow in a direction and generates the corresponding move
    private static void placeArrowAndGenerateMove(GraphNode node, GraphEdge currentEdge, GraphEdge.Direction arrowDirection, Graph gameState, AmazonsGameManager.Square activePlayer, Map<Move, Graph> playerMoves) {
        GraphEdge edgeInDir = currentEdge.getTargetNode().getAvailableOrStartEdge(node, arrowDirection);
        while (edgeInDir != null) {
            Graph clone = Graph.cloneGraph(gameState);
            Move currentMove = new Move(node.getNodeId(), currentEdge.getTargetNode().getNodeId(), edgeInDir.getTargetNode().getNodeId());
            clone.updateGraphWithNewMove(currentMove, activePlayer);
            playerMoves.put(currentMove, clone);
            edgeInDir = edgeInDir.getTargetNode().getAvailableOrStartEdge(node, arrowDirection);
        }
    }
	
	}