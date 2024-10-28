package model;

public class Tile {
	private Piece occupant;
	private boolean occupied;

	public Tile() {
	}

	public boolean checkOccupied() {
		return occupied;
	}

	public void setPiece(Piece mPiece) {
		occupant = mPiece;
		occupied = (mPiece != null);
	}

	public Piece getPiece() {
		return occupant;
	}
	
}
