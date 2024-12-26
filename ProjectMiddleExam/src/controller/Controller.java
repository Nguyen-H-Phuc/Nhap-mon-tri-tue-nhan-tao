package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
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
		getModel().addPlayers("White");
		getModel().setPlaying(true);
		getModel().setTurn(true);
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
					view.getPiece(i, j).addMouseListener(new MouseListener() {

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

						@Override
						public void mousePressed(MouseEvent e) {
						}

						@Override
						public void mouseReleased(MouseEvent e) {
						}

						@Override
						public void mouseEntered(MouseEvent e) {
						}

						@Override
						public void mouseExited(MouseEvent e) {
						}
					});
				}
			}
		}
	}

	public void initializeTiles() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				getView().getPanelTile(i, j).addMouseListener(new MouseListener() {

					Model m;
					View v;
					Player p1;
					Player p2;

					@Override
					public void mouseClicked(MouseEvent e) {
						m = getModel();
						p1 = m.getPlayer1();
						p2 = m.getPlayer2();
						v = getView();

						if (m.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
							if (m.getCurP() != null) {
								if (e.getSource() == m.getCurP().getParent()) {
									m.setCurP(null);
								}

								else if (((JPanel) e.getSource()).getComponentCount() != 0) {
									m.setCurP(null);
								} else {
									v.recolorTiles();
									// Moves piece to said tile

									v.setPieceLocation(m.getCurP(), (JPanel) e.getSource());

									// Gets coordinates of tile
									Object o = e.getSource();
									int[] newCords = getCoordinates(o);
									System.out.println(newCords[0]);

//									p1.movePiece(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p2);
									if (p1.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 6
											&& p1.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										v.setPieceLocation(v.getPiece(7, 7), v.getPanelTile(7, 5));
									}

									if (p1.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 2
											&& p1.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										v.setPieceLocation(v.getPiece(7, 0), v.getPanelTile(7, 3));
									}
																		
									m.handleMove(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p1, p2);
									m.setPlaying(!p1.checkWin(p2));
									if (p1.getPiece((m.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
										promote(m.getCurP(), v, m);
									}
									System.out.println(m.getBoard());
									endTurn();
									m.setCurP(null);
									endGame(m.getTurn());
//									 turn ai
									if(m.getPlaying()) {
										handleTurnAI(m, v, p1, p2, false);
									}
								}
							}
						} 
						if (!m.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
							if (m.getCurP() != null) {
								if (e.getSource() == m.getCurP().getParent()) {
									m.setCurP(null);
								}
								else if (((JPanel) e.getSource()).getComponentCount() != 0) {
									m.setCurP(null);
								} else {
									view.recolorTiles();
									// Moves piece to said tile
									view.setPieceLocation(m.getCurP(), (JPanel) e.getSource());

									// Gets coordinates of tile
									Object o = e.getSource();
									int[] newCords = getCoordinates(o);

//									p2.movePiece(m.getBoard(), p2.getPiece(m.getCurP().getIndex()), newCords, p1);
									if (p2.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 6
											&& p2.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(0, 7), view.getPanelTile(0, 5));
									}
									if (p2.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 2
											&& p2.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(0, 0), view.getPanelTile(0, 3));
									}
									m.handleMove(m.getBoard(), p2.getPiece(m.getCurP().getIndex()), newCords, p2, p1);
									if (p2.getPiece((m.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
										promote(m.getCurP(), v, m);
									}
									m.setPlaying(!p2.checkWin(p1));

									endTurn();
									m.setCurP(null);
									endGame(m.getTurn());

									if(m.getPlaying()) {
										handleTurnAI(m, v, p1, p2, m.getTurn());
									}
								}
							}
						}

					}
					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						m = getModel();
						if (((JPanel) e.getSource()).getBackground() == Color.green && m.getCurP() != null
								&& ((JPanel) e.getSource()).getComponentCount() == 0) {
							((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						} else {
							((Component) e.getSource()).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						}

					}

					@Override
					public void mouseExited(MouseEvent e) {
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
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), getModel().checkPromotion(m.getBoard()), 2);
			break;
		}
		case "Bishop": {
			currP.setNameAndUpdateIcon("Bishop");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), getModel().checkPromotion(m.getBoard()), 3);
			break;
		}
		case "Knight": {
			currP.setNameAndUpdateIcon("Knight");
			m.promote(m.getBoard(), m.getPlayer1(), m.getPlayer2(), getModel().checkPromotion(m.getBoard()), 4);
			break;
		}
		}
	}
	
	public boolean startWithWhite() {
		String startWithWhite = getView().showStartColorDialog();
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
				if (getView().getPiece(i, j) != null) {
					getView().getPiece(i, j).setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					getView().getPiece(i, j).removeMouseListener(getView().getPiece(i, j).getMouseListeners()[0]);
				}
			}
		}
	}

	public void endGame(boolean bWin) {
		Model m = getModel();

		if (!bWin) {
			// Kiểm tra thắng bằng chiếu bí hoặc loại vua
			if (m.getPlayer1().checkWin(m.getPlayer2())
					|| m.isCheckmate(m.getPlayer2(), m.getPlayer1(), m.getBoard())) {
				getView().setTurnLabelText("Player 1 wins!");
				removeListeners(); // Kết thúc trò chơi
			}
		} else {
			if (m.getPlayer2().checkWin(m.getPlayer1())
					|| m.isCheckmate(m.getPlayer1(), m.getPlayer2(), m.getBoard())) {
				getView().setTurnLabelText("Player 2 wins!");
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

	public Model getModel() {
		return model;
	}

	public View getView() {
		return view;
	}
}
