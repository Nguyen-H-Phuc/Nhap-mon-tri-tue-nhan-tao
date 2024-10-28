package model;

import java.util.ArrayList;

public class Player {
	private String color;
	private ArrayList<Piece> pieces;

	public Player(String color) {
		this.color = color;
		pieces = new ArrayList<Piece>();
		pieces.add(new Pawn(color, 0));
		pieces.add(new Pawn(color, 1));
		pieces.add(new Pawn(color, 2));
		pieces.add(new Pawn(color, 3));
		pieces.add(new Pawn(color, 4));
		pieces.add(new Pawn(color, 5));
		pieces.add(new Pawn(color, 6));
		pieces.add(new Pawn(color, 7));
		pieces.add(new Rook(color, 8));
		pieces.add(new Rook(color, 9));
		pieces.add(new Bishop(color, 10));
		pieces.add(new Bishop(color, 11));
		pieces.add(new Knight(color, 12));
		pieces.add(new Knight(color, 13));
		pieces.add(new Queen(color, 14));
		pieces.add(new King(color, 15));
	}

	public String getColor() {
		return color;
	}

	public Piece getPiece(int index) {
		return pieces.get(index - 1);
	}

	public void togglePiece(int index) {
		pieces.get(index).setDead();
	}

	public boolean checkWin(Player opponent) {
		if (opponent.getPiece(15).getAlive()) {
			return false;
		}
		return true;
	}

}
