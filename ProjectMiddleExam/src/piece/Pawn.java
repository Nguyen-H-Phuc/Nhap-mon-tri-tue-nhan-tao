package piece;

import java.util.ArrayList;
import java.util.List;

import model.Board;

public class Pawn extends Piece {


	public Pawn(String name, String color, int index) {
		super(name, color, index, 100);
	}

	public Pawn(Pawn originalPiece) {
		super(originalPiece);
	}

	@Override
	public List<int[]> listValidMoves(Board board) {
		List<int[]> listMove = new ArrayList<>();
		int[] cords = this.getCords();
		int currentRow = cords[0];
		int currentCol = cords[1];
		if(getAlive()==true) {
		if (getColor().equalsIgnoreCase("Black")) { // Nước đi cho quân tốt đen
			// Kiem tra xem quan chot trang co o vi tri mac dinh khong va neu 2 o phia tren khong bi chan them cho phep tien len 2 o
			if (currentRow == 1 && !board.getTile(currentRow + 2, currentCol).checkOccupied() 
					&& !board.getTile(currentRow + 1, currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow + 2, currentCol });
			}
			// kiem tra o phia truoc co bi chan khong neu khong bi chan thi cho phep tien len 1 o
			if (currentRow + 1 < 8 && !board.getTile(currentRow + 1, currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow + 1, currentCol });
			}
			// kiem tra xem o cheo len ben trai va cheo len ben phai co quan quan den khong neu co thi cho phep bat quan doi thu */
			if (currentRow + 1 < 8) {
				if (currentCol + 1 < 8 && board.getTile(currentRow + 1, currentCol + 1).checkOccupied()
						&& board.getTile(currentRow + 1, currentCol + 1).getPiece().getColor().equals("White")) {
					listMove.add(new int[] { currentRow + 1, currentCol + 1 });
				}
				if (currentCol - 1 >= 0 && board.getTile(currentRow + 1, currentCol - 1).checkOccupied()
						&& board.getTile(currentRow + 1, currentCol - 1).getPiece().getColor().equals("White")) {
					listMove.add(new int[] { currentRow + 1, currentCol - 1 });
				}
			}
		} else { // Nước đi cho quân tốt trắng
			// Kiem tra xem quan chot trang co o vi tri mac dinh khong va neu 2 o phia tren khong bi chan them cho phep tien len 2 o
			if (currentRow == 6 && !board.getTile(currentRow - 2, currentCol).checkOccupied() 
					&& !board.getTile(currentRow - 1, currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow - 2, currentCol });
			}
			// kiem tra o phia truoc co bi chan khong neu khong bi chan thi cho phep tien len 1 o
			if (currentRow - 1 >= 0 && !board.getTile(currentRow - 1, currentCol).checkOccupied()) {
				listMove.add(new int[] { currentRow - 1, currentCol });
			}
			// kiem tra xem o cheo len ben trai va cheo len ben phai co quan quan den khong neu co thi cho phep bat quan doi thu
			if (currentRow - 1 >= 0) {
				if (currentCol + 1 < 8 && board.getTile(currentRow - 1, currentCol + 1).checkOccupied()
						&& board.getTile(currentRow - 1, currentCol + 1).getPiece().getColor().equals("Black")) {
					listMove.add(new int[] { currentRow - 1, currentCol + 1 });
				}
				if (currentCol - 1 >= 0 && board.getTile(currentRow - 1, currentCol - 1).checkOccupied()
						&& board.getTile(currentRow - 1, currentCol - 1).getPiece().getColor().equals("Black")) {
					listMove.add(new int[] { currentRow - 1, currentCol - 1 });
				}
			}
		}}
		return listMove;
	}

	@Override
	public String toString() {
		if (this.getColor().equals("Black")) {
			return "p";
		}
		return "P";
	}

}
