package model;

import java.util.Arrays;
import java.util.List;

import piece.Bishop;
import piece.Knight;
import piece.Piece;
import piece.Queen;
import piece.Rook;
import view.PieceDisplay;

public class Model {
	private boolean turn;
	private boolean isPlaying;
	private Player player1;
	private Player player2;
	private Board board;
	private PieceDisplay currentP;

	public void addPlayers(String isWhite) {
		player1 = new Player(isWhite);
		String d = (isWhite.equals("White")) ? "Black" : "White";
		player2 = new Player(d);
		board = new Board(player1, player2);
	}
	
	//tra ve quan tot neu tren dong 0 hoac dong 7 co quan tot
	public Piece checkPromotion(Board board) {
		for (int i = 0; i < 8; i++) {
			if (board.getTile(0, i).getPiece() != null && board.getTile(0, i).getPiece().getName().equals("Pawn")) {
				return board.getTile(0, i).getPiece();
			}
			if (board.getTile(7, i).getPiece() != null && board.getTile(7, i).getPiece().getName().equals("Pawn")) {
				return board.getTile(7, i).getPiece();
			}
		}
		return null;
	}
	
	// thuc hien phong cap cho quan tot
	// 1 la quan hau, 2 la quan xe, 3 la quan tuong, 4 la quan ma
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
		}
		// Thiết lập toạ độ cho quân cờ mới
		if (piece != null) {
			piece.setCords(row, col);
			board.setTile(piece, row, col);
			// dong 0 la quan tot trang duoc phong cap
			if (row == 0) {
				white.getPieces()[pawn.getIndex()] = piece;
			} 
			// dong 7 quan tot den duoc phong cap
			else {
				black.getPieces()[pawn.getIndex()] = piece;
			}
		}
	}
	
	// lay quan tat ca quan cua opponent kiem tra xem co nuoc di nao chieu vua cua vua cua player khong 
	// player la nguoi choi bi chieu
	// opponent la nguoi chieu
	public boolean isCheck(Player player, Player opponent, Board board) {
		int[] kingPosition = player.getPiece(12).getCords(); // quan vua co index la 12
		for (Piece piece : opponent.getPieces()) {
			for (int[] validMove : piece.listValidMoves(board)) {
				if (Arrays.equals(validMove, kingPosition)) {
					return true; // Vua bị chiếu
				}
			}
		}
		return false; // Vua không bị chiếu
	}

	public boolean isCheckmate(Player player, Player opponent, Board board) {
		// Kiểm tra nếu đang bị chiếu
		if (!isCheck(player, opponent, board)) {
			return false; // Không bị chiếu, không phải chiếu bí
		}

		// Duyệt qua tất cả các quân cờ của người chơi bị chiếu
		for (Piece piece : player.getPieces()) {
			int[] originalPosition = piece.getCords().clone();

			for (int[] move : piece.listValidMoves(board)) {
				// Lưu trữ trạng thái quân cờ bị ăn, nếu có
				Piece targetPiece = board.getTile(move[0], move[1]).getPiece();

				// Thực hiện nước đi tạm thời
				board.setTile(null, originalPosition[0], originalPosition[1]);
				board.setTile(piece, move[0], move[1]);
				piece.setCords(move[0], move[1]);

				// Kiểm tra nếu vua không còn bị chiếu
				if (!isCheck(player, opponent, board)) {
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
			king.setCords(row, col);

			// Kiểm tra nếu vua bị chiếu
			if (isCheck(player, opponent, board)) {
				// undo 
				king.setCords(originalCords[0], originalCords[1]);
				return false;
			}
			// undo after move
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
		for (int col = kingCol; col != 6; col++) {
			// Di chuyển vua tạm thời
			king.setCords(row, col);

			// Kiểm tra nếu vua bị chiếu
			if (isCheck(player, opponent, board)) {
				// undo 
				king.setCords(originalCords[0], originalCords[1]);
				return false;
			}
			// undo after move
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
			}
			if(castlingQueenSide(board, player, opponent)) {
				listMove.add(new int[] {piece.getCords()[0], 2});
			}
		}
		return listMove;
	}
	
	//thuc hien nuoc di
	public void handleMove(Board board, Piece piece, int[] newCords, Player player, Player opponent) {
		// vua thuc hien nhap thanh
		if (piece.getName().equals("King") && piece.getCords()[1] == 4) {
			if(newCords[1] == 2) {
				player.movePiece(board, player.getPiece(8), new int[] {piece.getCords()[0],3}, opponent);
			}
			if(newCords[1] == 6) {
				player.movePiece(board, player.getPiece(15), new int[] {piece.getCords()[0],5}, opponent);
			}
		}
		player.movePiece(board, piece, newCords, opponent);
	}
	
	// danh gia dua tren diem cua tung quan co con song, so nuoc di cua moi quan co, co bi chieu, chieu het khong, co nuoc di nhap thanh khong
	public int heuristic(Player white, Player black, Board board) {
	    int score = 0;
	    // diem cho nguoi choi trang
	    for (int i = 0; i < 16; i++) {
	        if (white.getPiece(i).getAlive()) {
	            score += white.getPiece(i).getValue(); // lay gia tri cua quan co 
	            score += white.getPiece(i).listValidMoves(board).size(); // lay so nuoc hop le cua quan co
	        }
	        if (black.getPiece(i).getAlive()) {
	            score -= black.getPiece(i).getValue(); // lay gia tri cua quan co
	            score -= black.getPiece(i).listValidMoves(board).size(); // lay nuoc di hop le cua quan co
	        }
	    }
	    // Kiểm tra trạng thái thắng/thua
	    if (!setPlaying(white, black, board)) {
	        score -= 10000; // Trắng thua
	    }
	    if (!setPlaying(black, white, board)) {
	        score += 10000; // Đen thua
	    }
	    // kiem tra xem co bi chieu khong
	    if(isCheck(white, black, board)) {
	    	score -=50;
	    }
	    if(isCheck(black, white, board)) {
	    	score +=50;
	    }
	    score+=castled(white, black, board);
	    score-=castled(black, white, board);
	    return score;
	}
	
	public int castled(Player player, Player opponent, Board board) {
		int score = 0;
		if(castlingKingSide(board, player, opponent)) score+=30;
		if(castlingQueenSide(board, player, opponent)) score+=30;
		return score; 
	}

	public int[]minimax(boolean turnWhite,Board board, int depth, Player white, Player black) {
		// dieu kien dung
		if(depth == 0 || !setPlaying(turnWhite ? white : black, turnWhite? black: white, board)) {
			int score = heuristic(white, black, board);
			// ket thuc som thi cong them diem
			if(turnWhite && depth > 0) {
				score += depth * 100;
			}
			if(!turnWhite && depth > 0) {
				score -= depth * 100;
			}
			return new int[] {score,-1,-1,-1,-1};
		}

		int[] bestMove = new int[5];
		int bestValue = turnWhite ? Integer.MIN_VALUE: Integer.MAX_VALUE;
		//ben trang
		if(turnWhite) {
			for(int i = 0; i < 16; i++) {
				if(white.getPiece(i).getAlive()) {
					Piece piece = white.getPiece(i);
//					for(int[] move : listMoveLegal(white, black, white.getPiece(i), board)) {
					for(int[] move : piece.listValidMoves(board)) {
						Player max = new Player(white);
						Player min = new Player(black);
						Board newBoard = new Board(max, min);
//						System.out.println(newBoard.toString()+"Truoc khi di chuyen");
						// Cap nhat trang thai board						
						handleMove(newBoard, max.getPiece(i), move, max, min);
						if(checkPromotion(newBoard)!=null) {
							promote(newBoard,max, min,checkPromotion(newBoard), 1);
						}
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
						Player max = new Player(white);
						Player min = new Player(black);
						Board newBoard = new Board(min, max);

//						System.out.println(newBoard.toString()+"Truoc khi di chuyen");
						// Cap nhat trang thai board
						handleMove(newBoard, min.getPiece(i), move, min, max);
						if(checkPromotion(newBoard)!=null) {
							promote(newBoard,max, min,checkPromotion(newBoard), 1);
						}
//						System.out.println(newBoard.toString()+"sau khi di chuyen");
						
						int[] result = minimax(!turnWhite, newBoard, depth - 1, max, min);
						
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
	
	public int[] alphabeta(boolean turnWhite, int depth, Board board, Player white, Player black, int alpha, int beta) {
		if(depth == 0 || !setPlaying(turnWhite ? white : black, turnWhite? black: white, board)) {
			int score = heuristic(white, black, board);
			// ket thuc som thi cong them diem
			if(turnWhite && depth > 0) {
				score += depth * 100;
			}
			if(!turnWhite && depth > 0) {
				score -= depth * 100;
			}
			return new int[] {score,-1,-1,-1,-1};
		}
		int[] bestMove = new int[5];
		int bestValue = turnWhite ? Integer.MIN_VALUE: Integer.MAX_VALUE;
			//ben trang (max)
				if(turnWhite) {
					for(int i = 0; i < 16; i++) {
						if(white.getPiece(i).getAlive()) {
							Piece piece = white.getPiece(i);
							for(int[] move : listMoveLegal(white, black, white.getPiece(i), board)) {
								Player max = new Player(white);
								Player min = new Player(black);
								Board newBoard = new Board(max, min);
//								System.out.println(newBoard.toString()+"Truoc khi di chuyen");
								// Cap nhat trang thai board
								handleMove(newBoard, max.getPiece(i), move, max, min);
								if(checkPromotion(newBoard)!=null) {
									promote(newBoard,max, min,checkPromotion(newBoard), 1);
								}
//								System.out.println(newBoard.toString()+"sau khi di chuyen");
								
								int[] result = alphabeta(!turnWhite, depth - 1, newBoard, max, min,  alpha, beta);
								int currentValue = result[0];
								if(currentValue > bestValue) {
									bestValue = currentValue;
		                            bestMove[0] = currentValue;
		                            bestMove[1] = move[0];
		                            bestMove[2] = move[1];
		                            bestMove[3] = piece.getCords()[0];
		                            bestMove[4] = piece.getCords()[1];
								}
								alpha = Math.max(alpha, bestValue);
								if(beta<=alpha) {
									break;
								}
							}
						}
					}
				}
				// ben den
				if (!turnWhite) {
					for (int i = 0; i < 16; i++) {
						if (black.getPiece(i).getAlive()) {
							Piece piece = black.getPiece(i);
							for (int[] move : listMoveLegal(black, white, black.getPiece(i), board)) {
								Player max = new Player(black);
								Player min = new Player(white);
								Board newBoard = new Board(min, max);
//								System.out.println(newBoard.toString()+"Truoc khi di chuyen");
								// Cap nhat trang thai board
								handleMove(newBoard, max.getPiece(i), move, max, min);
								if (checkPromotion(newBoard) != null) {
									promote(newBoard, max, min, checkPromotion(newBoard), 1);
								}
//								System.out.println(newBoard.toString()+"sau khi di chuyen");

								int[] result = alphabeta(!turnWhite, depth - 1, newBoard, min, max, alpha, beta);

								int currentValue = result[0];
//								System.out.println(currentValue);
								if (currentValue < bestValue) {
									bestValue = currentValue;
									bestMove[0] = currentValue;
									bestMove[1] = move[0];
									bestMove[2] = move[1];
									bestMove[3] = piece.getCords()[0];
									bestMove[4] = piece.getCords()[1];
								}
								beta = Math.min(beta, bestValue);
								if (beta <= alpha) {
									break;
								}
							}
						}
					}
				}				
				bestMove[0] = bestValue;
			    return bestMove; 
			}
	
	public boolean getPlaying() {
		return this.isPlaying;
	}
	
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	
	public boolean setPlaying(Player player, Player opponent, Board board) {
	    // Kiểm tra nếu vua đã chết
	    if (!player.getPiece(12).getAlive()) {
	        return false;
	    }
	    // Kiểm tra nếu đang bị chiếu bí
	    if (isCheckmate(player, opponent, board)) {
	        return false;
	    }
	    return true;
	}

	public void setCurP(PieceDisplay pieceDisplay) {
		this.currentP = pieceDisplay;
	}
	
	public boolean getTurn() {
		return turn;
	}

	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	
	public PieceDisplay getCurP() {
		return currentP;
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
	
	public static void main(String[] args) {
	    Model m = new Model();
	    m.addPlayers("White");
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(3), new int[]{4, 3}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(4), new int[]{3, 4}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(3), new int[]{3, 4}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(13), new int[]{4, 1}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(2), new int[]{5, 2}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(13), new int[]{5, 2}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(1), new int[]{5, 2}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(14), new int[]{2, 7}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(11), new int[]{3, 3}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(11), new int[]{4, 7}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(3), new int[]{2, 4}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(11), new int[]{6, 5}, m.getPlayer2(), m.getPlayer1());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(12), new int[]{6, 5}, m.getPlayer1(), m.getPlayer2());
	    m.handleMove(m.getBoard(), m.getPlayer2().getPiece(3), new int[]{2, 4}, m.getPlayer2(), m.getPlayer1());
//	    System.out.println(m.getBoard());
	    m.handleMove(m.getBoard(), m.getPlayer1().getPiece(11), new int[] {2, 4}, m.getPlayer1(), m.getPlayer2());
	    m.minimax(false, m.getBoard(), 1, m.getPlayer1(), m.getPlayer2());
//	    System.out.println(m.getBoard());

//	    for(int [] move : m.listMoveLegal(m.getPlayer2(), m.getPlayer1(), m.getPlayer2().getPiece(15), m.getBoard())) {
//	    	System.out.println(move[0]);
//	    	System.out.println(move[1]);
//	    }
//	    Runtime runtime = Runtime.getRuntime();
//
//	    // Gọi garbage collector để dọn dẹp trước khi đo
//	    runtime.gc();
//
//	    // Bộ nhớ trước khi chạy minimax
//	    long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
//
//	    // Bắt đầu đo thời gian
//	    long startTime = System.nanoTime();
//
//	    // Chạy minimax
//	    int[] bestMove = m.minimax(false, m.getBoard(), 4, m.getPlayer1(), m.getPlayer2());
////	    int[] bestMove = m.alphabeta(false, 1, m.getBoard(), m.getPlayer1(), m.getPlayer2(), Integer.MIN_VALUE, Integer.MAX_VALUE);
//	    // Kết thúc đo thời gian
//	    long endTime = System.nanoTime();
//
//	    // Bộ nhớ sau khi chạy minimax
//	    long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
//
//	    // Tính thời gian thực thi
//	    long duration = (endTime - startTime) / 1_000_000; // Đổi ra mili giây
//	    System.out.println("Thời gian thực thi minimax: " + duration + " ms");
//
//	    // Tính bộ nhớ sử dụng
//	    long memoryUsed = (memoryAfter - memoryBefore) / 1024; // Đổi ra KB
//	    System.out.println("Bộ nhớ sử dụng minimax: " + memoryUsed + " KB");	    
//	    for(int i: bestMove)
//	    	System.out.println(i);
	}

}

