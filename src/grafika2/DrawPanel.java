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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
class DrawPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, ListSelectionListener
{
	static Graphics2D g2d;
	
	BufferedImage image;
	BufferedImage currImage;
	
	Color selectionColor;
	
	int[] currArgs;
	int currFigure;
	
	JButton rectButton;
	JButton ovalButton;
	JButton polynomialButton;
	JButton resetButton;
	JButton saveButton;
	JButton deleteButton;
	JButton colorButton;
	
	JList<String> list;
	DefaultListModel<String> defList;
	JSplitPane splitPane;
	
	ArrayList<Rectangle> rectArray;
	ArrayList<int[]> ovalArray;
	ArrayList<Polynomial> polynomialArray;
	ArrayList<Line2D.Double> lineArray;
	ArrayList<BufferedImage> imageList;
	
	public DrawPanel()
	{
		super();
		setLayout(null);
		setBackground(new Color(150, 150, 100));
		
		image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
		currImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		
		defList = new DefaultListModel<String>();
		list = new JList<String>(defList);
		splitPane = new JSplitPane();
		
		rectButton = new JButton("Rectangle");
		ovalButton = new JButton("Oval");
		polynomialButton = new JButton("Polynomial");
		resetButton = new JButton("Reset");
		saveButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		colorButton = new JButton("Color");
		
		rectArray = new ArrayList<Rectangle>();
		ovalArray = new ArrayList<int[]>();
		polynomialArray = new ArrayList<Polynomial>();
		lineArray = new ArrayList<Line2D.Double>();
		imageList = new ArrayList<BufferedImage>();
		
		currArgs = new int[4];
		currFigure = 1;
		
		rectButton.setBounds(20, 360, 100, 30);
		ovalButton.setBounds(20, 390, 100, 30);
		polynomialButton.setBounds(20, 420, 100, 30);
		resetButton.setBounds(190, 420, 100, 30);
		saveButton.setBounds(190, 390, 100, 30);
		deleteButton.setBounds(190, 360, 100, 30);
		colorButton.setBounds(190, 310, 100, 30);
		
		try
		{
			image = ImageIO.read(new File("cat2.bmp"));
		}
		catch (IOException e)
		{
			System.out.println("The image cannot be loaded");
		}
		
		add(rectButton);
		add(ovalButton);
		add(polynomialButton);
		add(resetButton);
		add(saveButton);
		add(deleteButton);
		add(colorButton);
		
		rectButton.addActionListener(this);
		ovalButton.addActionListener(this);
		polynomialButton.addActionListener(this);
		resetButton.addActionListener(this);
		saveButton.addActionListener(this);
		deleteButton.addActionListener(this);
		colorButton.addActionListener(this);
		
		list.setModel(defList);
		splitPane.setLeftComponent(new JScrollPane(list));
		splitPane.setRightComponent(new ImagePanel(image));
		splitPane.setDividerLocation(100);
		
		splitPane.setBounds(20, 20, 250, 200);
		list.addListSelectionListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(5);
		add(splitPane);
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.drawImage(image, null, 300, 0);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(selectionColor);
		
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
		
		if(arg0.getX() > 300 && arg0.getX() < 800 && arg0.getY() > 0 && arg0.getY() < 500)
		{
			if(currFigure == 1)
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
			if(currFigure == 0)
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
		
		if(arg0.getX() > 300 && arg0.getX() < 800 && arg0.getY() > 0 && arg0.getY() < 500)
		{
			if(currFigure == 2)
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
				else
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
					
					defList.addElement("Polynomial" + polynomialArray.size());
					
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
								img.setRGB(i, j, image.getRGB(minX + i - 300, minY + j));
							}
							else
							{
								img.setRGB(i, j, int2RGB(0, 0, 0));
							}
						}
					}
					imageList.add(img);
					
