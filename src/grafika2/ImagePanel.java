package grafika2;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

@SuppressWarnings("serial")
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
    	/*int height = getHeight() > getWidth() ? (int)((image.getHeight(null)/image.getWidth(null))*getWidth())
    			: getHeight();
    	int width = getHeight() > getWidth() ? getWidth()
    			: (int)((image.getWidth(null)/image.getHeight(null))*getHeight());*/
       g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
}