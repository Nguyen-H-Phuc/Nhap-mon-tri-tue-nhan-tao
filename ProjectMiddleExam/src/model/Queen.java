package model;

public class Queen extends Piece {

	public Queen(String color) {
		super(color);
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

}
