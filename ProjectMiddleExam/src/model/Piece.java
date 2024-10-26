package model;

public abstract class Piece {
	private String color;
	private boolean alive;
	private int[] cords;
	
	public Piece(String color) {
		this.color = color;
		this.alive = true;
		this.cords = new int[2];
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
	
	public abstract boolean isValidMove(int row, int col);
	
}
