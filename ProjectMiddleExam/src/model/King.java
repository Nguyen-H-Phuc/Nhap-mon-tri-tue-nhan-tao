package model;

import java.lang.reflect.Array;
import java.util.List;

public class King extends Piece {
	private int value;
	public King(String color, int index) {
		super(color, index);
		value = 1000000;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];

		if (Math.abs(currentRow - row) <= 1 && Math.abs(currentCol - col) <= 1) {
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
