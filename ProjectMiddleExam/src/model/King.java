package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	private int value;


	public King(String name, String color, int index) {
		super(name, color, index);
		value = 1000000;
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int currentRow = this.getCords()[0];
		int currentCol = this.getCords()[1];

		// Possible king movements: 1 square in any direction
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
			int newRow = currentRow + direction[0];
			int newCol = currentCol + direction[1];

			// Check if the new position is within bounds
			if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
				// Check if the tile is occupied
				if (!board.getTile(newRow,newCol).checkOccupied()
						|| !board.getTile(newRow,newCol).getPiece().getColor().equals(this.getColor())) {
					listMove.add(new int[] { newRow, newCol }); // Capture move or empty tile
				}
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
			return "k";
		}
		return "K";
	}
}
