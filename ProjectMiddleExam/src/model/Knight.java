package model;

import java.lang.reflect.Array;
import java.util.List;

public class Knight extends Piece {
	private int value;
	public Knight(String color, int index) {
		super(color, index);
		this.value =3;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];

		// Check for the L-shaped moves
		if ((col == currentCol + 2 || col == currentCol - 2) && (row == currentRow + 1 || row == currentRow - 1)) {
			return true;
		}
		if ((col == currentCol + 1 || col == currentCol - 1) && (row == currentRow + 2 || row == currentRow - 2)) {
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
