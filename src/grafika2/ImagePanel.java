package grafika2;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

class ImagePanel extends JPanel 
{
    private Image image;
    public ImagePanel(Image image) 
    {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) 
    {
       g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}