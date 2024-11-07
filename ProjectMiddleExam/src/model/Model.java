package model;

import java.util.Scanner;

import view.PieceDisplay;

public class Model {
	private PieceDisplay currentP;

	private Player player1;
	private Player player2;
	private boolean turn;
	private Board board;
	private boolean isPlaying;
	private int chosenPiece;

	public void addPlayers(String c) {
		player1 = new Player(c);
		String d = (c.equals("White")) ? "Black" : "White";
		player2 = new Player(d);

		if (c.equals("White"))
			board = new Board(player1, player2);
		else
			board = new Board(player2, player1);
	}

	public Piece checkPromotion() {
		for (int i = 0; i < 8; i++) {
			// Kiểm tra hàng đầu (0) cho quân tốt đen
			if (board.getTile(0, i).getPiece() != null && board.getTile(0, i).getPiece().getName().equals("Pawn")) {
				return board.getTile(0, i).getPiece();
			}
			// Kiểm tra hàng cuối (7) cho quân tốt trắng
			if (board.getTile(7, i).getPiece() != null && board.getTile(7, i).getPiece().getName().equals("Pawn")) {
				return board.getTile(7, i).getPiece();
			}
		}
		return null;
	}

	public void promote(Piece pawn, int choice) {
		Piece piece = null;
		int row = pawn.getCords()[0];
		int col = pawn.getCords()[1];

		// Tạo quân cờ mới dựa trên lựa chọn
		switch (choice) {
		case 1:
			piece = new Queen("Queen", pawn.getColor(), pawn.getIndex());
			break;
		case 2:
			piece = new Rook("Rook", pawn.getColor(), pawn.getIndex());
			break;
		case 3:
			piece = new Bishop("Bishop", pawn.getColor(), pawn.getIndex());
			break;
		case 4:
			piece = new Knight("Knight", pawn.getColor(), pawn.getIndex());
			break;
		default:
			System.out.println("Lựa chọn không hợp lệ!");
			return; // Thoát phương thức nếu lựa chọn không hợp lệ
		}

		// Thiết lập toạ độ cho quân cờ mới
		if (piece != null) {
			piece.setCords(row, col);
			board.setTile(piece, row, col);
			if (row == 0) {
				player1.getPieces().set(pawn.getIndex(), piece);
			} else {
				player2.getPieces().set(pawn.getIndex(), piece);
			}

		}
	}

	public void setPlaying(boolean b) {
		isPlaying = b;
	}

	public void setCurP(PieceDisplay p) {
		currentP = p;
	}

	public void setTurn(boolean t) {
		turn = t;
	}

	public boolean getPlaying() {
		return isPlaying;
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public Board getBoard() {
		return board;
	}

	public PieceDisplay getCurP() {
		return currentP;
	}

	public boolean getTurn() {
		return turn;
	}

	public void startGame() {
		Scanner scanner = new Scanner(System.in);
		setPlaying(true);
		Player currentPlayer;
		Player noCurrentPlayer;

		while (isPlaying) {
			System.out.println(getBoard()); // In ra bàn cờ hiện tại
			if (getTurn()) {
				currentPlayer = this.getPlayer1();
				noCurrentPlayer = this.getPlayer2();
				System.out.println("Turn: " + "White");
			} else {
				currentPlayer = this.getPlayer2();
				noCurrentPlayer = this.getPlayer1();
				System.out.println("Turn: " + "Black");
			}

			// Chọn quân cờ
			System.out.print("Choose your piece (e.g., 02 to 03): ");
			int row = scanner.nextInt();
			int col = scanner.nextInt();

			System.out.print("Choose position: ");
			int rowDes = scanner.nextInt();
			int colDes = scanner.nextInt();

			if (board.getTile(row, col).checkOccupied()
					&& board.getTile(row, col).getPiece().getColor().equals(currentPlayer.getColor())) {
				if (processMove(getBoard(), row, col, rowDes, colDes)) {
					currentPlayer.movePiece(board, board.getTile(row, col).getPiece(), new int[] { rowDes, colDes },
							noCurrentPlayer);
					if (checkGameEnd(noCurrentPlayer)) {
						System.out.println("Game over!");
						System.out.println(currentPlayer.getColor() + " win!!!");
						isPlaying = false; // Kết thúc game
					}
					if (checkPromotion() != null) {
						System.out.println("Chọn quân để phong cấp (1: Hậu, 2: Xe, 3: Tượng, 4: Mã): ");
						int choice = scanner.nextInt();
						promote(checkPromotion(), choice);

					}
					setTurn(!getTurn());
				} else {
					System.out.println("Invalid move. Try again.");
				}
			} else {
				System.out.println("khong co quan.");
			}

		}
		scanner.close();
	}

	private boolean processMove(Board b, int row, int col, int rowDes, int colDes) {
		for (int[] des : b.getTile(row, col).getPiece().listValidMoves(b)) {
			if (des[0] == rowDes && des[1] == colDes) {
				return true;
			}
		}
		return false;
	}

	private boolean checkGameEnd(Player p) {
		return p.checkWin(p);
	}

	public static void main(String[] args) {
		Model m = new Model();
		m.addPlayers("White");
		m.setTurn(true);
		m.startGame();
	}
}
