package model;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private int value;
    private boolean firstMove;

    public Rook(String color, int index) {
        super(color, index);
        this.value = 5;
        this.firstMove = true;
    }

    @Override
    public boolean isValidMove(int row, int col) {
        int currentRow = this.getCords()[0];
        int currentCol = this.getCords()[1];
        
        // Đi dọc hoặc ngang
        if (row == currentRow || col == currentCol) {
            return true;
        }
        return false;
    }

    @Override
    public List<int[]> listValidMoves(Board board) {
        List<int[]> listMove = new ArrayList<>();
        int currentRow = this.getCords()[0];
        int currentCol = this.getCords()[1];

        // Đi về phải
        for (int x = currentCol + 1; x < 8; x++) {
            if (!board.getTiles().get(currentRow).get(x).checkOccupied()) {
                listMove.add(new int[] { currentRow, x });
            } else {
                if (!board.getTiles().get(currentRow).get(x).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow, x });
                }
                break;
            }
        }

        // Đi về trái
        for (int x = currentCol - 1; x >= 0; x--) {
            if (!board.getTiles().get(currentRow).get(x).checkOccupied()) {
                listMove.add(new int[] { currentRow, x });
            } else {
                if (!board.getTiles().get(currentRow).get(x).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { currentRow, x });
                }
                break;
            }
        }

        // Đi xuống
        for (int x = currentRow + 1; x < 8; x++) {
            if (!board.getTiles().get(x).get(currentCol).checkOccupied()) {
                listMove.add(new int[] { x, currentCol });
            } else {
                if (!board.getTiles().get(x).get(currentCol).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { x, currentCol });
                }
                break;
            }
        }

        // Đi lên
        for (int x = currentRow - 1; x >= 0; x--) {
            if (!board.getTiles().get(x).get(currentCol).checkOccupied()) {
                listMove.add(new int[] { x, currentCol });
            } else {
                if (!board.getTiles().get(x).get(currentCol).getPiece().getColor().equals(this.getColor())) {
                    listMove.add(new int[] { x, currentCol });
                }
                break;
            }
        }

        return listMove;
    }

    public int getValue() {
        return this.value;
    }
}
