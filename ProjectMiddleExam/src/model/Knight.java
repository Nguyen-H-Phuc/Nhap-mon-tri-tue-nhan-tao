package model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
	private int value;

	public Knight(String name, String color, int index) {
		super(name, color, index);
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

	    // Các vị trí mà quân mã có thể di chuyển đến
	    int[][] possibleMoves = {
	        {currentRow - 2, currentCol - 1}, {currentRow - 2, currentCol + 1},
	        {currentRow + 2, currentCol - 1}, {currentRow + 2, currentCol + 1},
	        {currentRow - 1, currentCol - 2}, {currentRow - 1, currentCol + 2},
	        {currentRow + 1, currentCol - 2}, {currentRow + 1, currentCol + 2}
	    };

	    // Kiểm tra từng vị trí có thể di chuyển
	    for (int[] move : possibleMoves) {
	        int newRow = move[0];
	        int newCol = move[1];

	        // Kiểm tra xem vị trí có nằm trong bàn cờ không
	        if (newRow >= 0 && newRow <= 7 && newCol >= 0 && newCol <= 7) {
	            if (!board.getTiles()[newRow][newCol].checkOccupied() || 
	                !board.getTiles()[newRow][newCol].getPiece().getColor().equals(this.getColor())) {
	                listMove.add(new int[] { newRow, newCol });
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
		   if(this.getColor().equals("Black")) {
			   return "k";
		   }
	        return "N"; // Ký hiệu cho quân Mã
	    }
}
