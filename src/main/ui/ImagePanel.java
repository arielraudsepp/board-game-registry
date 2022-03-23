package ui;

// Code modified from
// http://www.java2s.com/Tutorials/Java/Swing_How_to/JLabel/Show_animated_GIF_without_using_a_JLabel.htm
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Graphics;
import javax.swing.*;

public class ImagePanel extends JPanel {
    Image image;

    public ImagePanel() {
        image = Toolkit.getDefaultToolkit().createImage("./data/gameNight.gif");
    }

    // MODIFIED: this
    // EFFECTS: draws image in panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
