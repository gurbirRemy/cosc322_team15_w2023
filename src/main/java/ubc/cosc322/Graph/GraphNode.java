package ubc.cosc322.Graph;

import java.util.ArrayList;
import java.util.List;


import ubc.cosc322.AmazonsGameManager;
import ubc.cosc322.AmazonsGameManager.Square;

/*need to change setters and getters
 * KingDistanceWhite -> kDistWhite
 * KingDistanceBlack -> kDistBlack
 * QueenDistanceWhite -> qDistWhite
 * QueenDistanceBlack -> qDistBlack
 */

public class GraphNode {

    private int id;
    private  List<GraphEdge> edges;
    private int kDistWhite;
    private int kDistBlack;
    private int qDistWhite;
    private int qDistBlack;
    private ubc.cosc322.AmazonsGameManager.Square squareValue;
    
 // Constructor: Initializes a new instance of GraphNode with id and square value, setting up its edges list and initializing distances.
    public GraphNode(int id, Square squareValue){
        this.id = id;
        
        edges = new ArrayList<>();
        
        setNodeValue(squareValue);
        initializeAllDistances();
    }
   
    // Clones the given sourceNode, copying its id, square value, and distance attributes.
   public static GraphNode cloneNode(GraphNode sourceNode){
       GraphNode clone = new GraphNode(sourceNode.id, sourceNode.squareValue);
       clone.setKingDistanceWhite(sourceNode.getKingDistanceWhite());
       clone.setKingDistanceBlack(sourceNode.getKingDistanceBlack());
       clone.setQueenDistanceWhite(sourceNode.getQueenDistanceWhite());
       clone.setQueenDistanceBlack(sourceNode.getQueenDistanceBlack());
       return clone;
   }
   
   // Sets the node value and resets distance attributes if the squareValue is an arrow.
   public void setNodeValue(AmazonsGameManager.Square squareValue) {
       if(squareValue.isArrow()) {
           setQueenDistanceBlack(0);
           setKingDistanceBlack(0);  
           setQueenDistanceWhite(0);
           setKingDistanceWhite(0);
       }
       this.squareValue = squareValue;
   }
   
   
   public GraphEdge getAvailableOrStartEdge(GraphNode initialNode, GraphEdge.Direction queryDirection){
       for(GraphEdge edge : edges){
           if(edge.getEdgeDirection() != queryDirection) {
               continue;
           }
           if ((edge.getEdgeExists() || edge.getTargetNode().equals(initialNode))) {
               return edge;
           }
       }
       return null;
   }
   
// Returns an edge that is available or leads back to the initialNode in the specified direction, or null if not found.
   public GraphEdge getExistingEdge(GraphEdge.Direction queryDirection){
       for(GraphEdge edge : edges){

           if(!edge.getEdgeExists()){
               continue;
           }
           if (edge.getEdgeDirection() == queryDirection) {
               return edge;
           }
       }
       return null;
   }
   
   // Returns an existing edge in the specified direction, or null if not found.
   public void setPlayerDistancesZero(AmazonsGameManager.Square player){
   	//If the player is black, the king and queen distances of the black player are set to zero.
       if(player.isBlack()){
           setQueenDistanceBlack(0);
           setKingDistanceBlack(0);  
       }
     //If the player is white, the king and queen distances of the white player are set to zero.
       else {        
           setQueenDistanceWhite(0);
           setKingDistanceWhite(0);
       }
   }


     public void initializeAllDistances(){
        setQueenDistanceWhite(Integer.MAX_VALUE);
        setQueenDistanceBlack(Integer.MAX_VALUE);
        
        setKingDistanceWhite(Integer.MAX_VALUE);
        setKingDistanceBlack(Integer.MAX_VALUE);
    }

    public int getNodeId(){ 
        return id; 
    }

    public Square getNodeValue() {
        return squareValue;
    }

    public List<GraphEdge> getAllEdges(){
        return edges;
    }


    public int getQueenDistanceWhite() {
        return qDistWhite;
    }

    public void setQueenDistanceWhite(int qDistWhite) {
        this.qDistWhite = qDistWhite;
    }

    public int getQueenDistanceBlack() {
        return qDistBlack;
    }

    public void setQueenDistanceBlack(int qDistBlack) {
        this.qDistBlack = qDistBlack;
    }

    public int getKingDistanceWhite() {
        return kDistWhite;
    }

    public void setKingDistanceWhite(int kDistWhite) {
        this.kDistWhite = kDistWhite;
    }

    public int getKingDistanceBlack() {
        return kDistBlack;
    }

    public void setKingDistanceBlack(int kDistBlack) {
        this.kDistBlack = kDistBlack;
    }

   
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GraphNode node = (GraphNode) o;
        if(id==node.id && squareValue==node.squareValue) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}