					pointArray.clear();
				}
			}
			repaint();
		}
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
		
		if(arg0.getX() > 300 && arg0.getX() < 800 && arg0.getY() > 0 && arg0.getY() < 500)
		{
			currArgs[0] = arg0.getX();
			currArgs[1] = arg0.getY();
			currArgs[2] = arg0.getX();
			currArgs[3] = arg0.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		Graphics g = getGraphics();
		g2d = (Graphics2D) g;
		
		if(arg0.getX() > 300 && arg0.getX() < 800 && arg0.getY() > 0 && arg0.getY() < 500)
		{
		
			if(currFigure == 1)
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
				
				defList.addElement("Rectangle" + rectArray.size());
				
				int maxX = (int) (rectArray.get(rectArray.size() - 1).getMaxX());
				int minX = (int) (rectArray.get(rectArray.size() - 1).getMinX());
				int maxY = (int) (rectArray.get(rectArray.size() - 1).getMaxY());
				int minY = (int) (rectArray.get(rectArray.size() - 1).getMinY());
				
				BufferedImage img = new BufferedImage(maxX - minX, maxY - minY, BufferedImage.TYPE_INT_RGB);
				
				for(int i = 0; i < maxX - minX; i++)
				{
					for(int j = 0; j < maxY - minY; j++)
					{
						img.setRGB(i, j, image.getRGB(minX + i - 300, minY + j));
					}
				}
				imageList.add(img);
			}
			else if(currFigure == 0)
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
				
				defList.addElement("Oval" + ovalArray.size());
				
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
							img.setRGB(i, j, image.getRGB(x + i - 300, y + j));	
						}
						else
						{
							img.setRGB(i, j, int2RGB(0, 0, 0));
						}
					}
				}
				imageList.add(img);
			}
			
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object source = e.getSource();
		if(source == ovalButton)
			currFigure = 0;
		if(source == rectButton)
			currFigure = 1;
		if(source == polynomialButton)
			currFigure = 2;
		if(source == resetButton)
		{
			rectArray.clear();;
			ovalArray.clear();;
			polynomialArray.clear();;
			lineArray.clear();;
			imageList.clear();;
			defList.clear();
			currImage = image;
			splitPane.setRightComponent(new ImagePanel(image));
			splitPane.setDividerLocation(100);
		}
		if(source == saveButton)
		{
			try
			{
				ImageIO.write(currImage, "bmp", new File(defList.get(list.getSelectedIndex()) + ".bmp"));
				System.out.println("Image created successfully");
			}
			catch(IOException ex)
			{
				System.out.println("The image cannot be stored");
			}
		}
		if(source == deleteButton)
		{
			String listElem = list.getSelectedValue();
		
			if(listElem.startsWith("Rectangle"))
			{
				int index = Integer.parseInt(listElem.substring(9, listElem.length()));
				index--;
				rectArray.get(index);
				
				int maxX = (int) (rectArray.get(index).getMaxX());
				int minX = (int) (rectArray.get(index).getMinX());
				int maxY = (int) (rectArray.get(index).getMaxY());
				int minY = (int) (rectArray.get(index).getMinY());
				
				for(int i = 0; i < maxX - minX; i++)
				{
					for(int j = 0; j < maxY - minY; j++)
					{
						image.setRGB(minX + i - 300, minY + j, int2RGB(0, 0, 0));
					}
				}
			}
			else if(listElem.startsWith("Polynomial"))
			{
				int index = Integer.parseInt(listElem.substring(10, listElem.length()));
				index--;
				ArrayList<Point> tempPointArray = polynomialArray.get(index).getPoints();
				ArrayList<Integer> xList = new ArrayList<Integer>();
				ArrayList<Integer> yList = new ArrayList<Integer>();
				
				for(int i = 0; i < tempPointArray.size(); i ++)
				{
					xList.add((int) tempPointArray.get(i).getX()); 
					yList.add((int) tempPointArray.get(i).getY()); 
				}
				
				int maxX = Collections.max(xList);
				int minX = Collections.min(xList);
				int maxY = Collections.max(yList);
				int minY = Collections.min(yList);
				int w = maxX - minX;
				int h = maxY - minY;
				
				for(int i = 0; i < w; i++)
				{
					for(int j = 0; j < h; j++)
					{
						if(polynomialArray.get(polynomialArray.size() - 1).isInside(new Point(minX+i, minY+j)))
						{
							image.setRGB(minX + i - 300, minY + j, int2RGB(0, 0, 0));
						}
					}
				}
			}
			else if(listElem.startsWith("Oval"))
			{
				int index = Integer.parseInt(listElem.substring(4, listElem.length()));
				index--;
				
				int x = ovalArray.get(index)[0];
				int y = ovalArray.get(index)[1];
				int w = ovalArray.get(index)[2];
				int h = ovalArray.get(index)[3];
				double midX = w/2;
				double midY = h/2;
				
				for(int i = 0; i < w; i++)
				{
					for(int j = 0; j < h; j++)
					{
						if( (i - midX)*(i - midX) / (midX*midX) + (j - midY)*(j - midY) / (midY*midY) < 1)
						{
							image.setRGB(x + i - 300, y + j, int2RGB(0, 0, 0));	
						}
					}
				}
			}
			imageList.remove(list.getSelectedIndex());
			defList.remove(list.getSelectedIndex());
			currImage = image;
			splitPane.setRightComponent(new ImagePanel(currImage));
		}
		else if(source == colorButton)
		{
			selectionColor = JColorChooser.showDialog(null, "Pick your color", selectionColor);
			if(selectionColor == null)
			{
				selectionColor = Color.GRAY;
			}
		}
		repaint();
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{
		try
		{
			if(list.getSelectedIndex() != -1)
				currImage = imageList.get(list.getSelectedIndex());
			splitPane.setRightComponent(new ImagePanel(currImage));
			splitPane.setDividerLocation(100);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.print("Index out of bounds: valueChanged");
		}
	}
	
	static int int2RGB( int red, int green, int blue)
	{
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;
		return (red << 16) + (green << 8) + blue;
	}
}