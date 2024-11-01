package model;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
	private int value;
	private boolean firstMove;

	public Rook(String name, String color, int index) {
		super(name, color, index);
		this.value = 5;
		this.firstMove = true;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];

		// Đi về phải
		for (int x = currentCol + 1; x < 8; x++) {
			if (!board.getTiles()[currentRow][x].checkOccupied()) {
				listMove.add(new int[] { currentRow, x });
			} else {
				if (!board.getTiles()[currentRow][x].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { currentRow, x });
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		// Đi về trái
		for (int x = currentCol - 1; x >= 0; x--) {
			if (!board.getTiles()[currentRow][x].checkOccupied()) {
				listMove.add(new int[] { currentRow, x });
			} else {
				if (!board.getTiles()[currentRow][x].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { currentRow, x });
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		// Đi xuống
		for (int x = currentRow + 1; x < 8; x++) {
			if (!board.getTiles()[x][currentCol].checkOccupied()) {
				listMove.add(new int[] { x, currentCol });
			} else {
				if (!board.getTiles()[x][currentCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { x, currentCol });
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		// Đi lên
		for (int x = currentRow - 1; x >= 0; x--) {
			if (!board.getTiles()[x][currentCol].checkOccupied()) {
				listMove.add(new int[] { x, currentCol });
			} else {
				if (!board.getTiles()[x][currentCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { x, currentCol });
				}
				break; // Dừng lại khi gặp quân cờ
			}
		}

		return listMove;
	}

	public int getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		if (this.getColor().equals("Black")) {
			return "r";
		}
		return "R";
	}
}
