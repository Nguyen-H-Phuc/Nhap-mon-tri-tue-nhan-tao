package model;

import java.util.List;

public abstract class Piece {
	private String name;
	private String color;
	private boolean alive;
	private boolean hasMoved;
	private int index; // position in player's list
	private int[] cords; // position in board, [0] is row, [1] is col
	private int value; // The value of the piece is used to calculate the heuristic

	public Piece(String name, String color, int index, int value) {
		this.name = name;
		this.color = color;
		this.alive = true;
		this.index = index;
		this.cords = new int[2];
		this.value = value;
	}
	
	public Piece(Piece other) {
	    this.name = other.getName();
	    this.color = other.getColor();
	    this.alive = other.getAlive();
	    this.hasMoved = other.isHasMoved();
	    this.index = other.getIndex();
	    this.cords = other.getCords() != null ? other.getCords().clone() : null;
	    this.value = other.getValue();
	}


	public String getName() {
		return this.name;
	}

	public String getColor() {
		return color;
	}

	public boolean getAlive() {
		return alive;
	}

	public void setDead() {
		alive = false;
	}

	public boolean isHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(boolean hasMove) {
		this.hasMoved = hasMove;
	}

	public int getIndex() {
		return index;
	}

	public void setCords(int row, int col) {
		cords[0] = row;
		cords[1] = col;
	}

	public int[] getCords() {
		return cords;
	}
	
	public int getValue() {
		return value;
	}

	public abstract String toString();

	public abstract List<int[]> listValidMoves(Board board);

}
