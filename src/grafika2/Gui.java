package grafika2;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gui 
{
	JFrame frame;
	public static void main(String[] args)
	{
		Gui gui = new Gui();
		gui.GUI();
	}
	
	private void GUI()
	{
		frame = new JFrame();
		DrawPanel panel = new DrawPanel();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("lab2");
		frame.setSize(800, 500);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
}

class DrawPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
	Graphics2D g2d;
	Rectangle rect;
	BufferedImage image;
	int[] currArgs = new int[4];
	
	public DrawPanel()
	{
		rect = new Rectangle(100, 100);
		image = new BufferedImage(400, 400, BufferedImage.TYPE_INT_RGB);
		
		try
		{
			image = ImageIO.read(new File("cat1.bmp"));
		}
		catch (IOException e)
		{
			System.out.println("The image cannot be loaded");
		}
		rect.setLocation(100, 100);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.draw(rect);
		g2d.drawImage(image, null, 350, 25);
		g2d.setPaintMode();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		g2d.setXORMode( Color.white );
		g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
		
		currArgs[2] = arg0.getX();
		currArgs[3] = arg0.getY();
		
		Rectangle r = new Rectangle(currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
		r.setLocation(currArgs[0], currArgs[1]);
		g2d.draw(r);
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) 
	{
		/*Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		g2d.drawOval(arg0.getX(), arg0.getY(), 70, 70);
		System.out.println("Mouse clicked at" + arg0.getX() +", "+ arg0.getY());
		Rectangle r = new Rectangle(100, 100);
		r.setLocation(arg0.getX(), arg0.getY());
		g2d.draw(r);*/
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		
		currArgs[0] = arg0.getX();
		currArgs[1] = arg0.getY();
		currArgs[2] = arg0.getX();
		currArgs[3] = arg0.getY();
		
		Rectangle r = new Rectangle();
		r.setLocation(currArgs[0], currArgs[1]);
		g2d.draw(r);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		currArgs[2] = arg0.getX() - currArgs[0];
		currArgs[3] = arg0.getY() - currArgs[1];
		
		Rectangle r = new Rectangle(currArgs[2], currArgs[3]);
		r.setLocation(currArgs[0], currArgs[1]);
		g2d.draw(r);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}
}
