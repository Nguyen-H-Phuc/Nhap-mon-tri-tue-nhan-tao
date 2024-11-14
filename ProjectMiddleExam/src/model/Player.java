package model;

import java.util.ArrayList;

public class Player {
	private String color;
	private ArrayList<Piece> pieces;

	public Player(String color) {
		this.color = color;
		pieces = new ArrayList<>();

		// Sử dụng vòng lặp để tạo quân cờ
		for (int i = 0; i < 8; i++) {
			pieces.add(new Pawn("Pawn", color, i));
		}
		pieces.add(new Rook("Rook", color, 8));
		pieces.add(new Knight("Knight", color, 9));
		pieces.add(new Bishop("Bishop", color, 10));
		pieces.add(new Queen("Queen", color, 11));
		pieces.add(new King("King", color, 12));
		pieces.add(new Bishop("Bishop", color, 13));
		pieces.add(new Knight("Knight", color, 14));
		pieces.add(new Rook("Rook", color, 15));
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public String getColor() {
		return color;
	}

	public Piece getPiece(int index) {
		return pieces.get(index);
	}

	public void togglePiece(int index) {
		pieces.get(index).setDead();
	}

	public boolean checkWin(Player opponent) {
		return !opponent.getPiece(12).getAlive(); // Có thể viết ngắn gọn hơn
	}

	public void movePiece(Board board, Piece piece, int[] newCords, Player opponent) {
		// Sets captured Opponent Piece to Dead (sets alive to false)
		if (board.getTile(newCords[0], newCords[1]).checkOccupied()) {
			opponent.togglePiece(board.getTile(newCords[0], newCords[1]).getPiece().getIndex());
		}
		// Sets Previous Tile's Piece to null
		board.setTile(null, piece.getCords()[0], piece.getCords()[1]);
		// Changes the Piece inside the destination Tile
		board.setTile(piece, newCords[0], newCords[1]);
		// Updates the New Cords of the Moved Piece
		pieces.get(piece.getIndex()).setCords(newCords[0], newCords[1]);
		pieces.get(piece.getIndex()).setHasMoved(true);
	}

	@Override
	public String toString() {
		return "Player" + color;
	}

}
