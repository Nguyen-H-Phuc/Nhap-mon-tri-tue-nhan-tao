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
			if(color.equals("White")) {
				pieces.get(i).setCords(6, i);
			}
			if(color.equals("Black")) {
				pieces.get(i).setCords(1, i);
			}
		}
		pieces.add(new Rook("Rook", color, 8));
		pieces.add(new Knight("Knight", color, 9));
		pieces.add(new Bishop("Bishop", color, 10));
		pieces.add(new Queen("Queen", color, 11));
		pieces.add(new King("King", color, 12));
		pieces.add(new Bishop("Bishop", color, 13));
		pieces.add(new Knight("Knight", color, 14));
		pieces.add(new Rook("Rook", color, 15));
		for(int i = 8;i<16;i++) {
			if(color.equals("White")) {
				pieces.get(i).setCords(7, i);
			}
			if(color.equals("Black")) {
				pieces.get(i).setCords(0, i);
			}
		}
	}
	
	public Player(Player other) {
	    this.color = other.getColor();
	    this.pieces = new ArrayList<>();
	    for (Piece piece : other.pieces) {
	    	if (piece instanceof Pawn) {
	            this.pieces.add(new Pawn((Pawn) piece));
	        } else if (piece instanceof Rook) {
	            this.pieces.add(new Rook((Rook) piece));
	        } else if (piece instanceof Knight) {
	            this.pieces.add(new Knight((Knight) piece));
	        }else if (piece instanceof Bishop) {
	            this.pieces.add(new Bishop((Bishop) piece));
	        }else if (piece instanceof Queen) {
	            this.pieces.add(new Queen((Queen) piece));
	        }else {
	        	this.pieces.add(new King((King) piece));	        
	        }
	    }	
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
