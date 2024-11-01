package model;

public class Tile {
	private Piece occupant;
	private boolean occupied;

	public Tile() {
		this.occupied = false; // Khởi tạo mặc định ô chưa bị chiếm
	}

	public boolean checkOccupied() {
		return occupied;
	}

	public void setPiece(Piece piece) {
		this.occupant = piece;
		this.occupied = (piece != null); // Cập nhật trạng thái bị chiếm
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
