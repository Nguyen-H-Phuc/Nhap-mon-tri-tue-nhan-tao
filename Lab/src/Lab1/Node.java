package Lab1;

import java.util.ArrayList;
import java.util.List;

public class Node {
	int state;
	boolean visited;
	List<Node> neighbours;
	Node parent;
	
	public Node(int state) {
		this.state = state;
		this.neighbours = new ArrayList<>();
		this.parent = null;
	}
	
	public void addNeighbours(Node neighbours) {
		this.neighbours.add(neighbours);
	}
	
	public List<Node> getNeigbours() {
	    return neighbours;
	}
	
}
