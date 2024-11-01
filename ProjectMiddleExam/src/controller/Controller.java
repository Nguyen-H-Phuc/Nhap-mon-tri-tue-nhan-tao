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
		
		model.setTurn(true);
		model.setPlaying(false);
		initializeTeams();
	}

	private void initializeTeams() {
		model.setPlaying(true);
		initializeListeners();
		initializeTiles();

		model.setCurP(null);
		model.addPlayers("White");
		model.setTurn(true);
	}

	public void initializeListeners() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (view.getPiece(i, j) != null) {
					view.getPiece(i, j).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					view.getPiece(i, j).addMouseListener(new MouseListener() {

						

						@Override
						public void mouseClicked(MouseEvent e) {
							Model model = getModel();
							View view = getView();
							Player p1 = model.getPlayer1();
							Player p2 = model.getPlayer2();
							if (model.getTurn() && p1.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (model.getCurP() == null) {

									model.setCurP((PieceDisplay) e.getSource());
									// Highlight valid moves in green
									for (int[] validMove : p1.getPiece(model.getCurP().getPosition())
											.listValidMoves(model.getBoard())) {
										view.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
									}
								} else if (e.getSource() == model.getCurP()) {

									view.recolorTiles();
									model.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent())
									.getBackground() == Color.green && model.getCurP() != null && model.getTurn()
									&& ((e.getSource() != model.getCurP())
											&& (((PieceDisplay) e.getSource()).getColor()).equals(p2.getColor()))) {
								view.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								p1.movePiece(model.getBoard(), p1.getPiece(model.getCurP().getPosition()), newCords,
										p2);
								model.setPlaying(!p1.checkWin(p2));

								int destX = view.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = view.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								view.setPieceLocation(model.getCurP(), view.getPanelTile(destY, destX));

								endTurn();
								model.setCurP(null);
								endGame(model.getPlaying());

							}

							else if (!model.getTurn() && p2.getColor().equals(((PieceDisplay) e.getSource()).getColor())) {
								if (model.getCurP() == null) {
									// Select the piece
									model.setCurP((PieceDisplay) e.getSource());
									// Highlight valid moves in green
									for (int[] validMove : p2.getPiece(model.getCurP().getPosition())
											.listValidMoves(model.getBoard())) {
										view.getPanelTile(validMove[0], validMove[1]).setBackground(Color.GREEN);
									}
								} else if (e.getSource() == model.getCurP()) {
									// Deselect if clicked again
									view.recolorTiles();
									model.setCurP(null);
								}
							} else if (((JPanel) ((PieceDisplay) e.getSource()).getParent())
									.getBackground() == Color.green && model.getCurP() != null && model.getTurn()
									&& ((e.getSource() != model.getCurP())
											&& (((PieceDisplay) e.getSource()).getColor()).equals(p2.getColor()))) {
								view.recolorTiles();
								Object o = e.getComponent().getParent();
								int[] newCords = getCoordinates(o);

								p2.movePiece(model.getBoard(), p2.getPiece(model.getCurP().getPosition()), newCords,
										p2);
								model.setPlaying(!p2.checkWin(p1));

								int destX = view.getTileCoordX((JPanel) ((PieceDisplay) e.getSource()).getParent());
								int destY = view.getTileCoordY((JPanel) ((PieceDisplay) e.getSource()).getParent());

								((PieceDisplay) e.getSource()).getParent().remove(0);
								view.setPieceLocation(model.getCurP(), view.getPanelTile(destY, destX));

								endTurn();
								model.setCurP(null);
								endGame(model.getPlaying());

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
					}
					);
				}
			}
		}
	}

	public void initializeTiles() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				getView().getPanelTile(i, j).addMouseListener(new MouseListener() {

					Model m;
					Player p1; 
					Player p2;

					@Override
					public void mouseClicked(MouseEvent e) {
						 m = getModel();
						 p1 = m.getPlayer1();
						 p2 = m.getPlayer2();

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

									p1.movePiece(m.getBoard(), p1.getPiece(m.getCurP().getPosition()), newCords, p2);
									m.setPlaying(!p1.checkWin(p2));

									endTurn();
									m.setCurP(null);

									endGame(m.getPlaying());
								}
							}
						} else if (!m.getTurn() && ((JPanel) e.getComponent()).getBackground().equals(Color.green)) {
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

									p2.movePiece(m.getBoard(), p2.getPiece(m.getCurP().getPosition()), newCords, p1);
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
			Model m = model;
			if (m.getPlayer1().checkWin(m.getPlayer2())) {
				view.setTurnLabelText("Player 1 wins!");
			} else
				view.setTurnLabelText("Player 2 wins !");

			removeListeners();
		}
	}

	public void endTurn() {
		model.setTurn(!model.getTurn());
	}

	public int[] getCoordinates(Object o) {
		int[] newCords;
		newCords = new int[2];
		newCords[0] = view.getTileCoordX(o);
		newCords[1] = view.getTileCoordY(o);
		return newCords;
	}
	
	public Model getModel() {
		return model;
	}
	
	public View getView() {
		return view;
	}
}
