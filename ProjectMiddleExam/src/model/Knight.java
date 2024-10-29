package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
	private int value;

	public Knight(String color, int index) {
		super(color, index);
		this.value = 3;
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
	    List<int[]> listMove = new ArrayList<>();
	    int currentRow = this.getCords()[0];
	    int currentCol = this.getCords()[1];

	    // Diagonal moves in all four directions
	    int[][] directions = {{-1, 1}, {-1, -1}, {1, 1}, {1, -1}};

	    for (int[] direction : directions) {
	        for (int i = 1; i < 8; i++) {
	            int newRow = currentRow + direction[0] * i;
	            int newCol = currentCol + direction[1] * i;

	            if (newRow < 0 || newRow > 7 || newCol < 0 || newCol > 7) {
	                break; // Out of bounds
	            }
	            if (board.getTiles().get(newRow).get(newCol).checkOccupied()) {
	                if (!board.getTiles().get(newRow).get(newCol).getPiece().getColor().equals(this.getColor())) {
	                    listMove.add(new int[]{newRow, newCol}); // Capture move
	                }
	                break; // Stop further movement in this direction
	            }
	            listMove.add(new int[]{newRow, newCol}); // Regular move
	        }
	    }

	    return listMove;
	}


	public int getValue() {
		return this.value;
	}
}
