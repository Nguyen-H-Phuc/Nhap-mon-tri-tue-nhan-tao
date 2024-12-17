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
	
	public Controller(View view, Model model) {
		this.view = view;
		this.model = model;
		initializeTeams();
	}

	private void initializeTeams() {
		getModel().setPlaying(true);
		initializeTiles();
		getModel().setCurP(null);
		getModel().addPlayers("White");
		getModel().setTurn(true);
		initializeListeners();
	}

	public void initializeListeners() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (view.getPiece(i, j) != null) {
					view.getPiece(i, j).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					view.getPiece(i, j).addMouseListener(new MouseListener() {

						Model m;
						View v;
						Player p1;
						Player p2;

						@Override
						public void mouseClicked(MouseEvent e) {
							m = getModel();
							v = getView();
							p1 = model.getPlayer1();
							p2 = model.getPlayer2();
							

							if (m.getTurn() && p1.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (m.getCurP() == null) {
									m.setCurP((PieceDisplay) e.getSource());
									System.out.println(m.getCurP().getIndex());
									// Highlight valid moves in green
									for (int[] validMove : m.listMoveLegal(p1, p2,
											p1.getPiece(m.getCurP().getIndex()),m.getBoard())) {
										v.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
										System.out.println(m.getBoard().toString());
									}
								} else if (e.getSource() == m.getCurP()) {
									v.recolorTiles();
									m.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent()).getBackground() == Color.GREEN 
									&& m.getCurP() != null && m.getTurn()
									&& ((e.getSource() != m.getCurP())
									&& (((PieceDisplay) e.getSource()).getColor()).equals(p2.getColor()))) {
								v.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								p1.movePiece(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p2);
								m.setPlaying(!p1.checkWin(p2) || m.isCheckmate(p1, p2));

								int destX = v.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = v.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								v.setPieceLocation(m.getCurP(), v.getPanelTile(destY, destX));
								
								//phong cap
								if (p1.getPiece((m.getCurP().getIndex())).getName().equals("Pawn") && newCords[0] == 0) {
									promote(m.getCurP(), v, m);
								}
								
								endTurn();
								m.setCurP(null);
								endGame(m.getPlaying());
							} 

							else if (!m.getTurn() && p2.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (m.getCurP() == null) {
									// Select the piece
									m.setCurP((PieceDisplay) e.getSource());
									// Highlight valid moves in green
									for (int[] validMove : m.listMoveLegal(p2, p1,
											p2.getPiece(m.getCurP().getIndex()),m.getBoard())) {
										v.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
									}
								} else if (e.getSource() == m.getCurP()) {
									// Deselect if clicked again
									v.recolorTiles();
									m.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent()).getBackground() == Color.GREEN 
									&& m.getCurP() != null && !m.getTurn()
									&& ((e.getSource() != m.getCurP())
									&& (((PieceDisplay) e.getSource()).getColor()).equals(p1.getColor()))) {
								v.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								p2.movePiece(m.getBoard(), p2.getPiece(m.getCurP().getIndex()), newCords, p1);
								m.setPlaying(!p2.checkWin(p1) || m.isCheckmate(p2, p1));

								int destX = v.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = v.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								v.setPieceLocation(m.getCurP(), v.getPanelTile(destY, destX));
								
								if (p2.getPiece((m.getCurP().getIndex())).getName().equals("Pawn")
										&& newCords[0] == 7) {
								promote(m.getCurP(), v, m);
								}

								endTurn();
								m.setCurP(null);
								endGame(m.getPlaying());
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
					View view;
					Player p1;
					Player p2;

					@Override
					public void mouseClicked(MouseEvent e) {
						m = getModel();
						p1 = m.getPlayer1();
						p2 = m.getPlayer2();
						view = getView();

						if (m.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
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
									System.out.println(newCords[0]);

//									p1.movePiece(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p2);
									if (p1.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 6
											&& p1.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(7, 7), view.getPanelTile(7, 5));
									}

									if (p1.getPiece(m.getCurP().getIndex()).getName().equals("King") && newCords[1] == 2
											&& p1.getPiece(m.getCurP().getIndex()).getCords()[1] == 4) {
										view.setPieceLocation(view.getPiece(7, 0), view.getPanelTile(7, 3));
									}
									m.processMove(m.getBoard(), p1.getPiece(m.getCurP().getIndex()), newCords, p1, p2);
									m.setPlaying(!p1.checkWin(p2));

									endTurn();
									m.setCurP(null);

									endGame(m.getPlaying());
								}
							}
					} 
							else if (!m.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
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
									m.processMove(m.getBoard(), p2.getPiece(m.getCurP().getIndex()), newCords, p2, p1);

									m.setPlaying(!p2.checkWin(p1));

									endTurn();
									m.setCurP(null);

									endGame(m.getPlaying());
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
	
	public void promote(PieceDisplay currP, View v, Model m) {
		String piece = v.showPromotionDialog();
		switch (piece) {
		case "Queen": {
			currP.setNameAndUpdateIcon("Queen");
			m.promote(m.getBoard(),m.getPlayer1(), m.getPlayer2(),m.checkPromotion(m.getBoard()), 1);
			break;
		}
		case "Rook": {
			currP.setNameAndUpdateIcon("Rook");
			m.promote(m.getBoard(),m.getPlayer1(), m.getPlayer2(),getModel().checkPromotion(m.getBoard()), 2);
			break;
		}
		case "Bishop": {
			currP.setNameAndUpdateIcon("Bishop");
			m.promote(m.getBoard(),m.getPlayer1(), m.getPlayer2(),getModel().checkPromotion(m.getBoard()), 3);
			break;
		}
		case "Knight": {
			currP.setNameAndUpdateIcon("Knight");
			m.promote(m.getBoard(),m.getPlayer1(), m.getPlayer2(),getModel().checkPromotion(m.getBoard()), 4);
			break;
		}
		}
	}


	
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
			if (m.getPlayer1().checkWin(m.getPlayer2()) || m.isCheckmate(m.getPlayer2(), m.getPlayer1())) {
				getView().setTurnLabelText("Player 1 wins!");
				removeListeners(); // Kết thúc trò chơi
			}
		} 
	    else {
	        if (m.getPlayer2().checkWin(m.getPlayer1()) || m.isCheckmate(m.getPlayer1(), m.getPlayer2())) {
	            getView().setTurnLabelText("Player 2 wins!");
	            removeListeners(); // Kết thúc trò chơi
	            return;
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
