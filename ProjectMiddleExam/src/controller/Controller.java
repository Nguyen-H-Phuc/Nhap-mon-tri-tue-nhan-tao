package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import model.Model;
import model.Player;
import view.PieceDisplay;
import view.View;

public class Controller {
	private View view;
	private Model model;
	boolean startWithWhite ;
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		startWithWhite = startWithWhite();
		initializeTeams();
	}

	private void initializeTeams() {
		model.addPlayers("White");
		model.setPlaying(true);
		model.setTurn(true);
		initializeListeners();
		initializeTiles();
		if (!startWithWhite) {
			int[] bestMove = model.minimax(true, model.getBoard(), 1, model.getPlayer1(), model.getPlayer2());
			for (int i : bestMove) {
				System.out.println(i);
			}
			model.setCurP(view.getPiece(bestMove[3], bestMove[4]));
			int[] move = { bestMove[1], bestMove[2] };
			if (view.getPanelTile(bestMove[1], bestMove[2]).getComponentCount() > 0) {
				view.getPanelTile(bestMove[1], bestMove[2]).remove(0);
			}
			model.getPlayer1().movePiece(model.getBoard(), model.getBoard().getTile(bestMove[3], bestMove[4]).getPiece(), move,
					model.getPlayer2());
			view.setPieceLocation(model.getCurP(), view.getPanelTile(bestMove[1], bestMove[2]));
			System.out.println(model.getBoard().toString());
			System.out.println(true);
			endTurn();
			model.setCurP(null);
			endGame(model.getPlaying());
		}
	}

	public void initializeListeners() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (view.getPiece(i, j) != null) {
					view.getPiece(i, j).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					view.getPiece(i, j).addMouseListener(new MouseAdapter() {

						@Override
						public void mouseClicked(MouseEvent e) {

							Player p1 = model.getPlayer1();
							Player p2 = model.getPlayer2();
							
							if (model.getTurn() && p1.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (model.getCurP() == null) {
									model.setCurP((PieceDisplay) e.getSource());
									System.out.println(model.getCurP().getIndex());
									// Highlight valid moves in green
									for (int[] validMove : model.listMoveLegal(p1, p2,
											p1.getPiece(model.getCurP().getIndex()),model.getBoard())) {
										view.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
									}
								} else if (e.getSource() == model.getCurP()) {
									view.recolorTiles();
									model.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent()).getBackground() == Color.GREEN 
									&& model.getCurP() != null && model.getTurn()
									&& ((e.getSource() != model.getCurP())
									&& (((PieceDisplay) e.getSource()).getColor()).equals(p2.getColor()))) {
								view.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								model.handleMove(model.getBoard(), p1.getPiece(model.getCurP().getIndex()), newCords, p1, p2);
								model.setPlaying(!p1.checkWin(p2) || model.isCheckmate(p1, p2, model.getBoard()));

								int destX = view.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = view.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								view.setPieceLocation(model.getCurP(), view.getPanelTile(destY, destX));
								
								//phong cap
								if (p1.getPiece((model.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
									promote(model.getCurP(), view, model);
								}
								endTurn();
								model.setCurP(null);
								endGame(model.getTurn());
								// turn ai
								if(model.getPlaying()) {
									handleTurnAI(model, view, p1, p2, model.getTurn());
								}
							} 

							else if (!model.getTurn() && p2.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (model.getCurP() == null) {
									// Select the piece
									model.setCurP((PieceDisplay) e.getSource());
									// Highlight valid moves in green
									for (int[] validMove : model.listMoveLegal(p2, p1, p2.getPiece(model.getCurP().getIndex()),
											model.getBoard())) {
										view.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
									}
								} else if (e.getSource() == model.getCurP()) {
									// Deselect if clicked again
									view.recolorTiles();
									model.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent())
									.getBackground() == Color.GREEN && model.getCurP() != null && !model.getTurn()
									&& ((e.getSource() != model.getCurP())
											&& (((PieceDisplay) e.getSource()).getColor()).equals(p1.getColor()))) {
								view.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								p2.movePiece(model.getBoard(), p2.getPiece(model.getCurP().getIndex()), newCords, p1);
								model.setPlaying(!p2.checkWin(p1) || model.isCheckmate(p2, p1, model.getBoard()));

								int destX = view.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = view.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								view.setPieceLocation(model.getCurP(), view.getPanelTile(destY, destX));

								if (p2.getPiece((model.getCurP().getIndex())).getName().equals("Pawn")
										&& newCords[0] == 7) {
									promote(model.getCurP(), view, model);
								}

								endTurn();
								model.setCurP(null);
								endGame(model.getTurn());
								if (model.getPlaying()) {
									handleTurnAI(model, view, p1, p2, model.getTurn());
								}
							}

						}
					});
				}
			}
		}
	}

	public void initializeTiles() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				view.getPanelTile(i, j).addMouseListener(new MouseAdapter() {

					@Override
					public void mouseClicked(MouseEvent e) {
						Player p1 = model.getPlayer1();
						Player p2 = model.getPlayer2();

						if (model.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
							if (model.getCurP() != null) {
								if (e.getSource() == model.getCurP().getParent()) {
									model.setCurP(null);
								}

								else if (((JPanel) e.getSource()).getComponentCount() != 0) {
									model.setCurP(null);
								} else {
									view.recolorTiles();
									// Moves piece to said tile

									view.setPieceLocation(model.getCurP(), (JPanel) e.getSource());

									// Gets coordinates of tile
									Object o = e.getSource();
									int[] newCords = getCoordinates(o);
									System.out.println(newCords[0]);

//									p1.movePiece(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p2);
									if (p1.getPiece(model.getCurP().getIndex()).getName().equals("King") && newCords[1] == 6
											&& p1.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(7, 7), view.getPanelTile(7, 5));
									}

									if (p1.getPiece(model.getCurP().getIndex()).getName().equals("King") && newCords[1] == 2
											&& p1.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(7, 0), view.getPanelTile(7, 3));
									}
																		
									model.handleMove(model.getBoard(), p1.getPiece(model.getCurP().getIndex()), newCords, p1, p2);
									model.setPlaying(!p1.checkWin(p2));
									if (p1.getPiece((model.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
										promote(model.getCurP(), view, model);
									}
									System.out.println(model.getBoard());
									endTurn();
									model.setCurP(null);
									endGame(model.getTurn());
//									 turn ai
									if(model.getPlaying()) {
										handleTurnAI(model, view, p1, p2, false);
									}
								}
							}
						} 
						if (!model.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
							if (model.getCurP() != null) {
								if (e.getSource() == model.getCurP().getParent()) {
									model.setCurP(null);
								}
								else if (((JPanel) e.getSource()).getComponentCount() != 0) {
									model.setCurP(null);
								} else {
									view.recolorTiles();
									// Moves piece to said tile
									view.setPieceLocation(model.getCurP(), (JPanel) e.getSource());

									// Gets coordinates of tile
									Object o = e.getSource();
									int[] newCords = getCoordinates(o);

//									p2.movePiece(m.getBoard(), p2.getPiece(m.getCurP().getIndex()), newCords, p1);
									if (p2.getPiece(model.getCurP().getIndex()).getName().equals("King") && newCords[1] == 6
											&& p2.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(0, 7), view.getPanelTile(0, 5));
									}
									if (p2.getPiece(model.getCurP().getIndex()).getName().equals("King") && newCords[1] == 2
											&& p2.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(0, 0), view.getPanelTile(0, 3));
									}
									model.handleMove(model.getBoard(), p2.getPiece(model.getCurP().getIndex()), newCords, p2, p1);
									if (p2.getPiece((model.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
										promote(model.getCurP(), view, model);
									}
									model.setPlaying(!p2.checkWin(p1));

									endTurn();
									model.setCurP(null);
									endGame(model.getTurn());

									if(model.getPlaying()) {
										handleTurnAI(model, view, p1, p2, model.getTurn());
									}
								}
							}
						}

					}
					@Override
					public void mouseEntered(MouseEvent e) {
						if (((JPanel) e.getSource()).getBackground() == Color.green && model.getCurP() != null
								&& ((JPanel) e.getSource()).getComponentCount() == 0) {
							((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else {
							((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}

					}
				});

			}
		}
	}
	
	public void handleTurnAI(Model model, View view, Player white, Player black, boolean turnWhite) {
		int[] bestMove = model.alphabeta(turnWhite, 3, model.getBoard(), white, black, Integer.MIN_VALUE, Integer.MAX_VALUE);
		model.setCurP(view.getPiece(bestMove[3], bestMove[4]));
		int []newCords = {bestMove[1],bestMove[2]};
		
		if(turnWhite) {
		if(view.getPanelTile(bestMove[1], bestMove[2]).getComponentCount()>0) {
			view.getPanelTile(bestMove[1], bestMove[2]).remove(0);
		}
		model.handleMove(model.getBoard(), white.getPiece(model.getCurP().getIndex()), newCords, white, black);
		view.setPieceLocation(model.getCurP(), view.getPanelTile(bestMove[1],bestMove[2]));
		// phong cap
		if (model.getCurP().getIndex() < 8 &&  newCords[0] == 0) {
			model.getCurP().setNameAndUpdateIcon("Queen");
			model.promote(model.getBoard(), white, black, model.getBoard().getTile(newCords[0], newCords[1]).getPiece(), 1);
		}
		// nhap thanh gan
		if (model.getCurP().getIndex()== 12 && newCords[1] == 6
				&& white.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
			view.setPieceLocation(view.getPiece(newCords[0], 7), view.getPanelTile(newCords[0], 5));
		}
		// nhap thanh xa
		if (model.getCurP().getIndex()== 12 && newCords[1] == 2
				&& white.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
			view.setPieceLocation(view.getPiece(newCords[0], 0), view.getPanelTile(newCords[0], 3));
		}
		}
		else {
		if(view.getPanelTile(bestMove[1], bestMove[2]).getComponentCount()>0) {
			view.getPanelTile(bestMove[1], bestMove[2]).remove(0);
		}
		model.handleMove(model.getBoard(), black.getPiece(model.getCurP().getIndex()), newCords, black, white);
		view.setPieceLocation(model.getCurP(), view.getPanelTile(bestMove[1],bestMove[2]));
		System.out.println(model.getBoard().getTile(newCords[0], newCords[1]).getPiece().getName());
		// phong cap
		if (model.getBoard().getTile(newCords[0], newCords[1]).getPiece().getName().equals("Pawn") && (newCords[0] == 7 || newCords[0]==0)) {
			model.getCurP().setNameAndUpdateIcon("Queen");
			model.promote(model.getBoard(), white, black, model.checkPromotion(model.getBoard()), 1);
		}
		// nhap thanh gan
		if (model.getCurP().getIndex()==12 && newCords[1] == 6
				&& black.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
			view.setPieceLocation(view.getPiece(newCords[0], 7), view.getPanelTile(newCords[0], 5));
		}
		// nhap thanh xa
		if (model.getCurP().getIndex()==12 && newCords[1] == 2
				&& black.getPiece(model.getCurP().getIndex()).getCords()[1] == 4) {
			view.setPieceLocation(view.getPiece(newCords[0], 0), view.getPanelTile(newCords[0], 3));
		}
		}
			
		endTurn();
		model.setCurP(null);
		endGame(model.getTurn());
	}
	
	// phong cap
	public void promote(PieceDisplay currP, View v, Model m) {
		String piece = v.showPromotionDialog();
		switch (piece) {
		case "Queen": {
			currP.setNameAndUpdateIcon("Queen");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), m.checkPromotion(m.getBoard()), 1);
			break;
		}
		case "Rook": {
			currP.setNameAndUpdateIcon("Rook");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), model.checkPromotion(m.getBoard()), 2);
			break;
		}
		case "Bishop": {
			currP.setNameAndUpdateIcon("Bishop");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), model.checkPromotion(m.getBoard()), 3);
			break;
		}
		case "Knight": {
			currP.setNameAndUpdateIcon("Knight");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), model.checkPromotion(m.getBoard()), 4);
			break;
		}
		}
	}
	
	public boolean startWithWhite() {
		String startWithWhite = view.showStartColorDialog();
		if(startWithWhite.equalsIgnoreCase("Bắt đầu với trắng")) {
			return true;
		}
		else { 
			return false;
		}
		}

	// loai bo su kien chuot de nguoi choi khong the di chuyen quan co khi tro choi ket thuc
	public void removeListeners() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (view.getPiece(i, j) != null) {
					view.getPiece(i, j).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					view.getPiece(i, j).removeMouseListener(view.getPiece(i, j).getMouseListeners()[0]);
				}
			}
		}
	}

	public void endGame(boolean bWin) {
		if (!bWin) {
			// Kiểm tra thắng bằng chiếu bí hoặc loại vua
			if (model.getPlayer1().checkWin(model.getPlayer2())
					|| model.isCheckmate(model.getPlayer2(), model.getPlayer1(), model.getBoard())) {
				view.setTurnLabelText("Player 1 wins!");
				removeListeners(); // Kết thúc trò chơi
			}
		} else {
			if (model.getPlayer2().checkWin(model.getPlayer1())
					|| model.isCheckmate(model.getPlayer1(), model.getPlayer2(), model.getBoard())) {
				view.setTurnLabelText("Player 2 wins!");
				removeListeners(); // Kết thúc trò chơi
			}
		}
	}


	public void endTurn() {
		model.setTurn(!model.getTurn());
	}

	public int[] getCoordinates(Object o) {
		int[] newCords = new int[2];
		newCords[0] = view.getTileCoordY(o);
		newCords[1] = view.getTileCoordX(o);
		return newCords;
	}
}

