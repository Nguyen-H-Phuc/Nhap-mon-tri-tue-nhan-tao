package Lab2;

import java.util.ArrayList;
import java.util.List;

public class Node {
	int n;
	List<Integer> state;
	List<Node> neighbours;

	public Node(int n) {
		this.n = n;
		this.state = new ArrayList<>();
		this.neighbours = new ArrayList<>();
	}

	public Node(int n, List<Integer> state) {
		this.n = n;
		this.state = state;
		this.neighbours = new ArrayList<>();
	}
	
	public void addNeighbours(Node neighbourNode) {
		this.neighbours.add(neighbourNode);
	}
	
	public boolean isValid(List<Integer> state) {
	    if (state.size() >= 1) {
	        int lastQueenRow = state.get(state.size() - 1);
	        int lastQueenCol = state.size() - 1;

	        for (int i = 0; i < state.size() - 1; i++) {
	            int row = state.get(i);
	            
	            if (row == lastQueenRow) {
	                return false;
	            }

	            if (Math.abs(row - lastQueenRow) == Math.abs(i - lastQueenCol)) {
	                return false;
	            }
	        }
	    }
	    return true;
	}

	
	private List<Integer> place(int x) {
	    List<Integer> newState = new ArrayList<>(this.state);
	    newState.add(x);

	    if (isValid(newState)) {
	        return newState;
	    } else {
	        return null;
	    }
	}
	
	public List<Node> getNeighbours() {
	    if (this.state.size() == this.n) {
	        return null; 
	    }

	    for (int row = 0; row < n; row++) {
	        List<Integer> newState = place(row);
	        if (newState != null) {
	            Node newNode = new Node(this.n, newState);
	            this.addNeighbours(newNode);
	        }
	    }

	    return this.neighbours;
	}

	
	
}
