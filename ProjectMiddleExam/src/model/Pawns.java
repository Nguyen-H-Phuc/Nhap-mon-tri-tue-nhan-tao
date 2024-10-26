package model;

public class Pawns extends Piece {

	public Pawns(String color) {
		super(color);
	}

	@Override
	  public boolean isValidMove(int row, int col) {
        int[] cords = this.getCords();
        int currentRow = cords[0];
        int currentCol = cords[1];

        if (getColor().equalsIgnoreCase("white")) {
            return (row == currentRow + 1) && (col == currentCol);
        } else if (getColor().equalsIgnoreCase("black")) {
            return (row == currentRow - 1) && (col == currentCol);
        }

        return false;
    }

}
