package model;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private int value;

    public Bishop(String color, int index) {
        super(color, index);
        this.value = 3;
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int[] cords = this.getCords();
        int currentRow = cords[0];
        int currentCol = cords[1];
        return Math.abs(currentCol - col) == Math.abs(currentRow - row);
    }

    @Override
    public List<int[]> listValidMoves(Board board) {
        List<int[]> listMove = new ArrayList<>();
        int currentRow = this.getCords()[0];
        int currentCol = this.getCords()[1];

        // Đi chéo lên bên phải
        for (int i = 1; i < 8; i++) {
            if (currentRow - i < 0 || currentCol + i > 7) {
                break;
            }
            if (board.getTiles().get(currentRow - i).get(currentCol + i).checkOccupied()) {
                if (!board.getTiles().get(currentRow - i).get(currentCol + i).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow - i, currentCol + i });
                }
                break;
            }
            listMove.add(new int[] { currentRow - i, currentCol + i });
        }

        // Đi chéo lên bên trái
        for (int i = 1; i < 8; i++) {
            if (currentRow - i < 0 || currentCol - i < 0) {
                break;
            }
            if (board.getTiles().get(currentRow - i).get(currentCol - i).checkOccupied()) {
                if (!board.getTiles().get(currentRow - i).get(currentCol - i).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow - i, currentCol - i });
                }
                break;
            }
            listMove.add(new int[] { currentRow - i, currentCol - i });
        }

        // Đi chéo xuống bên phải
        for (int i = 1; i < 8; i++) {
            if (currentRow + i > 7 || currentCol + i > 7) {
                break;
            }
            if (board.getTiles().get(currentRow + i).get(currentCol + i).checkOccupied()) {
                if (!board.getTiles().get(currentRow + i).get(currentCol + i).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow + i, currentCol + i });
                }
                break;
            }
            listMove.add(new int[] { currentRow + i, currentCol + i });
        }

        // Đi chéo xuống bên trái
        for (int i = 1; i < 8; i++) {
            if (currentRow + i > 7 || currentCol - i < 0) {
                break;
            }
            if (board.getTiles().get(currentRow + i).get(currentCol - i).checkOccupied()) {
                if (!board.getTiles().get(currentRow + i).get(currentCol - i).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow + i, currentCol - i });
                }
                break;
            }
            listMove.add(new int[] { currentRow + i, currentCol - i });
        }

        return listMove;
    }

    public int getValue() {
        return this.value;
    }
}
