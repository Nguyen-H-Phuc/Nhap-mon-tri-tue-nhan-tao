package model;

import java.util.Arrays;

import piece.Piece;

public class Test2 {
public static void main(String[] args) {
	Model m = new Model();
	m.addPlayers();
	// Xóa toàn bộ quân cờ khỏi bàn cờ để bắt đầu thiết lập
	for (int i = 0; i < 8; i++) {
	    for (int j = 0; j < 8; j++) {
	        m.getBoard().getTile(i, j).setPiece(null);
	    }
	}

	// Đặt các quân cờ của người chơi 1 (Trắng)
	// Vua trắng ở e1 (hàng 7, cột 4)
	Piece whiteKing = m.getPlayer1().getPiece(12);
	whiteKing.setCords(7, 4);
	m.getBoard().getTile(7, 4).setPiece(whiteKing);

	// Hậu trắng ở d4 (hàng 4, cột 3)
	Piece whiteQueen = m.getPlayer1().getPiece(11);
	whiteQueen.setCords(4, 3);
	m.getBoard().getTile(4, 3).setPiece(whiteQueen);

	// Xe trắng ở a1 (hàng 7, cột 0) và h1 (hàng 7, cột 7)
	Piece whiteRook1 = m.getPlayer1().getPiece(8);
	whiteRook1.setCords(7, 0);
	m.getBoard().getTile(7, 0).setPiece(whiteRook1);

	Piece whiteRook2 = m.getPlayer1().getPiece(15);
	whiteRook2.setCords(7, 7);
	m.getBoard().getTile(7, 7).setPiece(whiteRook2);

	// Tốt trắng ở c3 (hàng 5, cột 2), d2 (hàng 6, cột 3), e3 (hàng 5, cột 4), f4 (hàng 4, cột 5)
	Piece whitePawn1 = m.getPlayer1().getPiece(0);
	whitePawn1.setCords(5, 2);
	m.getBoard().getTile(5, 2).setPiece(whitePawn1);

	Piece whitePawn2 = m.getPlayer1().getPiece(1);
	whitePawn2.setCords(6, 3);
	m.getBoard().getTile(6, 3).setPiece(whitePawn2);

	Piece whitePawn3 = m.getPlayer1().getPiece(2);
	whitePawn3.setCords(5, 4);
	m.getBoard().getTile(5, 4).setPiece(whitePawn3);

	Piece whitePawn4 = m.getPlayer1().getPiece(3);
	whitePawn4.setCords(4, 5);
	m.getBoard().getTile(4, 5).setPiece(whitePawn4);

	// Đặt các quân cờ của người chơi 2 (Đen)
	// Vua đen ở e8 (hàng 0, cột 4)
	Piece blackKing = m.getPlayer2().getPiece(12);
	blackKing.setCords(0, 4);
	m.getBoard().getTile(0, 4).setPiece(blackKing);

	// Hậu đen ở d7 (hàng 1, cột 3)
	Piece blackQueen = m.getPlayer2().getPiece(11);
	blackQueen.setCords(1, 3);
	m.getBoard().getTile(1, 3).setPiece(blackQueen);

	// Xe đen ở a8 (hàng 0, cột 0) và h8 (hàng 0, cột 7)
	Piece blackRook1 = m.getPlayer2().getPiece(8);
	blackRook1.setCords(0, 0);
	m.getBoard().getTile(0, 0).setPiece(blackRook1);

	Piece blackRook2 = m.getPlayer2().getPiece(15);
	blackRook2.setCords(0, 7);
	m.getBoard().getTile(0, 7).setPiece(blackRook2);

	// Tốt đen ở c6 (hàng 2, cột 2), d5 (hàng 3, cột 3), e6 (hàng 2, cột 4), f5 (hàng 3, cột 5)
	Piece blackPawn1 = m.getPlayer2().getPiece(0);
	blackPawn1.setCords(2, 2);
	m.getBoard().getTile(2, 2).setPiece(blackPawn1);

	Piece blackPawn2 = m.getPlayer2().getPiece(1);
	blackPawn2.setCords(3, 3);
	m.getBoard().getTile(3, 3).setPiece(blackPawn2);

	Piece blackPawn3 = m.getPlayer2().getPiece(2);
	blackPawn3.setCords(2, 4);
	m.getBoard().getTile(2, 4).setPiece(blackPawn3);

	Piece blackPawn4 = m.getPlayer2().getPiece(3);
	blackPawn4.setCords(3, 5);
	m.getBoard().getTile(3, 5).setPiece(blackPawn4);

	// Toggle các quân cờ không sử dụng
	for (int i = 4; i <= 7; i++) {
	    m.getPlayer1().togglePiece(i); // Tốt trắng không sử dụng
	    m.getPlayer2().togglePiece(i); // Tốt đen không sử dụng
	}
	m.getPlayer1().togglePiece(9); // Mã trắng
	m.getPlayer1().togglePiece(10); // Tượng trắng
	m.getPlayer1().togglePiece(13); // Tượng trắng
	m.getPlayer1().togglePiece(14); // Mã trắng
	m.getPlayer2().togglePiece(9); // Mã đen
	m.getPlayer2().togglePiece(10); // Tượng đen
	m.getPlayer2().togglePiece(13); // Tượng đen
	m.getPlayer2().togglePiece(14); // Mã đen

	System.out.println(m.getBoard());
	
	Runtime runtime = Runtime.getRuntime();

	// Gọi garbage collector để dọn dẹp trước khi đo
	runtime.gc();

	// *** TEST MINIMAX ***
	// Bộ nhớ trước khi chạy Minimax
	long memoryBeforeMinimax = runtime.totalMemory() - runtime.freeMemory();

	// Bắt đầu đo thời gian Minimax
	long startTimeMinimax = System.nanoTime();

	// Chạy Minimax
	int[] bestMoveMinimax = m.minimax(false, m.getBoard(), 1, m.getPlayer1(), m.getPlayer2());

	// Kết thúc đo thời gian Minimax
	long endTimeMinimax = System.nanoTime();

	// Bộ nhớ sau khi chạy Minimax
	long memoryAfterMinimax = runtime.totalMemory() - runtime.freeMemory();

	// Tính thời gian và bộ nhớ sử dụng cho Minimax
	long durationMinimax = (endTimeMinimax - startTimeMinimax) / 1_000_000; // Đổi ra mili giây
	long memoryUsedMinimax = (memoryAfterMinimax - memoryBeforeMinimax) / 1024; // Đổi ra KB
	System.out.println("=== Kết quả Minimax ===");
	System.out.println("Thời gian thực thi Minimax: " + durationMinimax + " ms");
	System.out.println("Bộ nhớ sử dụng Minimax: " + memoryUsedMinimax + " KB");

	// Gọi garbage collector để dọn dẹp trước khi đo Alpha-Beta
	runtime.gc();

	// *** TEST ALPHA-BETA PRUNING ***
	// Bộ nhớ trước khi chạy Alpha-Beta
	long memoryBeforeAlphaBeta = runtime.totalMemory() - runtime.freeMemory();

	// Bắt đầu đo thời gian Alpha-Beta
	long startTimeAlphaBeta = System.nanoTime();

	// Chạy Alpha-Beta Pruning
	int[] bestMoveAlphaBeta = m.alphabeta(false, 1, m.getBoard(), m.getPlayer1(), m.getPlayer2(), Integer.MIN_VALUE,
			Integer.MAX_VALUE);

	// Kết thúc đo thời gian Alpha-Beta
	long endTimeAlphaBeta = System.nanoTime();

	// Bộ nhớ sau khi chạy Alpha-Beta
	long memoryAfterAlphaBeta = runtime.totalMemory() - runtime.freeMemory();

	// Tính thời gian và bộ nhớ sử dụng cho Alpha-Beta
	long durationAlphaBeta = (endTimeAlphaBeta - startTimeAlphaBeta) / 1_000_000; // Đổi ra mili giây
	long memoryUsedAlphaBeta = (memoryAfterAlphaBeta - memoryBeforeAlphaBeta) / 1024; // Đổi ra KB
	System.out.println("=== Kết quả Alpha-Beta ===");
	System.out.println("Thời gian thực thi Alpha-Beta: " + durationAlphaBeta + " ms");
	System.out.println("Bộ nhớ sử dụng Alpha-Beta: " + memoryUsedAlphaBeta + " KB");

	// So sánh kết quả
	System.out.println("=== So sánh kết quả ===");
	System.out.println("Minimax tốt nhất: " + Arrays.toString(bestMoveMinimax));
	System.out.println("Alpha-Beta tốt nhất: " + Arrays.toString(bestMoveAlphaBeta));
	System.out.println("Minimax nhanh hơn Alpha-Beta? " + (durationMinimax < durationAlphaBeta));
	System.out.println("Minimax tốn ít bộ nhớ hơn Alpha-Beta? " + (memoryUsedMinimax < memoryUsedAlphaBeta));

}
}

