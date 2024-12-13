package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class View {
	private JFrame frame;
	private JPanel gameBoard;
	private JPanel[][] tileSet;
	private int[][] tileCoordsX;
	private int[][] tileCoordsY;
	private JLabel turnLabel;
	private JPanel starterPanel;
	private JPanel panel;
	private PieceDisplay[][] pieces;
	private JPanel topPanel;
	private JPanel rightPanel;
	private JPanel leftPanel;
	private JPanel bottomPanel, pieceLeftPanel, pieceRightPanel;

    
	public View() {
		turnLabel = new JLabel("San sang!");
		frame = new JFrame("Chess");
		frame.setSize(1000, 825);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout()); 

		// Game board setup
		gameBoard = new JPanel();
		gameBoard.setLayout(new BorderLayout());

		panel = new JPanel();
		GridLayout chessBoard = new GridLayout(8, 8);
		tileSet = new JPanel[8][8];
		tileCoordsX = new int[8][8];
		tileCoordsY = new int[8][8];
		panel.setPreferredSize(new Dimension(800, 800)); 
		panel.setLayout(chessBoard);

		Color tempColor;
		boolean color = true;

		// Initialize the 8x8 grid of tiles with 100x100 size
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tempColor = color ? Color.decode("#4f772d") : Color.decode("#90a955");
				color = !color;

				JPanel boardPanel = new JPanel();
				boardPanel.setBackground(tempColor);
				boardPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for each tile
				tileCoordsX[i][j] = j;
				tileCoordsY[i][j] = i;

				tileSet[i][j] = boardPanel;
				panel.add(tileSet[i][j]);
			}
			color = !color; // Alternate colors for each row
		}

		gameBoard.add(panel, BorderLayout.CENTER);

		// TOP Panel
		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		
		JLabel titleBar = new JLabel("Chess");
		titleBar.setFont(new Font("Arial", Font.BOLD, 20));
		titleBar.setAlignmentX(JLabel.CENTER_ALIGNMENT);

		topPanel.add(Box.createGlue());
		topPanel.add(titleBar);
		topPanel.add(Box.createGlue());
		
		turnLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		topPanel.add(turnLabel);
		topPanel.add(Box.createGlue());

		topPanel.setPreferredSize(new Dimension(900, 80));			
		
		rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(100, 800));

		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(100, 800));
		
		bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(900, 25));
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(gameBoard, BorderLayout.CENTER);
		frame.add(rightPanel, BorderLayout.EAST);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		

		pieces = new PieceDisplay[2][16];
		initializePieces();

		frame.revalidate();
		frame.repaint();
		frame.setVisible(true);
	}

	public void initializePieces() {
		String color;
		for (int i = 0; i < 2; i++) { // Loop only up to 2
			color = (i == 0) ? "Black" : "White";
			for (int j = 0; j < 16; j++) { // Each player has up to 16 pieces
				pieces[i][j] = new PieceDisplay("", j, color);
			}
		}

		String black = "Black";
		pieces[0][8] = new PieceDisplay("Rook", 8,black); // Rook
		pieces[0][9] = new PieceDisplay("Knight", 9,black); // Knight
		pieces[0][10] = new PieceDisplay("Bishop", 10, black); // Bishop
		pieces[0][11] = new PieceDisplay("Queen", 11,black); // Queen
		pieces[0][12] = new PieceDisplay("King", 12,black); // King
		pieces[0][13] = new PieceDisplay("Bishop", 13,black); // Bishop
		pieces[0][14] = new PieceDisplay("Knight", 14, black); // Knight
		pieces[0][15] = new PieceDisplay("Rook", 15, black); // Rook
		for (int j = 0; j < 8; j++) {
			pieces[0][j] = new PieceDisplay("Pawn", j, black); // Pawns
			tileSet[1][j].add(pieces[0][j]);
			tileSet[0][j].add(pieces[0][j+8]);
		}

		// White pieces at the bottom
		String white = "White";
		pieces[1][8] = new PieceDisplay("Rook",8, white); // Rook
		pieces[1][9] = new PieceDisplay("Knight",9, white); // Knight
		pieces[1][10] = new PieceDisplay("Bishop",10, white); // Bishop
		pieces[1][11] = new PieceDisplay("Queen",11, white); // Queen
		pieces[1][12] = new PieceDisplay("King",12, white); // King
		pieces[1][13] = new PieceDisplay("Bishop",13, white); // Bishop
		pieces[1][14] = new PieceDisplay("Knight",14, white); // Knight
		pieces[1][15] = new PieceDisplay("Rook",15, white); // Rook

		// Add white pawns in the second-last row
		for (int j = 0; j < 8; j++) {
			pieces[1][j] = new PieceDisplay("Pawn", j, white); // Pawns
			tileSet[7][j].add(pieces[1][j+8]); // White major pieces on bottom row
			tileSet[6][j].add(pieces[1][j]); // White pawns in the second-last row
		}
	}
		
	public String showPromotionDialog() {
	    // Tạo dialog phong cấp
	    JDialog promotionDialog = new JDialog(frame, "Promotion", true);
	    promotionDialog.setLayout(new FlowLayout());
	    promotionDialog.setSize(300, 150);

	    JLabel label = new JLabel("Chọn quân cờ để phong cấp:");
	    label.setFont(new Font("Arial", Font.BOLD, 16));
	    promotionDialog.add(label);

	    // Các lựa chọn phong cấp
	    String[] pieces = { "Queen", "Rook", "Bishop", "Knight" };
	    Map<String, JButton> buttons = new HashMap<>();
	    final String[] selectedPiece = { null };

	    for (String piece : pieces) {
	        JButton button = new JButton(piece);
	        button.addActionListener(e -> {
	            selectedPiece[0] = piece;
	            promotionDialog.dispose();
	        });
	        buttons.put(piece, button);
	        promotionDialog.add(button);
	    }

	    // Hiển thị dialog ở giữa màn hình
	    promotionDialog.setLocationRelativeTo(frame);
	    promotionDialog.setVisible(true);

	    // Trả về lựa chọn của người chơi
	    return selectedPiece[0];
	}


	public void recolorTiles() {
	    Color tempColor;
	    // Lặp qua 8 hàng
	    for (int i = 0; i < 8; i++) {
	        // Đặt lại biến color cho mỗi hàng
	        boolean color = (i % 2 == 0); // Nếu hàng chẵn, bắt đầu với màu đầu tiên
	        // Lặp qua 8 cột
	        for (int j = 0; j < 8; j++) {
	            tempColor = color ? Color.decode("#4f772d") : Color.decode("#90a955");
	            tileSet[i][j].setBackground(tempColor);
	            color = !color; // Đảo màu cho ô tiếp theo
	        }
	    }
	}

	public JFrame getFrame() {
		return frame;
	}

	public PieceDisplay getPiece(int x, int y) {
		if (tileSet[x][y].getComponentCount() > 0) {
			return (PieceDisplay) tileSet[x][y].getComponent(0);
		} else
			return null;
	}

	public JPanel getPanelTile(int x, int y) {
		return tileSet[x][y];
	}

	// Gets all tiles in the gameBoard for referencing.
	public JPanel[][] getPanelTiles() {
		return tileSet;
	}

	// Returns the x coordinate of a certain tile in the board.
	public int getTileCoordX(Object o) {
		int x = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (o == tileSet[i][j])
					x = j;
		if (x >= 0)
			return x;
		else
			return 0;
	}

	// Returns the y coordinate of a certain tile in the board.

	public int getTileCoordY(Object o) {
		int y = 0;
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				if (o == tileSet[i][j])
					y = i;
		if (y >= 0)
			return y;
		else
			return 0;
	}

	// Gets the gameboard, the mainframe of the GUI. return gameBoard reference.

	public JPanel getGameBoard() {
		return gameBoard;
	}

	// Moves piece to said tile/location using x and y coordinates.
	public void setPieceLocation(PieceDisplay piece, int x, int y) {
		PieceDisplay pieceDisp = piece;
		tileSet[y][x].add(pieceDisp);
	}

	// Moves piece to said tile/location using a destination panel.
	public void setPieceLocation(PieceDisplay piece, JPanel destPanel) {
		JPanel refPanel = (JPanel) piece.getParent();
		refPanel.remove(0);
		refPanel.revalidate();
		refPanel.repaint();
		PieceDisplay pieceDisp = piece;
		pieceDisp.setVisible(true);
		destPanel.add(pieceDisp);
		destPanel.repaint();
	}
	

	// Sets the text at the top to determine whose turn it is and if the game has
	// ended.

	public void setTurnLabelText(String t) {
		turnLabel.setText(t);
	}
	
	public void setPieceRightPanel(boolean visible) {
		pieceRightPanel.setVisible(visible);
	}
	
	public JPanel getPieceRightPanel() {
		return pieceRightPanel;
	}
	
	public void setPieceLeftPanel(boolean visible) {
		pieceLeftPanel.setVisible(visible);
	}
	
	public JPanel getPieceLeftPanel() {
		return pieceLeftPanel;
	}

}
