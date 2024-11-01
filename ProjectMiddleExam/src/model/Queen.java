package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
	private int value;

	public Queen(String name, String color, int index) {
		super(name, color, index);
		this.value = 9;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];

		// Directions: horizontal, vertical, and diagonal
		int[][] directions = { { 1, 0 }, // Down
				{ -1, 0 }, // Up
				{ 0, 1 }, // Right
				{ 0, -1 }, // Left
				{ 1, 1 }, // Down-Right
				{ 1, -1 }, // Down-Left
				{ -1, 1 }, // Up-Right
				{ -1, -1 } // Up-Left
		};

		for (int[] direction : directions) {
			for (int i = 1; i < 8; i++) { // Mở rộng đến tối đa 7 ô
				int newRow = currentRow + direction[0] * i;
				int newCol = currentCol + direction[1] * i;

				// Kiểm tra xem vị trí mới có nằm trong bàn cờ không
				if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
					break; // Ra ngoài bàn cờ
				}

				// Kiểm tra xem ô đó có bị chiếm không
				if (board.getTiles()[newRow][newCol].checkOccupied()) {
					// Nếu ô đó bị chiếm bởi quân cờ đối phương, thêm vào danh sách (có thể ăn)
					if (!board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
						listMove.add(new int[] { newRow, newCol }); // Nước đi ăn
					}
					break; // Dừng di chuyển trong hướng này
				}

				// Nếu ô đó trống, thêm nước đi vào danh sách
				listMove.add(new int[] { newRow, newCol });
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
			return "q";
		}
		return "Q";
	}

}
