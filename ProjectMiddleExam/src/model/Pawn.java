package model;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	private int value;
	private boolean firstMove;

	public Pawn(String color, int index) {
		super(color, index);
		value = 1;
		firstMove = true;
	}

	@Override
	public boolean isValidMove(int row, int col) {
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];

		if (getColor().equalsIgnoreCase("white")) {
			// Đi thẳng
			if ((row == currentRow + 1) && (col == currentCol))
				return true;
			// Bắt quân theo đường chéo
			if ((row == currentRow + 1) && (Math.abs(col - currentCol) == 1))
				return true;
		} else if (getColor().equalsIgnoreCase("black")) {
			// Đi thẳng
			if ((row == currentRow - 1) && (col == currentCol))
				return true;
			// Bắt quân theo đường chéo
			if ((row == currentRow - 1) && (Math.abs(col - currentCol) == 1))
				return true;
		}

		return false;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];

		if (getColor().equalsIgnoreCase("white")) {
			// Nước đi ban đầu
			if (firstMove && !board.getTiles().get(currentRow + 1).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow + 1, currentCol });
				listMove.add(new int[] { currentRow + 2, currentCol });
			}
			// Đi một ô
			if (!board.getTiles().get(currentRow + 1).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow + 1, currentCol });
			}
			// Bat chot qua duong
			if (board.getTiles().get(currentRow).get(currentCol + 1).checkOccupied()
					|| board.getTiles().get(currentRow).get(currentCol - 1).checkOccupied()) {
				if (board.getTiles().get(currentRow + 1).get(currentCol + 1).getPiece().getColor().equals("black")) {
					listMove.add(new int[] { currentRow + 1, currentCol + 1 });
				}
				if (board.getTiles().get(currentRow + 1).get(currentCol - 1).getPiece().getColor().equals("black")) {
					listMove.add(new int[] { currentRow + 1, currentCol - 1 });
				}
			}

		} else { // Tốt đen
			if (firstMove && board.getTiles().get(currentRow - 1).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow - 1, currentCol });
				listMove.add(new int[] { currentRow - 2, currentCol });
			}
			// Đi một ô
			if (!board.getTiles().get(currentRow - 1).get(currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow - 1, currentCol });
			}
			// Bat chot qua duong
			if (board.getTiles().get(currentRow + 1).get(currentCol - 1).checkOccupied()
					|| board.getTiles().get(currentRow + 1).get(currentCol - 1).checkOccupied()) {
				if (board.getTiles().get(currentRow + 1).get(currentCol - 1).getPiece().getColor().equals("white")) {
					listMove.add(new int[] { currentRow + 1, currentCol + 1 });
				}
				if (board.getTiles().get(currentRow + 1).get(currentCol - 1).getPiece().getColor().equals("white")) {
					listMove.add(new int[] { currentRow + 1, currentCol - 1 });
				}
			}
		}
		return listMove;
	}

	public int getValue() {
		return this.value;
	}
}
