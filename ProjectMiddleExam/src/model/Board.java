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
		setBoard(p1, p2);
	}

	private void setBoard(Player p1, Player p2) {
		for (int i = 0; i < 8; i++) {
			tiles[6][i].setPiece(p1.getPiece(i));
			p1.getPiece(i).setCords(6, i);

			tiles[7][i].setPiece(p1.getPiece(i + 8));
			p1.getPiece(i + 8).setCords(7, i);

			tiles[1][i].setPiece(p2.getPiece(i));
			p2.getPiece(i).setCords(1, i);

			tiles[0][i].setPiece(p2.getPiece(i + 8)); // Thay đổi từ p1 sang p2
			p2.getPiece(i + 8).setCords(0, i);
		}
	}

	public Tile[][] getTiles() {
		return tiles; // Trả về mảng 2 chiều
	}

	public void setTile(Piece piece, int row, int col) {
		tiles[row][col].setPiece(piece); // Sử dụng mảng 2 chiều
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column]; // Sử dụng mảng 2 chiều
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
