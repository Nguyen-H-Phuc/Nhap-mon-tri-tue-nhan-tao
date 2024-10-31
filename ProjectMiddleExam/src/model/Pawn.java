package model;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
	private int value;

	public Pawn(String name, String color, int index) {
		super(name, color, index);
		value = 1;
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

	    if (getColor().equalsIgnoreCase("Black")) { // Nước đi cho quân tốt đen
	        // Nước đi ban đầu
	        if (currentRow == 1 && !board.getTiles()[currentRow + 2][currentCol].checkOccupied()) {
	            listMove.add(new int[] { currentRow + 2, currentCol });
	        }
	        // Đi một ô
	        if (currentRow + 1 < 8 && !board.getTiles()[currentRow + 1][currentCol].checkOccupied()) {
	            listMove.add(new int[] { currentRow + 1, currentCol });
	        }
	        // Bắt quân
	        if (currentRow + 1 < 8) {
	            if (currentCol + 1 < 8 && board.getTiles()[currentRow + 1][currentCol + 1].checkOccupied() &&
	                board.getTiles()[currentRow + 1][currentCol + 1].getPiece().getColor().equals("White")) {
	                listMove.add(new int[] { currentRow + 1, currentCol + 1 });
	            }
	            if (currentCol - 1 >= 0 && board.getTiles()[currentRow + 1][currentCol - 1].checkOccupied() &&
	                board.getTiles()[currentRow + 1][currentCol - 1].getPiece().getColor().equals("White")) {
	                listMove.add(new int[] { currentRow + 1, currentCol - 1 });
	            }
	        }
	    } else { // Nước đi cho quân tốt trắng
	        // Nước đi ban đầu
	        if (currentRow ==6 && !board.getTiles()[currentRow - 2][currentCol].checkOccupied()) {
	            listMove.add(new int[] { currentRow - 2, currentCol });
	        }
	        // Đi một ô
	        if (currentRow - 1 >= 0 && !board.getTiles()[currentRow - 1][currentCol].checkOccupied()) {
	            listMove.add(new int[] { currentRow - 1, currentCol });
	        }
	        // Bắt quân
	        if (currentRow - 1 >= 0) {
	            if (currentCol + 1 < 8 && board.getTiles()[currentRow - 1][currentCol + 1].checkOccupied() &&
	                board.getTiles()[currentRow - 1][currentCol + 1].getPiece().getColor().equals("Black")) {
	                listMove.add(new int[] { currentRow - 1, currentCol + 1 });
	            }
	            if (currentCol - 1 >= 0 && board.getTiles()[currentRow - 1][currentCol - 1].checkOccupied() &&
	                board.getTiles()[currentRow - 1][currentCol - 1].getPiece().getColor().equals("Black")) {
	                listMove.add(new int[] { currentRow - 1, currentCol - 1 });
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
			   return "p";
		   }
	        return "P"; // Ký hiệu cho quân Tốt
	    }

}
