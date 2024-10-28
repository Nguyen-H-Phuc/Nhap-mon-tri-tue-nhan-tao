package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
	private int value;
	private boolean firstMove;

	public Rook(String color, int index) {
		super(color, index);
		this.value = 5;
		this.firstMove = true;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int currentCol = this.getCords()[1];
		int currentRow = this.getCords()[0];
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
		List<int[]> listMove = new ArrayList<>();
		int currentCol = this.getCords()[1];
		int currentRow = this.getCords()[0];
		for (int x = 1; x + currentCol < 8; x++) {
			if (!board.getTiles().get(currentRow).get(x + currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow, currentCol + x });
				if (board.getTiles().get(currentRow).get(x + currentCol).getPiece().getColor()
						.equals(this.getColor())) {
					break;
				} else {
					listMove.add(new int[] { currentRow, currentCol + x });
					break;
				}
			}
		}
		for (int x = 1; currentCol - x >=0; x++) {
			if (!board.getTiles().get(currentRow).get(currentCol - x).checkOccupied()) {
				listMove.add(new int[] { currentRow, currentCol - x });
				if (board.getTiles().get(currentRow).get(currentCol-x).getPiece().getColor()
						.equals(this.getColor())) {
					break;
				} else {
					listMove.add(new int[] { currentRow, currentCol - x });
					break;
				}
			}
		}
		for (int x = 1; x + currentRow < 8; x++) {
			if (!board.getTiles().get(currentRow+x).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow+x, currentCol});
				if (board.getTiles().get(currentRow+x).get(currentCol).getPiece().getColor()
						.equals(this.getColor())) {
					break;
				} else {
					listMove.add(new int[] { currentRow, currentCol + x });
					break;
				}
			}
		}
		for (int x = 1;  currentRow-x >=0; x++) {
			if (!board.getTiles().get(currentRow-x).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow-x, currentCol });
				if (board.getTiles().get(currentRow-x).get(currentCol).getPiece().getColor()
						.equals(this.getColor())) {
					break;
				} else {
					listMove.add(new int[] { currentRow-x, currentCol});
					break;
				}
			}
		}
		return listMove;
	}

	public int getValue() {
		return this.value;
	}

}
