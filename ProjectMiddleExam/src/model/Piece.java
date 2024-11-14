package model;

import java.lang.reflect.Array;
import java.util.List;

public abstract class Piece {
	private String name;
	private String color;
	private boolean alive;
	private boolean hasMoved;
	private int index; // position in player's list
	private int[] cords; // position in board, [0] is row, [1] is col

	public Piece(String name, String color, int index) {
		this.name = name;
		this.color = color;
		this.alive = true;
		this.hasMoved = false;
		this.index = index;
		this.cords = new int[2];
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
	
	public void setAlive() {
		alive = true;
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

	public abstract String toString();

	public abstract List<int[]> listValidMoves(Board board);

}
