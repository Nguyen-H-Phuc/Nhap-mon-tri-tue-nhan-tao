package model;

import java.util.Arrays;

public class Test4 {
	public static void main(String[] args) {
		Model m = new Model();
		m.addPlayers();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				m.getBoard().getTile(i, j).setPiece(null);
			}
		}

		// Đặt Vua trắng ở vị trí g1 (hàng 7, cột 6)
		m.getPlayer1().getPiece(12).setCords(7, 6);
		m.getBoard().getTile(7, 6).setPiece(m.getPlayer1().getPiece(12));

		// Đặt Xe trắng ở vị trí h7 (hàng 1, cột 7)
		m.getPlayer1().getPiece(8).setCords(1, 7);
		m.getBoard().getTile(1, 7).setPiece(m.getPlayer1().getPiece(8));

		// Đặt Vua đen ở vị trí g8 (hàng 0, cột 6)
		m.getPlayer2().getPiece(12).setCords(0, 6);
		m.getBoard().getTile(0, 6).setPiece(m.getPlayer2().getPiece(12));

		// Đặt Tốt đen ở vị trí f7 (hàng 1, cột 5)
		m.getPlayer2().getPiece(0).setCords(1, 5);
		m.getBoard().getTile(1, 5).setPiece(m.getPlayer2().getPiece(0));

		// Toggle tất cả các quân không được sử dụng
		for (int i = 0; i < 16; i++) {
			if (i != 12 && i != 8) {
				m.getPlayer1().togglePiece(i); // Toggle quân trắng không sử dụng
			}
			if (i != 12 && i != 0) {
				m.getPlayer2().togglePiece(i); // Toggle quân đen không sử dụng
			}
		}

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
