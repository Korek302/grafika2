package grafika2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
	static Color selectionColor;
	static int currFigure;
	
	JPanel buttonPanel;
	BufferedImage currImage;
	
	MyImagePanel imagePanel;

	JButton rectButton;
	JButton ovalButton;
	JButton polynomialButton;
	JButton resetButton;
	JButton saveButton;
	JButton deleteButton;
	JButton colorButton;
	
	JList<String> list;
	static DefaultListModel<String> defList;
	JSplitPane splitPane;
	
	public DrawPanel()
	{
		super();
		setLayout(new GridBagLayout());
		setBackground(new Color(150, 150, 100));
		GridBagConstraints gridConstraints = new GridBagConstraints();
		
		gridConstraints.insets = new Insets(1,1,1,1);
		gridConstraints.anchor = GridBagConstraints.CENTER;
		gridConstraints.fill = GridBagConstraints.BOTH;

		
		imagePanel = new MyImagePanel();
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(2, 3));
		currImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		
		defList = new DefaultListModel<>();
		list = new JList<>(defList);
		splitPane = new JSplitPane();
		
		/*JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setMinimumSize(new Dimension(100, 100));
		add(scrollPane, BorderLayout.EAST);
		scrollPane.setVisible(true);
		list.setCellRenderer(new Renderer());*/
		
		rectButton = new JButton("Rectangle");
		ovalButton = new JButton("Oval");
		polynomialButton = new JButton("Polynomial");
		resetButton = new JButton("Reset");
		saveButton = new JButton("Save");
		deleteButton = new JButton("Delete");
		colorButton = new JButton("Color");
		
		currFigure = 1;
		
		/*rectButton.setBounds(20, 360, 100, 30);
		ovalButton.setBounds(20, 390, 100, 30);
		polynomialButton.setBounds(20, 420, 100, 30);
		resetButton.setBounds(190, 420, 100, 30);
		saveButton.setBounds(190, 390, 100, 30);
		deleteButton.setBounds(190, 360, 100, 30);
		colorButton.setBounds(190, 310, 100, 30);*/
		
		
		
		buttonPanel.add(rectButton);
		buttonPanel.add(ovalButton);
		buttonPanel.add(polynomialButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(deleteButton);
		add(colorButton);
		
		
		rectButton.addActionListener(this);
		ovalButton.addActionListener(this);
		polynomialButton.addActionListener(this);
		resetButton.addActionListener(this);
		saveButton.addActionListener(this);
		deleteButton.addActionListener(this);
		colorButton.addActionListener(this);
		
		/*Dimension minimumSizeList = new Dimension(100, 50);
		list.setMinimumSize(minimumSizeList);*/
		
		splitPane.setResizeWeight(1.0);
		list.setModel(defList);
		splitPane.setLeftComponent(new JScrollPane(list));
		splitPane.setRightComponent(new JScrollPane(new ImagePanel(imagePanel.getImage())));
		splitPane.setDividerLocation(100);
		
		//splitPane.setBounds(20, 20, 250, 200);
		
		list.addListSelectionListener(this);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setVisibleRowCount(5);
		
		
		gridConstraints.gridwidth = 5;
		gridConstraints.gridheight = 3;
		gridConstraints.gridx = 5;
		gridConstraints.gridy = 5;
		gridConstraints.weightx = 10;
		gridConstraints.weighty = 10;
		add(splitPane, gridConstraints);
		
		gridConstraints.gridwidth = 3;
		gridConstraints.gridheight = 1;
		gridConstraints.gridx = 4;
		gridConstraints.gridy = 10;
		gridConstraints.weightx = 5;
		gridConstraints.weighty = 5;
		gridConstraints.fill = GridBagConstraints.NONE;
		add(buttonPanel, gridConstraints);
		
		gridConstraints.gridy = 11;
		add(colorButton, gridConstraints);
		
		gridConstraints.gridwidth = 15;
		gridConstraints.gridheight = 15;
		gridConstraints.gridx = 20;
		gridConstraints.gridy = 7;
		gridConstraints.weightx = 80;
		gridConstraints.weighty = 80;
		gridConstraints.fill = GridBagConstraints.BOTH;
		add(imagePanel, gridConstraints);
		
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g2d = (Graphics2D) g;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) 
	{}

	@Override
	public void mouseMoved(MouseEvent arg0) 
	{}

	
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{}

	@Override
	public void mouseEntered(MouseEvent arg0) 
	{}

	@Override
	public void mouseExited(MouseEvent arg0) 
	{}

	@Override
	public void mousePressed(MouseEvent arg0) 
	{}

	@Override
	public void mouseReleased(MouseEvent arg0) 
	{}

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
			try
			{
				imagePanel.setImage(ImageIO.read(new File("cat2.bmp")));
			}
			catch (IOException ex)
			{
				System.out.println("The image cannot be loaded");
			}
			imagePanel.rectArray.clear();
			imagePanel.ovalArray.clear();
			imagePanel.polynomialArray.clear();
			imagePanel.lineArray.clear();
			imagePanel.imageList.clear();
			defList.clear();
			currImage = imagePanel.image;
			splitPane.setRightComponent(new ImagePanel(currImage));
			splitPane.setDividerLocation(100);
			//imagePanel.repaint();
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
			catch(IndexOutOfBoundsException ex)
			{
				System.out.println("No image picked");
			}
		}
		if(source == deleteButton)
		{
			String listElem = list.getSelectedValue();
			try
			{
				if(listElem.startsWith("Rectangle"))
				{
					int index = Integer.parseInt(listElem.substring(9, listElem.length()));
					index--;
					imagePanel.rectArray.get(index);
					
					int maxX = (int) (imagePanel.rectArray.get(index).getMaxX());
					int minX = (int) (imagePanel.rectArray.get(index).getMinX());
					int maxY = (int) (imagePanel.rectArray.get(index).getMaxY());
					int minY = (int) (imagePanel.rectArray.get(index).getMinY());
					
					for(int i = 0; i < maxX - minX; i++)
					{
						for(int j = 0; j < maxY - minY; j++)
						{
							imagePanel.image.setRGB(minX + i, minY + j, int2RGB(0, 0, 0));
						}
					}
				}
				else if(listElem.startsWith("Polynomial"))
				{
					int index = Integer.parseInt(listElem.substring(10, listElem.length()));
					index--;
					ArrayList<Point> tempPointArray = imagePanel.polynomialArray.get(index).getPoints();
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
							if(imagePanel.polynomialArray.get(imagePanel.polynomialArray.size() 
									- 1).isInside(new Point(minX+i, minY+j)))
							{
								imagePanel.image.setRGB(minX + i, minY + j, int2RGB(0, 0, 0));
							}
						}
					}
				}
				else if(listElem.startsWith("Oval"))
				{
					int index = Integer.parseInt(listElem.substring(4, listElem.length()));
					index--;
					
					int x = imagePanel.ovalArray.get(index)[0];
					int y = imagePanel.ovalArray.get(index)[1];
					int w = imagePanel.ovalArray.get(index)[2];
					int h = imagePanel.ovalArray.get(index)[3];
					double midX = w/2;
					double midY = h/2;
					
					for(int i = 0; i < w; i++)
					{
						for(int j = 0; j < h; j++)
						{
							if( (i - midX)*(i - midX) / (midX*midX) + (j - midY)*(j - midY) / (midY*midY) < 1)
							{
								imagePanel.image.setRGB(x + i, y + j, int2RGB(0, 0, 0));	
							}
						}
					}
				}
				imagePanel.imageList.remove(list.getSelectedIndex());
				defList.remove(list.getSelectedIndex());
				currImage = imagePanel.image;
				splitPane.setRightComponent(new ImagePanel(currImage));
			}
			catch(Exception ex)
			{
				System.out.println("No image picked");
			}
		}
		else if(source == colorButton)
		{
			selectionColor = JColorChooser.showDialog(null, "Pick your color", selectionColor);
			if(selectionColor == null)
			{
				selectionColor = Color.LIGHT_GRAY;
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
				currImage = imagePanel.imageList.get(list.getSelectedIndex());
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