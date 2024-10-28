package model;

import java.util.ArrayList;

public class Board {
	private ArrayList<ArrayList<Tile>> tiles;

	public Board(Player p1, Player p2) {
		tiles = new ArrayList<ArrayList<Tile>>();
		for (int x = 0; x < 9; x++) {
			tiles.add(new ArrayList<Tile>());
		}
		setBoard(p1, p2);
	}

	private void setBoard(Player p1, Player p2) {
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				tiles.get(x).add(new Tile());
			}
		}

		for (int x = 0; x < 8; x++) {
			tiles.get(x).get(1).setPiece(p1.getPiece(x));
			p1.getPiece(x).setCords(x, 1);
			p1.getPiece(x).setAlive();
			tiles.get(x).get(6).setPiece(p2.getPiece(x));
			p1.getPiece(x).setCords(x, 2);
			p1.getPiece(x).setAlive();
		}

		// Sets white left
		// white rook
		tiles.get(0).get(0).setPiece(p1.getPiece(8));
		p1.getPiece(8).setCords(0, 0);
		p1.getPiece(8).setAlive();
		tiles.get(7).get(0).setPiece(p1.getPiece(9));
		p1.getPiece(9).setCords(7, 0);
		p1.getPiece(9).setAlive();
		// white knight
		tiles.get(1).get(0).setPiece(p1.getPiece(12));
		p1.getPiece(12).setCords(1, 0);
		p1.getPiece(12).setAlive();
		tiles.get(1).get(0).setPiece(p1.getPiece(13));
		p1.getPiece(13).setCords(1, 0);
		p1.getPiece(13).setAlive();
		// white bishop
		tiles.get(2).get(0).setPiece(p1.getPiece(10));
		p1.getPiece(10).setCords(2, 0);
		p1.getPiece(10).setAlive();
		tiles.get(5).get(0).setPiece(p1.getPiece(11));
		p1.getPiece(11).setCords(5, 0);
		p1.getPiece(11).setAlive();
		// white queen
		tiles.get(3).get(0).setPiece(p1.getPiece(14));
		p1.getPiece(14).setCords(3, 0);
		p1.getPiece(14).setAlive();
		// white king
		tiles.get(4).get(0).setPiece(p1.getPiece(15));
		p1.getPiece(15).setCords(3, 0);
		p1.getPiece(15).setAlive();

		// Sets black left
		// black rook
		tiles.get(0).get(7).setPiece(p1.getPiece(8));
		p1.getPiece(8).setCords(0, 7);
		p1.getPiece(8).setAlive();
		tiles.get(7).get(7).setPiece(p1.getPiece(9));
		p1.getPiece(9).setCords(7, 7);
		p1.getPiece(9).setAlive();
		// black knight
		tiles.get(1).get(7).setPiece(p1.getPiece(12));
		p1.getPiece(12).setCords(1, 7);
		p1.getPiece(12).setAlive();
		tiles.get(1).get(7).setPiece(p1.getPiece(13));
		p1.getPiece(13).setCords(1, 7);
		p1.getPiece(13).setAlive();
		// black bishop
		tiles.get(2).get(7).setPiece(p1.getPiece(10));
		p1.getPiece(10).setCords(2, 7);
		p1.getPiece(10).setAlive();
		tiles.get(5).get(7).setPiece(p1.getPiece(11));
		p1.getPiece(11).setCords(5, 7);
		p1.getPiece(11).setAlive();
		// black queen
		tiles.get(3).get(7).setPiece(p1.getPiece(14));
		p1.getPiece(14).setCords(3, 7);
		p1.getPiece(14).setAlive();
		// black king
		tiles.get(4).get(7).setPiece(p1.getPiece(15));
		p1.getPiece(15).setCords(3, 7);
		p1.getPiece(15).setAlive();

	}
	
	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
}
