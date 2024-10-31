package view;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class PieceDisplay extends JLabel {
	protected String name;
    protected int position;
    protected String colS;

    public PieceDisplay(String name, int position, String c) {
        setPreferredSize(new Dimension(100, 100));  // Sửa lại kích thước để khớp với ô 100x100
        name = name;
        position = position;
        colS = c;

        URL imgIconUrl = null;
        if (c.equals("White")) {
            if (name.equals("Pawn"))
                imgIconUrl = getClass().getResource("icons/w-pawn.png");
            else if (name.equals("Rook"))
                imgIconUrl = getClass().getResource("icons/w-rook.png");
            else if (name.equals("Bishop"))
                imgIconUrl = getClass().getResource("icons/w-bishop.png");
            else if (name.equals("Knight"))
                imgIconUrl = getClass().getResource("icons/w-knight.png");
            else if (name.equals("Queen"))
                imgIconUrl = getClass().getResource("icons/w-queen.png");
            else if (name.equals("King"))
                imgIconUrl = getClass().getResource("icons/w-king.png");
        } else {
            if (name.equals("Pawn"))
                imgIconUrl = getClass().getResource("icons/b-pawn.png");
            else if (name.equals("Rook"))
                imgIconUrl = getClass().getResource("icons/b-rook.png");
            else if (name.equals("Bishop"))
                imgIconUrl = getClass().getResource("icons/b-bishop.png");
            else if (name.equals("Knight"))
                imgIconUrl = getClass().getResource("icons/b-knight.png");
            else if (name.equals("Queen"))
                imgIconUrl = getClass().getResource("icons/b-queen.png");
            else if (name.equals("King"))
                imgIconUrl = getClass().getResource("icons/b-king.png");
        }

        // Kiểm tra nếu imgIconUrl không null, sau đó điều chỉnh kích thước icon
        if (imgIconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imgIconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
        }
        
        // Tùy chọn căn giữa icon trong ô
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);

        revalidate();
        repaint();
    }
    
    public String getColor() {
    	return colS;
    }
    
    public int getPosition() {
    	return position;
    }
}
