package piece;

import java.util.List;

import model.Board;

public abstract class Piece {
	private String name;
	private String color;
	private boolean alive;
	private boolean hasMoved;
	private int index; // vi tri quan co trong mang quan co cua nguoi choi
	private int[] cords; // vi quan co tren ban co, cords[0] la dong, cords[1] la cot
	private int value; // gia tri cua quan co

	public Piece(String name, String color, int index, int value) {
		this.name = name;
		this.color = color;
		this.alive = true;
		this.index = index;
		this.cords = new int[2];
		this.value = value;
	}
	
	//copy tu piece khac
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
