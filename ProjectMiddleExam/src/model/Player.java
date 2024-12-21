package model;

public class Player {
	private String color;
	private Piece[] pieces;

	public Player(String color) {
		this.color = color;
		pieces = new Piece[16];
		// Sử dụng vòng lặp để tạo quân cờ
		for (int i = 0; i < 8; i++) {
			pieces[i] =new Pawn("Pawn", color, i);
			if (color.equals("White")) {
				pieces[i].setCords(6, i);
			}
			if (color.equals("Black")) {
				pieces[i].setCords(1, i);
			}
		}
		pieces[8] = new Rook("Rook", color, 8);
		pieces[9] = new Knight("Knight", color, 9);
		pieces[10] = new Bishop("Bishop", color, 10);
		pieces[11] = new Queen("Queen", color, 11);
		pieces[12] = new King("King", color, 12);
		pieces[13] = new Bishop("Bishop", color, 13);
		pieces[14] = new Knight("Knight", color, 14);
		pieces[15] = new Rook("Rook", color, 15);
		if(this.color.equals("White")) {
			pieces[8].setCords(7, 0);
			pieces[9].setCords(7, 1);
			pieces[10].setCords(7, 2);
			pieces[11].setCords(7, 3);
			pieces[12].setCords(7, 4);
			pieces[13].setCords(7, 5);
			pieces[14].setCords(7, 6);
			pieces[15].setCords(7, 7);
		}else {
			pieces[8].setCords(0, 0);
			pieces[9].setCords(0, 1);
			pieces[10].setCords(0, 2);
			pieces[11].setCords(0, 3);
			pieces[12].setCords(0, 4);
			pieces[13].setCords(0, 5);
			pieces[14].setCords(0, 6);
			pieces[15].setCords(0, 7);
		}
	}
	
	public Player(Player other) {
	    this.color = other.getColor();
	    this.pieces = new Piece[16];
	    for (int i = 0; i < 16; i++) {
	    	Piece piece = other.getPiece(i);
	    	if (piece instanceof Pawn) {
	            this.pieces[i] = new Pawn((Pawn) piece);
	        } else if (piece instanceof Rook) {
	        	this.pieces[i] = new Rook((Rook) piece);
	        } else if (piece instanceof Knight) {
	        	this.pieces[i] = new Knight((Knight) piece);
	        }else if (piece instanceof Bishop) {
	        	this.pieces[i] = new Bishop((Bishop) piece);
	        }else if (piece instanceof Queen) {
	        	this.pieces[i] = new Queen((Queen) piece);
	        }else {
	        	this.pieces[i] = new King((King) piece);	        
	        }
	    }	
	}
	
	public Piece[] getPieces() {
		return pieces;
	}
	
	public String getColor() {
		return color;
	}

	public Piece getPiece(int index) {
		return pieces[index];
	}
	
	// 
	public void togglePiece(int index) {
		pieces[index].setDead();
	}

	public boolean checkWin(Player opponent) {
		return !opponent.getPiece(12).getAlive();
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
		pieces[piece.getIndex()].setCords(newCords[0], newCords[1]);
		pieces[piece.getIndex()].setHasMoved(true);
	}

	@Override
	public String toString() {
		return "Player" + color;
	}

}
