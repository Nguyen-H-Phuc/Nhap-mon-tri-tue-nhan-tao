package piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;

public class Bishop extends Piece {

	public Bishop(String name, String color, int index) {
		super(name, color, index, 300);
	}

	public Bishop(Bishop originalPiece) {
		super(originalPiece);
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];

		if(getAlive()==true) {
		// Đi chéo lên bên phải
		for (int i = 1; i < 8; i++) {
			int newRow = currentRow - i;
			int newCol = currentCol + i;

			if (newRow < 0 || newCol > 7) {
				break; // Dừng lại nếu ra ngoài bàn cờ
			}
			if (board.getTiles()[newRow][newCol].checkOccupied()) {
				if (!board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol }); // Bắt quân
				}
				break; // Dừng lại khi gặp quân cờ
			}
			listMove.add(new int[] { newRow, newCol });
		}

		// Đi chéo lên bên trái
		for (int i = 1; i < 8; i++) {
			int newRow = currentRow - i;
			int newCol = currentCol - i;

			if (newRow < 0 || newCol < 0) {
				break; // Dừng lại nếu ra ngoài bàn cờ
				}
			if (board.getTiles()[newRow][newCol].checkOccupied()) {
				if (!board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol }); // Bắt quân
					}
				break; // Dừng lại khi gặp quân cờ
				}
			listMove.add(new int[] { newRow, newCol });
			}

		// Đi chéo xuống bên phải
		for (int i = 1; i < 8; i++) {
			int newRow = currentRow + i;
			int newCol = currentCol + i;

			if (newRow > 7 || newCol > 7) {
				break; // Dừng lại nếu ra ngoài bàn cờ
				}
			if (board.getTiles()[newRow][newCol].checkOccupied()) {
				if (!board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol }); // Bắt quân
					}
				break; // Dừng lại khi gặp quân cờ
				}
			listMove.add(new int[] { newRow, newCol });
			}

		// Đi chéo xuống bên trái
		for (int i = 1; i < 8; i++) {
			int newRow = currentRow + i;
			int newCol = currentCol - i;

			if (newRow > 7 || newCol < 0) {
				break; // Dừng lại nếu ra ngoài bàn cờ
				}
			
			if (board.getTiles()[newRow][newCol].checkOccupied()) {
				if (!board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol }); // Bắt quân
					}
				break; // Dừng lại khi gặp quân cờ
				}
			listMove.add(new int[] { newRow, newCol });
			}
		}
		return listMove;
	}


	@Override
	public String toString() {
		if (this.getColor().equals("Black")) {
			return "b";
		}
		return "B";
	}
}
