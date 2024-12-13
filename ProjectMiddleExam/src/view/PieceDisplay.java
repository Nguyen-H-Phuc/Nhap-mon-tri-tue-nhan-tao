package view;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;

public class PieceDisplay extends JLabel {
	protected String name;
    protected int index;
    protected String color;

    public PieceDisplay(String name, int position, String color) {
        setPreferredSize(new Dimension(100, 100));
        this.name = name;
        this.index = position;
        this.color = color;


        URL imgIconUrl = null;
        if (color.equals("White")) {
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
    
    public void setNameAndUpdateIcon(String newName) {
        this.name = newName; // Cập nhật thuộc tính name
        
        URL imgIconUrl = null;
        if (this.color.equals("White")) {
            if (newName.equals("Pawn"))
                imgIconUrl = getClass().getResource("icons/w-pawn.png");
            else if (newName.equals("Rook"))
                imgIconUrl = getClass().getResource("icons/w-rook.png");
            else if (newName.equals("Bishop"))
                imgIconUrl = getClass().getResource("icons/w-bishop.png");
            else if (newName.equals("Knight"))
                imgIconUrl = getClass().getResource("icons/w-knight.png");
            else if (newName.equals("Queen"))
                imgIconUrl = getClass().getResource("icons/w-queen.png");
            else if (newName.equals("King"))
                imgIconUrl = getClass().getResource("icons/w-king.png");
        } else {
            if (newName.equals("Pawn"))
                imgIconUrl = getClass().getResource("icons/b-pawn.png");
            else if (newName.equals("Rook"))
                imgIconUrl = getClass().getResource("icons/b-rook.png");
            else if (newName.equals("Bishop"))
                imgIconUrl = getClass().getResource("icons/b-bishop.png");
            else if (newName.equals("Knight"))
                imgIconUrl = getClass().getResource("icons/b-knight.png");
            else if (newName.equals("Queen"))
                imgIconUrl = getClass().getResource("icons/b-queen.png");
            else if (newName.equals("King"))
                imgIconUrl = getClass().getResource("icons/b-king.png");
        }

        // Cập nhật icon nếu URL không null
        if (imgIconUrl != null) {
            ImageIcon originalIcon = new ImageIcon(imgIconUrl);
            Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaledImage));
        }

        revalidate(); // Yêu cầu cập nhật lại giao diện
        repaint();
    }

    
    public String getColor() {
    	return color;
    }
    
    public int getIndex() {
    	return index;
    }
}
