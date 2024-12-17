package model;


import java.util.Arrays;
import java.util.List;
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

	public Piece checkPromotion(Board board) {
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
	
	public void promote(Board board, Player white, Player black, Piece pawn, int choice) {
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
				white.getPieces()[pawn.getIndex()] = piece;
			} else {
				black.getPieces()[pawn.getIndex()] = piece;
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
	
	//nhap thanh gan
	public boolean castlingQueenSide(Board board, Player player, Player opponent) {
		Piece king = player.getPiece(12);
		Piece rook = player.getPiece(8);
		//check king or rook is has move 
		if(king.isHasMoved()==true || rook.getAlive()==false || rook.isHasMoved()==true) {
			return false;
		}
		int row = king.getCords()[0];
		int kingCol = 4;
		//check tile between king and rook is occupied
		for(int col = kingCol - 1; col != 0; col--) {
			if(board.getTile(row, col).checkOccupied()) {
				return false;
			}
		}
		// check 
		int[] originalCords = king.getCords().clone();
		for (int col = kingCol; col != 2; col --) {
			// Di chuyển vua tạm thời
			board.setTile(null, originalCords[0], originalCords[1]);
			board.setTile(king, row, col);
			king.setCords(row, col);

			// Kiểm tra nếu vua bị chiếu
			if (isCheck(player, opponent)) {
				// undo 
				board.setTile(king, originalCords[0], originalCords[1]);
				board.setTile(null, row, col);
				king.setCords(originalCords[0], originalCords[1]);
				return false;
			}
			// undo after move
			board.setTile(king, originalCords[0], originalCords[1]);
			board.setTile(null, row, col);
			king.setCords(originalCords[0], originalCords[1]);
		}
		return true;
	}
	
	// nhap thanh xa
	public boolean castlingKingSide(Board board, Player player, Player opponent) {
		Piece king = player.getPiece(12);
		Piece rook = player.getPiece(8);
		//check king or rook is has move 
		if(king.isHasMoved()==true || rook.getAlive()==false || rook.isHasMoved()==true) {
			return false;
		}
		int row = king.getCords()[0];
		int kingCol = 4;
		//check tile between king and rook is occupied
		for(int col = kingCol + 1; col != 7; col++) {
			if(board.getTile(row, col).checkOccupied()) {
				return false;
			}
		}
		// check 
		int[] originalCords = king.getCords().clone();
		for (int col = kingCol; col != 6; col ++) {
			// Di chuyển vua tạm thời
			board.setTile(null, originalCords[0], originalCords[1]);
			board.setTile(king, row, col);
			king.setCords(row, col);

			// Kiểm tra nếu vua bị chiếu
			if (isCheck(player, opponent)) {
				// undo 
				board.setTile(king, originalCords[0], originalCords[1]);
				board.setTile(null, row, col);
				king.setCords(originalCords[0], originalCords[1]);
				return false;
			}
			// undo after move
			board.setTile(king, originalCords[0], originalCords[1]);
			board.setTile(null, row, col);
			king.setCords(originalCords[0], originalCords[1]);
		}
		return true;
	}
	
	// them nuoc di nhap thanh cho vua neu co, cac quan co khac di binh thuong
	public List<int[]> listMoveLegal(Player player, Player opponent, Piece piece,Board board) {
		List<int[]> listMove = piece.listValidMoves(board);
		if(piece.getName().equals("King")) {
			if(castlingKingSide(board, player, opponent)) {
				listMove.add(new int[] {piece.getCords()[0], 6});
			}if(castlingQueenSide(board, player, opponent)) {
				listMove.add(new int[] {piece.getCords()[0], 2});
			}
		}
		return listMove;
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

	//thuc hien nuoc di
	public void processMove(Board board, Piece piece, int[] newCords, Player player, Player opponent) {
		if (player.getPiece(piece.getIndex()).getName().equals("King") && !player.getPiece(piece.getIndex()).isHasMoved()) {
			castling(board, player, opponent, newCords[1]);
		}
		player.movePiece(board, piece, newCords, opponent);
	}
	
	public int heuristic(Player white, Player black,Board board) {
		int score = 0;
		for(int i = 0; i<16;i++) {
			if(white.getPiece(i).getAlive()) {
				score+=white.getPiece(i).getValue();
				//tinh xem nguoi choi trang co bao nhieu nuoc di
				score+=white.getPiece(i).listValidMoves(board).size();
			}
			if(black.getPiece(i).getAlive()) {
				score-=black.getPiece(i).getValue();
				//tinh xem nguoi choi den co bao nhieu nuoc di
				score-=black.getPiece(i).listValidMoves(board).size();
			}
		}return score;
	}
	
	public int[]minimax(boolean turnWhite,Board board, int depth, Player white, Player black) {
		if(depth == 0) {
			return new int[] {heuristic(white, black, board),-1,-1,-1,-1};
		}
		int[] bestMove = new int[5];
		int bestValue = turnWhite ? Integer.MIN_VALUE: Integer.MAX_VALUE;
		//ben trang
		if(turnWhite) {
			for(int i = 0; i < 16; i++) {
				if(white.getPiece(i).getAlive()) {
					Piece piece = white.getPiece(i);
					for(int[] move : listMoveLegal(white, black, white.getPiece(i), board)) {
						Player max = new Player(white);
						Player min = new Player(black);
						Board newBoard = new Board(max, min);
//						System.out.println(newBoard.toString()+"Truoc khi di chuyen");
						// Cap nhat trang thai board
						if(checkPromotion(newBoard)!=null) {
							promote(newBoard,max, min,checkPromotion(newBoard), 1);
						}
						processMove(newBoard, max.getPiece(i), move, max, min);
//						System.out.println(newBoard.toString()+"sau khi di chuyen");
						
						int[] result = minimax(!turnWhite, newBoard, depth - 1, max, min);
						int currentValue = result[0];
						if(currentValue > bestValue) {
							bestValue = currentValue;
                            bestMove[0] = currentValue;
                            bestMove[1] = move[0];
                            bestMove[2] = move[1];
                            bestMove[3] = piece.getCords()[0];
                            bestMove[4] = piece.getCords()[1];
						}
					}
				}
			}
		}
		// ben den
		if(!turnWhite) {
			for(int i = 0; i < 16; i++) {
				if(black.getPiece(i).getAlive()) {
					Piece piece = black.getPiece(i);
					for(int[] move : listMoveLegal(black, white, black.getPiece(i), board)) {
						Player max = new Player(black);
						Player min = new Player(white);
						Board newBoard = new Board(min, max);
						System.out.println(newBoard.toString()+"Truoc khi di chuyen");
						// Cap nhat trang thai board
						if(checkPromotion(newBoard)!=null) {
							promote(newBoard,max, min,checkPromotion(newBoard), 1);
						}
						processMove(newBoard, max.getPiece(i), move, max, min);
						System.out.println(newBoard.toString()+"sau khi di chuyen");
						
						int[] result = minimax(!turnWhite, newBoard, depth - 1, min, max);
						
						int currentValue = result[0];
//						System.out.println(currentValue);
						if(currentValue < bestValue) {
							bestValue = currentValue;
                            bestMove[0] = currentValue;
                            bestMove[1] = move[0];
                            bestMove[2] = move[1];
                            bestMove[3] = piece.getCords()[0];
                            bestMove[4] = piece.getCords()[1];
						}
					}
				}
			}
		}
		
		bestMove[0] = bestValue;
	    return bestMove; 
	}

	public static void main(String[] args) {
		Model m = new Model();
		m.addPlayers("White");
		int [] bestmove = m.minimax(false, m.getBoard(), 1, m.getPlayer1(), m.getPlayer2());
		for(int i : bestmove) {
			System.out.println(i);
		}
	}
}

