package model;

import java.lang.reflect.Array;
import java.util.List;

public abstract class Piece {
	private int index; //position in pieces
	private String color;
	private boolean alive;
	private int[] cords; // position in board, [0] is row, [1] is col

	public Piece(String color, int index) {
		this.color = color;
		this.alive = true;
		this.cords = new int[2];
		this.index = index;
	}

	public void setCords(int row, int col) {
		cords[0] = row;
		cords[1] = col;
	}

	public int[] getCords() {
		return cords;
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

	public void setAlive() {
		alive = true;
	}
	
	public int getIndex() {
		return index;
	}
	

	public abstract boolean isValidMove(int row, int col);
	
	public abstract List<int[]> listValidMoves(Board board);

}
