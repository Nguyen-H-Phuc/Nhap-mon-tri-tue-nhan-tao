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
			tiles.get(1).get(x).setPiece(p1.getPiece(x));
			p1.getPiece(x).setCords(1, x);
			p1.getPiece(x).setAlive();
			tiles.get(6).get(x).setPiece(p2.getPiece(x));
			p2.getPiece(x).setCords(6, x);
			p2.getPiece(x).setAlive();
		}

		// Sets white left
		// white rook
		tiles.get(0).get(0).setPiece(p1.getPiece(8));
		p1.getPiece(8).setCords(0, 0);
		p1.getPiece(8).setAlive();
		tiles.get(0).get(7).setPiece(p1.getPiece(9));
		p1.getPiece(9).setCords(0, 7);
		p1.getPiece(9).setAlive();
		// white knight
		tiles.get(0).get(1).setPiece(p1.getPiece(12));
		p1.getPiece(12).setCords(0, 1);
		p1.getPiece(12).setAlive();
		tiles.get(0).get(6).setPiece(p1.getPiece(13));
		p1.getPiece(13).setCords(0, 6);
		p1.getPiece(13).setAlive();
		// white bishop
		tiles.get(0).get(2).setPiece(p1.getPiece(10));
		p1.getPiece(10).setCords(0, 2);
		p1.getPiece(10).setAlive();
		tiles.get(0).get(5).setPiece(p1.getPiece(11));
		p1.getPiece(11).setCords(0, 5);
		p1.getPiece(11).setAlive();
		// white queen
		tiles.get(0).get(3).setPiece(p1.getPiece(14));
		p1.getPiece(14).setCords(0, 3);
		p1.getPiece(14).setAlive();
		// white king
		tiles.get(0).get(4).setPiece(p1.getPiece(15));
		p1.getPiece(15).setCords(0, 4);
		p1.getPiece(15).setAlive();

		// Sets black left
		// black rook
		tiles.get(7).get(0).setPiece(p2.getPiece(8));
		p2.getPiece(8).setCords(7, 0);
		p2.getPiece(8).setAlive();
		tiles.get(7).get(7).setPiece(p2.getPiece(9));
		p2.getPiece(9).setCords(7, 7);
		p2.getPiece(9).setAlive();
		// black knight
		tiles.get(7).get(1).setPiece(p2.getPiece(12));
		p2.getPiece(12).setCords(7, 1);
		p2.getPiece(12).setAlive();
		tiles.get(7).get(6).setPiece(p2.getPiece(13));
		p2.getPiece(13).setCords(7, 6);
		p2.getPiece(13).setAlive();
		// black bishop
		tiles.get(7).get(2).setPiece(p2.getPiece(10));
		p2.getPiece(10).setCords(7, 2);
		p2.getPiece(10).setAlive();
		tiles.get(7).get(5).setPiece(p2.getPiece(11));
		p2.getPiece(11).setCords(7, 5);
		p2.getPiece(11).setAlive();
		// black queen
		tiles.get(7).get(3).setPiece(p2.getPiece(14));
		p2.getPiece(14).setCords(7, 3);
		p2.getPiece(14).setAlive();
		// black king
		tiles.get(7).get(4).setPiece(p2.getPiece(15));
		p2.getPiece(15).setCords(7, 4);
		p2.getPiece(15).setAlive();

	}

	public ArrayList<ArrayList<Tile>> getTiles() {
		return tiles;
	}
}
