package model;

import java.lang.reflect.Array;
import java.util.List;

public class Rook extends Piece {
	private int value;
	public Rook(String color, int index) {
		super(color, index);
		this.value = 5;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentCol = cords[1];
		int currentRow = cords[0];
		if (row != currentRow && currentCol == col) {
			return true;
		}
		if (row == currentRow && currentCol != col) {
			return true;
		}
		return false;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getValue() {
		return this.value;
	}

}
