package model;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook(String name, String color, int index) {
		super(name, color, index,500);
	}

	public Rook(Rook originalPiece) {
		super(originalPiece);
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];
		if (getAlive() == true) {
			// Đi về phải
			for (int x = currentCol + 1; x < 8; x++) {
				if (!board.getTile(currentRow, x).checkOccupied()) {
					listMove.add(new int[] { currentRow, x });
				} else {
					if (!board.getTile(currentRow, x).getPiece().getColor().equals(this.getColor())) {
						listMove.add(new int[] { currentRow, x });
					}
					break; // Dừng lại khi gặp quân cờ
				}
			}

			// Đi về trái
			for (int x = currentCol - 1; x >= 0; x--) {
				if (!board.getTile(currentRow, x).checkOccupied()) {
					listMove.add(new int[] { currentRow, x });
				} else {
					if (!board.getTile(currentRow, x).getPiece().getColor().equals(this.getColor())) {
						listMove.add(new int[] { currentRow, x });
					}
					break; // Dừng lại khi gặp quân cờ
				}
			}

			// Đi xuống
			for (int x = currentRow + 1; x < 8; x++) {
				if (!board.getTile(x, currentCol).checkOccupied()) {
					listMove.add(new int[] { x, currentCol });
				} else {
					if (!board.getTile(x, currentCol).getPiece().getColor().equals(this.getColor())) {
						listMove.add(new int[] { x, currentCol });
					}
					break; // Dừng lại khi gặp quân cờ
				}
			}

			// Đi lên
			for (int x = currentRow - 1; x >= 0; x--) {
				if (!board.getTile(x, currentCol).checkOccupied()) {
					listMove.add(new int[] { x, currentCol });
				} else {
					if (!board.getTile(x, currentCol).getPiece().getColor().equals(this.getColor())) {
						listMove.add(new int[] { x, currentCol });
					}
					break; // Dừng lại khi gặp quân cờ
				}
			}
		}

		return listMove;
	}

	@Override
	public String toString() {
		if (this.getColor().equals("Black")) {
			return "r";
		}
		return "R";
	}
}
