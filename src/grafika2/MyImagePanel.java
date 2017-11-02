package grafika2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyImagePanel  extends JPanel implements ActionListener, MouseListener, MouseMotionListener
{
	static Graphics2D g2d;
	
	BufferedImage image;
	
	int[] currArgs;
	
	ArrayList<Rectangle> rectArray;
	ArrayList<int[]> ovalArray;
	ArrayList<Polynomial> polynomialArray;
	ArrayList<Line2D.Double> lineArray;
	ArrayList<BufferedImage> imageList;
	
	public MyImagePanel()
	{
		super();
		setLayout(null);
		setBackground(new Color(150, 150, 100));
		
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		
		rectArray = new ArrayList<Rectangle>();
		ovalArray = new ArrayList<int[]>();
		polynomialArray = new ArrayList<Polynomial>();
		lineArray = new ArrayList<Line2D.Double>();
		imageList = new ArrayList<BufferedImage>();
		
		currArgs = new int[4];
		
		try
		{
			image = ImageIO.read(new File("cat2.bmp"));
			
		}
		catch (IOException e)
		{
			System.out.println("The image cannot be loaded");
		}
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public void setImage(BufferedImage newImg)
	{
		image = newImg;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.drawImage(image, null, 0, 0);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(DrawPanel.selectionColor);
		
		for(int i = 0; i < rectArray.size(); i++)
		{
			g2d.draw(rectArray.get(i));
		}
		
		for(int i = 0; i < ovalArray.size(); i++)
		{
			g2d.drawOval(ovalArray.get(i)[0], ovalArray.get(i)[1], 
					ovalArray.get(i)[2], ovalArray.get(i)[3]);
		}
		
		for(int i = 0; i < lineArray.size(); i++)
		{
			g2d.drawLine((int)lineArray.get(i).getX1(), (int)lineArray.get(i).getY1(), 
					(int)lineArray.get(i).getX2(), (int)lineArray.get(i).getY2());
		}
		
		for(int i = 0; i < polynomialArray.size(); i++)
		{
			polynomialArray.get(i).draw();
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		g2d.setXORMode( Color.white );
				
		if(DrawPanel.currFigure == 1)
		{
			if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
			{
				g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
			{
				g2d.drawRect(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawRect(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
			{
				g2d.drawRect(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawRect(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
			}
			else if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])
			{
				g2d.drawRect(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawRect(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
			}
		}
		if(DrawPanel.currFigure == 0)
		{
			if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
			{
				g2d.drawOval(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawOval(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
			{
				g2d.drawOval(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawOval(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
			}
			else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
			{
				g2d.drawOval(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawOval(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
			}
			else if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])
			{
				g2d.drawOval(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				g2d.drawOval(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{}
	
	ArrayList<Point> pointArray = new ArrayList<Point>();
	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{	
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		
		if(DrawPanel.currFigure == 2)
		{
			if(arg0.getButton() == MouseEvent.BUTTON1)
			{
				if(pointArray.size() > 0)
				{
					lineArray.add(new Line2D.Double(pointArray.get(pointArray.size() - 1).getX(),
							pointArray.get(pointArray.size() - 1).getY(),
							arg0.getX(),
							arg0.getY()));
				}
				pointArray.add(arg0.getPoint());
			}
			else if(arg0.getButton() == MouseEvent.BUTTON3 && pointArray.size() > 2)
			{
				ArrayList<Point> temp = new ArrayList<Point>();
				for(int i = 1; i < pointArray.size(); i++)
					temp.add(pointArray.get(i));
				try
				{
					Polynomial p = new Polynomial(pointArray.get(0), temp);
					p.draw();
					polynomialArray.add(p);
				}
				catch(Exception e)
				{
					System.out.println("Not enought points for polynomial\n");
				}
				
				ArrayList<Integer> xList = new ArrayList<Integer>();
				ArrayList<Integer> yList = new ArrayList<Integer>();
				
				for(int i = 0; i < pointArray.size(); i ++)
				{
					xList.add((int) pointArray.get(i).getX()); 
					yList.add((int) pointArray.get(i).getY()); 
				}
				
				int maxX = Collections.max(xList);
				int minX = Collections.min(xList);
				int maxY = Collections.max(yList);
				int minY = Collections.min(yList);
				int w = maxX - minX;
				int h = maxY - minY;
				
				BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				
				for(int i = 0; i < w; i++)
				{
					for(int j = 0; j < h; j++)
					{
						if(polynomialArray.get(polynomialArray.size() - 1).isInside(new Point(minX+i, minY+j)))
						{
							img.setRGB(i, j, image.getRGB(minX + i, minY + j));
						}
						else
						{
							img.setRGB(i, j, int2RGB(0, 0, 0));
						}
					}
				}
				imageList.add(img);
				DrawPanel.defList.addElement("Polynomial" + polynomialArray.size());
				pointArray.clear();
			}
		}
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		currArgs[0] = arg0.getX();
		currArgs[1] = arg0.getY();
		currArgs[2] = arg0.getX();
		currArgs[3] = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		
		if(DrawPanel.currFigure == 1)
		{
			if(currArgs[0] != currArgs[2] && currArgs[1] != currArgs[3] && 
					arg0.getX() > 0 && arg0.getY() > 0 && arg0.getX() < image.getWidth() && arg0.getY() < image.getHeight())
			{
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				
				if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
				{
					g2d.drawRect(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
					rectArray.add(new Rectangle(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]));
				}
				else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
				{
					g2d.drawRect(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
					rectArray.add(new Rectangle(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]));
				}
				else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
				{
					g2d.drawRect(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
					rectArray.add(new Rectangle(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]));
				}
				else if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])
				{
					g2d.drawRect(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
					rectArray.add(new Rectangle(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]));
				}
				
				int maxX = (int) (rectArray.get(rectArray.size() - 1).getMaxX());
				int minX = (int) (rectArray.get(rectArray.size() - 1).getMinX());
				int maxY = (int) (rectArray.get(rectArray.size() - 1).getMaxY());
				int minY = (int) (rectArray.get(rectArray.size() - 1).getMinY());
			
			
				BufferedImage img = new BufferedImage(maxX - minX, maxY - minY, BufferedImage.TYPE_INT_RGB);

				for(int i = 0; i < maxX - minX; i++)
				{
					for(int j = 0; j < maxY - minY; j++)
					{
						img.setRGB(i, j, image.getRGB(minX + i, minY + j));
					}
				}
				imageList.add(img);
				DrawPanel.defList.addElement("Rectangle" + rectArray.size());
			}
			else
			{
				System.out.println("Rectangle too small");
			}
		}
		else if(DrawPanel.currFigure == 0)
		{
			if(currArgs[0] != currArgs[2] && currArgs[1] != currArgs[3] && 
					arg0.getX() > 0 && arg0.getY() > 0 && arg0.getX() < image.getWidth() && arg0.getY() < image.getHeight())
			{
				currArgs[2] = arg0.getX();
				currArgs[3] = arg0.getY();
				
				if(arg0.getX() >= currArgs[0] && arg0.getY() >= currArgs[1])
				{
					g2d.drawOval(currArgs[0], currArgs[1], currArgs[2] - currArgs[0], currArgs[3] - currArgs[1]);
					int[] arr = new int[4];
					arr[0] = currArgs[0];
					arr[1] = currArgs[1];
					arr[2] = currArgs[2] - currArgs[0];
					arr[3] = currArgs[3] - currArgs[1];
					ovalArray.add(arr);
				}
				else if(arg0.getX() < currArgs[0] && arg0.getY() >= currArgs[1])
				{
					g2d.drawOval(currArgs[2], currArgs[1], currArgs[0] - currArgs[2], currArgs[3] - currArgs[1]);
					int[] arr = new int[4];
					arr[0] = currArgs[2];
					arr[1] = currArgs[1];
					arr[2] = currArgs[0] - currArgs[2];
					arr[3] = currArgs[3] - currArgs[1];
					ovalArray.add(arr);
				}
				else if(arg0.getX() < currArgs[0] && arg0.getY() < currArgs[1])
				{
					g2d.drawOval(currArgs[2], currArgs[3], currArgs[0] - currArgs[2], currArgs[1] - currArgs[3]);
					int[] arr = new int[4];
					arr[0] = currArgs[2];
					arr[1] = currArgs[3];
					arr[2] = currArgs[0] - currArgs[2];
					arr[3] = currArgs[1] - currArgs[3];
					ovalArray.add(arr);
				}
				else if(arg0.getX() >= currArgs[0] && arg0.getY() < currArgs[1])
				{
					g2d.drawOval(currArgs[0], currArgs[3], currArgs[2] - currArgs[0], currArgs[1] - currArgs[3]);
					int[] arr = new int[4];
					arr[0] = currArgs[0];
					arr[1] = currArgs[3];
					arr[2] = currArgs[2] - currArgs[0];
					arr[3] = currArgs[1] - currArgs[3];
					ovalArray.add(arr);
				}
				
				int x = ovalArray.get(ovalArray.size() - 1)[0];
				int y = ovalArray.get(ovalArray.size() - 1)[1];
				int w = ovalArray.get(ovalArray.size() - 1)[2];
				int h = ovalArray.get(ovalArray.size() - 1)[3];
				double midX = w/2;
				double midY = h/2;
				
				BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				
				for(int i = 0; i < w; i++)
				{
					for(int j = 0; j < h; j++)
					{
						if( (i - midX)*(i - midX) / (midX*midX) + (j - midY)*(j - midY) / (midY*midY) < 1)
						{
							img.setRGB(i, j, image.getRGB(x + i, y + j));	
						}
						else
						{
							img.setRGB(i, j, int2RGB(0, 0, 0));
						}
					}
				}
				imageList.add(img);
				DrawPanel.defList.addElement("Oval" + ovalArray.size());
			}
			else
			{
				System.out.println("Oval too small");
			}
			
			
		}
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		repaint();
	}
	
	static int int2RGB( int red, int green, int blue)
	{
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;
		return (red << 16) + (green << 8) + blue;
	}
}
