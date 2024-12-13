package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {


	public Knight(String name, String color, int index) {
		super(name, color, index,300);
	}

	public Knight(Knight originalPiece) {
		super(originalPiece);
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];
		
		if(getAlive()==true) {
		// Các vị trí mà quân mã có thể di chuyển đến
		int[][] possibleMoves = { { currentRow - 2, currentCol - 1 }, { currentRow - 2, currentCol + 1 },
				{ currentRow + 2, currentCol - 1 }, { currentRow + 2, currentCol + 1 },
				{ currentRow - 1, currentCol - 2 }, { currentRow - 1, currentCol + 2 },
				{ currentRow + 1, currentCol - 2 }, { currentRow + 1, currentCol + 2 } };

		// Kiểm tra từng vị trí có thể di chuyển
		for (int[] move : possibleMoves) {
			int newRow = move[0];
			int newCol = move[1];

			// Kiểm tra xem vị trí có nằm trong bàn cờ không
			if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
				if (!board.getTile(newRow,newCol).checkOccupied()
						|| !board.getTile(newRow,newCol).getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol });
				}
			}
		}}

		return listMove;
	}

	@Override
	public String toString() {
		if (this.getColor().equals("Black")) {
			return "n";
		}
		return "N"; // Ký hiệu cho quân Mã
	}
}
