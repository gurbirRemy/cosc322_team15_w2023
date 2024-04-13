package ubc.cosc322.Graph;

import ubc.cosc322.AmazonsGameManager.Square;
import ubc.cosc322.Algorithm.AmazonsDistanceHeuristic.GraphDistanceCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


public class Graph {
	
	private int height;
    private int width;
    private List<GraphNode> node_List;
    

   // Constructor for creating a  graph
    public Graph(int[][] gameBoard) {
    	
    	
    	// Create empty graph when gameBoard is null
        if(gameBoard == null){
        	node_List = new ArrayList<>();
            width = 10;
            height = 10;
            
            return;
        }
        
        //initializes the graph with the provided gameBoard array, after 1st time
        else{
            width = gameBoard[0].length;
            height = gameBoard.length;
            node_List = new ArrayList<>(width * height);
            initializeGraph(gameBoard);
        }
       
    }

    
    
    //Create deep copy of the given Graph to update the graph everytime with new edges 
    
    public static Graph cloneGraph(Graph original){
    	
        // deep clone node 
    	Graph clone = new Graph(null);
        for (GraphNode original_node: original.getAllGraphNodes()) {
            clone.node_List.add(GraphNode.cloneNode(original_node));
        }

        // Clone edges 
        for (int i = 0; i < original.getAllGraphNodes().size(); i++) {
            GraphNode originalNode = original.getAllGraphNodes().get(i);
            GraphNode clonedNode = clone.getAllGraphNodes().get(i);

            for (GraphEdge edge : originalNode.getAllEdges()) {
                int endPointId = edge.getTargetNode().getNodeId();
                GraphNode targetNodeClone = clone.getAllGraphNodes().get(endPointId);
                clonedNode.getAllEdges().add(new GraphEdge(targetNodeClone, edge.getEdgeDirection(), edge.getEdgeExists()));
            }
        }
        return clone;
    }

   
    
    public List<GraphNode> getAllGraphNodes(){
        return node_List;
    }

  
    // Change the node, when new move comes in - UPDATE GRAPH
   
    public void updateGraphWithNewMove(MovesGenerator.Move currentMove, Square currentPlayer){
    	// Get the initial, new, and arrow nodes from the graph
        GraphNode startNode = node_List.get(currentMove.currentIndex());
        GraphNode newNode = node_List.get(currentMove.nextIndex());
        GraphNode arrowNode = node_List.get(currentMove.arrowIndex());

     // Set the starting node's value to empty and disconnect its edges
        startNode.setNodeValue(Square.EMPTY);
        connectOrDisconnectEdges(startNode, true);

     // Assign the new node's value to the current player and connect its edges
        newNode.setNodeValue(currentPlayer);
        connectOrDisconnectEdges(newNode, false);

        // Update the arrow node's value to ARROW and connect its edges
        arrowNode.setNodeValue(Square.ARROW);
        connectOrDisconnectEdges(arrowNode, false);

        // Initialize distances for each node and calculate distances for both players
     
        node_List.forEach(GraphNode::initializeAllDistances);

        GraphDistanceCalculator.calculatePlayerDistances(this, Square.WHITE);
        GraphDistanceCalculator.calculatePlayerDistances(this, Square.BLACK);
    }

    /**
    Initialize graph by passing the board, add nodes for each square, connect
    each node to its adjacent neighbors. 
   
    */
    private void initializeGraph(int[][] board) {
        // Create graph nodes for each square on the board
        IntStream.range(0, height * width).forEach(id -> {
            int row = id / width;
            int col = id % width;
            GraphNode node = new GraphNode(id, Square.valueOf(board[row][col]));
            node_List.add(node);
        });

        // Connect nodes to their adjacent neighbors
        IntStream.range(0, height * width).forEach(id -> {
            int row = id / width;
            int col = id % width;
            GraphNode node = node_List.get(id);

            connectNeighbor(node, row - 1, col, GraphEdge.Direction.TOP);
            connectNeighbor(node, row + 1, col, GraphEdge.Direction.BOTTOM);
            connectNeighbor(node, row, col + 1, GraphEdge.Direction.RIGHT);
            connectNeighbor(node, row, col - 1, GraphEdge.Direction.LEFT);
            connectNeighbor(node, row + 1, col + 1, GraphEdge.Direction.BOTTOM_RIGHT);
            connectNeighbor(node, row + 1, col - 1, GraphEdge.Direction.BOTTOM_LEFT);
            connectNeighbor(node, row - 1, col + 1, GraphEdge.Direction.TOP_RIGHT);
            connectNeighbor(node, row - 1, col - 1, GraphEdge.Direction.TOP_LEFT);
        });
    }

    private void connectNeighbor(GraphNode node, int neighborRow, int neighborCol, GraphEdge.Direction direction) {
        if (neighborRow >= 0 && neighborRow < height && neighborCol >= 0 && neighborCol < width) {
            int neighborId = neighborRow * width + neighborCol;
            GraphNode neighborNode = node_List.get(neighborId);
            addEdgeToNeighbour(node, neighborNode, direction, neighborNode.getNodeValue().isEmpty());
        }
    }

    //connects or disconnects edges of the given node based on the given toggle value.
   
    private void connectOrDisconnectEdges(GraphNode node, boolean toggle) {
        for (GraphEdge forwardEdge : node.getAllEdges())
            for (GraphEdge backwardEdge : forwardEdge.getTargetNode().getAllEdges())
                if (backwardEdge.getTargetNode() == node){
                    backwardEdge.setEdgeExists(toggle);
                }
                    
    }

  
    //Adds an edge from a start node to a neighbor node following the direction
  
    private void addEdgeToNeighbour(GraphNode startNode, GraphNode neighbourNode, GraphEdge.Direction direction, boolean exists){
        if(startNode.getAllEdges().size() == 8){
            return;
        }
        GraphEdge newEdge = new GraphEdge(neighbourNode, direction, exists);
        startNode.getAllEdges().add(newEdge);
    }
    
    // More VALIDATIONS
    //hashcoding
    @Override
    public int hashCode(){
        return super.hashCode();
    }

   
    
   // Checks if this Graph object = another object
    
    @Override
    public boolean equals(Object obj){
    	 // Checking if the object to compare is null or not of the same class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Graph otherGraph = (Graph)obj;
        
        // Two Graph objects are equal if their nodeList have the same size and their corresponding nodes are equal.
        if (node_List.size() != otherGraph.node_List.size()) {
            return false;
        }
        
        // Comparing individual nodes in the nodeList
        for (int i = 0; i < node_List.size(); i++) {
            if (!node_List.get(i).equals(otherGraph.node_List.get(i))) {
                return false;
            }
        }
        return true;
    }

   

   }