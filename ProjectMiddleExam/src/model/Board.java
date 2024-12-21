package model;

public class Board {
	private Tile[][] tiles;
	
	public Board(Player p1, Player p2) {
		tiles = new Tile[8][8]; // Khởi tạo mảng 2 chiều
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				tiles[x][y] = new Tile(); // Khởi tạo từng ô
			}
		}
		// dat quan co cua nguoi choi p1 vao vi tri
		for(Piece piece : p1.getPieces()) {
			tiles[piece.getCords()[0]][piece.getCords()[1]].setPiece(piece);
		}
		// dat quan co cua nguoi choi p2 vao vi tri
		for(Piece piece : p2.getPieces()) {
			tiles[piece.getCords()[0]][piece.getCords()[1]].setPiece(piece);
		}
	}

	public Tile[][] getTiles() {
		return tiles; // Trả về mảng 2 chiều
	}
	
	public void setTile(Piece piece, int row, int col) {
		tiles[row][col].setPiece(piece);
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	@Override
	public String toString() {
		StringBuilder boardString = new StringBuilder();
		boardString.append("  0 1 2 3 4 5 6 7\n"); // Đánh số cột
		for (int x = 0; x < 8; x++) {
			boardString.append(x).append(" "); // Đánh số hàng
			for (int y = 0; y < 8; y++) {
				Piece piece = tiles[x][y].getPiece();
				if (piece != null) {
					boardString.append(piece.toString()).append(" "); // Sử dụng ký hiệu quân cờ
				} else {
					boardString.append(". "); // Ô trống
				}
			}
			boardString.append(x).append("\n"); // Đánh số hàng
		}
		boardString.append("  0 1 2 3 4 5 6 7\n"); // Đánh số cột
		return boardString.toString();
	}
}
