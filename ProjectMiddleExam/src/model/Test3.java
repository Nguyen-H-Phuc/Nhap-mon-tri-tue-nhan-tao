package model;

import java.util.Arrays;

public class Test3 {
	public static void main(String[] args) {

		Model m = new Model();
		m.addPlayers();
		Player p1 = m.getPlayer1();
		Player p2 = m.getPlayer2();

		m.handleMove(m.getBoard(), p1.getPiece(3), new int[] { 4, 3 }, p1, p2);
		m.handleMove(m.getBoard(), p2.getPiece(3), new int[] { 3, 3 }, p2, p1);
		m.handleMove(m.getBoard(), p1.getPiece(4), new int[] { 4, 4 }, p1, p2);
		m.handleMove(m.getBoard(), p2.getPiece(4), new int[] { 3, 4 }, p2, p1);
		m.handleMove(m.getBoard(), p1.getPiece(13), new int[] { 4, 2 }, p1, p2);
		m.handleMove(m.getBoard(), p2.getPiece(13), new int[] { 3, 2 }, p2, p1);
		m.handleMove(m.getBoard(), p1.getPiece(14), new int[] { 5, 5 }, p1, p2);
		m.handleMove(m.getBoard(), p2.getPiece(14), new int[] { 2, 5 }, p2, p1);
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

//		// Gọi garbage collector để dọn dẹp trước khi đo Alpha-Beta
//		runtime.gc();
//
//		// *** TEST ALPHA-BETA PRUNING ***
//		// Bộ nhớ trước khi chạy Alpha-Beta
//		long memoryBeforeAlphaBeta = runtime.totalMemory() - runtime.freeMemory();
//
//		// Bắt đầu đo thời gian Alpha-Beta
//		long startTimeAlphaBeta = System.nanoTime();
//
//		// Chạy Alpha-Beta Pruning
//		int[] bestMoveAlphaBeta = m.alphabeta(false, 4, m.getBoard(), m.getPlayer1(), m.getPlayer2(), Integer.MIN_VALUE,
//				Integer.MAX_VALUE);
//
//		// Kết thúc đo thời gian Alpha-Beta
//		long endTimeAlphaBeta = System.nanoTime();
//
//		// Bộ nhớ sau khi chạy Alpha-Beta
//		long memoryAfterAlphaBeta = runtime.totalMemory() - runtime.freeMemory();

		// Tính thời gian và bộ nhớ sử dụng cho Alpha-Beta
//		long durationAlphaBeta = (endTimeAlphaBeta - startTimeAlphaBeta) / 1_000_000; // Đổi ra mili giây
//		long memoryUsedAlphaBeta = (memoryAfterAlphaBeta - memoryBeforeAlphaBeta) / 1024; // Đổi ra KB
//		System.out.println("=== Kết quả Alpha-Beta ===");
//		System.out.println("Thời gian thực thi Alpha-Beta: " + durationAlphaBeta + " ms");
//		System.out.println("Bộ nhớ sử dụng Alpha-Beta: " + memoryUsedAlphaBeta + " KB");

		// So sánh kết quả
		System.out.println("=== So sánh kết quả ===");
		System.out.println("Minimax tốt nhất: " + Arrays.toString(bestMoveMinimax));
//		System.out.println("Alpha-Beta tốt nhất: " + Arrays.toString(bestMoveAlphaBeta));
//		System.out.println("Minimax nhanh hơn Alpha-Beta? " + (durationMinimax < durationAlphaBeta));
//		System.out.println("Minimax tốn ít bộ nhớ hơn Alpha-Beta? " + (memoryUsedMinimax < memoryUsedAlphaBeta));
	}
}
