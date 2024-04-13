package ubc.cosc322.Graph;

public class GraphEdge {
	// variable for edges
	private final GraphNode target_Node;
	private final Direction edge_Direction;
	private boolean edge_State;

	// Constructor for GraphEdge object

	public GraphEdge(GraphNode targetNode, Direction direction, boolean edgeExists) {
		this.edge_Direction = direction;
		this.edge_State = edgeExists;
		this.target_Node = targetNode;
	}

	// Clones an existing GraphEdge now

	public static GraphEdge cloneEdge(GraphEdge edge, GraphNode connectedNode) {
		return new GraphEdge(connectedNode, edge.getEdgeDirection(), edge.edge_State);
	}

	// set standard directions
	public static class Direction {
		public static final Direction RIGHT = new Direction("Right");
		public static final Direction LEFT = new Direction("Left");
		
		public static final Direction TOP = new Direction("Top");
		public static final Direction TOP_RIGHT = new Direction("Top-Right");
		public static final Direction TOP_LEFT = new Direction("Top-Left");
		
		public static final Direction BOTTOM = new Direction("Bottom");
		public static final Direction BOTTOM_RIGHT = new Direction("Bottom-Right");
		public static final Direction BOTTOM_LEFT = new Direction("Bottom-Left");

		public String directionLabel;

		// Constructor for Direction object

		Direction(String directionLabel) {
			this.directionLabel = directionLabel;
		}

        // Return directions 
		public String getDirectionLabel() {
			return directionLabel;
		}

		
		// Construct array with eight Direction objects
		
		public static GraphEdge.Direction[] directions = { GraphEdge.Direction.TOP, GraphEdge.Direction.TOP_RIGHT,
				GraphEdge.Direction.RIGHT, GraphEdge.Direction.BOTTOM_RIGHT, GraphEdge.Direction.BOTTOM,
				GraphEdge.Direction.BOTTOM_LEFT, GraphEdge.Direction.LEFT, GraphEdge.Direction.TOP_LEFT };

		// Returns the array

		public static GraphEdge.Direction[] getAllDirections() {
			return directions;
		}

	}

	// Getters
	public GraphNode getTargetNode() {
		return target_Node;
	}

	public Direction getEdgeDirection() {
		return edge_Direction;
	}

	public boolean getEdgeExists() {
		return edge_State;
	}

	// setters
	public void setEdgeExists(boolean edgeExists) {
		this.edge_State = edgeExists;
	}

	@Override
	public String toString() {
		return String.format("Index: %d, Value: %s, Direction: %s", target_Node.getNodeId(), target_Node.getNodeValue(),
				edge_Direction.getDirectionLabel());
	}
}