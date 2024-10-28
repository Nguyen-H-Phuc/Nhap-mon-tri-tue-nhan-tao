package model;

import java.lang.reflect.Array;
import java.util.List;

public class Queen extends Piece {
	private int value;
	public Queen(String color, int index) {
		super(color, index);
		this.value = 9;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];
		if (currentCol != col && currentRow == row) {
			return true;
		} else if (currentRow != row && currentCol == col) {
			return true;
		} else if (Math.abs(currentCol - col) == Math.abs(currentRow - row)) {
			return true;
		}
		return false;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		
		return null;
	}
	
	public int getValue() {
		return this.value;
	}

}
