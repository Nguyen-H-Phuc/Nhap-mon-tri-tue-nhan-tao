package model;

import java.lang.reflect.Array;
import java.util.List;

public class Bishop extends Piece {
	private int value;
	public Bishop(String color, int index) {
		super(color, index);
		this.value = 3;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];
		if (Math.abs(currentCol - col) == Math.abs(currentRow - row)) {
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
