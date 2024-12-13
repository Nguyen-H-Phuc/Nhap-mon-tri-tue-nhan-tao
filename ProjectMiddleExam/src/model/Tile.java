package model;

public class Tile {
	private Piece occupant;
	private boolean occupied;

	public Tile() {
		this.occupied = false; // cell is unused
	}

	public boolean checkOccupied() {
		return occupied;
	}

	public void setPiece(Piece piece) {
		this.occupant = piece;
		this.occupied = (piece != null); // update occupied if cell is occupied
	}

	public Piece getPiece() {
		return occupant;
	}

	@Override
	public String toString() {
		if (occupied) {
			return occupant.toString();
		} else {
			return "-";
		}
	}

}
