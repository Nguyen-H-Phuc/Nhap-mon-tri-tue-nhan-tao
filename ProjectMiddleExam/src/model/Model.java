package model;

import java.util.Arrays;
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

	public boolean isCheck(Player player1, Player player2) {
		int[] kingPosition = player1.getPiece(12).getCords(); // Giả định chỉ số của vua là 12
		for (Piece piece : player2.getPieces()) {
			for (int[] validMove : piece.listValidMoves(board)) {
				if (Arrays.equals(validMove, kingPosition)) {
					return true; // Vua bị chiếu
				}
			}
		}
		return false; // Vua không bị chiếu
	}

	public boolean isCheckmate(Player player1, Player player2) {
		// Kiểm tra nếu đang bị chiếu
		if (!isCheck(player1, player2)) {
			return false; // Không bị chiếu, không phải chiếu bí
		}

		// Duyệt qua tất cả các quân cờ của người chơi bị chiếu
		for (Piece piece : player1.getPieces()) {
			int[] originalPosition = piece.getCords().clone();

			for (int[] move : piece.listValidMoves(board)) {
				// Lưu trữ trạng thái quân cờ bị ăn, nếu có
				Piece targetPiece = board.getTile(move[0], move[1]).getPiece();

				// Thực hiện nước đi tạm thời
				board.setTile(null, originalPosition[0], originalPosition[1]);
				board.setTile(piece, move[0], move[1]);
				piece.setCords(move[0], move[1]);

				// Kiểm tra nếu vua không còn bị chiếu
				if (!isCheck(player1, player2)) {
					// Hoàn tác nước đi
					board.setTile(targetPiece, move[0], move[1]); // Đặt lại quân cờ bị ăn (nếu có)
					board.setTile(piece, originalPosition[0], originalPosition[1]);
					piece.setCords(originalPosition[0], originalPosition[1]);

					return false; // Không phải chiếu bí, có thể thoát chiếu
				}

				// Hoàn tác nước đi nếu vẫn còn bị chiếu
				board.setTile(targetPiece, move[0], move[1]); // Đặt lại quân cờ bị ăn (nếu có)
				board.setTile(piece, originalPosition[0], originalPosition[1]);
				piece.setCords(originalPosition[0], originalPosition[1]);
			}
		}

		return true; // Không có nước đi nào thoát khỏi chiếu, đây là chiếu bí
	}

	public boolean castling(Board board, Player player1, Player player2, int colDes) {
		if (colDes != 6 && colDes != 2) {
			return false;
		}

		boolean isKingside = (colDes == 6);

		Piece king = player1.getPiece(12);
		int row = king.getCords()[0];

		int rookCol = isKingside ? 7 : 0; // Xe vua (hàng bên phải) ở cột 7, Xe hậu (trái) ở cột 0
		int kingCol = 4; // Vua ban đầu ở cột 4
		int newKingCol = isKingside ? 6 : 2; // Vị trí mới của Vua

		Piece rook = board.getTile(row, rookCol).getPiece();

		// Kiểm tra xem Vua và Xe có còn ở vị trí ban đầu không
		if (king.isHasMoved() || rook == null || rook.isHasMoved()) {
			return false;
		}

		// Kiểm tra các ô giữa Vua và Xe
		int step = isKingside ? 1 : -1;
		for (int col = kingCol + step; col != rookCol; col += step) {
			if (board.getTile(row, col).checkOccupied()) {
				return false;
			}
		}

		// Kiểm tra các ô Vua đi qua không bị chiếu
		int[] originalCords = king.getCords().clone();
		for (int col = kingCol; col != newKingCol + step; col += step) {
			// Di chuyển vua tạm thời
			board.setTile(null, originalCords[0], originalCords[1]);
			board.setTile(king, row, col);
			king.setCords(row, col);

			// Kiểm tra nếu vua bị chiếu
			if (isCheck(player1, player2)) {
				// Hoàn tác nước đi
				board.setTile(king, originalCords[0], originalCords[1]);
				board.setTile(null, row, col);
				king.setCords(originalCords[0], originalCords[1]);
				return false;
			}
			// Hoàn tác nước đi sau mỗi bước kiểm tra
			board.setTile(king, originalCords[0], originalCords[1]);
			board.setTile(null, row, col);
			king.setCords(originalCords[0], originalCords[1]);
		}

		king.setHasMoved(true);
		rook.setHasMoved(true);

		board.setTile(rook, row, kingCol + step);
		board.setTile(null, row, rookCol);

		if (isKingside) {
			player1.getPiece(15).setCords(row, kingCol + step);
		} else {
			player1.getPiece(8).setCords(row, kingCol + step);
		}
		return true;
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
		Player opponent;

		while (getPlaying()) {
			System.out.println(getBoard()); // In ra bàn cờ hiện tại
			if (getTurn()) {
				currentPlayer = this.getPlayer1();
				opponent = this.getPlayer2();
				System.out.println("Turn: " + "White");
			} else {
				currentPlayer = this.getPlayer2();
				opponent = this.getPlayer1();
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
				if (processMove(getBoard(), row, col, rowDes, colDes, currentPlayer, opponent)) {
					currentPlayer.movePiece(board, board.getTile(row, col).getPiece(), new int[] { rowDes, colDes },
							opponent);

					if (checkPromotion() != null) {
						System.out.println("Chọn quân để phong cấp (1: Hậu, 2: Xe, 3: Tượng, 4: Mã): ");
						int choice = scanner.nextInt();
						promote(checkPromotion(), choice);
					}

					if (isCheckmate(opponent, currentPlayer)) {
						System.out.println("Game over!");
						System.out.println(currentPlayer.getColor() + " win!!!");
						setPlaying(!getPlaying());
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

	private boolean processMove(Board b, int row, int col, int rowDes, int colDes, Player player1, Player player2) {
		if (b.getTile(row, col).getPiece().getName().equals("King") && !b.getTile(row, col).getPiece().isHasMoved()) {
			return castling(b, player1, player2, colDes);
		}
		for (int[] des : b.getTile(row, col).getPiece().listValidMoves(b)) {
			if (des[0] == rowDes && des[1] == colDes) {
				return true;
			}
		}
		return false;
	}

	private boolean checkGameEnd(Player player1, Player player2) {
		return isCheckmate(player1, player2);
	}

	public static void main(String[] args) {
		Model m = new Model();
		m.addPlayers("White");
		m.setTurn(true);
		m.startGame();
	}
}
//63
//43
//13
//33
//76
//55
//06
//25
//62
//42
//02
//35
//42
//33
//35
//71
//73
//40
//12
//22
//33
//22
//71
//44
//22
//12
//03
//13
//12
//02

//60
//40
//17
//37
//61
//41
//16
//36
//62
//42
//06
//27
//71
//50
//05
//16
//72
//61
//04
//06
//73
//62
